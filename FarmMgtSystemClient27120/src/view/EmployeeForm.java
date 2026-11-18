/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.toedter.calendar.*;
import controller.EmployeeController;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Employee;
import util.SessionManager;

/**
 *
 * @author Munezero Cindyyy
 */
public class EmployeeForm extends BaseForm {
    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(EmployeeForm.class.getName());
    private DefaultTableModel tableModel;
    private boolean isEditing = false;
    private int selectedEmployeeId = -1;
    // Named listener so we can remove it on dispose
    private final SessionManager.DataChangeListener employeeDataListener = new SessionManager.DataChangeListener() {
        @Override
        public void onDataChanged(String entityType) {
            if ("employee".equalsIgnoreCase(entityType)) {
                // Only refresh if table has data (user clicked Show All)
                if (tableModel.getRowCount() > 0) {
                    loadAllEmployees();
                }
            }
        }
    };
    /**
     * Creates new form EmployeeForm
     */
    public EmployeeForm() {
        initComponents();
        // Check if user is logged in
        if (!SessionManager.getInstance().isLoggedIn()) {
            showDualLanguageError("Please log in first.");
            this.dispose();
            new LoginForm().setVisible(true);
            return;
        }
        // Initialize table model
        tableModel = (DefaultTableModel) tblEmployees.getModel();
        // Do NOT auto-load employees - only load on "Show All" button
        // loadAllEmployees(); // REMOVED - only load on Show All button
        // Fields should be editable by default - including National ID
        setFieldsEditable(true);
        // National ID should be editable for manual entry
        txtEmployeeId.setEditable(true);
        // Setup table selection listener
        setupTableSelection();
        // register to update when employees are added elsewhere (named listener)
        SessionManager.getInstance().addDataChangeListener(employeeDataListener);
        applyFormTranslations();
    }
    
    private void applyFormTranslations() {
        applyTitleTranslation(jLabel1, "EMPLOYEE MANAGEMENT");
        applyLabelTranslation(jLabel2, "Name:");
        applyLabelTranslation(jLabel3, "Role:");
        applyLabelTranslation(jLabel4, "Contact:");
        applyLabelTranslation(jLabel5, "Salary(FRW):");
        applyLabelTranslation(jLabel6, "Hire date:");
        applyLabelTranslation(jLabel7, "National ID:");
        applyButtonTranslation(btnAdd, "Add employee");
        applyButtonTranslation(btnUpdate, "Update employee");
        applyButtonTranslation(btnDelete, "Delete employee");
        applyButtonTranslation(btnSearch, "Search");
        applyButtonTranslation(btnShow, "Show All");
        applyButtonTranslation(btnBacktoDashboard, "Back to dashboard");
        applyButtonTranslation(btnLogout, "Logout");
        applyButtonTranslation(btnAddContract, "Add contract details");
    }
    
