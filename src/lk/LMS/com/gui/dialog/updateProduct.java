/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package lk.LMS.com.gui.dialog;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import lk.LMS.com.gui.HomeScreen;
import lk.LMS.com.gui.Logger.LMS_Logger;
import lk.LMS.com.gui.connection.Mysql;
import lk.LMS.com.validation.productValidator;
import lk.LMS.com.gui.util.AppIconUtill;

/**
 *
 * @author MB
 */
public class updateProduct extends javax.swing.JDialog {

    private JTable productTable;
    private static final java.util.logging.Logger logger = LMS_Logger.getLogger();
    private HashMap<String, Integer> supplierMap = new HashMap<>();
    private String productId;
    private HomeScreen homeScreen;
    private String selectedImagePath = null;
    private javax.swing.JLabel imagePreviewLabel;
    private javax.swing.JButton browseImageButton;
    private javax.swing.JTextField imagePathField;

    public updateProduct(java.awt.Frame parent, boolean modal, String productId, JTable table, HomeScreen screen) {
        super(parent, modal);
        initComponents();
        init();
        this.productId = productId;
        this.productTable = table;
        loadProduct();
        this.homeScreen = screen;
    }

    private void init() {

        sliderStatus.setMinimum(0);
        sliderStatus.setMaximum(1);
        sliderStatus.setValue(1); // Default to Active
        sliderStatus.setMajorTickSpacing(1);
        sliderStatus.setPaintTicks(true);
        sliderStatus.setPaintLabels(true);
        sliderStatus.setSnapToTicks(true);

        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/lk/LMS/com/img/delete.png"));
        Image image = deleteIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        delete.setIcon(new ImageIcon(image));

        // --- Image Panel (added programmatically) ---
        imagePreviewLabel = new javax.swing.JLabel("No Image", javax.swing.JLabel.CENTER);
        imagePreviewLabel.setPreferredSize(new java.awt.Dimension(200, 180));
        imagePreviewLabel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY, 1));
        imagePreviewLabel.setBackground(new java.awt.Color(245, 245, 245));
        imagePreviewLabel.setOpaque(true);
        imagePreviewLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 12));
        imagePreviewLabel.setForeground(java.awt.Color.GRAY);

        imagePathField = new javax.swing.JTextField();
        imagePathField.setEditable(false);
        imagePathField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        imagePathField.setPreferredSize(new java.awt.Dimension(350, 28));

        browseImageButton = new javax.swing.JButton("Browse Image");
        browseImageButton.setBackground(new java.awt.Color(79, 70, 229));
        browseImageButton.setForeground(java.awt.Color.WHITE);
        browseImageButton.setFocusPainted(false);
        browseImageButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        browseImageButton.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        browseImageButton.addActionListener(e -> browseImage());

        javax.swing.JPanel browseRow = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        browseRow.setOpaque(false);
        browseRow.add(imagePathField);
        browseRow.add(browseImageButton);

        javax.swing.JPanel imageSection = new javax.swing.JPanel();
        imageSection.setLayout(new java.awt.BorderLayout(5, 8));
        imageSection.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)),
                "Product Image",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)));
        imageSection.add(browseRow, java.awt.BorderLayout.NORTH);
        imageSection.add(imagePreviewLabel, java.awt.BorderLayout.CENTER);

        // Re-layout dialog to fit image panel on the right
        getContentPane().setLayout(new java.awt.BorderLayout(10, 0));
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        getContentPane().add(imageSection, java.awt.BorderLayout.EAST);
        pack();
    }

    private void loadProduct() {

        try {
            ResultSet rs1 = Mysql.excute("SELECT * FROM `product` WHERE `product`.`pid` = '" + productId + "' ");
            while (rs1.next()) {
                String name = rs1.getString("product.name");
                prNameInput.setText(name);
                prNameInput.setEditable(false);

                int status_sid = rs1.getInt("status_sid");
                sliderStatus.setValue((status_sid == 1) ? 1 : 0);
            }
            int currentSupplierId = -1;
            ResultSet rs2 = Mysql.excute("SELECT * FROM `stock` WHERE `stock`.`product_pid` = '" + productId + "' ");
            if (rs2.next()) {
                int qty = rs2.getInt("qty");
                double price = rs2.getDouble("price");
                currentSupplierId = rs2.getInt("suppliers_supid");

                prQty.setText(String.valueOf(qty));
                prPrice.setText(String.valueOf(price));
            }

            // Load existing product image
            ResultSet rsImg = Mysql.excute("SELECT `path` FROM `product_image` WHERE `product_id` = '" + productId + "'");
            if (rsImg.next()) {
                String existingPath = rsImg.getString("path");
                if (existingPath != null && !existingPath.isBlank()) {
                    showImagePreview(existingPath);
                    imagePathField.setText(existingPath);
                }
            }

            Vector<String> supplier = new Vector();
            supplier.add("Select supplier");
            supplierMap.clear();
            ResultSet rs3 = Mysql.excute("SELECT * FROM `suppliers` WHERE `status_sid` = 1");
            String selectedSupplierName = "Select Supplier";
            while (rs3.next()) {

                String supplierName = rs3.getString("name");
                int id = rs3.getInt("supid");

                supplierMap.put(supplierName, id);
                supplier.add(supplierName);
                if (id == currentSupplierId) {
                    selectedSupplierName = supplierName;

                }

            }
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(supplier);
            prSupplierCombo.setModel(model);
            prSupplierCombo.setSelectedItem(selectedSupplierName);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveProduct() {
        try {
            String qtyStr = prQty.getText().trim();
            String priceStr = prPrice.getText().trim();
            String selectedSupplierName = prSupplierCombo.getSelectedItem().toString();
            logger.info("Saving product update: productId=" + productId + ", qty=" + qtyStr + ", price=" + priceStr + ", supplier=" +
                    selectedSupplierName + ", status=" + sliderStatus.getValue());
            if (!productValidator.isNonNegativeDouble(prPrice.getText(), "Product price")) {
                return;
            }
            if (!productValidator.isNonNegativeInt(prQty.getText(), "Product quntity")) {
                return;
            }
            if (!productValidator.isComboBoxValid(prSupplierCombo, "supplier")) {
                return;
            }
            int qty = Integer.parseInt(qtyStr);
            double price = Double.parseDouble(priceStr);

            int supid = supplierMap.get(selectedSupplierName);
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ResultSet rs = Mysql.excute("SELECT * FROM `stock` WHERE `product_pid` = '" + productId + "'");

            int sliderVal = sliderStatus.getValue();
            int status_sid = (sliderVal == 1) ? 1 : 2;
            if (rs.next()) {

                Mysql.excute("UPDATE `stock` SET `qty` = '" + qty
                        + "', `price` = '" + price
                        + "', `suppliers_supid` = '" + supid
                        + "', `update_date` = '" + now
                        + "' WHERE `product_pid` = '" + productId + "'");
            } else {

                Mysql.excute("INSERT INTO `stock` (`product_pid`, `qty`, `price`, `suppliers_supid`, `update_date`) VALUES ("
                        + "'" + productId + "', '" + qty + "', '" + price + "', '" + supid + "', '" + now + "')");

            }
            Mysql.excute("UPDATE `product` SET `status_sid` = '" + status_sid + "' WHERE `pid` = '" + productId + "'");
            // Update image if a new one was selected
            if (selectedImagePath != null && !selectedImagePath.isBlank()) {
                String escapedPath = selectedImagePath.replace("\\", "\\\\");
                ResultSet rsImgCheck = Mysql.excute("SELECT `product_id` FROM `product_image` WHERE `product_id` = '" + productId + "'");
                if (rsImgCheck.next()) {
                    Mysql.excute("UPDATE `product_image` SET `path` = '" + escapedPath + "' WHERE `product_id` = '" + productId + "'");
                } else {
                    Mysql.excute("INSERT INTO `product_image`(`path`, `product_id`) VALUES ('" + escapedPath + "', '" + productId + "')");
                }
                logger.info("Product image updated for productId=" + productId);
            }
            logger.info("Product updated successfully: productId=" + productId);
            JOptionPane.showMessageDialog(this, "Product updated successfully!");

            refreshProductViews();

            dispose();

        } catch (NumberFormatException e) {
            logger.warning("Invalid input for quantity or price. Qty: " + prQty.getText() + ", Price: " + prPrice.getText());
            JOptionPane.showMessageDialog(this, "Invalid quantity or price.");
        } catch (Exception e) {
            logger.severe("Error saving product update for productId " + productId + ": " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving product: " + e.getMessage());
        }

    }

    private void deleteproduct() {

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to deactivate this product?", "Confirm Deactivate", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Mysql.excute("UPDATE `product` SET `status_sid` = '2' WHERE `pid` = '" + productId + "'");

                JOptionPane.showMessageDialog(this, "Product deactivated successfully.");

                refreshProductViews();
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deactivating product: " + e.getMessage());
            }
        }

    }

    private void browseImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files (.png, .jpg, .jpeg)", "png", "jpg", "jpeg");
        chooser.setFileFilter(filter);
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                File imageFolder = new File("Pimg");
                if (!imageFolder.exists()) {
                    imageFolder.mkdir();
                }
                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destination = new File(imageFolder, fileName);
                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                selectedImagePath = destination.getAbsolutePath();
                imagePathField.setText(selectedImagePath);
                showImagePreview(selectedImagePath);
                logger.info("New image selected for productId=" + productId + ": " + selectedImagePath);
            } catch (IOException e) {
                logger.severe("Error copying image: " + e.getMessage());
                JOptionPane.showMessageDialog(this, "Could not copy image file: " + e.getMessage());
            }
        }
    }

    private void showImagePreview(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(scaled));
            imagePreviewLabel.setText("");
        } else {
            imagePreviewLabel.setIcon(null);
            imagePreviewLabel.setText("Image not found");
        }
    }

    private void refreshProductViews() {
        if (homeScreen != null) {
            homeScreen.loadproductcard();
            homeScreen.loadDeactivatedProductCards();
            homeScreen.loadPurchaseHistory();
        }
        AppIconUtill.TableUtils.refreshTable(productTable, "SELECT `product`.`pid`, `product`.`name`, `brand`.`name`, `catagory`.`name`, `product`.`discription`"
                + "    FROM `product`"
                + "    LEFT JOIN `brand` ON `product`.`brand_bid` = `brand`.`bid`"
                + "    LEFT JOIN `catagory` ON `product`.`category_cid` = `catagory`.`cid`"
                + "    ORDER BY `product`.`pid` DESC", 5);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        prNameInput = new javax.swing.JTextField();
        prPrice = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        prQty = new javax.swing.JTextField();
        prSupplierCombo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        sliderStatus = new javax.swing.JSlider();
        Status = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        delete = new javax.swing.JButton();

        setResizable(false);

        jPanel3.setLayout(new java.awt.GridLayout(2, 2, 10, 5));

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel2.setText("Product Name");
        jPanel3.add(jLabel2);

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel1.setText("Price");
        jPanel3.add(jLabel1);

        prNameInput.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        prNameInput.setPreferredSize(new java.awt.Dimension(68, 30));
        jPanel3.add(prNameInput);
        jPanel3.add(prPrice);

        jPanel4.setLayout(new java.awt.GridLayout(2, 2, 10, 5));

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel5.setText("Quntity");
        jPanel4.add(jLabel5);

        jLabel7.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel7.setText("Supplier");
        jPanel4.add(jLabel7);
        jPanel4.add(prQty);

        prSupplierCombo.setFont(new java.awt.Font("Javanese Text", 1, 16)); // NOI18N
        prSupplierCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(prSupplierCombo);

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        jLabel3.setText("Product Update");

        sliderStatus.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStatusStateChanged(evt);
            }
        });

        Status.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        Status.setText("Active");

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(sliderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Status))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 22, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        saveProduct();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void sliderStatusStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStatusStateChanged
        // TODO add your handling code here:
        int val = sliderStatus.getValue();
        String statusText = (val == 1) ? "Active" : "Deactive";
        Status.setText(statusText);
        Status.setForeground((val == 1) ? java.awt.Color.GREEN.darker() : java.awt.Color.RED);
    }//GEN-LAST:event_sliderStatusStateChanged

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        // TODO add your handling code here:

        deleteproduct();


    }//GEN-LAST:event_deleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField prNameInput;
    private javax.swing.JTextField prPrice;
    private javax.swing.JTextField prQty;
    private javax.swing.JComboBox<String> prSupplierCombo;
    private javax.swing.JSlider sliderStatus;
    // End of variables declaration//GEN-END:variables
}
