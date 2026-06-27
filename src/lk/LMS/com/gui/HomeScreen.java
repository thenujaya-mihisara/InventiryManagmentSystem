
package lk.LMS.com.gui;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import lk.LMS.com.gui.Logger.LMS_Logger;
import lk.LMS.com.gui.connection.Mysql;
import lk.LMS.com.gui.dialog.AddBrand;
import lk.LMS.com.gui.dialog.AddSupplierDialog;
import lk.LMS.com.gui.dialog.AddUserDialog;
import lk.LMS.com.gui.dialog.UserActiveDeactive;
import lk.LMS.com.gui.dialog.addProduct;
import lk.LMS.com.gui.dialog.supplierActiveDeactive;
import lk.LMS.com.gui.panel.productCard;
import lk.LMS.com.gui.util.AppIconUtill;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.ReportExportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class HomeScreen
extends JFrame {
    private static final String DATABASE_NAME = "inventorymanagmentsystem";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "1234";
    private static final Color APP_BACKGROUND = new Color(247, 249, 252);
    private static final Color SURFACE = Color.WHITE;
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color TEXT = new Color(15, 23, 42);
    private static final Color MUTED_TEXT = new Color(71, 85, 105);
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DANGER = new Color(220, 38, 38);
    private static final Color SUCCESS = new Color(5, 150, 105);
    private static final Color WARNING = new Color(217, 119, 6);
    private JTable purchaseHistoryTable;
    private JTextField activeProductSearchInput;
    private JTextField inactiveProductSearchInput;
    private JTextField productTableSearchInput;
    private JTextField purchaseHistorySearchInput;
    private JPanel cardContainer;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane5;
    private JTabbedPane jTabbedPane1;
    private JTable jTable1;
    private JTable jTable2;
    private JTable jTable3;
    private JPanel deactivatedCardContainer;

    public HomeScreen() {
        this.initComponents();
        this.setExtendedState(6);
        this.init();
        this.connectionproduct();
        this.connectionuser();
        this.connectinsupplers();
        this.loadproductcard();
        this.loadDeactivatedProductCards();
        this.loadPurchaseHistory();
    }

    private void exportReportToExcel(JasperPrint fillReport, String fileName) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(Boolean.valueOf(false));
        configuration.setDetectCellType(Boolean.valueOf(true));
        configuration.setCollapseRowSpan(Boolean.valueOf(false));
        exporter.setExporterInput(new SimpleExporterInput(fillReport));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    private void init() {
        AppIconUtill.applyIcon(this);
        FlatSVGIcon productsIcon1 = new FlatSVGIcon("lk/LMS/com/img/box.svg", 22, 22);
        FlatSVGIcon productsIcon2 = new FlatSVGIcon("lk/LMS/com/img/user.svg", 22, 22);
        FlatSVGIcon productsIcon3 = new FlatSVGIcon("lk/LMS/com/img/suplliers.svg", 22, 22);
        FlatSVGIcon productsIcon4 = new FlatSVGIcon("lk/LMS/com/img/dashboard.svg", 22, 22);
        FlatSVGIcon inactiveProductsIcon = new FlatSVGIcon("lk/LMS/com/img/none.svg", 22, 22);
        FlatSVGIcon purchaseHistoryIcon = new FlatSVGIcon("lk/LMS/com/img/history.svg", 22, 22);
        this.jScrollPane5.getVerticalScrollBar().setUnitIncrement(20);
        this.setupDashboardSearch();
        this.setupDatabaseMaintenancePanel();
        this.jTabbedPane1.removeAll();
        this.jTabbedPane1.addTab("Dashboard", (Icon)productsIcon4, this.jPanel7);
        this.jTabbedPane1.addTab("Products", (Icon)productsIcon1, this.createProductManagementPanel());
        this.jTabbedPane1.addTab("Users", (Icon)productsIcon2, this.jPanel2);
        this.jTabbedPane1.addTab("Suppliers", (Icon)productsIcon3, this.jPanel6);
        this.deactivatedCardContainer = new JPanel(new FlowLayout(1, 15, 15));
        JScrollPane deactivatedScrollPane = new JScrollPane(this.deactivatedCardContainer);
        deactivatedScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        JPanel inactiveProductsPanel = new JPanel(new BorderLayout(0, 10));
        inactiveProductsPanel.setBackground(APP_BACKGROUND);
        this.inactiveProductSearchInput = this.createSearchField("Search inactive products...");
        this.inactiveProductSearchInput.getDocument().addDocumentListener(this.createSearchListener(() -> this.loadDeactivatedProductCards()));
        inactiveProductsPanel.add(this.inactiveProductSearchInput, BorderLayout.NORTH);
        inactiveProductsPanel.add(deactivatedScrollPane, BorderLayout.CENTER);
        this.jTabbedPane1.addTab("Inactive Products", (Icon)inactiveProductsIcon, inactiveProductsPanel);
        this.setupPurchaseHistoryTab(purchaseHistoryIcon);
        this.applyModernUiStyles();
    }

    private void setupDashboardSearch() {
        this.activeProductSearchInput = this.createSearchField("Search active products...");
        this.activeProductSearchInput.getDocument().addDocumentListener(this.createSearchListener(() -> this.loadproductcard()));
        this.jPanel7.remove(this.jScrollPane5);
        this.jPanel7.setLayout(new BorderLayout(0, 10));
        this.jPanel7.add(this.activeProductSearchInput, BorderLayout.NORTH);
        this.jPanel7.add(this.jScrollPane5, BorderLayout.CENTER);
    }

    private JTextField createSearchField(String placeholder) {
        JTextField searchField = new JTextField();
        searchField.putClientProperty("JTextField.placeholderText", placeholder);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(240, 38));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return searchField;
    }

    private JPanel createProductManagementPanel() {
        JPanel productPanel = new JPanel(new BorderLayout(0, 10));
        productPanel.setBackground(APP_BACKGROUND);
        this.productTableSearchInput = this.createSearchField("Search products table...");
        this.productTableSearchInput.getDocument().addDocumentListener(this.createSearchListener(() -> this.connectionproduct()));
        productPanel.add(this.productTableSearchInput, BorderLayout.NORTH);
        productPanel.add(this.jPanel1, BorderLayout.CENTER);
        return productPanel;
    }

    private DocumentListener createSearchListener(Runnable refreshAction) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshAction.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshAction.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshAction.run();
            }
        };
    }

    private void applyModernUiStyles() {
        this.getContentPane().setBackground(APP_BACKGROUND);
        this.jPanel1.setBackground(APP_BACKGROUND);
        this.jPanel2.setBackground(APP_BACKGROUND);
        this.jPanel3.setBackground(APP_BACKGROUND);
        this.jPanel4.setBackground(APP_BACKGROUND);
        this.jPanel5.setBackground(APP_BACKGROUND);
        this.jPanel6.setBackground(APP_BACKGROUND);
        this.jPanel7.setBackground(APP_BACKGROUND);
        this.cardContainer.setBackground(APP_BACKGROUND);
        if (this.deactivatedCardContainer != null) {
            this.deactivatedCardContainer.setBackground(APP_BACKGROUND);
        }
        this.jLabel2.setFont(new Font("Segoe UI", 1, 28));
        this.jLabel2.setForeground(TEXT);
        this.jLabel2.setText("Inventory Control System");
        this.jTabbedPane1.setFont(new Font("Segoe UI", 1, 14));
        this.jTabbedPane1.setBackground(SURFACE);
        this.jTabbedPane1.setForeground(MUTED_TEXT);
        this.jTabbedPane1.putClientProperty("JTabbedPane.tabHeight", 42);
        this.jTabbedPane1.putClientProperty("JTabbedPane.tabInsets", new Insets(8, 14, 8, 14));
        this.jTabbedPane1.putClientProperty("JTabbedPane.contentSeparatorHeight", 1);
        this.jTabbedPane1.putClientProperty("JTabbedPane.showTabSeparators", true);
        this.styleTable(this.jTable1);
        this.styleTable(this.jTable2);
        this.styleTable(this.jTable3);
        this.styleTable(this.purchaseHistoryTable);
        this.styleScrollPane(this.jScrollPane1);
        this.styleScrollPane(this.jScrollPane2);
        this.styleScrollPane(this.jScrollPane3);
        this.styleScrollPane(this.jScrollPane5);
        this.styleButton(this.jButton1, PRIMARY);
        this.styleButton(this.jButton2, DANGER);
        this.styleButton(this.jButton3, PRIMARY);
        this.styleButton(this.jButton4, DANGER);
        this.styleButton(this.jButton5, DANGER);
        this.styleButton(this.jButton6, PRIMARY);
        this.styleButton(this.jButton7, MUTED_TEXT);
        this.styleButton(this.jButton8, MUTED_TEXT);
        this.styleButton(this.jButton9, MUTED_TEXT);
    }

    private void styleTable(JTable table) {
        if (table == null) {
            return;
        }
        table.setFont(new Font("Segoe UI", 0, 14));
        table.setRowHeight(42);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT);
        table.setForeground(TEXT);
        table.setBackground(SURFACE);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", 1, 13));
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(MUTED_TEXT);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
    }

    private void styleScrollPane(JScrollPane scrollPane) {
        if (scrollPane == null) {
            return;
        }
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(SURFACE);
        scrollPane.setBackground(SURFACE);
    }

    private void styleButton(JButton button, Color background) {
        if (button == null) {
            return;
        }
        button.setFont(new Font("Segoe UI", 1, 13));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        button.putClientProperty("JButton.buttonType", "roundRect");
        button.putClientProperty("JComponent.minimumWidth", 110);
    }

    private void setupPurchaseHistoryTab(FlatSVGIcon tabIcon) {
        this.purchaseHistoryTable = new JTable();
        this.purchaseHistoryTable.setFont(new Font("Sitka Text", 3, 20));
        this.purchaseHistoryTable.setRowHeight(42);
        this.purchaseHistoryTable.setModel(new DefaultTableModel(new Object[0][], new String[]{"Order ID", "Product", "Code", "Qty", "Unit Price", "Total", "Date"}){
            boolean[] canEdit;
            {
                this.canEdit = new boolean[]{false, false, false, false, false, false, false};
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        JScrollPane purchaseScrollPane = new JScrollPane(this.purchaseHistoryTable);
        this.purchaseHistorySearchInput = this.createSearchField("Search purchase history...");
        this.purchaseHistorySearchInput.getDocument().addDocumentListener(this.createSearchListener(() -> this.loadPurchaseHistory()));
        JButton refreshButton = new JButton("Refresh");
        JButton reportButton = new JButton("Get Purchase Report");
        this.styleButton(refreshButton, MUTED_TEXT);
        this.styleButton(reportButton, DANGER);
        refreshButton.addActionListener(evt -> this.loadPurchaseHistory());
        reportButton.addActionListener(evt -> this.generatePurchaseHistoryReport());
        JPanel buttonPanel = new JPanel(new FlowLayout(2, 15, 10));
        buttonPanel.add(refreshButton);
        buttonPanel.add(reportButton);
        JPanel purchasePanel = new JPanel(new BorderLayout(10, 10));
        purchasePanel.setBackground(APP_BACKGROUND);
        buttonPanel.setBackground(APP_BACKGROUND);
        this.styleScrollPane(purchaseScrollPane);
        purchasePanel.add((Component)this.purchaseHistorySearchInput, "North");
        purchasePanel.add((Component)purchaseScrollPane, "Center");
        purchasePanel.add((Component)buttonPanel, "South");
        this.jTabbedPane1.addTab("  Purchase History  ", (Icon)tabIcon, purchasePanel);
    }

    public void loadPurchaseHistory() {
        if (this.purchaseHistoryTable == null) {
            return;
        }
        try {
            String searchFilter = this.buildPurchaseHistorySearchFilter();
            ResultSet rs = Mysql.excute("SELECT `purchase_item`.`purchase_order_id`, `product`.`name`, `product`.`product_code`, `purchase_item`.`qty`, `purchase_item`.`price`, (`purchase_item`.`qty` * `purchase_item`.`price`) AS item_total, `purchase_order`.`purchased_at` FROM `purchase_item` INNER JOIN `purchase_order` ON `purchase_item`.`purchase_order_id` = `purchase_order`.`id` INNER JOIN `stock` ON `purchase_item`.`stock_stid` = `stock`.`stid` INNER JOIN `product` ON `stock`.`product_pid` = `product`.`pid` " + searchFilter + " ORDER BY `purchase_order`.`purchased_at` DESC");
            DefaultTableModel dtm = (DefaultTableModel)this.purchaseHistoryTable.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> row = new Vector<String>();
                row.add(rs.getString("purchase_order_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("product_code"));
                row.add(rs.getString("qty"));
                row.add(rs.getString("price"));
                row.add(rs.getString("item_total"));
                row.add(rs.getString("purchased_at"));
                dtm.addRow(row);
            }
        }
        catch (SQLException e) {
            LMS_Logger.getLogger().log(Level.SEVERE, "Purchase history loading failed.", e);
            JOptionPane.showMessageDialog(this, "Purchase history loading failed: " + e.getMessage(), "Database Error", 0);
        }
    }

    private String buildPurchaseHistorySearchFilter() {
        String keyword = this.getSearchText(this.purchaseHistorySearchInput);
        if (keyword.isEmpty()) {
            return "";
        }
        String search = this.escapeSql(keyword);
        return "WHERE `purchase_item`.`purchase_order_id` LIKE '%" + search + "%'"
                + " OR `product`.`name` LIKE '%" + search + "%'"
                + " OR `product`.`product_code` LIKE '%" + search + "%'"
                + " OR `purchase_order`.`purchased_at` LIKE '%" + search + "%'";
    }

    private void generatePurchaseHistoryReport() {
        try {
            if (this.purchaseHistoryTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No purchase history records to report.");
                return;
            }
            JasperPrint fillReport = this.createTableReport("Purchase History Report", this.purchaseHistoryTable);
            JasperViewer.viewReport((JasperPrint)fillReport, (boolean)false);
            JasperExportManager.exportReportToPdfFile((JasperPrint)fillReport, (String)"report_purchase_history.pdf");
            this.exportReportToExcel(fillReport, "report_purchase_history.xlsx");
            LMS_Logger.getLogger().info("Purchase history report generated successfully.");
        }
        catch (JRException e) {
            LMS_Logger.getLogger().log(Level.SEVERE, "Purchase history report failed.", e);
            JOptionPane.showMessageDialog(this, "Purchase history report failed: " + e.getMessage(), "Jasper Error", 0);
        }
    }

    private JasperPrint createTableReport(String title, JTable table) throws JRException {
        JasperDesign design = new JasperDesign();
        design.setName(title.replace(" ", "_"));
        design.setPageWidth(842);
        design.setPageHeight(595);
        design.setColumnWidth(782);
        design.setLeftMargin(30);
        design.setRightMargin(30);
        design.setTopMargin(30);
        design.setBottomMargin(30);
        JRDesignStyle headerStyle = new JRDesignStyle();
        headerStyle.setName("HeaderStyle");
        headerStyle.setFontSize(Float.valueOf(10.0f));
        headerStyle.setBold(Boolean.valueOf(true));
        headerStyle.setMode(ModeEnum.OPAQUE);
        headerStyle.setBackcolor(new Color(220, 220, 220));
        headerStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        headerStyle.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        design.addStyle((JRStyle)headerStyle);
        JRDesignStyle detailStyle = new JRDesignStyle();
        detailStyle.setName("DetailStyle");
        detailStyle.setFontSize(Float.valueOf(9.0f));
        detailStyle.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        design.addStyle((JRStyle)detailStyle);
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(45);
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setText(title);
        titleText.setX(0);
        titleText.setY(0);
        titleText.setWidth(782);
        titleText.setHeight(35);
        titleText.setFontSize(Float.valueOf(18.0f));
        titleText.setBold(Boolean.valueOf(true));
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleBand.addElement((JRDesignElement)titleText);
        design.setTitle((JRBand)titleBand);
        int[] widths = new int[]{70, 180, 110, 60, 90, 90, 182};
        JRDesignBand columnHeader = new JRDesignBand();
        columnHeader.setHeight(28);
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(24);
        int x = 0;
        for (int i = 0; i < table.getColumnCount(); ++i) {
            String fieldName = table.getColumnName(i);
            JRDesignField field = new JRDesignField();
            field.setName(fieldName);
            field.setValueClass(String.class);
            design.addField((JRField)field);
            JRDesignStaticText headerText = new JRDesignStaticText();
            headerText.setText(fieldName);
            headerText.setX(x);
            headerText.setY(0);
            headerText.setWidth(widths[i]);
            headerText.setHeight(28);
            headerText.setStyle((JRStyle)headerStyle);
            columnHeader.addElement((JRDesignElement)headerText);
            JRDesignTextField detailText = new JRDesignTextField();
            detailText.setX(x);
            detailText.setY(0);
            detailText.setWidth(widths[i]);
            detailText.setHeight(24);
            detailText.setStyle((JRStyle)detailStyle);
            detailText.setExpression((JRExpression)new JRDesignExpression("$F{" + fieldName + "}"));
            detailBand.addElement((JRDesignElement)detailText);
            x += widths[i];
        }
        design.setColumnHeader((JRBand)columnHeader);
        ((JRDesignSection)design.getDetailSection()).addBand((JRBand)detailBand);
        JasperReport report = JasperCompileManager.compileReport((JasperDesign)design);
        return JasperFillManager.fillReport((JasperReport)report, new HashMap(), (JRDataSource)new JRTableModelDataSource(table.getModel()));
    }

    private void setupDatabaseMaintenancePanel() {
        JButton backupButton = new JButton("Backup Database");
        JButton restoreButton = new JButton("Restore Database");
        this.styleButton(backupButton, SUCCESS);
        this.styleButton(restoreButton, WARNING);
        backupButton.addActionListener(evt -> this.backupDatabase());
        restoreButton.addActionListener(evt -> this.restoreDatabase());
        JPanel maintenancePanel = new JPanel(new FlowLayout(2, 15, 10));
        maintenancePanel.setBackground(APP_BACKGROUND);
        maintenancePanel.add(backupButton);
        maintenancePanel.add(restoreButton);
        this.jPanel7.removeAll();
        this.jPanel7.setLayout(new BorderLayout(10, 10));
        this.jPanel7.add((Component)maintenancePanel, "North");
        this.jPanel7.add((Component)this.jScrollPane5, "Center");
        this.jPanel7.revalidate();
        this.jPanel7.repaint();
    }

    private void backupDatabase() {
        File backupDirectory = new File("database_backups");
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File suggestedFile = new File(backupDirectory, "inventorymanagmentsystem_backup_" + timestamp + ".sql");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Database Backup");
        fileChooser.setSelectedFile(suggestedFile);
        fileChooser.setFileFilter(new FileNameExtensionFilter("SQL Backup File (*.sql)", "sql"));
        if (fileChooser.showSaveDialog(this) != 0) {
            return;
        }
        File backupFile = this.ensureSqlExtension(fileChooser.getSelectedFile());
        this.runDatabaseTask("Database backup completed successfully:\n" + backupFile.getAbsolutePath(), "Database backup failed.", List.of("mysqldump", "-u", DATABASE_USER, "-p1234", DATABASE_NAME), backupFile, null);
    }

    private void restoreDatabase() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Database Backup");
        fileChooser.setFileFilter(new FileNameExtensionFilter("SQL Backup File (*.sql)", "sql"));
        if (fileChooser.showOpenDialog(this) != 0) {
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "This will restore the selected SQL file into the database. Continue?", "Confirm Database Restore", 0);
        if (confirm != 0) {
            return;
        }
        File sqlFile = fileChooser.getSelectedFile();
        this.runDatabaseTask("Database restore completed successfully.", "Database restore failed.", List.of("mysql", "-u", DATABASE_USER, "-p1234", DATABASE_NAME), null, sqlFile);
    }

    private File ensureSqlExtension(File file) {
        if (file.getName().toLowerCase().endsWith(".sql")) {
            return file;
        }
        return new File(file.getParentFile(), file.getName() + ".sql");
    }

    private void runDatabaseTask(final String successMessage, final String errorMessage, final List<String> command, final File outputFile, final File inputFile) {
        new SwingWorker<Integer, Void>(){
            private String processError = "";

            @Override
            protected Integer doInBackground() throws Exception {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                if (outputFile != null) {
                    processBuilder.redirectOutput(outputFile);
                }
                if (inputFile != null) {
                    processBuilder.redirectInput(inputFile);
                }
                processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);
                Process process = processBuilder.start();
                byte[] errorBytes = process.getErrorStream().readAllBytes();
                this.processError = new String(errorBytes);
                return process.waitFor();
            }

            @Override
            protected void done() {
                try {
                    int exitCode = (Integer)this.get();
                    if (exitCode == 0) {
                        LMS_Logger.getLogger().info(successMessage);
                        JOptionPane.showMessageDialog(HomeScreen.this, successMessage);
                        HomeScreen.this.connectionproduct();
                        HomeScreen.this.connectionuser();
                        HomeScreen.this.connectinsupplers();
                        HomeScreen.this.loadproductcard();
                        HomeScreen.this.loadDeactivatedProductCards();
                        HomeScreen.this.loadPurchaseHistory();
                    } else {
                        LMS_Logger.getLogger().severe(errorMessage + " " + this.processError);
                        JOptionPane.showMessageDialog(HomeScreen.this, errorMessage + "\n" + this.processError, "Database Error", 0);
                    }
                }
                catch (Exception e) {
                    String message = e.getCause() instanceof IOException ? "MySQL backup/restore command was not found. Add MySQL Server bin folder to PATH and try again." : e.getMessage();
                    LMS_Logger.getLogger().log(Level.SEVERE, errorMessage, e);
                    JOptionPane.showMessageDialog(HomeScreen.this, errorMessage + "\n" + message, "Database Error", 0);
                }
            }
        }.execute();
    }

    public void loadproductcard() {
        try {
            this.cardContainer.removeAll();
            String searchFilter = this.buildProductCardSearchFilter(this.activeProductSearchInput);
            ResultSet rs = Mysql.excute("SELECT * FROM `product` LEFT JOIN `stock` ON `product`.`pid` = `stock`.`product_pid` LEFT JOIN `product_image` ON `product`.`pid` = `product_image`.`product_id` WHERE `product`.`status_sid` = 1" + searchFilter + " ORDER BY `pid` DESC");
            int cardCount = 0;
            while (rs.next()) {
                String id = rs.getString("product.pid");
                String name = rs.getString("product.name");
                String productCode = rs.getString("product.product_code");
                double price = 0.0;
                int qty = 0;
                String path = " ";
                try {
                    if (rs.getObject("price") != null) {
                        price = rs.getDouble("price");
                    }
                    if (rs.getObject("qty") != null) {
                        qty = rs.getInt("qty");
                    }
                    if (rs.getObject("path") != null) {
                        path = rs.getString("path");
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    LMS_Logger.getLogger().severe("Error in division: " + e.getMessage());
                }
                productCard card = new productCard(id, name, productCode, qty, price, path, this.jTable1, this, true);
                this.cardContainer.add(card);
                ++cardCount;
            }
            this.cardContainer.revalidate();
            this.cardContainer.repaint();
            int rowCount = (int)Math.ceil((double)cardCount / 2.0);
            int cardHeight = 450;
            this.cardContainer.setPreferredSize(new Dimension(700, rowCount * cardHeight));
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading cards: " + e.getMessage());
            LMS_Logger.getLogger().severe("Error in division: " + e.getMessage());
        }
    }

    public void loadDeactivatedProductCards() {
        try {
            this.deactivatedCardContainer.removeAll();
            String searchFilter = this.buildProductCardSearchFilter(this.inactiveProductSearchInput);
            ResultSet rs = Mysql.excute("SELECT * FROM `product` LEFT JOIN `stock` ON `product`.`pid` = `stock`.`product_pid` LEFT JOIN `product_image` ON `product`.`pid` = `product_image`.`product_id` WHERE `product`.`status_sid` = 2" + searchFilter + " ORDER BY `pid` DESC");
            int cardCount = 0;
            while (rs.next()) {
                String id = rs.getString("product.pid");
                String name = rs.getString("product.name");
                String productCode = rs.getString("product.product_code");
                double price = 0.0;
                int qty = 0;
                String path = " ";
                try {
                    if (rs.getObject("price") != null) {
                        price = rs.getDouble("price");
                    }
                    if (rs.getObject("qty") != null) {
                        qty = rs.getInt("qty");
                    }
                    if (rs.getObject("path") != null) {
                        path = rs.getString("path");
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                productCard card = new productCard(id, name, productCode, qty, price, path, this.jTable1, this, false);
                this.deactivatedCardContainer.add(card);
                ++cardCount;
            }
            this.deactivatedCardContainer.revalidate();
            this.deactivatedCardContainer.repaint();
            int rowCount = (int)Math.ceil((double)cardCount / 2.0);
            int cardHeight = 450;
            this.deactivatedCardContainer.setPreferredSize(new Dimension(700, rowCount * cardHeight));
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading deactivated cards: " + e.getMessage());
        }
    }

    private String buildProductCardSearchFilter(JTextField searchInput) {
        String keyword = this.getSearchText(searchInput);
        if (keyword.isEmpty()) {
            return "";
        }
        String search = this.escapeSql(keyword);
        return " AND (`product`.`name` LIKE '%" + search + "%'"
                + " OR `product`.`product_code` LIKE '%" + search + "%'"
                + " OR `stock`.`qty` LIKE '%" + search + "%'"
                + " OR `stock`.`price` LIKE '%" + search + "%')";
    }

    private String getSearchText(JTextField searchInput) {
        return searchInput == null ? "" : searchInput.getText().trim();
    }

    private String escapeSql(String value) {
        return value.replace("'", "''").replace("\\", "\\\\");
    }

    private void connectionproduct() {
        try {
            String keyword = this.getSearchText(this.productTableSearchInput);
            String searchFilter = "";
            if (!keyword.isEmpty()) {
                String search = this.escapeSql(keyword);
                searchFilter = " WHERE (`product`.`name` LIKE '%" + search + "%'"
                        + " OR `brand`.`name` LIKE '%" + search + "%'"
                        + " OR `catagory`.`name` LIKE '%" + search + "%'"
                        + " OR `product`.`discription` LIKE '%" + search + "%'"
                        + " OR `product`.`pid` LIKE '%" + search + "%')";
            }
            ResultSet rs = Mysql.excute("SELECT * FROM `product` LEFT JOIN `brand` ON `product`.`brand_bid` = `brand`.`bid` LEFT JOIN `catagory` ON `product`.`category_cid` = `catagory`.`cid`" + searchFilter + " ORDER BY `product`.`pid` DESC");
            DefaultTableModel dtm = (DefaultTableModel)this.jTable1.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> v = new Vector<String>();
                this.jTable1.setRowHeight(50);
                v.add(rs.getString("product.pid"));
                v.add(rs.getString("product.name"));
                v.add(rs.getString("brand.name"));
                v.add(rs.getString("catagory.name"));
                v.add(rs.getString("product.discription"));
                dtm.addRow(v);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            LMS_Logger.getLogger().severe("Error in connectionproduct: " + e.getMessage());
        }
    }

    private void connectionuser() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM `admin` INNER JOIN `status` ON `admin`.`status_sid` = `status`.`sid` INNER JOIN `role` ON `admin`.`role_rid` = `role`.`rid` ORDER BY `aid` DESC");
            DefaultTableModel dtm = (DefaultTableModel)this.jTable2.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> v = new Vector<String>();
                this.jTable2.setRowHeight(50);
                v.add(rs.getString("aid"));
                v.add(rs.getString("name"));
                v.add(rs.getString("email"));
                v.add(rs.getString("password"));
                v.add(rs.getString("role.name"));
                v.add(rs.getString("status.name"));
                dtm.addRow(v);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            LMS_Logger.getLogger().severe("Error in division: " + e.getMessage());
        }
    }

    private void connectinsupplers() {
        try {
            ResultSet rs = Mysql.excute("SELECT * FROM suppliers ORDER BY supid DESC");
            DefaultTableModel dtm = (DefaultTableModel)this.jTable3.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> v = new Vector<String>();
                this.jTable3.setRowHeight(50);
                v.add(rs.getString("supid"));
                v.add(rs.getString("name"));
                v.add(rs.getString("email"));
                v.add(rs.getString("mobile"));
                dtm.addRow(v);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            LMS_Logger.getLogger().severe("Error in division: " + e.getMessage());
        }
    }

    private void initComponents() {
        this.jPanel4 = new JPanel();
        this.jLabel2 = new JLabel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jTabbedPane1.setFont(new Font("Segoe UI", 1, 32));
        this.jPanel7 = new JPanel();
        this.jScrollPane5 = new JScrollPane();
        this.cardContainer = new JPanel();
        this.jPanel1 = new JPanel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton7 = new JButton();
        this.jPanel2 = new JPanel();
        this.jScrollPane2 = new JScrollPane();
        this.jTable2 = new JTable();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jButton9 = new JButton();
        this.jPanel6 = new JPanel();
        this.jScrollPane3 = new JScrollPane();
        this.jTable3 = new JTable();
        this.jButton5 = new JButton();
        this.jButton6 = new JButton();
        this.jButton8 = new JButton();
        this.jPanel3 = new JPanel();
        this.jPanel5 = new JPanel();
        this.setDefaultCloseOperation(3);
        this.setTitle("Dashboard");
        this.jLabel2.setFont(new Font("Sitka Text", 3, 36));
        this.jLabel2.setText("Inventry control system");
        this.cardContainer.setLayout(new FlowLayout(1, 15, 15));
        this.jScrollPane5.setViewportView(this.cardContainer);
        GroupLayout jPanel7Layout = new GroupLayout(this.jPanel7);
        this.jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane5, -1, 1083, Short.MAX_VALUE).addContainerGap()));
        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane5, -1, 561, Short.MAX_VALUE).addContainerGap()));
        this.jTabbedPane1.addTab("                   Dashboard                        ", this.jPanel7);
        this.jTable1.setFont(new Font("Sitka Text", 3, 24));
        this.jTable1.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}, {null, null, null, null, null}}, new String[]{"ID", "Name", "Brand", "Category", "Discription"}){
            Class[] types;
            boolean[] canEdit;
            {
                this.types = new Class[]{String.class, String.class, String.class, String.class, String.class};
                this.canEdit = new boolean[]{false, false, false, false, false};
            }

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane1.setViewportView(this.jTable1);
        if (this.jTable1.getColumnModel().getColumnCount() > 0) {
            this.jTable1.getColumnModel().getColumn(0).setResizable(false);
            this.jTable1.getColumnModel().getColumn(1).setResizable(false);
            this.jTable1.getColumnModel().getColumn(2).setResizable(false);
            this.jTable1.getColumnModel().getColumn(3).setResizable(false);
            this.jTable1.getColumnModel().getColumn(4).setResizable(false);
        }
        this.jButton1.setBackground(new Color(51, 51, 255));
        this.jButton1.setForeground(new Color(255, 255, 255));
        this.jButton1.setText("Add Products");
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setBackground(new Color(255, 51, 51));
        this.jButton2.setForeground(new Color(255, 255, 255));
        this.jButton2.setText("Get Stock Report");
        this.jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton2ActionPerformed(evt);
            }
        });
        this.jButton7.setText("Add Brand");
        this.jButton7.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton7ActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 1095, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jButton7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jButton2, -2, 162, -2).addGap(18, 18, 18).addComponent(this.jButton1, -2, 156, -2).addGap(33, 33, 33)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 489, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton1, -1, 52, Short.MAX_VALUE).addComponent(this.jButton7)).addComponent(this.jButton2, -1, -1, Short.MAX_VALUE)).addGap(14, 14, 14)));
        this.jTabbedPane1.addTab("                   Product                        ", this.jPanel1);
        this.jTable2.setFont(new Font("Sitka Text", 3, 24));
        this.jTable2.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}}, new String[]{"User ID", "Name", "Email", "Password", "Role", "Status"}){
            Class[] types;
            boolean[] canEdit;
            {
                this.types = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class};
                this.canEdit = new boolean[]{false, false, false, false, false, false};
            }

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane2.setViewportView(this.jTable2);
        if (this.jTable2.getColumnModel().getColumnCount() > 0) {
            this.jTable2.getColumnModel().getColumn(0).setResizable(false);
            this.jTable2.getColumnModel().getColumn(1).setResizable(false);
            this.jTable2.getColumnModel().getColumn(2).setResizable(false);
            this.jTable2.getColumnModel().getColumn(3).setResizable(false);
            this.jTable2.getColumnModel().getColumn(4).setResizable(false);
            this.jTable2.getColumnModel().getColumn(5).setResizable(false);
        }
        this.jButton3.setBackground(new Color(51, 51, 255));
        this.jButton3.setForeground(new Color(255, 255, 255));
        this.jButton3.setText("Add User");
        this.jButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton3ActionPerformed(evt);
            }
        });
        this.jButton4.setBackground(new Color(255, 51, 51));
        this.jButton4.setForeground(new Color(255, 255, 255));
        this.jButton4.setText("Get User Report");
        this.jButton4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton4ActionPerformed(evt);
            }
        });
        this.jButton9.setText("Active/Deactive");
        this.jButton9.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton9ActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, -1, 1095, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addComponent(this.jButton9, -2, 135, -2).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addComponent(this.jButton4, -2, 156, -2).addGap(18, 18, 18).addComponent(this.jButton3, -2, 156, -2).addGap(31, 31, 31)))));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.jButton9).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -1, 440, Short.MAX_VALUE).addGap(27, 27, 27).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton4, -2, 52, -2).addComponent(this.jButton3, -2, 52, -2)).addGap(16, 16, 16)));
        this.jTabbedPane1.addTab("                     Users                    ", this.jPanel2);
        this.jTable3.setFont(new Font("Sitka Text", 3, 24));
        this.jTable3.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}}, new String[]{"Suppler ID", "Name", "Email", "Mobile"}){
            Class[] types;
            boolean[] canEdit;
            {
                this.types = new Class[]{String.class, String.class, String.class, String.class};
                this.canEdit = new boolean[]{false, false, false, false};
            }

            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane3.setViewportView(this.jTable3);
        if (this.jTable3.getColumnModel().getColumnCount() > 0) {
            this.jTable3.getColumnModel().getColumn(0).setResizable(false);
            this.jTable3.getColumnModel().getColumn(1).setResizable(false);
            this.jTable3.getColumnModel().getColumn(2).setResizable(false);
            this.jTable3.getColumnModel().getColumn(3).setResizable(false);
        }
        this.jButton5.setBackground(new Color(255, 51, 51));
        this.jButton5.setForeground(new Color(255, 255, 255));
        this.jButton5.setText("Get Supplers Report");
        this.jButton5.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton5ActionPerformed(evt);
            }
        });
        this.jButton6.setBackground(new Color(51, 51, 255));
        this.jButton6.setForeground(new Color(255, 255, 255));
        this.jButton6.setText("Add Supplers");
        this.jButton6.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton6ActionPerformed(evt);
            }
        });
        this.jButton8.setText("Active/Deactive");
        this.jButton8.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                HomeScreen.this.jButton8ActionPerformed(evt);
            }
        });
        GroupLayout jPanel6Layout = new GroupLayout(this.jPanel6);
        this.jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane3, -1, 1095, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup().addComponent(this.jButton8).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup().addComponent(this.jButton5, -2, 164, -2).addGap(18, 18, 18).addComponent(this.jButton6, -2, 156, -2).addGap(31, 31, 31)))));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(this.jButton8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane3).addGap(26, 26, 26).addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton5, -2, 52, -2).addComponent(this.jButton6, -2, 52, -2)).addGap(16, 16, 16)));
        this.jTabbedPane1.addTab("                  Suppliers                  ", this.jPanel6);
        GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
        this.jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        GroupLayout jPanel5Layout = new GroupLayout(this.jPanel5);
        this.jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        GroupLayout jPanel4Layout = new GroupLayout(this.jPanel4);
        this.jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jPanel5, -1, -1, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2, -2, 433, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel3, -1, -1, Short.MAX_VALUE)).addGroup(jPanel4Layout.createSequentialGroup().addComponent(this.jTabbedPane1).addContainerGap()));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jPanel5, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE))).addComponent(this.jPanel3, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTabbedPane1)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel4, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel4, -1, -1, Short.MAX_VALUE));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void jButton8ActionPerformed(ActionEvent evt) {
        LMS_Logger.getLogger().info("Supplier Active/Deactivate Dialog opened");
        supplierActiveDeactive SActiveDeactive = new supplierActiveDeactive(null, true, this.jTable3);
        SActiveDeactive.setLocationRelativeTo(null);
        SActiveDeactive.setVisible(true);
    }

    private void jButton6ActionPerformed(ActionEvent evt) {
        LMS_Logger.getLogger().info("Add Supplier Dialog opened");
        AddSupplierDialog suppler = new AddSupplierDialog(null, true, this.jTable3);
        suppler.setLocationRelativeTo(null);
        suppler.setVisible(true);
    }

    private void jButton5ActionPerformed(ActionEvent evt) {
        try {
            InputStream filePath = this.getClass().getClassLoader().getResourceAsStream("Report/supplier_Report.jasper");
            HashMap parameter = new HashMap();
            JRTableModelDataSource jrTableModelDataSource = new JRTableModelDataSource(this.jTable3.getModel());
            JasperPrint fillReport = JasperFillManager.fillReport((InputStream)filePath, parameter, (JRDataSource)jrTableModelDataSource);
            JasperViewer.viewReport((JasperPrint)fillReport, (boolean)false);
            JasperExportManager.exportReportToPdfFile((JasperPrint)fillReport, (String)"report_supplier.pdf");
            this.exportReportToExcel(fillReport, "report_supplier.xlsx");
            LMS_Logger.getLogger().info("supplier Report generated successfully.");
        }
        catch (JRException e) {
            LMS_Logger.getLogger().log(Level.SEVERE, "Jasper error occurred: ", e);
            JOptionPane.showMessageDialog(null, "An error occurred while generating the report." + e.getMessage(), "Jasper Error", 0);
        }
    }

    private void jButton4ActionPerformed(ActionEvent evt) {
        try {
            InputStream filePath = this.getClass().getClassLoader().getResourceAsStream("Report/User_Report.jasper");
            HashMap parameter = new HashMap();
            JRTableModelDataSource jrTableModelDataSource = new JRTableModelDataSource(this.jTable2.getModel());
            JasperPrint fillReport = JasperFillManager.fillReport((InputStream)filePath, parameter, (JRDataSource)jrTableModelDataSource);
            JasperViewer.viewReport((JasperPrint)fillReport, (boolean)false);
            JasperExportManager.exportReportToPdfFile((JasperPrint)fillReport, (String)"report_user.pdf");
            this.exportReportToExcel(fillReport, "report_user.xlsx");
            LMS_Logger.getLogger().info("user Report generated successfully.");
        }
        catch (JRException e) {
            LMS_Logger.getLogger().log(Level.SEVERE, "Jasper error occurred: ", e);
            JOptionPane.showMessageDialog(null, "An error occurred while generating the report." + e.getMessage(), "Jasper Error", 0);
        }
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        LMS_Logger.getLogger().info("Add User Dialog opened");
        AddUserDialog user = new AddUserDialog(null, true, this.jTable2);
        user.setLocationRelativeTo(null);
        user.setVisible(true);
    }

    private void jButton7ActionPerformed(ActionEvent evt) {
        LMS_Logger.getLogger().info("Add Brand Dialog opened");
        AddBrand AddBrand2 = new AddBrand(null, true);
        AddBrand2.setLocationRelativeTo(null);
        AddBrand2.setVisible(true);
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        try {
            InputStream filePath = this.getClass().getClassLoader().getResourceAsStream("Report/Stock_Report.jasper");
            if (filePath == null) {
                JOptionPane.showMessageDialog(null, "Report file not found!", "Error", 0);
                LMS_Logger.getLogger().severe("Stock_Report.jasper file not found in resources.");
                return;
            }
            HashMap parameter = new HashMap();
            Connection connection = Mysql.getconnection();
            JasperPrint fillReport = JasperFillManager.fillReport((InputStream)filePath, parameter, (Connection)connection);
            JasperViewer.viewReport((JasperPrint)fillReport, (boolean)false);
            JasperExportManager.exportReportToPdfFile((JasperPrint)fillReport, (String)"report.pdf");
            this.exportReportToExcel(fillReport, "report.xlsx");
            LMS_Logger.getLogger().info("Stock report generated successfully.");
        }
        catch (JRException e) {
            LMS_Logger.getLogger().log(Level.SEVERE, "Jasper error occurred: ", e);
            JOptionPane.showMessageDialog(null, "An error occurred while generating the report." + e.getMessage(), "Jasper Error", 0);
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        LMS_Logger.getLogger().info("Add Product Dialog opened");
        addProduct productDialog = new addProduct(null, true, this.jTable1, this);
        productDialog.setLocationRelativeTo(null);
        productDialog.setVisible(true);
    }

    private void jButton9ActionPerformed(ActionEvent evt) {
        LMS_Logger.getLogger().info("User Active/Deactivate Dialog opened");
        UserActiveDeactive UActiveDeactive = new UserActiveDeactive(null, true, this.jTable2);
        UActiveDeactive.setLocationRelativeTo(null);
        UActiveDeactive.setVisible(true);
    }

    public static void main(String[] args) {
        UIManager.put("Button.arc", 15);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextComponent.arc", 15);
        FlatIntelliJLaf.setup();
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new HomeScreen().setVisible(true);
            }
        });
    }
}
