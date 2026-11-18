/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.CropController;
import controller.EmployeeController;
import controller.LivestockController;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Crop;
import model.Employee;
import model.Livestock;
import model.User;
import util.SessionManager;

/**
 *
 * @author Munezero Cindyyy
 */
public class DashboardFrame extends BaseForm
        implements SessionManager.SessionListener, SessionManager.DataChangeListener {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(DashboardFrame.class.getName());

    /**
     * Creates new form DashboardFrame
     */
    public DashboardFrame() {
        initComponents();

        // Check if user is logged in
        SessionManager session = SessionManager.getInstance();
        if (!session.isLoggedIn()) {
            showDualLanguageError("Please log in first.");
            this.dispose();
            new LoginForm().setVisible(true);
            return;
        }
        
        User currentUser = session.getCurrentUser();

        if (currentUser != null) {
            applyWelcomeTitleTranslation(jLabel1, "Welcome", currentUser.getFirstname() + " " + currentUser.getLastname());
        } else {
            jLabel1.setText("Welcome, Guest!");
        }

        // Register as listener for session and data changes
        session.addSessionListener(this);
        session.addDataChangeListener(this);

        addCardListeners();
        loadDashboardData();
        addHoverEffect(cardCrops);
        addHoverEffect(cardLivestock);
        addHoverEffect(cardEmployees);
        loadRecentCropsTable();
        loadRecentLivestockTable();
        initDashboardActions();
        // setupLogoutButton(); // REMOVED - duplicate confirmation handled in btnlogoutActionPerformed
        applyFormTranslations();
    }
    
    private void applyFormTranslations() {
        applyButtonTranslation(btnDashBoard, "Dashboard");
        applyButtonTranslation(btnCrop, "Crops");
        applyButtonTranslation(btnLivestock, "Livestock");
        applyButtonTranslation(btnEmployees, "Employees");
        applyLabelTranslation(jLabel10, "Recently added livestock and crops");
        applyButtonTranslation(btnNotifications, "Notifications");
        applyButtonTranslation(btnlogout, "Logout");
        applyButtonTranslation(btnAddCrop, "Add Crop");
        applyButtonTranslation(btnAddLivestock, "Add Livestock");
        applyButtonTranslation(btnAddEmployee, "Add Employee");
        applyButtonTranslation(btnReport, "View Report");
        // Card labels
        
        applyTitleTranslation(jLabel2, "Total Crops");
        applyLabelTranslation(jLabel4, "Total Livestock");
        applyLabelTranslation(jLabel8, "Total Employees");
    }

    // Logout button setup - REMOVED duplicate confirmation (handled in btnlogoutActionPerformed)

    // SessionListener implementation
    @Override
    public void onLogin(User user) {
        // Called when user logs in — this frame is shown for logged-in user
        jLabel1.setText("Welcome, " + user.getFirstname() + " " + user.getLastname() + "!");
        loadDashboardData();
        loadRecentCropsTable();
        loadRecentLivestockTable();
    }

    @Override
    public void onLogout(User previousUser) {
        // Called when user logs out — close this frame and return to login
        this.dispose();
    }

    // DataChangeListener implementation
    @Override
    public void onDataChanged(String entityType) {
        // Called when data of a given entity type changes
        if ("crop".equals(entityType)) {
            refreshCropCard();
            refreshRecentCropsTable();
        } else if ("livestock".equals(entityType)) {
            refreshLivestockCard();
            refreshRecentLivestockTable();
        } else if ("employee".equals(entityType)) {
            refreshEmployeeCard();
        }
    }

    private void refreshCropCard() {
        try {
            SessionManager session = SessionManager.getInstance();
            String nationalId = session.getCurrentUserId();
            if (nationalId != null) {
                CropController cropController = new CropController();
                Long count = cropController.countCropsByUser(nationalId);
                jLabel3.setText(String.valueOf(count != null ? count : 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshLivestockCard() {
        try {
            SessionManager session = SessionManager.getInstance();
            String nationalId = session.getCurrentUserId();
            if (nationalId != null) {
                LivestockController livestockController = new LivestockController();
                Long count = livestockController.countLivestockByUser(nationalId);
                jLabel5.setText(String.valueOf(count != null ? count : 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshEmployeeCard() {
        try {
            SessionManager session = SessionManager.getInstance();
            String nationalId = session.getCurrentUserId();
            if (nationalId != null) {
                EmployeeController employeeController = new EmployeeController();
                Long count = employeeController.countEmployeesByUser(nationalId);
                jLabel9.setText(String.valueOf(count != null ? count : 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshRecentCropsTable() {
        loadRecentCropsTable();
    }

    private void refreshRecentLivestockTable() {
        loadRecentLivestockTable();
    }

    // Initialize quick-add button listeners
    private void initDashboardActions() {
        // Quick Add Crop button
        btnAddCrop.addActionListener(e -> {
            AddCropDialog addCropDialog = new AddCropDialog(this, true);
            addCropDialog.setVisible(true);
            // Reload recent crops table after adding
            loadRecentCropsTable();
        });

        // Quick Add Livestock button
        btnAddLivestock.addActionListener(e -> {
            AddLivestockDialog addLivestockDialog = new AddLivestockDialog(this, true);
            addLivestockDialog.setVisible(true);
            loadRecentLivestockTable();
        });

        // Quick Add Employee button
        btnAddEmployee.addActionListener(e -> {
            AddEmployeeDialog addEmployeeDialog = new AddEmployeeDialog(this, true);
            addEmployeeDialog.setVisible(true);
            loadDashboardData(); // Refresh the entire dashboard
        });
    }
    
    private void addCardListeners() {
        // Make sure cardCrops is declared as a JPanel (or JButton) in your GUI
        cardCrops.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new CropForm().setVisible(true);
                dispose();
            }
        });

        cardLivestock.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LivestockForm().setVisible(true);
                dispose();
            }
        });


        cardEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new EmployeeForm ().setVisible(true);
                dispose();
            }
        });
    }

    private void addHoverEffect(JPanel panel) {
    Color original = panel.getBackground();
    Color hover = new Color(200, 230, 255); // light blue

    panel.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            panel.setBackground(hover);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            panel.setBackground(original);
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // navigate to corresponding frame
        }
    });
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        mainContentPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        cardEmployees = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cardLivestock = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cardCrops = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAddCrop = new javax.swing.JButton();
        btnAddLivestock = new javax.swing.JButton();
        btnAddEmployee = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRecentLivestock = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRecentCrops = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnDashBoard = new javax.swing.JButton();
        btnCrop = new javax.swing.JButton();
        btnLivestock = new javax.swing.JButton();
        btnEmployees = new javax.swing.JButton();
        btnNotifications = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnlogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainContentPanel.setMaximumSize(new java.awt.Dimension(1000, 100));
        mainContentPanel.setPreferredSize(new java.awt.Dimension(800, 700));

        jPanel6.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel6.setRequestFocusEnabled(false);
        jPanel6.setLayout(new java.awt.GridLayout(1, 1));

        cardEmployees.setBackground(new java.awt.Color(204, 255, 204));
        cardEmployees.setPreferredSize(new java.awt.Dimension(32, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel8.setText(" Total Employees");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel9.setText("0");

        javax.swing.GroupLayout cardEmployeesLayout = new javax.swing.GroupLayout(cardEmployees);
        cardEmployees.setLayout(cardEmployeesLayout);
        cardEmployeesLayout.setHorizontalGroup(
            cardEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
            .addGroup(cardEmployeesLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardEmployeesLayout.setVerticalGroup(
            cardEmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardEmployeesLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(88, 88, 88))
        );

        jPanel6.add(cardEmployees);

        cardLivestock.setBackground(new java.awt.Color(153, 255, 153));
        cardLivestock.setPreferredSize(new java.awt.Dimension(32, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel4.setText("Total Livestock");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel5.setText("0");

        javax.swing.GroupLayout cardLivestockLayout = new javax.swing.GroupLayout(cardLivestock);
        cardLivestock.setLayout(cardLivestockLayout);
        cardLivestockLayout.setHorizontalGroup(
            cardLivestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardLivestockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(cardLivestockLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardLivestockLayout.setVerticalGroup(
            cardLivestockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardLivestockLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jPanel6.add(cardLivestock);

        cardCrops.setBackground(new java.awt.Color(204, 255, 204));
        cardCrops.setPreferredSize(new java.awt.Dimension(32, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel2.setText("   Total Crops");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel3.setText("0");

        javax.swing.GroupLayout cardCropsLayout = new javax.swing.GroupLayout(cardCrops);
        cardCrops.setLayout(cardCropsLayout);
        cardCropsLayout.setHorizontalGroup(
            cardCropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardCropsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(cardCropsLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardCropsLayout.setVerticalGroup(
            cardCropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardCropsLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 86, Short.MAX_VALUE))
        );

        jPanel6.add(cardCrops);

        jPanel4.setMaximumSize(new java.awt.Dimension(60, 40));
        jPanel4.setMinimumSize(new java.awt.Dimension(60, 40));
        jPanel4.setPreferredSize(new java.awt.Dimension(60, 40));

        btnAddCrop.setText("Add Crop");
        btnAddCrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCropActionPerformed(evt);
            }
        });

        btnAddLivestock.setText("Add Livestock");
        btnAddLivestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLivestockActionPerformed(evt);
            }
        });

        btnAddEmployee.setText("Add Employee");
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnAddCrop, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddLivestock, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCrop, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddLivestock, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(4, Short.MAX_VALUE))
        );

        tableRecentLivestock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Animal Type  ", "Age", "Breed", " Health Status", "Quantity"
            }
        ));
        jScrollPane2.setViewportView(tableRecentLivestock);

        tableRecentCrops.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Crop Name   ", "Planting Date ", "Harvest Date", "Expecter yield", "Status"
            }
        ));
        jScrollPane1.setViewportView(tableRecentCrops);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 63));

        jLabel10.setBackground(new java.awt.Color(153, 204, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("                       Recently added livestock and crops");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        btnDashBoard.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDashBoard.setText("Dashboard");
        btnDashBoard.setPreferredSize(new java.awt.Dimension(100, 56));
        btnDashBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashBoardActionPerformed(evt);
            }
        });

        btnCrop.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCrop.setText("Crops");
        btnCrop.setPreferredSize(new java.awt.Dimension(100, 48));
        btnCrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCropActionPerformed(evt);
            }
        });

        btnLivestock.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLivestock.setText("Livestock");
        btnLivestock.setPreferredSize(new java.awt.Dimension(100, 48));
        btnLivestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLivestockActionPerformed(evt);
            }
        });

        btnEmployees.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnEmployees.setText("Employees");
        btnEmployees.setPreferredSize(new java.awt.Dimension(100, 56));
        btnEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeesActionPerformed(evt);
            }
        });

        btnNotifications.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        btnNotifications.setText("Notifications");
        btnNotifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotificationsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCrop, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLivestock, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNotifications)
                .addGap(224, 224, 224))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLivestock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(7, 7, 7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnNotifications, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                .addComponent(btnEmployees, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pipoooo.jpg"))); // NOI18N
        jLabel12.setText("jLabel12");

        btnlogout.setBackground(new java.awt.Color(204, 204, 204));
        btnlogout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnlogout.setText("Logout");
        btnlogout.setPreferredSize(new java.awt.Dimension(72, 23));
        btnlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Welcome,[User]");

        btnReport.setText("View Report");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainContentPanelLayout = new javax.swing.GroupLayout(mainContentPanel);
        mainContentPanel.setLayout(mainContentPanelLayout);
        mainContentPanelLayout.setHorizontalGroup(
            mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentPanelLayout.createSequentialGroup()
                .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainContentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainContentPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReport)
                                .addGap(18, 18, 18)
                                .addComponent(btnlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(212, 212, 212))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainContentPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(0, 2, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContentPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(403, 403, 403))
            .addGroup(mainContentPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(mainContentPanelLayout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainContentPanelLayout.createSequentialGroup()
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 1002, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        mainContentPanelLayout.setVerticalGroup(
            mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainContentPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnlogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReport)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
            .addGroup(mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContentPanelLayout.createSequentialGroup()
                    .addContainerGap(124, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(414, Short.MAX_VALUE)))
        );

        jPanel6.getAccessibleContext().setAccessibleName("");
        jPanel6.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(mainContentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNotificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificationsActionPerformed
        NotificationsDialog dialog = new NotificationsDialog(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNotificationsActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        ReportDialog reportDialog = new ReportDialog(this, true);
        reportDialog.setVisible(true);
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnDashBoardActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, 
        "You are already on the Dashboard!", 
        "Dashboard", 
        JOptionPane.INFORMATION_MESSAGE);
    }// GEN-LAST:event_btnDashBoardActionPerformed

    private void btnSalesActionPerformed(java.awt.event.ActionEvent evt) {                                         

    }// GEN-LAST:event_btnSalesActionPerformed

    private void btnLivestockActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        new LivestockForm().setVisible(true);
    this.dispose();
    }// GEN-LAST:event_btnLivestockActionPerformed

    private void btnCropActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        new CropForm().setVisible(true);
    this.dispose(); 
    }// GEN-LAST:event_btnCropActionPerformed

    private void btnEmployeesActionPerformed(java.awt.event.ActionEvent evt) {                                             
         // TODO add your handling code here:
         this.dispose(); // Close current dashboard
    new EmployeeForm().setVisible(true);
    }// GEN-LAST:event_btnEmployeesActionPerformed

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.getInstance().endSession();
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }// GEN-LAST:event_btnlogoutActionPerformed
    private AddCropDialog addCropDialog = null; // Class-level variable
    private void btnAddCropActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        // TODO add your handling code here:
    AddCropDialog addCropDialog = new AddCropDialog(this, true);
    addCropDialog.setLocationRelativeTo(this);
    addCropDialog.setVisible(true);
    
    // Refresh the dashboard after dialog closes
    loadDashboardData();
    loadRecentCropsTable();
        
    }// GEN-LAST:event_btnAddCropActionPerformed

    private void btnAddLivestockActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        AddLivestockDialog addLivestockDialog = new AddLivestockDialog(this, true);
    addLivestockDialog.setLocationRelativeTo(this);
    addLivestockDialog.setVisible(true);
    
    // Refresh the dashboard after dialog closes
    loadDashboardData();
    loadRecentLivestockTable();
    }// GEN-LAST:event_btnAddLivestockActionPerformed

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        AddEmployeeDialog addEmployeeDialog = new AddEmployeeDialog(this, true);
    addEmployeeDialog.setLocationRelativeTo(this);
    addEmployeeDialog.setVisible(true);
    
    // Refresh the dashboard after dialog closes
    loadDashboardData();
    }// GEN-LAST:event_btnAddEmployeeActionPerformed

