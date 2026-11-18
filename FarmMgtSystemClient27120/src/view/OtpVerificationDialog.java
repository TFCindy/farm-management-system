package view;

import controller.UserController;
import java.awt.Color;
import javax.swing.JOptionPane;
import model.User;
import util.SessionManager;

/**
 * OTP Verification Dialog
 * Blue & white theme matching other views
 */
public class OtpVerificationDialog extends BaseDialog {
    
    private String email;
    private User verifiedUser;
    
    /**
     * Creates new form OtpVerificationDialog
     */
    public OtpVerificationDialog(java.awt.Frame parent, boolean modal, String email) {
        super(parent, modal);
        this.email = email;
        initComponents();
        setupDialog();
    }
    
    private void setupDialog() {
        setTitle("OTP Verification");
        setLocationRelativeTo(null);
        lblEmail.setText("Enter OTP sent to: " + email);
        lblEmail.setForeground(new Color(0, 153, 153)); // Blue theme
    }
    
    public User getVerifiedUser() {
        return verifiedUser;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtOtp = new javax.swing.JTextField();
        btnVerify = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 2));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("OTP VERIFICATION");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblEmail.setText("Enter OTP sent to your email");

        txtOtp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtOtp.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnVerify.setBackground(new java.awt.Color(0, 153, 153));
        btnVerify.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVerify.setForeground(new java.awt.Color(255, 255, 255));
        btnVerify.setText("Verify OTP");
        btnVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(0, 153, 153));
        btnCancel.setText("Cancel");
        btnCancel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 1));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOtp)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVerify, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addComponent(lblEmail)
                .addGap(20, 20, 20)
                .addComponent(txtOtp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerify, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyActionPerformed
        String otp = txtOtp.getText().trim();
        
        if (otp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the OTP.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Initialize user controller with proper error handling
            UserController userController = null;
            try {
                userController = new UserController();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unable to connect to the server. Please check your connection and try again.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (userController == null) {
                JOptionPane.showMessageDialog(this, "Service unavailable. Please try again later.", "Service Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verify OTP with proper RemoteException handling
            User user = null;
            try {
                user = userController.loginWithOtp(email, otp);
            } catch (java.rmi.RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Invalid or expired OTP. Please request a new OTP and try again.", "OTP Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred during OTP verification. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (user != null) {
                verifiedUser = user;
                SessionManager sessionManager = SessionManager.getInstance();
                if (sessionManager != null) {
                    sessionManager.startSession(user);
                }
                JOptionPane.showMessageDialog(this, "OTP verified successfully! You are now logged in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid or expired OTP. Please try again.", "OTP Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVerifyActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnVerify;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JTextField txtOtp;
    // End of variables declaration//GEN-END:variables
}

