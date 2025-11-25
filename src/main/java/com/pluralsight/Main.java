package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error on required application details to run");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        try {
            Scanner myScanner = new Scanner(System.in);

            boolean isDone = false;

            while (!isDone) {
                System.out.println("What do you want to do?");
                System.out.println("  1) Display all products");
                System.out.println("  2) Display all customers");
                System.out.println("  0) Exit");
                System.out.println("Select an option:");
                String userInput = myScanner.nextLine().trim();

                int userChoice = Integer.parseInt(userInput);

                switch (userChoice) {
                    case 1:
                        displayAllProducts(username, password);
                        break;
                    case 2:
                        displayAllCustomers(username, password);
                        break;
                    case 0:
                        isDone = true;
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Please check your inputs!");
            System.out.println(ex.getMessage());
        }
    }

    public static void displayAllProducts(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password
            );

            String query = """
                    SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                    FROM products
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet results = preparedStatement.executeQuery();

            System.out.println("ID  " + " Name" + " ".repeat(37) + "Price   " + "Stock");
            System.out.println("---- " + "-".repeat(40) + " -------" + " -----");

            while (results.next()) {
                int productID = results.getInt(1);
                String productName = results.getString(2);
                double productPrice = results.getDouble(3);
                int productStock = results.getInt(4);


                System.out.printf("%-4d %-40s %-7.2f %-5d\n",
                        productID, productName, productPrice, productStock);
            }
        } catch (Exception ex) {
            System.out.println("Error occur!");
            System.out.println(ex.getMessage());
        }
    }
}
