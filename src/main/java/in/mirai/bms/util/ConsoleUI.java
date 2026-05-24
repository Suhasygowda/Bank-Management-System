package in.mirai.bms.util;

/**
 * Utility class for enhancing the console interface.
 * Provides ANSI color codes and formatted UI components like headers, loading bars, and status messages.
 */
public class ConsoleUI {

    // ANSI Escape Codes for Colors
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String WHITE = "\u001B[37m";

    /**
     * Clears the console screen using ANSI escape sequences.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints a stylized header box with the given title.
     *
     * @param title The title to display in the header.
     */
    public static void printHeader(String title) {
        int width = 40;
        String border = "═".repeat(width);
        System.out.println("\n" + CYAN + "╔" + border + "╗" + RESET);
        
        int padding = (width - title.length()) / 2;
        String format = "║%" + padding + "s%s%" + (width - padding - title.length()) + "s║";
        System.out.printf(format + "\n", "", BOLD + title.toUpperCase() + RESET + CYAN, "");
        
        System.out.println("╚" + border + "╝" + RESET);
    }

    /**
     * Prints a success message with a checkmark icon.
     *
     * @param message The success message to display.
     */
    public static void printSuccess(String message) {
        System.out.println("\n" + GREEN + "✔ " + message + RESET);
    }

    /**
     * Prints an error message with a cross icon.
     *
     * @param message The error message to display.
     */
    public static void printError(String message) {
        System.out.println("\n" + RED + "✘ " + message + RESET);
    }

    /**
     * Prints an informational message with an info icon.
     *
     * @param message The info message to display.
     */
    public static void printInfo(String message) {
        System.out.println(CYAN + "ℹ " + message + RESET);
    }

    /**
     * Prints an input prompt with a hand pointer icon.
     *
     * @param message The prompt message to display.
     */
    public static void printPrompt(String message) {
        System.out.print(YELLOW + "👉 " + message + RESET);
    }

    /**
     * Displays a simulated loading animation with a message.
     *
     * @param message The message to show during loading.
     */
    public static void showLoading(String message) {
        System.out.print(BLUE + "⏳ " + message);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(400);
                System.out.print(".");
            }
            System.out.println(RESET);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Prints a horizontal divider line.
     */
    public static void printDivider() {
        System.out.println(WHITE + "─".repeat(42) + RESET);
    }
}