    private void setupTableSelection() {
    tblEmployees.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tblEmployees.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    // Reset any previous editing state
                    if (isEditing) {
                        isEditing = false;
                        btnUpdate.setText("Update Employee");
                        setFieldsEditable(false);
                    }

                    DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();
                    // First column now contains National ID (as string)
                    String nationalIdOrId = model.getValueAt(selectedRow, 0).toString();
                    String name = model.getValueAt(selectedRow, 1).toString();
                    String role = model.getValueAt(selectedRow, 2).toString();
                    String contact = model.getValueAt(selectedRow, 3).toString();
                    double salary = Double.parseDouble(model.getValueAt(selectedRow, 4).toString());
                    Date hireDate = (Date) model.getValueAt(selectedRow, 5);

                    // Try to find employee by National ID or by parsing as ID
                    EmployeeController employeeController = new EmployeeController();
                    Employee employee = null;
                    
                    // First try to find by National ID
                    List<Employee> employees = employeeController.findEmployeesByUser(SessionManager.getInstance().getCurrentUserId());
                    for (Employee emp : employees) {
                        if (emp.getNationalId() != null && emp.getNationalId().equals(nationalIdOrId)) {
                            employee = emp;
                            break;
                        }
                        if (String.valueOf(emp.getEmployeeId()).equals(nationalIdOrId)) {
                            employee = emp;
                            break;
                        }
                    }
                    
                    if (employee == null) {
                        // If not found, create a temporary employee object for display
                        employee = new Employee();
                        employee.setNationalId(nationalIdOrId);
                        employee.setName(name);
                        employee.setRole(role);
                        employee.setContact(contact);
                        employee.setSalary(salary);
                        employee.setHireDate(hireDate);
                        // Try to parse as employee ID
                        if (employee != null) {
                            try {
                                employee.setEmployeeId(Integer.parseInt(nationalIdOrId));
                            } catch (NumberFormatException ex) {
                                // If it's a National ID, we'll need to find the actual employee ID
                                employee.setEmployeeId(-1);
                            }
                        }
                    }
                    populateForm(employee);
                    setFieldsEditable(false);
                    setSelectedEmployeeId(employee.getEmployeeId());

                    JOptionPane.showMessageDialog(this,
                            "Record loaded. Click Update to modify or Delete to remove.",
                            "Record Selected",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error selecting employee: " + ex.getMessage(), "Selection Error",
                            JOptionPane.ERROR_MESSAGE);
                    resetSelectedEmployeeId();
                }
            }
        }
    });
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSalary = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtEmployeeId = new javax.swing.JTextField();
        cmbRole = new javax.swing.JComboBox<>();
        jHireDate = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        btnAddContract = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnShow = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnBacktoDashboard = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel1.setText("EMPLOYEE MANAGEMENT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153), new java.awt.Color(0, 153, 153)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("       Name:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("        Role:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("        Contact:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("       Salary(FRW):");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("         Hire date:");

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Name", "Role", "Contact", "Salary", "Hire date"
            }
        ));
        jScrollPane1.setViewportView(tblEmployees);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText(" National ID:");

        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Farm manager", "Item2 " }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtSalary, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(cmbRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jHireDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmployeeId, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                .addComponent(txtContact))
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtContact)
                            .addComponent(jLabel4))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtEmployeeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jHireDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
        );

        btnAddContract.setText("Add contract details");
        btnAddContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContractActionPerformed(evt);
            }
        });

        btnAdd.setText("Add employee");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update employee");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSearch.setText("Search Employee");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnShow.setText("Show All");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnShow)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnAddContract))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnSearch)
                    .addComponent(btnShow)
                    .addComponent(btnAddContract)
                    .addComponent(btnDelete))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        btnBacktoDashboard.setText("Back to Dashboard");
        btnBacktoDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBacktoDashboardActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBacktoDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBacktoDashboard)
                    .addComponent(btnLogout))
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        // Search by employee name (not ID)
        String searchTerm = txtName.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an employee name to search.", "Search Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            EmployeeController employeeController = new EmployeeController();
            List<Employee> searchResults = employeeController.searchEmployeesByName(
                    SessionManager.getInstance().getCurrentUserId(),
                    searchTerm);

            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No employees found with name: " + searchTerm, "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                resetSelectedEmployeeId();
            } else {
                // Display first result in form (non-editable)
                Employee employee = searchResults.get(0);
                populateForm(employee);
                setFieldsEditable(false);
                setSelectedEmployeeId(employee.getEmployeeId());

                // Display all results in table
                displayEmployeesInTable(searchResults);

                JOptionPane.showMessageDialog(this,
                        "Record loaded. Click Update to modify or Delete to remove.",
                        "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during search: " + e.getMessage(), "Search Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (!isEditing) {
            // Start editing mode
            if (!isEmployeeSelected()) {
                JOptionPane.showMessageDialog(this, "Please select an employee to update.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            setFieldsEditable(true);
            isEditing = true;
            btnUpdate.setText("Save Changes");
            JOptionPane.showMessageDialog(this, "You can now edit the fields. Click 'Save Changes' when done.");
        } else {
            // Save changes
            if (validateForm()) {
                try {
                    // Get National ID from form (editable field)
                    String nationalId = txtEmployeeId.getText().trim();
                    if (nationalId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "National ID is required!", "Validation Error",
                                JOptionPane.ERROR_MESSAGE);
                        txtEmployeeId.requestFocus();
                        return;
                    }
                    
                    // Validate National ID format
                    if (!nationalId.matches("^[123]\\d{15}$")) {
                        JOptionPane.showMessageDialog(this, "National ID must be exactly 16 digits starting with 1, 2, or 3!", 
                                "Validation Error", JOptionPane.ERROR_MESSAGE);
                        txtEmployeeId.requestFocus();
                        return;
                    }
                    
                    Employee employee = new Employee();
                    employee.setEmployeeId(getSelectedEmployeeId());
                    employee.setNationalId(nationalId); // Use National ID from form
                    employee.setName(txtName.getText().trim());
                    employee.setContact(txtContact.getText().trim());
                    employee.setRole(cmbRole.getSelectedItem().toString());
                    employee.setSalary(Double.parseDouble(txtSalary.getText().trim()));
                    employee.setHireDate(jHireDate.getDate());

                    EmployeeController employeeController = new EmployeeController();
                    int result = employeeController.updateEmployeeById(employee);

                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Employee updated successfully!");
                        isEditing = false;
                        btnUpdate.setText("Update Employee");

                        // AUTO-CLEAR FORM
                        clearForm();
                        // Only refresh table if it has data (user clicked Show All)
                        if (tableModel.getRowCount() > 0) {
                            loadAllEmployees();
                        }
                        resetSelectedEmployeeId();
                        // Fields should be editable after clearing
                        setFieldsEditable(true);
                        try {
                            SessionManager.getInstance().notifyDataChanged("employee");
                        } catch (Exception ignored) {
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:

        // Check if all required fields are filled
        if (txtName.getText().trim().isEmpty() ||
                cmbRole.getSelectedItem() == null ||
                txtContact.getText().trim().isEmpty() ||
                txtSalary.getText().trim().isEmpty() ||
                jHireDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Contact validation - must be exactly 10 digits
        String contact = txtContact.getText().trim();
        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact must be exactly 10 digits!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            txtContact.requestFocus();
            return;
        }

        // Salary validation
        try {
            double salary = Double.parseDouble(txtSalary.getText().trim());
            if (salary <= 0) {
                JOptionPane.showMessageDialog(this, "Salary must be a positive number!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                txtSalary.requestFocus();
                return;
            }

            // Optional: Set maximum salary limit
            if (salary > 1000000) { // 1 million maximum
                JOptionPane.showMessageDialog(this, "Salary cannot exceed 1,000,000!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                txtSalary.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salary must be a valid number!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            txtSalary.requestFocus();
            return;
        }

        // Hire date validation - cannot be in the future
        java.util.Date hireDate = jHireDate.getDate();
        java.util.Date currentDate = new java.util.Date();
        if (hireDate.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Hire date cannot be in the future!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            jHireDate.requestFocus();
            return;
        }

        try {
            // Check if user is logged in
            SessionManager session = SessionManager.getInstance();
            if (!session.isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Please login to add employee.", "Session Expired",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                new LoginForm().setVisible(true);
                return;
            }

            // Use the logged-in user's nationalId (farmer's nationalId)
            String nationalId = session.getCurrentUserId();
            if (nationalId == null || nationalId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Session expired. Please login again.", "Session Error",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                new LoginForm().setVisible(true);
                return;
            }

            // Create employee object
            Employee employee = new Employee();
            employee.setNationalId(nationalId); // Use logged-in farmer's National ID
            employee.setName(txtName.getText().trim());
            employee.setRole(cmbRole.getSelectedItem().toString());
            employee.setContact(contact);
            employee.setSalary(Double.parseDouble(txtSalary.getText().trim()));
            employee.setHireDate(hireDate);

            // Save to database via controller
            EmployeeController employeeController = new EmployeeController();
            int result = employeeController.createEmployee(employee);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // AUTO-CLEAR FORM
                clearForm();
                // Only refresh table if it has data (user clicked Show All)
                if (tableModel.getRowCount() > 0) {
                    loadAllEmployees();
                }
                resetSelectedEmployeeId();
                // Fields should be editable after clearing
                setFieldsEditable(true);

                // Refresh dashboard if open
                refreshDashboard();
                try {
                    util.SessionManager.getInstance().notifyDataChanged("employee");
                } catch (Exception ignored) {
                }

            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Salary!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            txtSalary.requestFocus();
        } catch (java.rmi.ConnectException e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to server. Please ensure the server is running and try again.", "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (java.rmi.RemoteException e) {
            JOptionPane.showMessageDialog(this, "Connection error. Please check your network connection and try again.", "Network Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding employee. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        loadAllEmployees();
        clearForm();
        // Fields should be editable after clearing
        setFieldsEditable(true);
        isEditing = false;
        btnUpdate.setText("Update Employee");
        resetSelectedEmployeeId();
        JOptionPane.showMessageDialog(this, "Showing all employees.");
    }//GEN-LAST:event_btnShowActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (!isEmployeeSelected()) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String employeeName = txtName.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete employee: " + employeeName + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                EmployeeController employeeController = new EmployeeController();
                boolean result = employeeController.deleteEmployeeById(getSelectedEmployeeId(),
                        SessionManager.getInstance().getCurrentUserId());

                if (result) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");

                    // AUTO-CLEAR FORM
                    clearForm();
                    // Only refresh table if it has data (user clicked Show All)
                    if (tableModel.getRowCount() > 0) {
                        loadAllEmployees();
                    }
                    resetSelectedEmployeeId();
                    // Fields should be editable after clearing
                    setFieldsEditable(true);

                    if (isEditing) {
                        isEditing = false;
                        btnUpdate.setText("Update Employee");
                    }

                    // Refresh dashboard if open
                    refreshDashboard();
                    try {
                        SessionManager.getInstance().notifyDataChanged("employee");
                    } catch (Exception ignored) {
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContractActionPerformed
        // TODO add your handling code here:
        if (!isEmployeeSelected()) {
            JOptionPane.showMessageDialog(this, "Please select an employee first to add a contract.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get selected employee
            EmployeeController employeeController = new EmployeeController();
            Employee employee = new Employee();
            employee.setEmployeeId(getSelectedEmployeeId());
            Employee selectedEmployee = employeeController.searchEmployeeById(employee);
            
            if (selectedEmployee == null) {
                JOptionPane.showMessageDialog(this, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Open contract dialog with selected employee
            AddContractDialog contractDialog = new AddContractDialog(this, true, selectedEmployee);
            contractDialog.setVisible(true);
            
            // Refresh if contract was added
            if (tableModel.getRowCount() > 0) {
                loadAllEmployees();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening contract dialog: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAddContractActionPerformed

    private void btnBacktoDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBacktoDashboardActionPerformed
       
        this.dispose(); // Close current form
        new DashboardFrame().setVisible(true);
    }//GEN-LAST:event_btnBacktoDashboardActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
         // TODO add your handling code here:
        // TODO add your handling code here:
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // End session
            SessionManager.getInstance().endSession();

            // Close current form and open login
            this.dispose();
            new LoginForm().setVisible(true);

            JOptionPane.showMessageDialog(null, "You have been logged out successfully.");
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void loadAllEmployees() {
        try {
            SessionManager session = SessionManager.getInstance();
            EmployeeController employeeController = new EmployeeController();
            List<Employee> employeeList = employeeController.findEmployeesByUser(session.getCurrentUserId());
            displayEmployeesInTable(employeeList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage(), "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void displayEmployeesInTable(List<Employee> employeeList) {
        DefaultTableModel model = (DefaultTableModel) tblEmployees.getModel();
        model.setRowCount(0);

        for (Employee employee : employeeList) {
            model.addRow(new Object[] {
                    employee.getNationalId() != null ? employee.getNationalId() : String.valueOf(employee.getEmployeeId()), // Display National ID in Employee ID column
                    employee.getName(),
                    employee.getRole(),
                    employee.getContact(),
                    employee.getSalary(),
                    employee.getHireDate()
            });
        }
    }

    private void populateForm(Employee employee) {
        // Populate all fields including National ID (editable)
        // Display National ID in Employee ID field
        txtEmployeeId.setText(employee.getNationalId() != null ? employee.getNationalId() : String.valueOf(employee.getEmployeeId()));
        txtName.setText(employee.getName());
        txtContact.setText(employee.getContact());
        cmbRole.setSelectedItem(employee.getRole());
        txtSalary.setText(String.valueOf(employee.getSalary()));
        jHireDate.setDate(employee.getHireDate());
    }

    private void clearForm() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtContact.setText("");
        cmbRole.setSelectedIndex(0);
        txtSalary.setText("");
        jHireDate.setDate(null);
    }

    private void setFieldsEditable(boolean editable) {
        txtName.setEditable(editable);
        txtContact.setEditable(editable);
        cmbRole.setEnabled(editable);
        txtSalary.setEditable(editable);
        jHireDate.setEnabled(editable);
        // National ID should be editable for manual entry
        txtEmployeeId.setEditable(editable);
    }

    private boolean validateForm() {
            String nationalId = txtEmployeeId.getText().trim();
        String name = txtName.getText().trim();
        String contact = txtContact.getText().trim();
        String role = cmbRole.getSelectedItem().toString().trim();
        String salaryText = txtSalary.getText().trim();
        Date hireDate = jHireDate.getDate();

        if (nationalId.isEmpty() || name.isEmpty() || contact.isEmpty() || role.equals("Select Role") ||
                salaryText.isEmpty() || hireDate == null) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            return false;
            }
            
        // Validate National ID format
            if (!nationalId.matches("^[123]\\d{15}$")) {
                JOptionPane.showMessageDialog(this, "National ID must be exactly 16 digits starting with 1, 2, or 3!", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                txtEmployeeId.requestFocus();
            return false;
        }

        try {
            double salary = Double.parseDouble(salaryText);
            if (salary <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid salary!", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salary must be a number!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Employee ID Management
    private void setSelectedEmployeeId(int employeeId) {
        this.selectedEmployeeId = employeeId;
    }

    private int getSelectedEmployeeId() {
        return this.selectedEmployeeId;
    }

    private void resetSelectedEmployeeId() {
        this.selectedEmployeeId = -1;
    }

    private boolean isEmployeeSelected() {
        return this.selectedEmployeeId != -1;
    }

    // Table click event - now handled by setupTableSelection()

    // Refresh dashboard
    private void refreshDashboard() {
        java.awt.Window[] windows = java.awt.Window.getWindows();
        for (java.awt.Window window : windows) {
            if (window instanceof DashboardFrame) {
                DashboardFrame dashboard = (DashboardFrame) window;
                dashboard.loadDashboardData();
                break;
            }
        }
    }

   // GEN-LAST:event_btnUpdateActionPerformed

    // GEN-LAST:event_btnDeleteActionPerformed

    // GEN-LAST:event_btnSearchActionPerformed

   // GEN-LAST:event_btnRetriveActionPerformed

    @Override
    public void dispose() {
        try {
            SessionManager.getInstance().removeDataChangeListener(employeeDataListener);
        } catch (Exception ignored) {
        }
        super.dispose();
    }

   // GEN-LAST:event_btnDashboardActionPerformed

    // GEN-LAST:event_btnLogoutActionPerformed

   // GEN-LAST:event_btnAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new EmployeeForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddContract;
    private javax.swing.JButton btnBacktoDashboard;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShow;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbRole;
    private com.toedter.calendar.JDateChooser jHireDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmployeeId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSalary;
    // End of variables declaration//GEN-END:variables
}
