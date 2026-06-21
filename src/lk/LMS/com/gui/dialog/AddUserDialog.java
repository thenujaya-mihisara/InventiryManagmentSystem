/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package lk.LMS.com.gui.dialog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import lk.LMS.com.gui.Logger.LMS_Logger;
import lk.LMS.com.gui.connection.Mysql;
import lk.LMS.com.validation.UserValidate;
import lk.LMS.com.gui.util.AppIconUtill;

/**
 *
 * @author MB
 */
public class AddUserDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = LMS_Logger.getLogger();
    private JTable userTable;

    HashMap<String, Integer> statusMap = new HashMap<>();
    HashMap<String, Integer> roleMap = new HashMap<>();

    public AddUserDialog(java.awt.Frame parent, boolean modal, JTable table) {
        super(parent, modal);
        initComponents();
        statusload();
        roleload();
        this.userTable = table;
        init();
    }
    
    private void init(){
    urpasswordInput.putClientProperty("FlatLaf.style","showRevealButton:true");
   
    }

    private void statusload() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM `status`");
            Vector<String> status = new Vector<>();
            while (rs.next()) {
                String statusName = rs.getString("name");
                int id = rs.getInt("sid");

                status.add(statusName);
                statusMap.put(statusName, id);
            }
            DefaultComboBoxModel dcm = new DefaultComboBoxModel(status);
            urStatusCombo.setModel(dcm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void roleload() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM `role`");
            Vector<String> status = new Vector<>();
            
            while (rs.next()) {
                String statusName = rs.getString("name");
                int id = rs.getInt("rid");

                status.add(statusName);
                roleMap.put(statusName, id);
            }
            DefaultComboBoxModel dcm = new DefaultComboBoxModel(status);
            urRolesCombo.setModel(dcm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void insertUser() {
        String userName = urNameInput.getText();
        String useremail = urEmailInput.getText().trim();
        String password = String.valueOf(urpasswordInput.getPassword());

        String status = urStatusCombo.getSelectedItem().toString();
        int statusId = statusMap.get(status);

        String role = urRolesCombo.getSelectedItem().toString();
        int roleId = roleMap.get(role);

        if (!UserValidate.isInputFieldValid(userName)) {
            return;
        }
        if (!UserValidate.isEmailValid(useremail)) {
            return;
        }
        if (!UserValidate.isPasswordValid(password)) {
            return;
        }
        

        logger.info("Attempting to add user: " + userName + ", email: " + useremail);

        try {
            ResultSet rs = Mysql.excute("SELECT `aid` FROM `admin` WHERE `admin`.`email` = '" + useremail + "'");
            if (rs.next()) {
                logger.warning("User already exists with email: " + useremail);
                JOptionPane.showMessageDialog(null,
                        "This user is already exists ",
                        "user information dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Mysql.excute("INSERT INTO `admin`(`name`,`email`,`password`,`status_sid`,`role_rid`)"
                        + "VALUES('" + userName + "','" + useremail + "','" + password + "','" + statusId + "','" + roleId + "')");
                logger.info("New user added successfully: " + userName);
                JOptionPane.showMessageDialog(null,
                        "New user addedd succesfully ",
                        "user information dialog",
                        JOptionPane.INFORMATION_MESSAGE);
                AppIconUtill.TableUtils.refreshTable(userTable, "SELECT `admin`.`aid`, `admin`.`name`, `admin`.`email`,`admin`.`password`, `role`.`name`, `status`.`name`"
                        + "    FROM `admin`"
                        + "    INNER JOIN `status` ON `admin`.`status_sid` = `status`.`sid`"
                        + "    INNER JOIN `role` ON `admin`.`role_rid` = `role`.`rid` "
                        + "    ORDER BY `admin`.`aid` DESC", 6);
                this.dispose();

            }
        } catch (SQLException e) {
            logger.severe("Error inserting user: " + e.getMessage());
            e.printStackTrace();
            this.dispose();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        urNameInput = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        urpasswordInput = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        urEmailInput = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        urRolesCombo = new javax.swing.JComboBox<>();
        urStatusCombo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jLabel8.setText("Add User");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("User Name");

        urNameInput.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Password");

        urpasswordInput.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Email");

        urEmailInput.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        urEmailInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urEmailInputActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.GridLayout(2, 2, 5, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Role");
        jPanel2.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Status");
        jPanel2.add(jLabel4);

        urRolesCombo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        urRolesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(urRolesCombo);

        urStatusCombo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        urStatusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(urStatusCombo);

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save user");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(urEmailInput)
                    .addComponent(urpasswordInput)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 425, Short.MAX_VALUE))
                    .addComponent(urNameInput)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urNameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urEmailInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(urpasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void urEmailInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urEmailInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_urEmailInputActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        insertUser();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField urEmailInput;
    private javax.swing.JTextField urNameInput;
    private javax.swing.JComboBox<String> urRolesCombo;
    private javax.swing.JComboBox<String> urStatusCombo;
    private javax.swing.JPasswordField urpasswordInput;
    // End of variables declaration//GEN-END:variables
}
