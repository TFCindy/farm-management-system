package notification;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OtpService {
    private static final OtpService INSTANCE = new OtpService();
    private static final SecureRandom rnd = new SecureRandom();
    private static final int EXPIRY_MILLIS = 5 * 60 * 1000; // 5 minutes

    private static class OtpEntry {
        final String otp;
        final long expiresAt;
        OtpEntry(String otp, long expiresAt) {
            this.otp = otp; this.expiresAt = expiresAt;
        }
    }

    private final Map<String, OtpEntry> map = new ConcurrentHashMap<>();

    private OtpService() {}

    public static OtpService getInstance() { return INSTANCE; }

    public String generateOtp(String email) {
        String otp = String.format("%06d", rnd.nextInt(1_000_000));
        long expiresAt = System.currentTimeMillis() + EXPIRY_MILLIS;
        map.put(email, new OtpEntry(otp, expiresAt));
        return otp;
    }

    public boolean verifyOtp(String email, String submittedOtp) {
        OtpEntry e = map.get(email);
        if (e == null) return false;
        if (System.currentTimeMillis() > e.expiresAt) { map.remove(email); return false; }
        boolean ok = e.otp.equals(submittedOtp);
        if (ok) map.remove(email); // consume OTP after successful verification
        return ok;
    }
}
