package in.mirai.bms;

import in.mirai.bms.model.Account;
import in.mirai.bms.service.AccountService;
import in.mirai.bms.serviceimpl.AccountServiceImpl;
import in.mirai.bms.util.ConsoleUI;

import java.util.Scanner;

public class Main {

    private static final Scanner        sc             = new Scanner(System.in);
    private static final AccountService accountService = new AccountServiceImpl();

    // ── Menu box geometry ────────────────────────────────────────────────────
    // Inner width of the menu box (between the two ║ characters).
    // Must equal: len("  [n]  ") + longest_label + trailing_spaces
    // len("  [n]  ") = 7, longest label = "View Account Details" = 20 → inner = 44
    private static final int MENU_INNER = 44;

    public static void main(String[] args) {
        ConsoleUI.clearScreen();
        showWelcomeScreen();

        int choice;
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
                default -> ConsoleUI.printWarn("Invalid choice. Enter a number from 1 to 7.");
            }

            if (choice != 7) {
                System.out.println();
                ConsoleUI.printDivider();
                ConsoleUI.printPrompt("Press Enter to return to menu...");
                sc.nextLine();
                if (sc.hasNextLine()) sc.nextLine();
                ConsoleUI.clearScreen();
            }

        } while (choice != 7);

        sc.close();
    }

    // ── Screens ──────────────────────────────────────────────────────────────

    private static void createAccount() {
        ConsoleUI.printHeader("Create Account");
        ConsoleUI.printSectionLabel("New Account Registration");

        sc.nextLine(); // Consume newline from menu selection
        String holderName = getValidString("Holder Name       : ");
        int    pin        = getValidPin();
        int    balance    = getPositiveAmount("Initial Balance ₹ : ");

        Account account = new Account();
        account.setHolderName(holderName);
        account.setPin(pin);
        account.setBalance(balance);

        ConsoleUI.showLoading("Creating Account");
        accountService.createAccount(account);
    }

    private static void viewAccount() {
        ConsoleUI.printHeader("View Account");
        int accountId = getValidInt("Account ID  : ");
        int pin       = getValidPin();

        ConsoleUI.showLoading("Fetching Account Details");
        accountService.viewAccount(accountId, pin);
    }

    private static void depositMoney() {
        ConsoleUI.printHeader("Deposit Funds");
        int accountId = getValidInt("Account ID  : ");
        int pin       = getValidPin();
        int amount    = getPositiveAmount("Amount ₹    : ");

        ConsoleUI.showLoading("Processing Deposit");
        accountService.deposit(accountId, amount, pin);
    }

    private static void withdrawMoney() {
        ConsoleUI.printHeader("Withdraw Funds");
        int accountId = getValidInt("Account ID  : ");
        int pin       = getValidPin();
        int amount    = getPositiveAmount("Amount ₹    : ");

        ConsoleUI.showLoading("Processing Withdrawal");
        accountService.withdraw(accountId, amount, pin);
    }

    private static void transferMoney() {
        ConsoleUI.printHeader("Transfer Funds");
        int senderId   = getValidInt("Sender Account ID   : ");
        int pin        = getValidPin();
        int receiverId = getValidInt("Receiver Account ID : ");
        int amount     = getPositiveAmount("Amount ₹            : ");

        ConsoleUI.showLoading("Initiating Fund Transfer");
        accountService.transfer(senderId, receiverId, amount, pin);
    }

    private static void transactionHistory() {
        ConsoleUI.printHeader("Transaction History");
        int accountId = getValidInt("Account ID  : ");
        int pin       = getValidPin();

        ConsoleUI.showLoading("Retrieving History");
        accountService.transactionHistory(accountId, pin);
    }

    // ── Welcome & Menu ───────────────────────────────────────────────────────

    private static void showWelcomeScreen() {
        ConsoleUI.printHeader("Mirai Bank");
        System.out.println(ConsoleUI.TEAL + ConsoleUI.BOLD
                + "         Safe  •  Secure  •  Reliable"
                + ConsoleUI.RESET);
        System.out.println();
    }

    private static void showMenu() {
        String thick = "═".repeat(MENU_INNER);
        String thin  = "─".repeat(MENU_INNER);

        System.out.println();
        System.out.println(ConsoleUI.TEAL + "  ╔" + thick + "╗" + ConsoleUI.RESET);

        // Title row — manually centred, no printf (avoids ANSI width issue)
        String title  = "MIRAI BANK  —  MAIN MENU";
        int    lp     = (MENU_INNER - title.length()) / 2;
        int    rp     = MENU_INNER - title.length() - lp;
        System.out.println(ConsoleUI.TEAL + "  ║" + ConsoleUI.RESET
                + " ".repeat(lp)
                + ConsoleUI.BOLD + ConsoleUI.SAND + title + ConsoleUI.RESET
                + " ".repeat(rp)
                + ConsoleUI.TEAL + "║" + ConsoleUI.RESET);

        System.out.println(ConsoleUI.TEAL + "  ╠" + thick + "╣" + ConsoleUI.RESET);
        menuRow(1, "Create New Account");
        menuRow(2, "View Account Details");
        menuRow(3, "Deposit Funds");
        menuRow(4, "Withdraw Funds");
        menuRow(5, "Transfer Funds");
        menuRow(6, "Transaction History");
        System.out.println(ConsoleUI.TEAL + "  ╠" + thin + "╣" + ConsoleUI.RESET);
        menuRow(7, "Exit Application");
        System.out.println(ConsoleUI.TEAL + "  ╚" + thick + "╝" + ConsoleUI.RESET);
    }

    /**
     * Renders one menu row with pixel-perfect ║ alignment.
     *
     * Inner content = "  [n]  " (7 chars) + label + trailing spaces = MENU_INNER
     */
    private static void menuRow(int n, String label) {
        int trailing = MENU_INNER - 7 - label.length();   // 7 = len("  [n]  ")
        System.out.println(
                ConsoleUI.TEAL   + "  ║" + ConsoleUI.RESET
                        + "  "
                        + ConsoleUI.AMBER  + "[" + n + "]" + ConsoleUI.RESET
                        + "  "
                        + ConsoleUI.BRIGHT_WHITE + label + ConsoleUI.RESET
                        + " ".repeat(Math.max(0, trailing))
                        + ConsoleUI.TEAL + "║" + ConsoleUI.RESET
        );
    }

    private static void exitApplication() {
        ConsoleUI.printHeader("Goodbye");
        System.out.println(ConsoleUI.BRIGHT_GREEN + ConsoleUI.BOLD
                + "        Thank you for banking with Mirai!"
                + ConsoleUI.RESET);
        System.out.println(ConsoleUI.SLATE + "        Have a great day.\n" + ConsoleUI.RESET);
        ConsoleUI.printTimestamp();
    }

    // ── Input Validators ─────────────────────────────────────────────────────

    private static int getValidChoice() {
        while (true) {
            System.out.println();
            ConsoleUI.printPrompt("Enter choice (1–7): ");
            if (sc.hasNextInt()) return sc.nextInt();
            ConsoleUI.printWarn("Enter a number between 1 and 7.");
            sc.next();
        }
    }

    private static int getValidInt(String message) {
        while (true) {
            ConsoleUI.printPrompt(message);
            if (sc.hasNextInt()) return sc.nextInt();
            ConsoleUI.printWarn("Numbers only, please.");
            sc.next();
        }
    }

    private static int getPositiveAmount(String message) {
        while (true) {
            int amount = getValidInt(message);
            if (amount > 0) return amount;
            ConsoleUI.printWarn("Amount must be greater than ₹0.");
        }
    }

    private static int getValidPin() {
        while (true) {
            int pin = getValidInt("4-digit PIN : ");
            if (String.valueOf(pin).length() == 4) return pin;
            ConsoleUI.printWarn("PIN must be exactly 4 digits.");
        }
    }

    private static String getValidString(String message) {
        while (true) {
            ConsoleUI.printPrompt(message);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            ConsoleUI.printWarn("This field cannot be empty.");
        }
    }
}