/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poe3;

import java.util.Scanner;

/**
 *
 * @author Tshegofatso
 */

public class POE3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // ============================
        // PART 1: Registration & Login
        // ============================        
        System.out.print("Enter First Name: ");
        String firstName = input.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = input.nextLine();

        POE3class userObj = new POE3class(firstName, lastName);

        System.out.print("Enter Username: ");
        userObj.setUserName(input.nextLine());

        System.out.print("Enter Password: ");
        userObj.setPassword(input.nextLine());

        System.out.print("Enter Cell Phone Number (with international code): ");
        userObj.setCellPhoneNumber(input.nextLine());

        System.out.println("\n--- Registration Status ---");
        System.out.println(userObj.registerUser());

        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String loginUserName = input.nextLine();
        System.out.print("Enter Password: ");
        String loginPassword = input.nextLine();

        boolean loginStatus = userObj.loginUser(loginUserName, loginPassword);
        System.out.println(userObj.returnLoginStatus(loginStatus));

        // ============================
        // PART 2 & 3: QuickChat Messaging
        // ============================
        if (loginStatus) {
            // Load existing stored messages from file
            POE3class.loadStoredMessagesFromFile();
            POE3class.runQuickChat(loginStatus);
        } else {
            System.out.println("Cannot access QuickChat due to failed login.");
        }

        input.close();
    }
}