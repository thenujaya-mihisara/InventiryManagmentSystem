/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.LMS.com.gui.util;

import java.sql.ResultSet;
import java.awt.Image;
import java.net.URL;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lk.LMS.com.gui.connection.Mysql;

public class AppIconUtill {
    
    private static Image appIcon;
    
    static {
        try {
            URL iconPath = AppIconUtill.class.getResource("/lk/LMS/com/img/splashscreen.png");
            ImageIcon icon = new ImageIcon(iconPath);
            AppIconUtill.appIcon = icon.getImage();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Invalid icon path...");
        }
    }

  

    public static void applyIcon(JFrame frame) {
        if (frame != null) {
            frame.setIconImage(appIcon);
        }
    }

public class TableUtils {

    public static void refreshTable(JTable table, String query, int columnCount) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); 

            ResultSet rs = Mysql.excute(query);

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}