package in.mirai.bms.util;

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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        int width = 40;
        String border = "═".repeat(width);
        System.out.println("\n" + CYAN + "╔" + border + "╗" + RESET);
        
        int padding = (width - title.length()) / 2;
        String format = "║%" + padding + "s%s%" + (width - padding - title.length()) + "s║";
        System.out.printf(format + "\n", "", BOLD + title.toUpperCase() + RESET + CYAN, "");
        
        System.out.println("╚" + border + "╝" + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println("\n" + GREEN + "✔ " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println("\n" + RED + "✘ " + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(CYAN + "ℹ " + message + RESET);
    }

    public static void printPrompt(String message) {
        System.out.print(YELLOW + "👉 " + message + RESET);
    }

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

    public static void printDivider() {
        System.out.println(WHITE + "─".repeat(42) + RESET);
    }
}
