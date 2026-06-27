/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package lk.LMS.com.gui.dialog;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import lk.LMS.com.gui.connection.Mysql;
import java.sql.ResultSet;
import lk.LMS.com.gui.HomeScreen;
import java.util.logging.Logger;

/**
 *
 * @author MB
 */
public class BuyNow extends javax.swing.JDialog {

    private static final Logger logger = Logger.getLogger(BuyNow.class.getName());
    private String productId;
    private String productName;
    private double productPrice;
    private int availableQty;
    private HomeScreen homeScreen;

    public BuyNow(java.awt.Frame parent, boolean modal, String productId, String productName, double price, int qty, HomeScreen screen) {
        super(parent, modal);
        initComponents();
        this.productId = productId;
        this.productName = productName;
        this.productPrice = price;
        this.availableQty = qty;
        this.homeScreen = screen;

        logger.info("Opening BuyNow dialog for productId: " + productId);

        applyModernUi();
        bname.setText(productName);
        bprice.setText("Price: Rs. " + price);
        bqty.setText("Available stock: " + qty);

        if (qty > 0) {
            Spinnerqty.setModel(new javax.swing.SpinnerNumberModel(1, 1, qty, 1));
            updateBuyButtonTotal();
        } else {
            Spinnerqty.setModel(new javax.swing.SpinnerNumberModel(0, 0, 0, 1));
            buy.setEnabled(false);
            buy.setText("Out of Stock");
        }
        Spinnerqty.addChangeListener(evt -> updateBuyButtonTotal());
    }

    public void buyProduct() {

        try {
            int buyQty = (Integer) Spinnerqty.getValue(); // spinnerQty = user input
            if (buyQty <= 0) {
                JOptionPane.showMessageDialog(this, "Please select at least one item.");
                return;
            }
            if (buyQty > availableQty) {
                JOptionPane.showMessageDialog(this, "Not enough stock available.");
                return;
            }
            ResultSet statusCheck = Mysql.excute("SELECT `status_sid` FROM `product` WHERE `pid` = '" + productId + "'");
        if (statusCheck.next()) {
            if (statusCheck.getInt("status_sid") != 1) {
                JOptionPane.showMessageDialog(this, "This product is inactive and cannot be purchased.");
                return;
            }
        }
            logger.info("Attempting to purchase productId: " + productId + " with qty: " + Spinnerqty.getValue());
            double totalPrice = buyQty * productPrice;

            Mysql.excute("INSERT INTO `purchase_order` (`total_price`, `purchased_at`) VALUES (" + totalPrice + ", NOW())");

            ResultSet rs = Mysql.excute("SELECT LAST_INSERT_ID() AS order_id");
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt("order_id");
            } else {
                JOptionPane.showMessageDialog(this, "can't find order ID.");
                return;
            }

            ResultSet stockRs = Mysql.excute("SELECT `stid` FROM `stock` WHERE `product_pid` = '" + productId + "'");
            int stockId = 0;
            if (stockRs.next()) {
                stockId = stockRs.getInt("stid");

            } else {
                JOptionPane.showMessageDialog(this, "stock empty.");
                return;
            } 
           
            Mysql.excute("INSERT INTO `purchase_item` (`qty`, `price`, `purchase_order_id`, `stock_stid`) "
                    + "VALUES (" + buyQty + ", " + productPrice + ", " + orderId + ", " + stockId + ")");

            Mysql.excute("UPDATE `stock` SET `qty` = qty - " + buyQty + " WHERE `stid` = " + stockId);

            JOptionPane.showMessageDialog(this, "Purchase successful!","Success",JOptionPane.INFORMATION_MESSAGE);
            logger.info("Purchase completed for productId: " + productId + ", qty: " + buyQty);
            if (homeScreen != null) {
                homeScreen.loadproductcard();
                homeScreen.loadPurchaseHistory();
            }
            this.dispose(); 

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            logger.severe("Error while purchasing productId: " + productId + " - " + e.getMessage());
        }
    }

    private void applyModernUi() {
        Color background = new Color(248, 250, 252);
        Color text = new Color(15, 23, 42);
        Color mutedText = new Color(71, 85, 105);
        Color primary = new Color(37, 99, 235);
        Color border = new Color(203, 213, 225);

        jPanel1.setBackground(background);
        jPanel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        jLabel1.setForeground(text);
        bname.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        bname.setForeground(text);
        bprice.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        bprice.setForeground(mutedText);
        bqty.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        bqty.setForeground(mutedText);

        Spinnerqty.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        Spinnerqty.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        jSeparator1.setForeground(border);
        jSeparator1.setBackground(border);

        styleButton(buy, primary, Color.WHITE);
        styleButton(cancel, Color.WHITE, mutedText);
        cancel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    }

    private void styleButton(javax.swing.JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
    }

    private void updateBuyButtonTotal() {
        int buyQty = (Integer) Spinnerqty.getValue();
        double total = buyQty * productPrice;
        buy.setText("Buy - Rs. " + String.format("%.2f", total));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bname = new javax.swing.JLabel();
        bprice = new javax.swing.JLabel();
        bqty = new javax.swing.JLabel();
        Spinnerqty = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        cancel = new javax.swing.JButton();
        buy = new javax.swing.JButton();

        setUndecorated(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setFont(new java.awt.Font("Serif", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buy Now");

        bname.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        bname.setForeground(new java.awt.Color(255, 255, 255));
        bname.setText("jLabel2");

        bprice.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        bprice.setForeground(new java.awt.Color(255, 255, 255));
        bprice.setText("jLabel3");

        bqty.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        bqty.setForeground(new java.awt.Color(255, 255, 255));
        bqty.setText("jLabel4");

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));

        cancel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        buy.setBackground(new java.awt.Color(51, 102, 255));
        buy.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        buy.setForeground(new java.awt.Color(255, 255, 255));
        buy.setText("Buy Now");
        buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Spinnerqty, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bqty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bprice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(buy)
                        .addGap(29, 29, 29))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bname, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bprice, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bqty, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Spinnerqty, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buy, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
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

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void buyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyActionPerformed
        buyProduct();
    }//GEN-LAST:event_buyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner Spinnerqty;
    private javax.swing.JLabel bname;
    private javax.swing.JLabel bprice;
    private javax.swing.JLabel bqty;
    private javax.swing.JButton buy;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
