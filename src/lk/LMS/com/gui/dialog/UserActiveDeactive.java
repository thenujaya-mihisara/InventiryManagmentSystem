/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package lk.LMS.com.gui.dialog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import lk.LMS.com.gui.Logger.LMS_Logger;
import lk.LMS.com.gui.connection.Mysql;
import lk.LMS.com.gui.util.AppIconUtill;



public class UserActiveDeactive extends javax.swing.JDialog {
private static final java.util.logging.Logger logger = LMS_Logger.getLogger();
   private JTable userTable; 
    public UserActiveDeactive(java.awt.Frame parent, boolean modal, JTable table) {
        super(parent, modal);
        this.userTable = table;
        initComponents();
        loadUserList();
    }
private HashMap<String, Integer> userMap = new HashMap<>();
    private void loadUserList() {
    try {
        ResultSet rs = Mysql.excute("SELECT * FROM `admin` ORDER BY name DESC");

        DefaultListModel<String> model = new DefaultListModel<>();
        userMap.clear();

        while (rs.next()) {
            int aid = rs.getInt("aid");
            String name = rs.getString("name");

            model.addElement(name);
            userMap.put(name, aid);
        }

        jList1.setModel(model);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  

private int selectedUserId = 0;

private void loadSupplierStatus(int aid) {
    try {
        ResultSet rs = Mysql.excute("SELECT `status_sid` FROM `admin` WHERE aid = '" + aid + "'");
        if (rs.next()) {
            selectedUserId = aid;
            int status = rs.getInt("status_sid");

            if (status == 1) {
                Active.setSelected(true);
            } else if (status == 2) {
                Deactive.setSelected(true);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private void SaveStatus() {
    int newStatus = Active.isSelected() ? 1 : 2;

    try {
        Mysql.excute("UPDATE `admin` SET `status_sid` = '" + newStatus + "' WHERE aid = '" + selectedUserId + "'");
        JOptionPane.showMessageDialog(this, "Supplier status updated successfully.");
        loadSupplierStatus(selectedUserId); 
         AppIconUtill.TableUtils.refreshTable(userTable, "SELECT `admin`.`aid`, `admin`.`name`, `admin`.`email`,`admin`.`password`, `role`.`name`, `status`.`name`"
                        + "    FROM `admin`"
                        + "    INNER JOIN `status` ON `admin`.`status_sid` = `status`.`sid`"
                        + "    INNER JOIN `role` ON `admin`.`role_rid` = `role`.`rid` "
                        + "    ORDER BY `admin`.`aid` DESC", 6);
         dispose();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        Active = new javax.swing.JRadioButton();
        Deactive = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        buttonGroup1.add(Active);
        Active.setText("Active");
        Active.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiveActionPerformed(evt);
            }
        });

        buttonGroup1.add(Deactive);
        Deactive.setText("Deactive");

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jLabel1.setText("Select User status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(Active)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Deactive)
                .addGap(44, 44, 44))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Deactive)
                    .addComponent(Active))
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addGap(30, 30, 30))
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting()) {
            String selectedName = jList1.getSelectedValue();
            if (selectedName != null) {
                int supid = userMap.get(selectedName);
                loadSupplierStatus(supid);
            }
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        SaveStatus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ActiveActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Active;
    private javax.swing.JRadioButton Deactive;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
