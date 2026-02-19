/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.iau.dailyplanner.ui.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RegisterFrameTest {

    @Test
    public void testShortPassword() {
        RegisterFrame frame = new RegisterFrame();
        String result = frame.validatePassword("Ab1!");
        assertEquals("Password must be at least 8 characters long.", result);
    }

    @Test
    public void testMissingUppercase() {
        RegisterFrame frame = new RegisterFrame();
        String result = frame.validatePassword("abc123!@");
        assertEquals("Password must contain at least one uppercase letter.", result);
    }

    @Test
    public void testMissingDigit() {
        RegisterFrame frame = new RegisterFrame();
        String result = frame.validatePassword("Abcdefg!");
        assertEquals("Password must contain at least one digit.", result);
    }

    @Test
    public void testMissingSpecialChar() {
        RegisterFrame frame = new RegisterFrame();
        String result = frame.validatePassword("Abcdefg1");
        assertEquals("Password must contain at least one special character.", result);
    }

    @Test
    public void testStrongPassword() {
        RegisterFrame frame = new RegisterFrame();
        String result = frame.validatePassword("Abc123!@");
        assertNull(result); 
    }
}
