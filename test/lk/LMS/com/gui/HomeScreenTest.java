/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package lk.LMS.com.gui;

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
public class HomeScreenTest {
    
    public HomeScreenTest() {
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
     * Test of loadproductcard method, of class HomeScreen.
     */
    @Test
    public void testLoadproductcard() {
        System.out.println("loadproductcard");
        HomeScreen instance = new HomeScreen();
        instance.loadproductcard();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of main method, of class HomeScreen.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        HomeScreen.main(args);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