public void loadRecentCropsTable() {
    try {
        SessionManager session = SessionManager.getInstance();
        String nationalId = session.getCurrentUserId();
        
        if (nationalId == null) {
            return; // User not logged in
        }
        
        CropController cropController = new CropController();
        List<Crop> allCrops = cropController.findCropsByUser(nationalId);
        // Get recent 5 crops (already sorted DESC by service)
        List<Crop> crops = allCrops.size() > 5 ? allCrops.subList(0, 5) : allCrops;

        DefaultTableModel model = (DefaultTableModel) tableRecentCrops.getModel();
        model.setRowCount(0); // clear existing rows

        // Add rows (already sorted DESC by DAO)
        crops.forEach(crop -> model.addRow(new Object[]{
            crop.getCropName(),
            crop.getPlantingDate() != null ? crop.getPlantingDate().toString() : "",
            crop.getHarvestDate() != null ? crop.getHarvestDate().toString() : "",
            crop.getStatus() != null ? crop.getStatus() : ""
        }));

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void loadRecentLivestockTable() {
    try {
        SessionManager session = SessionManager.getInstance();
        String nationalId = session.getCurrentUserId();
        
        if (nationalId == null) {
            return; // User not logged in
        }
        
        LivestockController livestockController = new LivestockController();
        List<Livestock> allLivestock = livestockController.findLivestockByUser(nationalId);
        // Get recent 5 livestock (already sorted DESC by service)
        List<Livestock> animals = allLivestock.size() > 5 ? allLivestock.subList(0, 5) : allLivestock;

        DefaultTableModel model = (DefaultTableModel) tableRecentLivestock.getModel();
        model.setRowCount(0);

        // Add rows (already sorted DESC by DAO)
        animals.forEach(animal -> model.addRow(new Object[]{
            animal.getAnimalType(),
            animal.getAge(),
            animal.getBreed(),
            animal.getHealthStatus(),
            animal.getQuantity()
        }));

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void loadDashboardData() {
    try {
        SessionManager session = SessionManager.getInstance();
        String nationalId = session.getCurrentUserId();
        
        if (nationalId == null) {
            // User not logged in, show 0 for all
            jLabel3.setText("0");
            jLabel5.setText("0");
            jLabel9.setText("0");
            return;
        }
        
        CropController cropController = new CropController();
        LivestockController livestockController = new LivestockController();
        EmployeeController employeeController = new EmployeeController();

        Long totalCropsLong = cropController.countCropsByUser(nationalId);
        Long totalLivestockLong = livestockController.countLivestockByUser(nationalId);
        Long totalEmployeesLong = employeeController.countEmployeesByUser(nationalId);
        
        int totalCrops = totalCropsLong != null ? totalCropsLong.intValue() : 0;
        int totalLivestock = totalLivestockLong != null ? totalLivestockLong.intValue() : 0;
        int totalEmployees = totalEmployeesLong != null ? totalEmployeesLong.intValue() : 0;

        // Update labels
        jLabel3.setText(String.valueOf(totalCrops));
        jLabel5.setText(String.valueOf(totalLivestock));
        jLabel9.setText(String.valueOf(totalEmployees));

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading dashboard data: " + e.getMessage());
    }
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new DashboardFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCrop;
    private javax.swing.JButton btnAddEmployee;
    private javax.swing.JButton btnAddLivestock;
    private javax.swing.JButton btnCrop;
    private javax.swing.JButton btnDashBoard;
    private javax.swing.JButton btnEmployees;
    private javax.swing.JButton btnLivestock;
    private javax.swing.JButton btnNotifications;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnlogout;
    private javax.swing.JPanel cardCrops;
    private javax.swing.JPanel cardEmployees;
    private javax.swing.JPanel cardLivestock;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainContentPanel;
    private javax.swing.JTable tableRecentCrops;
    private javax.swing.JTable tableRecentLivestock;
    // End of variables declaration//GEN-END:variables
}
