/*
 * Decompiled with CFR 0.152.
 */
package lk.LMS.com.gui.Logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LMS_Logger {
    private static final Logger logger = Logger.getLogger("InventorySystem");

    public static Logger getLogger() {
        return logger;
    }

    static {
        try {
            FileHandler handler = new FileHandler("LMS.log", true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        }
        catch (IOException e) {
            System.out.println("Failed to set up logger: " + e.getMessage());
        }
    }
}
