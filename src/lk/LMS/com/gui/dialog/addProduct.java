/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package lk.LMS.com.gui.dialog;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import lk.LMS.com.gui.HomeScreen;
import lk.LMS.com.gui.Logger.LMS_Logger;
import lk.LMS.com.gui.connection.Mysql;
import lk.LMS.com.validation.productValidator;
import lk.LMS.com.gui.util.AppIconUtill;

public class addProduct extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = LMS_Logger.getLogger();
    private JTable productTable;
    private HomeScreen homeScreen;

    HashMap<String, Integer> brandMap = new HashMap<>();
    HashMap<String, Integer> categoryMap = new HashMap<>();
    HashMap<String, Integer> statusMap = new HashMap<>();

    public addProduct(java.awt.Frame parent, boolean modal, JTable table, HomeScreen screen) {
        super(parent, modal);
        initComponents();
        infocategoryload();
        infobrandload();
        infostatusload();
        loadproductCode();
        this.productTable = table;
        this.homeScreen = screen;

    }

    private void infocategoryload() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM `catagory`");

            Vector<String> catagory = new Vector();
            catagory.add("Select category");
            while (rs.next()) {
                String categoryName = rs.getString("name");
                int id = rs.getInt("cid");

                catagory.add(categoryName);
                categoryMap.put(categoryName, id);

            }
            DefaultComboBoxModel dcm = new DefaultComboBoxModel(catagory);
            prCategoryCombo.setModel(dcm);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void infobrandload() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM `brand`");

            Vector<String> brand = new Vector();

            brand.add("Select brand");

            while (rs.next()) {
                String brandName = rs.getString("name");
                int id = rs.getInt("bid");

                brand.add(brandName);
                brandMap.put(brandName, id);
            }
            DefaultComboBoxModel dcm1 = new DefaultComboBoxModel(brand);
            prBrandCombo.setModel(dcm1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void infostatusload() {
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
            prStatusCombo.setModel(dcm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String loadproductCode() {

        String productcode = "IMS_" + System.currentTimeMillis();
        return productcode;
    }

    private void insertProduct() {

        if (!productValidator.isTextFieldValid(prNameInput.getText(), "Product Name")) {
            return;
        }
        if (!productValidator.isComboBoxValid(prCategoryCombo, "Product Category")) {
            return;
        }
        if (!productValidator.isComboBoxValid(prBrandCombo, "Product Brand")) {
            return;
        }

        String productName = prNameInput.getText();
        String productCode = loadproductCode();
        String productDise = prDiscriptionInput.getText();
        String imagePath = prImagePathInput.getText();
       
        if (imagePath.isEmpty()) {
            imagePath = "lk/LMS/com/img/splashscreen1.png";
        }

        String category = prCategoryCombo.getSelectedItem().toString();
        int categoryId = categoryMap.get(category);

        String brand = prBrandCombo.getSelectedItem().toString();
        int brandId = brandMap.get(brand);

        String status = prStatusCombo.getSelectedItem().toString();
        int statusId = statusMap.get(status);

        try {
            ResultSet rs = Mysql.excute("SELECT `pid` FROM `product` WHERE `product`.`name` = '" + productName + "' "
                    + "AND `product`.`category_cid` = '" + categoryId + "' "
                    + "AND `product`.`brand_bid` = '" + brandId + "'");
            if (rs.next()) {
                logger.warning("Product already exists: " + productName);
                JOptionPane.showMessageDialog(null,
                        "This product is already exists ",
                        "product information dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Mysql.excute("INSERT INTO `product`(`name`,`discription`,`product_code`,`category_cid`,`brand_bid`,`status_sid`)"
                        + "VALUES('" + productName + "','" + productDise + "','" + productCode + "','" + categoryId + "','" + brandId + "','" + statusId + "')");

                ResultSet rs1 = Mysql.excute("SELECT LAST_INSERT_ID()");
                if (rs1.next()) {
                    int lastInsertId = rs1.getInt(1);
                    String escapedPath = imagePath.replace("\\", "\\\\");
                    Mysql.excute("INSERT INTO `product_image`(`path`,`product_id`)VALUE ('" + escapedPath + "','" + lastInsertId + "')");
                }

                logger.info("Product added successfully: " + productName);
                JOptionPane.showMessageDialog(null,
                        "New product addedd succesfully ",
                        "product information dialog",
                        JOptionPane.INFORMATION_MESSAGE);
                AppIconUtill.TableUtils.refreshTable(productTable, "SELECT `product`.`pid`, `product`.`name`, `brand`.`name`, `catagory`.`name`, `product`.`discription`"
                        + "    FROM `product`"
                        + "    LEFT JOIN `brand` ON `product`.`brand_bid` = `brand`.`bid`"
                        + "    LEFT JOIN `catagory` ON `product`.`category_cid` = `catagory`.`cid`"
                        + "    ORDER BY `product`.`pid` DESC", 5);

                if (homeScreen != null) {
                    homeScreen.loadproductcard();
                }

                this.dispose();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe("Error adding product: " + e.getMessage());
            this.dispose();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        prNameInput = new javax.swing.JTextField();
        prCategoryCombo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        prBrandCombo = new javax.swing.JComboBox<>();
        prStatusCombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        prDiscriptionInput = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        productImagePanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        prImagePathInput = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setLayout(new java.awt.GridLayout(2, 2, 10, 5));

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel2.setText("Product Name");
        jPanel2.add(jLabel2);

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel1.setText("Product Category");
        jPanel2.add(jLabel1);

        prNameInput.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        prNameInput.setPreferredSize(new java.awt.Dimension(68, 30));
        jPanel2.add(prNameInput);

        prCategoryCombo.setFont(new java.awt.Font("Javanese Text", 1, 16)); // NOI18N
        prCategoryCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(prCategoryCombo);

        jPanel3.setLayout(new java.awt.GridLayout(2, 2, 10, 5));

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel5.setText("Product Brand");
        jPanel3.add(jLabel5);

        jLabel7.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel7.setText("Product Status");
        jPanel3.add(jLabel7);

        prBrandCombo.setFont(new java.awt.Font("Javanese Text", 1, 16)); // NOI18N
        prBrandCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(prBrandCombo);

        prStatusCombo.setFont(new java.awt.Font("Javanese Text", 1, 16)); // NOI18N
        prStatusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(prStatusCombo);

        jLabel4.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel4.setText("Product Description");

        prDiscriptionInput.setColumns(20);
        prDiscriptionInput.setFont(new java.awt.Font("JetBrains Mono", 1, 16)); // NOI18N
        prDiscriptionInput.setRows(5);
        jScrollPane1.setViewportView(prDiscriptionInput);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Product info", jPanel1);

        javax.swing.GroupLayout productImagePanelLayout = new javax.swing.GroupLayout(productImagePanel);
        productImagePanel.setLayout(productImagePanelLayout);
        productImagePanelLayout.setHorizontalGroup(
            productImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        productImagePanelLayout.setVerticalGroup(
            productImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(productImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(590, 590, 590)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(prImagePathInput, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prImagePathInput, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(productImagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("product image", jPanel6);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jLabel8.setText("Add Product");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jTabbedPane1)
                .addGap(6, 6, 6))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        insertProduct();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String productcode = "IMG_" + System.currentTimeMillis();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(productcode + "(.png, .jpeg, .jpg)",
                "png", "jpeg", "jpg");
        chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(productImagePanel);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                File imageFolder = new File("Pimg");
                if (!imageFolder.exists()) {
                    imageFolder.mkdir();
                    logger.info("Image folder created: " + imageFolder.getAbsolutePath());
                }
                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destinationFile = new File(imageFolder, fileName);
                prImagePathInput.setText(destinationFile.getAbsolutePath());
                Files.copy(selectedFile.toPath(),
                        destinationFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                logger.severe("Error copying image file: " + e.getMessage());
                e.printStackTrace();
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> prBrandCombo;
    private javax.swing.JComboBox<String> prCategoryCombo;
    private javax.swing.JTextArea prDiscriptionInput;
    private javax.swing.JTextField prImagePathInput;
    private javax.swing.JTextField prNameInput;
    private javax.swing.JComboBox<String> prStatusCombo;
    private javax.swing.JPanel productImagePanel;
    // End of variables declaration//GEN-END:variables
}
