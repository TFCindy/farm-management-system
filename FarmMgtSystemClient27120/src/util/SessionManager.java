package util;

import model.User;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

/**
 * SessionManager is a thread-safe singleton that manages the logged-in user
 * session.
 *
 * Responsibilities:
 * - Track the current logged-in {@link model.User} (primary key: nationalId).
 * - Provide login / logout methods.
 * - Persist last logged-in nationalId (optional helper) using a small file (avoids Windows registry access).
 * - Allow UI components to register listeners for session and data-change
 * events so
 * dashboards and tables can update in response to new data or logout.
 */
public class SessionManager {
    private static volatile SessionManager instance;

    private volatile User currentUser;
    private volatile long loginTime;

    private final List<SessionListener> sessionListeners = new CopyOnWriteArrayList<>();
    private final List<DataChangeListener> dataChangeListeners = new CopyOnWriteArrayList<>();

    // File used to optionally persist last logged-in id (avoids Windows registry access)
    private static final Path LAST_NID_FILE = Paths.get(System.getProperty("user.home"), ".farmmgt_last_nid");

    private SessionManager() {
        // no-op
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Start a session for the provided user. The user's nationalId is treated as
     * the primary key.
     * Notifies registered session listeners.
     */
    public void startSession(User user) {
        if (user == null)
            return;
        this.currentUser = user;
        this.loginTime = System.currentTimeMillis();
        // Diagnostic log: record session start and user identity
        try { System.out.println("SessionManager.startSession: user=" + user); } catch (Exception ignore) {}
        // persist last logged id (helper only) — write to file (best-effort)
        try {
            Files.write(LAST_NID_FILE, user.getNationalId().getBytes(StandardCharsets.UTF_8));
        } catch (Exception ignored) {
        }
        // notify listeners
        for (SessionListener l : sessionListeners) {
            try {
                l.onLogin(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** End current session and notify listeners. */
    public void endSession() {
        User old = this.currentUser;
        this.currentUser = null;
        this.loginTime = 0;
        try {
            Files.deleteIfExists(LAST_NID_FILE);
        } catch (Exception ignored) {
        }
        for (SessionListener l : sessionListeners) {
            try {
                l.onLogout(old);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUserId() {
        return currentUser != null ? currentUser.getNationalId() : null;
    }

    public long getLoginTime() {
        return loginTime;
    }

    // --- Session listeners ---
    public void addSessionListener(SessionListener l) {
        if (l != null)
            sessionListeners.add(l);
    }

    public void removeSessionListener(SessionListener l) {
        if (l != null)
            sessionListeners.remove(l);
    }

    // --- Data change listeners ---
    /**
     * Register listener to be notified when data changes (e.g.,
     * crop/livestock/employee added).
     */
    public void addDataChangeListener(DataChangeListener l) {
        if (l != null)
            dataChangeListeners.add(l);
    }

    public void removeDataChangeListener(DataChangeListener l) {
        if (l != null)
            dataChangeListeners.remove(l);
    }

    /**
     * Notify registered listeners that data of `entityType` changed for the current
     * user.
     * entityType examples: "crop", "livestock", "employee".
     * This should be called by DAO/UI code after a successful add/update/delete so
     * the dashboard
     * cards and recent-tables can update themselves.
     */
    public void notifyDataChanged(String entityType) {
        for (DataChangeListener l : dataChangeListeners) {
            try {
                l.onDataChanged(entityType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // --- Persistence helpers ---
    /**
     * Return last persisted national id (may be null). This is a helper only — it
     * does not auto-login
     * because reconstructing a full User requires a DAO lookup.
     */
    public String getLastPersistedNationalId() {
        try {
            if (Files.exists(LAST_NID_FILE)) {
                String s = new String(Files.readAllBytes(LAST_NID_FILE), StandardCharsets.UTF_8).trim();
                return s.isEmpty() ? null : s;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // Listener interfaces
    public interface SessionListener {
        void onLogin(User user);

        void onLogout(User previousUser);
    }

    public interface DataChangeListener {
        /**
         * Called when data of given entityType changed. Implementations should refresh
         * counts/tables.
         */
        void onDataChanged(String entityType);
    }
}