/*
 * Decompiled with CFR 0.152.
 */
package lk.LMS.com.gui.dialog;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import lk.LMS.com.gui.connection.Mysql;
import lk.LMS.com.gui.util.AppIconUtill;

public class supplierActiveDeactive
extends JDialog {
    private JTable supplierTable;
    private HashMap<String, Integer> supplierMap = new HashMap();
    private int selectedSupId = 0;
    private JRadioButton Active;
    private JRadioButton Deactive;
    private ButtonGroup buttonGroup1;
    private JButton jButton1;
    private JLabel jLabel1;
    private JList<String> jList1;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;

    public supplierActiveDeactive(Frame parent, boolean modal, JTable table) {
        super(parent, modal);
        this.initComponents();
        this.supplierTable = table;
        this.loadSupplierList();
    }

    private void loadSupplierList() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM `suppliers` ORDER BY name DESC");
            DefaultListModel<String> model = new DefaultListModel<String>();
            this.supplierMap.clear();
            while (rs.next()) {
                int supid = rs.getInt("supid");
                String name = rs.getString("name");
                model.addElement(name);
                this.supplierMap.put(name, supid);
            }
            this.jList1.setModel(model);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSupplierStatus(int supid) {
        try {
            ResultSet rs = Mysql.excute("SELECT `status_sid` FROM suppliers WHERE supid = '" + supid + "'");
            if (rs.next()) {
                this.selectedSupId = supid;
                int status = rs.getInt("status_sid");
                if (status == 1) {
                    this.Active.setSelected(true);
                } else if (status == 2) {
                    this.Deactive.setSelected(true);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SaveStatus() {
        int newStatus = this.Active.isSelected() ? 1 : 2;
        try {
            Mysql.excute("UPDATE `suppliers` SET `status_sid` = '" + newStatus + "' WHERE supid = '" + this.selectedSupId + "'");
            JOptionPane.showMessageDialog(this, "Supplier status updated successfully.");
            this.loadSupplierStatus(this.selectedSupId);
            AppIconUtill.TableUtils.refreshTable(this.supplierTable, "SELECT * FROM suppliers ORDER BY supid DESC", 4);
            this.dispose();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        this.buttonGroup1 = new ButtonGroup();
        this.jPanel1 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jList1 = new JList();
        this.Active = new JRadioButton();
        this.Deactive = new JRadioButton();
        this.jButton1 = new JButton();
        this.jLabel1 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setResizable(false);
        this.jList1.setModel((ListModel<String>)new AbstractListModel<String>(){
            String[] strings = new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

            @Override
            public int getSize() {
                return this.strings.length;
            }

            @Override
            public String getElementAt(int i) {
                return this.strings[i];
            }
        });
        this.jList1.addListSelectionListener(new ListSelectionListener(){

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                supplierActiveDeactive.this.jList1ValueChanged(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.jList1);
        this.buttonGroup1.add(this.Active);
        this.Active.setText("Active");
        this.buttonGroup1.add(this.Deactive);
        this.Deactive.setText("Deactive");
        this.jButton1.setText("SAVE");
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                supplierActiveDeactive.this.jButton1ActionPerformed(evt);
            }
        });
        this.jLabel1.setFont(new Font("Yu Gothic UI Semibold", 1, 24));
        this.jLabel1.setText("Select supplier status");
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGap(28, 28, 28).addComponent(this.Active).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.Deactive).addGap(44, 44, 44)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(130, 130, 130).addComponent(this.jButton1)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 272, -2)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 320, -2))).addContainerGap(-1, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 38, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 195, Short.MAX_VALUE).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.Deactive).addComponent(this.Active)).addGap(32, 32, 32).addComponent(this.jButton1).addGap(30, 30, 30)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -1, -1, Short.MAX_VALUE));
        this.pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.SaveStatus();
    }

    private void jList1ValueChanged(ListSelectionEvent evt) {
        String selectedName;
        if (!evt.getValueIsAdjusting() && (selectedName = this.jList1.getSelectedValue()) != null) {
            int supid = this.supplierMap.get(selectedName);
            this.loadSupplierStatus(supid);
        }
    }
}
