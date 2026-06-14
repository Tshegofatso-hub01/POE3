/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poe3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 *
 * @author Tshegofatso
 */
public class JUnitTest3 {
    
    @BeforeEach
    public void setUp() {
        // Clear arrays before each test
        POE3class.getSentMessages().clear();
        POE3class.getStoredMessages().clear();
        POE3class.getDisregardedMessages().clear();
    }

    // ============================
    // PART 1: Registration & Login Tests
    // ============================
    
    @Test
    public void testCorrectUsername() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setUserName("kyl_1");
        assertTrue(user.checkUserName());
    }

    @Test
    public void testIncorrectUsername() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setUserName("kyle!");
        assertFalse(user.checkUserName());
    }

    @Test
    public void testCorrectPassword() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setPassword("Ch&&sec@ke99!");
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testIncorrectPassword() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setPassword("password");
        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCorrectCellPhone() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setCellPhoneNumber("+27838968976");
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testIncorrectCellPhone() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setCellPhoneNumber("08966553");
        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testSuccessfulLogin() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setUserName("kyl_1");
        user.setPassword("Ch&&sec@ke99!");
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testFailedLogin() {
        POE3class user = new POE3class("Kyle", "Smith");
        user.setUserName("kyl_1");
        user.setPassword("Ch&&sec@ke99!");
        assertFalse(user.loginUser("wrong", "password"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        POE3class user = new POE3class("Kyle", "Smith");
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                     user.returnLoginStatus(true));
    }

    @Test
    public void testReturnLoginStatusFail() {
        POE3class user = new POE3class("Kyle", "Smith");
        assertEquals("Username or password incorrect, please try again.",
                     user.returnLoginStatus(false));
    }

    // ============================
    // PART 2: QuickChat Messaging Tests
    // ============================

    @Test
    public void testMessageLengthValid() {
        POE3class.Message msg = new POE3class.Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthInvalid() {
        String longMessage = "A".repeat(260);
        POE3class.Message msg = new POE3class.Message(1, "+27718693002", longMessage);
        assertFalse(msg.checkMessageLength());
    }

    @Test
    public void testRecipientValid() {
        POE3class.Message msg = new POE3class.Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testRecipientInvalid() {
        POE3class.Message msg = new POE3class.Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        POE3class.Message msg = new POE3class.Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        assertTrue(hash.contains("HI") && hash.contains("TONIGHT"), 
                   "Hash should contain uppercase first and last words. Got: " + hash);
    }

    @Test
    public void testSendMessage() {
        POE3class.Message msg = new POE3class.Message(1, "+27718693002", "Test message");
        assertEquals("Message successfully sent.", msg.sendMessage());
    }

    @Test
    public void testDiscardMessage() {
        POE3class.Message msg = new POE3class.Message(2, "+27718693002", "Test message");
        assertEquals("Press 0 to delete the message.", msg.discardMessage());
    }

    @Test
    public void testStoreMessage() {
        POE3class.Message msg = new POE3class.Message(3, "+27718693002", "Test message");
        assertEquals("Message successfully stored.", msg.storeMessage());
    }
    
    // ============================
    // PART 3: Array and Stored Messages Tests
    // ============================
    
    @Test
    public void testSentMessagesArrayPopulated() {
        // Load test data
        POE3class.loadTestData();
        
        // Check that sent messages array contains expected messages
        boolean hasMessage1 = false;
        boolean hasMessage4 = false;
        
        for (POE3class.Message msg : POE3class.getSentMessages()) {
            if (msg.getContent().equals("Did you get the cake?")) {
                hasMessage1 = true;
            }
            if (msg.getContent().equals("It is dinner time!")) {
                hasMessage4 = true;
            }
        }
        
        assertTrue(hasMessage1 && hasMessage4, 
                  "Sent messages should contain: 'Did you get the cake?' and 'It is dinner time!'");
    }
    
    @Test
    public void testDisplayLongestMessage() {
        // Load test data
        POE3class.loadTestData();
        
        // Find the longest message manually
        String longestContent = "";
        for (POE3class.Message msg : POE3class.getStoredMessages()) {
            if (msg.getContent().length() > longestContent.length()) {
                longestContent = msg.getContent();
            }
        }
        
        assertEquals("Where are you? You are late! I have asked you to be on time.", 
                     longestContent,
                     "The longest message should be from test data message 2");
    }
    
    @Test
    public void testSearchByMessageID() {
        // Load test data
        POE3class.loadTestData();
        
        // Get message ID for message 4
        String targetMessageID = null;
        String expectedContent = "It is dinner time!";
        String expectedRecipient = "0838884567";
        
        for (POE3class.Message msg : POE3class.getSentMessages()) {
            if (msg.getContent().equals(expectedContent)) {
                targetMessageID = msg.getMessageID();
                break;
            }
        }
        
        assertNotNull(targetMessageID, "Message ID should exist for test message 4");
        
        // Search for the message by ID
        boolean found = false;
        for (POE3class.Message msg : POE3class.getSentMessages()) {
            if (msg.getMessageID().equals(targetMessageID)) {
                assertEquals(expectedRecipient, msg.getRecipient());
                assertEquals(expectedContent, msg.getContent());
                found = true;
                break;
            }
        }
        
        assertTrue(found, "Message should be found by its ID");
    }
    
    @Test
    public void testSearchByRecipient() {
        // Load test data
        POE3class.loadTestData();
        
        String targetRecipient = "+27838884567";
        ArrayList<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Where are you? You are late! I have asked you to be on time.");
        expectedMessages.add("Ok, I am leaving without you.");
        
        ArrayList<String> foundMessages = new ArrayList<>();
        for (POE3class.Message msg : POE3class.getStoredMessages()) {
            if (msg.getRecipient().equals(targetRecipient)) {
                foundMessages.add(msg.getContent());
            }
        }
        
        assertEquals(2, foundMessages.size(), "Should find 2 messages for recipient +27838884567");
        assertTrue(foundMessages.containsAll(expectedMessages), 
                  "Should contain both expected messages for recipient +27838884567");
    }
    
    @Test
    public void testDeleteMessageByHash() {
        // Load test data
        POE3class.loadTestData();
        
        // Get hash for message 2
        String targetHash = null;
        String expectedContent = "Where are you? You are late! I have asked you to be on time.";
        
        for (POE3class.Message msg : POE3class.getStoredMessages()) {
            if (msg.getContent().equals(expectedContent)) {
                targetHash = msg.getMessageHash();
                break;
            }
        }
        
        assertNotNull(targetHash, "Message hash should exist for test message 2");
        
        int originalSize = POE3class.getStoredMessages().size();
        POE3class.deleteMessageByHash(targetHash);
        
        assertEquals(originalSize - 1, POE3class.getStoredMessages().size(), 
                    "Message should be deleted from stored messages");
        
        // Verify the message is no longer in stored messages
        boolean stillExists = false;
        for (POE3class.Message msg : POE3class.getStoredMessages()) {
            if (msg.getContent().equals(expectedContent)) {
                stillExists = true;
                break;
            }
        }
        
        assertFalse(stillExists, "Deleted message should not remain in stored messages");
    }
    
    @Test
    public void testDisplayReport() {
        // Load test data
        POE3class.loadTestData();
        
        // Just verify report can be generated without errors
        assertDoesNotThrow(() -> POE3class.displayReport(), 
                          "Display report should execute without throwing exceptions");
        
        // Verify report shows all stored messages
        int storedCount = POE3class.getStoredMessages().size();
        assertTrue(storedCount > 0, "Should have stored messages to display");
    }
}
