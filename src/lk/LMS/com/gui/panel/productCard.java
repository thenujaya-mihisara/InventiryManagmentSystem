/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package lk.LMS.com.gui.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import lk.LMS.com.gui.HomeScreen;
import lk.LMS.com.gui.Logger.LMS_Logger;
import lk.LMS.com.gui.dialog.BuyNow;
import lk.LMS.com.gui.dialog.updateProduct;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

/**
 *
 * @author MB
 */
public class productCard extends javax.swing.JPanel {

    private String productId;
    private String productName;
    private String productCode;
    private int availableQty;
    private double productPrice;
    private static final java.util.logging.Logger logger = LMS_Logger.getLogger();
    private HomeScreen homeScreen;
    private JTable productTable;
    private boolean activeProduct;

    public productCard(String id, String name, int qty, double price, String path, JTable table, HomeScreen homeScreen) {
        this(id, name, "", qty, price, path, table, homeScreen, true);
    }

    public productCard(String id, String name, int qty, double price, String path, JTable table, HomeScreen homeScreen, boolean activeProduct) {
        this(id, name, "", qty, price, path, table, homeScreen, activeProduct);
    }

    public productCard(String id, String name, String code, int qty, double price, String path, JTable table, HomeScreen homeScreen, boolean activeProduct) {
        initComponents();
        this.productCode = code;
        this.activeProduct = activeProduct;
        this.homeScreen = homeScreen;
        this.productTable = table;
        applyCardStyle();
        initBarcodeClick();
        setData(id, name, qty, price, path);
    }

    public void setData(String id, String name, int qty, double price, String path) {
        this.productId = id;
        this.productName = name;
        this.availableQty = qty;
        this.productPrice = price;
        logger.info("Setting productCard data: id=" + id + ", name=" + name + ", qty=" + qty + ", price=" + price);
        cardName.setText(name);
        cardQty.setText("Qty: " + qty);
        cardPrice.setText("Price: Rs. " + price);
        jButton2.setEnabled(activeProduct);
        jButton2.setText(activeProduct ? "Buy Now" : "Inactive");
        jButton2.setToolTipText(activeProduct ? "Buy this product" : "Activate this product before selling it");

        ImageIcon icon;

        if (path != null && !path.isBlank() && new File(path).exists()) {
            icon = new ImageIcon(path);

        } else {
            icon = new ImageIcon(getClass().getResource("/lk/LMS/com/img/splashscreen1.png"));
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            int width = 200;
            int height = 185;

            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            cardImage.setIcon(new ImageIcon(img));
        });
    }

    private void initBarcodeClick() {
        cardImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardImage.setToolTipText("Click to view product barcode");
        cardImage.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showBarcodePopup();
            }
        });
    }

    private void showBarcodePopup() {
        if (productCode == null || productCode.isBlank()) {
            JOptionPane.showMessageDialog(this, "Product barcode code is not available.");
            return;
        }
        try {
            Barcode barcode = BarcodeFactory.createCode128(productCode);
            barcode.setBarHeight(90);
            barcode.setBarWidth(2);
            barcode.setDrawingText(true);
            BufferedImage barcodeImage = BarcodeImageHandler.getImage(barcode);
            JLabel barcodeLabel = new JLabel(new ImageIcon(barcodeImage));
            barcodeLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            JOptionPane.showMessageDialog(this, barcodeLabel, productName + " - Barcode", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            logger.severe("Barcode generation failed for productId " + productId + ": " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Could not generate barcode: " + e.getMessage());
        }
    }

    private void applyCardStyle() {
        Color surface = activeProduct ? Color.WHITE : new Color(248, 250, 252);
        Color border = activeProduct ? new Color(203, 213, 225) : new Color(226, 232, 240);
        Color text = new Color(15, 23, 42);
        Color mutedText = new Color(71, 85, 105);
        Color buyColor = activeProduct ? new Color(37, 99, 235) : new Color(148, 163, 184);
        Color updateColor = activeProduct ? new Color(5, 150, 105) : new Color(217, 119, 6);

        setOpaque(true);
        setBackground(surface);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setPreferredSize(new java.awt.Dimension(248, 430));
        setMinimumSize(new java.awt.Dimension(248, 430));
        setMaximumSize(new java.awt.Dimension(248, 430));

        cardName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 17));
        cardName.setForeground(text);
        cardQty.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        cardQty.setForeground(mutedText);
        cardPrice.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
        cardPrice.setForeground(text);
        cardImage.setOpaque(true);
        cardImage.setBackground(new Color(241, 245, 249));
        cardImage.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        styleButton(jButton1, updateColor);
        styleButton(jButton2, buyColor);
    }

    private void styleButton(javax.swing.JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardName = new javax.swing.JLabel();
        cardQty = new javax.swing.JLabel();
        cardImage = new javax.swing.JLabel();
        cardPrice = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setAlignmentX(10.0F);
        setAlignmentY(5.0F);
        setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        setMaximumSize(new java.awt.Dimension(236, 423));
        setMinimumSize(new java.awt.Dimension(236, 423));

        cardName.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        cardName.setText("jLabel1");

        cardQty.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        cardQty.setText("jLabel2");

        cardPrice.setFont(new java.awt.Font("Segoe UI Variable", 1, 18)); // NOI18N
        cardPrice.setText("jLabel4");

        jButton1.setBackground(new java.awt.Color(0, 204, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(51, 51, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Buy Now");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(cardImage, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardQty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(cardImage, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cardName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cardQty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cardPrice)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        logger.info("Opening updateProduct dialog for productId: " + productId);
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        java.awt.Frame parentFrame = (java.awt.Frame) window;

        updateProduct dialog = new updateProduct(parentFrame, true, productId, productTable, homeScreen);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!activeProduct) {
            javax.swing.JOptionPane.showMessageDialog(this, "Activate this product before selling it.");
            return;
        }
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        java.awt.Frame parentFrame = (java.awt.Frame) window;

        BuyNow buynow = new BuyNow(parentFrame, true, productId, productName, productPrice, availableQty, homeScreen);
        buynow.setLocationRelativeTo(parentFrame);
        buynow.setVisible(true);


    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cardImage;
    private javax.swing.JLabel cardName;
    private javax.swing.JLabel cardPrice;
    private javax.swing.JLabel cardQty;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
