package in.mirai.bms;

import in.mirai.bms.model.Account;
import in.mirai.bms.service.AccountService;
import in.mirai.bms.serviceimpl.AccountServiceImpl;
import in.mirai.bms.util.ConsoleUI;

import java.util.Scanner;

/**
 * The main entry point for the Mirai Bank Management System.
 * Handles the application lifecycle, main menu navigation, and user input validation.
 */
public class Main {

    /** Scanner instance for user input */
    private static final Scanner sc = new Scanner(System.in);
    
    /** Service layer instance for handling bank operations */
    private static final AccountService accountService = new AccountServiceImpl();

    /**
     * Main method to start the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int choice;
        ConsoleUI.clearScreen();
        showWelcomeScreen();

        do {
            showMenu();
            choice = getValidChoice();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewAccount();
                case 3 -> depositMoney();
                case 4 -> withdrawMoney();
                case 5 -> transferMoney();
                case 6 -> transactionHistory();
                case 7 -> exitApplication();
                default -> ConsoleUI.printError("Invalid Choice! Please try again.");
            }
            
            if (choice != 7) {
                System.out.println("\nPress Enter to continue...");
                sc.nextLine();
                if (sc.hasNextLine()) sc.nextLine(); // Consume newline
            }

        } while (choice != 7);

        sc.close();
    }

    /**
     * Prompts the user for account details and creates a new account.
     */
    private static void createAccount() {
        ConsoleUI.printHeader("Create Account");

        int accountId = getValidInt("Enter Account ID: ");
        sc.nextLine(); // Consume newline

        String holderName = getValidString("Enter Holder Name: ");
        int pin = getValidPin();
        int balance = getPositiveAmount("Enter Initial Balance ₹: ");

        Account account = new Account();
        account.setAccountId(accountId);
        account.setHolderName(holderName);
        account.setPin(pin);
        account.setBalance(balance);

        ConsoleUI.showLoading("Processing Account Creation");
        accountService.createAccount(account);
    }

    /**
     * Validates user and displays account information.
     */
    private static void viewAccount() {
        ConsoleUI.printHeader("View Account");
        int accountId = getValidInt("Enter Account ID: ");
        int pin = getValidPin();
        
        ConsoleUI.showLoading("Fetching Account Details");
        accountService.viewAccount(accountId, pin);
    }

    /**
     * Prompts for deposit details and processes the transaction.
     */
    private static void depositMoney() {
        ConsoleUI.printHeader("Deposit Money");
        int accountId = getValidInt("Enter Account ID: ");
        int pin = getValidPin();
        int amount = getPositiveAmount("Enter Amount ₹: ");

        ConsoleUI.showLoading("Processing Deposit");
        accountService.deposit(accountId, amount, pin);
    }

    /**
     * Prompts for withdrawal details and processes the transaction.
     */
    private static void withdrawMoney() {
        ConsoleUI.printHeader("Withdraw Money");
        int accountId = getValidInt("Enter Account ID: ");
        int pin = getValidPin();
        int amount = getPositiveAmount("Enter Amount ₹: ");

        ConsoleUI.showLoading("Processing Withdrawal");
        accountService.withdraw(accountId, amount, pin);
    }

    /**
     * Prompts for transfer details and initiates fund movement.
     */
    private static void transferMoney() {
        ConsoleUI.printHeader("Transfer Money");
        int senderId = getValidInt("Enter Sender Account ID: ");
        int pin = getValidPin();
        int receiverId = getValidInt("Enter Receiver Account ID: ");
        int amount = getPositiveAmount("Enter Amount ₹: ");

        ConsoleUI.showLoading("Initiating Fund Transfer");
        accountService.transfer(senderId, receiverId, amount, pin);
    }

    /**
     * Displays all past transactions for a validated account.
     */
    private static void transactionHistory() {
        ConsoleUI.printHeader("Transaction History");
        int accountId = getValidInt("Enter Account ID: ");
        int pin = getValidPin();

        ConsoleUI.showLoading("Retrieving History");
        accountService.transactionHistory(accountId, pin);
    }

    /**
     * Displays the bank's welcome screen.
     */
    private static void showWelcomeScreen() {
        ConsoleUI.printHeader("Welcome to Mirai Bank");
        System.out.println(ConsoleUI.CYAN + "      Safe • Secure • Reliable" + ConsoleUI.RESET);
    }

    /**
     * Renders the main bank menu options.
     */
    private static void showMenu() {
        System.out.println("\n" + ConsoleUI.CYAN + "╔════════════════════════════════════════╗");
        System.out.println("║ " + ConsoleUI.BOLD + ConsoleUI.WHITE + "            BANK MAIN MENU            " + ConsoleUI.RESET + ConsoleUI.CYAN + " ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  " + ConsoleUI.YELLOW + "1." + ConsoleUI.RESET + " Create New Account                ║");
        System.out.println("║  " + ConsoleUI.YELLOW + "2." + ConsoleUI.RESET + " View Account Details              ║");
        System.out.println("║  " + ConsoleUI.YELLOW + "3." + ConsoleUI.RESET + " Deposit Funds                     ║");
        System.out.println("║  " + ConsoleUI.YELLOW + "4." + ConsoleUI.RESET + " Withdraw Funds                    ║");
        System.out.println("║  " + ConsoleUI.YELLOW + "5." + ConsoleUI.RESET + " Transfer Funds                    ║");
        System.out.println("║  " + ConsoleUI.YELLOW + "6." + ConsoleUI.RESET + " View Transaction History          ║");
        System.out.println("║  " + ConsoleUI.YELLOW + "7." + ConsoleUI.RESET + " Exit Application                  ║");
        System.out.println("╚════════════════════════════════════════╝" + ConsoleUI.RESET);
    }

    /**
     * Displays a goodbye message and terminates the session.
     */
    private static void exitApplication() {
        ConsoleUI.printHeader("Thank You For Banking");
        System.out.println(ConsoleUI.GREEN + "      Visit Again • Have a Great Day!" + ConsoleUI.RESET);
    }

    // --- Helper Methods for Input Validation ---

    private static int getValidChoice() {
        while (true) {
            ConsoleUI.printPrompt("Enter Choice: ");
            if (sc.hasNextInt()) return sc.nextInt();
            ConsoleUI.printError("Invalid Input! Enter numbers 1-7.");
            sc.next();
        }
    }

    private static int getValidInt(String message) {
        while (true) {
            ConsoleUI.printPrompt(message);
            if (sc.hasNextInt()) return sc.nextInt();
            ConsoleUI.printError("Invalid Input! Numbers only.");
            sc.next();
        }
    }

    private static int getPositiveAmount(String message) {
        while (true) {
            int amount = getValidInt(message);
            if (amount > 0) return amount;
            ConsoleUI.printError("Amount must be greater than 0!");
        }
    }

    private static int getValidPin() {
        while (true) {
            int pin = getValidInt("Enter 4-digit PIN: ");
            if (String.valueOf(pin).length() == 4) return pin;
            ConsoleUI.printError("PIN must be exactly 4 digits!");
        }
    }

    private static String getValidString(String message) {
        while (true) {
            ConsoleUI.printPrompt(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            ConsoleUI.printError("Input cannot be empty!");
        }
    }
}
