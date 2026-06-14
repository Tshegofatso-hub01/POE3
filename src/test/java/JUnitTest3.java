/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poe3;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tshegofatso
 */
public class POE3class {
    // ============================
    // PART 1: Registration & Login
    // ============================
    private String userName;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // ============================
    // PART 3: Arrays for message management
    // ============================
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();
    
    private static int totalMessagesSent = 0;

    public POE3class(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Existing getters and setters
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setCellPhoneNumber(String cellPhoneNumber) { this.cellPhoneNumber = cellPhoneNumber; }

    public boolean checkUserName() {
        if (userName.contains("_") && userName.length() <= 5) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        }
    }

    public boolean checkPasswordComplexity() {
        if (password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }
    }

    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber.matches("^\\+27\\d{9}$")) {
            System.out.println("Cell phone number successfully added.");
            return true;
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        }
    }

    public String registerUser() {
        if (!checkUserName()) return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        if (!checkPasswordComplexity()) return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        if (!checkCellPhoneNumber()) return "Cell phone number incorrectly formatted or does not contain international code.";
        return "User registered successfully.";
    }

    public boolean loginUser(String enteredUserName, String enteredPassword) {
        return this.userName.equals(enteredUserName) && this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // ============================
    // PART 3: QuickChat Messaging with Arrays
    // ============================
    
    // Load test data to populate arrays
    public static void loadTestData() {
        // Clear existing data
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        totalMessagesSent = 0;
        
        // Test Data Message 1 - Sent
        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        sentMessages.add(msg1);
        messageIDs.add(msg1.getMessageID());
        messageHashes.add(msg1.getMessageHash());
        totalMessagesSent++;
        
        // Test Data Message 2 - Stored
        Message msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        storedMessages.add(msg2);
        messageIDs.add(msg2.getMessageID());
        messageHashes.add(msg2.getMessageHash());
        
        // Test Data Message 3 - Disregarded
        Message msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        disregardedMessages.add(msg3);
        messageIDs.add(msg3.getMessageID());
        messageHashes.add(msg3.getMessageHash());
        
        // Test Data Message 4 - Sent (Developer recipient)
        Message msg4 = new Message(4, "0838884567", "It is dinner time!");
        sentMessages.add(msg4);
        messageIDs.add(msg4.getMessageID());
        messageHashes.add(msg4.getMessageHash());
        totalMessagesSent++;
        
        // Test Data Message 5 - Stored
        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        storedMessages.add(msg5);
        messageIDs.add(msg5.getMessageID());
        messageHashes.add(msg5.getMessageHash());
        
        System.out.println("Test data loaded successfully!");
        System.out.println("Sent messages: " + sentMessages.size());
        System.out.println("Stored messages: " + storedMessages.size());
        System.out.println("Disregarded messages: " + disregardedMessages.size());
    }
    
    // Save stored messages to text file (alternative to JSON)
    public static void saveStoredMessagesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("stored_messages.txt"))) {
            writer.println("=== STORED MESSAGES ===");
            writer.println("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            writer.println("Total Stored Messages: " + storedMessages.size());
            writer.println();
            
            for (int i = 0; i < storedMessages.size(); i++) {
                Message msg = storedMessages.get(i);
                writer.println("--- Message " + (i + 1) + " ---");
                writer.println("Message ID: " + msg.getMessageID());
                writer.println("Message Number: " + msg.getMessageNumber());
                writer.println("Recipient: " + msg.getRecipient());
                writer.println("Content: " + msg.getContent());
                writer.println("Message Hash: " + msg.getMessageHash());
                writer.println();
            }
            System.out.println("Stored messages saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
    
    // Load stored messages from text file
    public static void loadStoredMessagesFromFile() {
        File file = new File("stored_messages.txt");
        if (!file.exists()) {
            System.out.println("No existing saved messages found. Starting fresh.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean readingMessage = false;
            String messageID = "";
            int messageNumber = 0;
            String recipient = "";
            String content = "";
            String messageHash = "";
            
            storedMessages.clear();
            
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Message ID: ")) {
                    messageID = line.substring(12);
                    readingMessage = true;
                } else if (line.startsWith("Message Number: ")) {
                    messageNumber = Integer.parseInt(line.substring(16));
                } else if (line.startsWith("Recipient: ")) {
                    recipient = line.substring(11);
                } else if (line.startsWith("Content: ")) {
                    content = line.substring(9);
                } else if (line.startsWith("Message Hash: ")) {
                    messageHash = line.substring(14);
                    // Create and add the message
                    Message msg = new Message(messageNumber, recipient, content);
                    storedMessages.add(msg);
                }
            }
            System.out.println("Stored messages loaded from file successfully! Found: " + storedMessages.size());
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
    
    // Display sender and recipient of all stored messages
    public static void displaySendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        System.out.println("\n=== Sender and Recipient of All Stored Messages ===");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            System.out.println("Message " + (i + 1) + ":");
            System.out.println("  Recipient: " + msg.getRecipient());
            System.out.println("  Message: " + msg.getContent());
        }
    }
    
    // Display the longest stored message
    public static void displayLongestMessage() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        
        Message longestMsg = storedMessages.get(0);
        for (Message msg : storedMessages) {
            if (msg.getContent().length() > longestMsg.getContent().length()) {
                longestMsg = msg;
            }
        }
        
        System.out.println("\n=== Longest Stored Message ===");
        System.out.println("Recipient: " + longestMsg.getRecipient());
        System.out.println("Message: " + longestMsg.getContent());
        System.out.println("Length: " + longestMsg.getContent().length() + " characters");
    }
    
    // Search for a message ID and display corresponding recipient and message
    public static void searchByMessageID(String messageID) {
        boolean found = false;
        
        // Search in all message collections
        for (Message msg : sentMessages) {
            if (msg.getMessageID().equals(messageID)) {
                System.out.println("\n=== Message Found ===");
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getContent());
                found = true;
                return;
            }
        }
        
        for (Message msg : storedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                System.out.println("\n=== Message Found ===");
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getContent());
                found = true;
                return;
            }
        }
        
        for (Message msg : disregardedMessages) {
            if (msg.getMessageID().equals(messageID)) {
                System.out.println("\n=== Message Found ===");
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getContent());
                found = true;
                return;
            }
        }
        
        if (!found) {
            System.out.println("Message ID not found.");
        }
    }
    
    // Search for all messages stored for a particular recipient
    public static void searchByRecipient(String recipient) {
        ArrayList<Message> foundMessages = new ArrayList<>();
        
        for (Message msg : storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                foundMessages.add(msg);
            }
        }
        
        if (foundMessages.isEmpty()) {
            System.out.println("No messages found for recipient: " + recipient);
        } else {
            System.out.println("\n=== Messages for Recipient: " + recipient + " ===");
            for (int i = 0; i < foundMessages.size(); i++) {
                Message msg = foundMessages.get(i);
                System.out.println((i + 1) + ". " + msg.getContent());
            }
        }
    }
    
    // Delete a message using the message hash
    public static void deleteMessageByHash(String messageHash) {
        boolean removed = false;
        
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equals(messageHash)) {
                String deletedContent = storedMessages.get(i).getContent();
                storedMessages.remove(i);
                removed = true;
                System.out.println("Message: \"" + deletedContent + "\" successfully deleted.");
                saveStoredMessagesToFile(); // Save updated list
                return;
            }
        }
        
        if (!removed) {
            System.out.println("Message hash not found in stored messages.");
        }
    }
    
    // Display report with full details of all stored messages
    public static void displayReport() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages to display.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                     STORED MESSAGES REPORT");
        System.out.println("=".repeat(80));
        
        for (int i = 0; i < storedMessages.size(); i++) {
            Message msg = storedMessages.get(i);
            System.out.println("\n--- Message " + (i + 1) + " ---");
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Message ID: " + msg.getMessageID());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message Content: " + msg.getContent());
            System.out.println("Message Length: " + msg.getContent().length() + " characters");
            System.out.println("-".repeat(40));
        }
        System.out.println("=".repeat(80));
        System.out.println("Total Stored Messages: " + storedMessages.size());
        System.out.println("=".repeat(80));
    }
    
    // Getter methods for arrays (for testing)
    public static ArrayList<Message> getSentMessages() { return sentMessages; }
    public static ArrayList<Message> getStoredMessages() { return storedMessages; }
    public static ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public static ArrayList<String> getMessageHashes() { return messageHashes; }
    public static ArrayList<String> getMessageIDs() { return messageIDs; }
    
    public static void runQuickChat(boolean loginStatus) {
        if (!loginStatus) {
            System.out.println("Login failed. Cannot access QuickChat.");
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Stored Messages Management");
            System.out.println("4) Quit");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    sendMessages(input);
                    break;

                case 2:
                    displayRecentlySent();
                    break;
                    
                case 3:
                    storedMessagesMenu(input);
                    break;

                case 4:
                    running = false;
                    System.out.println("Exiting QuickChat...");
                    saveStoredMessagesToFile(); // Save before exiting
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        
        System.out.println("Total messages sent: " + totalMessagesSent);
        input.close();
    }
    
    private static void sendMessages(Scanner input) {
        System.out.print("How many messages do you want to send? ");
        int numMessages = input.nextInt();
        input.nextLine();

        for (int i = 1; i <= numMessages; i++) {
            System.out.print("Enter recipient number (+27...): ");
            String recipient = input.nextLine();

            System.out.print("Enter message content: ");
            String content = input.nextLine();

            Message msg = new Message(i, recipient, content);

            if (!msg.checkMessageLength()) {
                System.out.println("Message exceeds 250 characters by " +
                    (msg.getContent().length() - 250) + "; please reduce the size.");
                continue;
            }

            if (!msg.checkRecipientCell()) {
                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                continue;
            }

            System.out.println("Choose action: ");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message");
            int action = input.nextInt();
            input.nextLine();

            if (action == 1) {
                sentMessages.add(msg);
                totalMessagesSent++;
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                System.out.println(msg.sendMessage());
                msg.printMessage();
            } else if (action == 2) {
                disregardedMessages.add(msg);
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                System.out.println(msg.discardMessage());
            } else if (action == 3) {
                storedMessages.add(msg);
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                System.out.println(msg.storeMessage());
                saveStoredMessagesToFile(); // Save after storing
            }
        }
    }
    
    private static void displayRecentlySent() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
        } else {
            System.out.println("\n=== Recently Sent Messages ===");
            int start = Math.max(0, sentMessages.size() - 5);
            for (int i = start; i < sentMessages.size(); i++) {
                System.out.println("\n--- Message " + (i + 1) + " ---");
                sentMessages.get(i).printMessage();
            }
        }
    }
    
    private static void storedMessagesMenu(Scanner input) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages Management ---");
            System.out.println("1) Display all stored messages (sender & recipient)");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message by ID");
            System.out.println("4) Search for messages by recipient");
            System.out.println("5) Delete a message by hash");
            System.out.println("6) Display full report");
            System.out.println("7) Load test data");
            System.out.println("8) Back to main menu");
            System.out.print("Choose an option: ");
            int option = input.nextInt();
            input.nextLine();
            
            switch (option) {
                case 1:
                    displaySendersAndRecipients();
                    break;
                case 2:
                    displayLongestMessage();
                    break;
                case 3:
                    System.out.print("Enter Message ID: ");
                    String msgId = input.nextLine();
                    searchByMessageID(msgId);
                    break;
                case 4:
                    System.out.print("Enter Recipient number: ");
                    String recipient = input.nextLine();
                    searchByRecipient(recipient);
                    break;
                case 5:
                    System.out.print("Enter Message Hash: ");
                    String hash = input.nextLine();
                    deleteMessageByHash(hash);
                    break;
                case 6:
                    displayReport();
                    break;
                case 7:
                    loadTestData();
                    saveStoredMessagesToFile();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ============================
    // Inner Message Class
    // ============================
    public static class Message {
        private String messageID;
        private int messageNumber;
        private String recipient;
        private String content;
        private String messageHash;

        public Message(int messageNumber, String recipient, String content) {
            this.messageID = generateMessageID();
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;
            this.messageHash = createMessageHash();
        }

        private String generateMessageID() {
            long id = (long)(Math.random() * 1_000_000_0000L);
            return String.format("%010d", id);
        }

        private String createMessageHash() {
            String[] words = content.split(" ");
            if (words.length == 0) return messageID.substring(0, 2) + ":" + messageNumber + ":";
            String firstWord = words[0];
            String lastWord = words[words.length - 1];
            return messageID.substring(0, 2) + ":" + messageNumber + ":" +
                   firstWord.toUpperCase() + lastWord.toUpperCase();
        }

        public boolean checkMessageLength() { return content.length() <= 250; }
        public boolean checkRecipientCell() { 
            return recipient.matches("^\\+27\\d{9}$") || recipient.matches("^083\\d{7}$");
        }

        public String sendMessage() { return "Message successfully sent."; }
        public String discardMessage() { return "Press 0 to delete the message."; }
        public String storeMessage() { return "Message successfully stored."; }

        public void printMessage() {
            System.out.println("Message ID: " + messageID);
            System.out.println("Message Hash: " + messageHash);
            System.out.println("Recipient: " + recipient);
            System.out.println("Message: " + content);
        }

        // Getters
        public String getMessageID() { return messageID; }
        public String getRecipient() { return recipient; }
        public String getContent() { return content; }
        public String getMessageHash() { return messageHash; }
        public int getMessageNumber() { return messageNumber; }
    }
}