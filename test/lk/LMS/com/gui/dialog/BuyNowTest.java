/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package lk.LMS.com.gui.dialog;

import lk.LMS.com.gui.HomeScreen;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MB
 */
public class BuyNowTest {
    
    public BuyNowTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buyProduct method, of class BuyNow.
     */
    @Test
    public void testBuyProduct() {
        HomeScreen screen = null;

    BuyNow dialog = new BuyNow(null, true, "P001", "Test Product", 100.0, 10, screen);
    
    // You may need to mock Mysql.excute() calls here if not using real DB
    
    dialog.buyProduct();
    
    // Check expected behavior or DB changes here
    // OR assert no exception thrown
       
    }
    
}
