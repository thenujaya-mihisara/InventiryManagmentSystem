/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.LMS.com.gui.Logger;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
/**
 *
 * @author MB
 */
public class LMS_Logger {
    private static final Logger logger = Logger.getLogger("InventorySystem");

    static {
        try {
           FileHandler handler = new FileHandler("LMS.log",true); 
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            System.out.println("Failed to set up logger: " + e.getMessage());
            
        }
    }

    public static Logger getLogger() {
        return logger;
    }

}
