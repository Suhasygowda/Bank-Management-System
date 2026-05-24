package in.mirai.bms.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ConsoleUI — Production-grade terminal UI for Mirai Bank.
 *
 * Palette  : Amber + Teal (no blue, no purple)
 * Features : spinning loader, status badges, account card,
 *            balance-delta line, transaction table, audit timestamp.
 */
public class ConsoleUI {

    // ─── Reset / Style ────────────────────────────────────────────────────────
    public static final String RESET     = "\u001B[0m";
    public static final String BOLD      = "\u001B[1m";
    public static final String DIM       = "\u001B[2m";
    public static final String ITALIC    = "\u001B[3m";

    // ─── Standard Colors (kept for compatibility) ─────────────────────────────
    public static final String RED       = "\u001B[31m";
    public static final String GREEN     = "\u001B[32m";
    public static final String YELLOW    = "\u001B[33m";
    public static final String CYAN      = "\u001B[36m";
    public static final String WHITE     = "\u001B[37m";

    // ─── Bright Colors ────────────────────────────────────────────────────────
    public static final String BRIGHT_RED    = "\u001B[91m";
    public static final String BRIGHT_GREEN  = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_CYAN   = "\u001B[96m";
    public static final String BRIGHT_WHITE  = "\u001B[97m";

    // ─── 256-Color Helpers ────────────────────────────────────────────────────
    /** Foreground from the 256-colour palette. */
    public static String fg(int code) { return "\u001B[38;5;" + code + "m"; }
    /** Background from the 256-colour palette. */
    public static String bg(int code) { return "\u001B[48;5;" + code + "m"; }

    // ─── Named Palette (amber + teal, no blue/purple) ────────────────────────
    public static final String AMBER      = fg(214);  // warm amber
    public static final String AMBER_SOFT = fg(222);  // pale gold
    public static final String TEAL       = fg(43);   // rich teal
    public static final String TEAL_LIGHT = fg(80);   // sky-teal
    public static final String MINT       = fg(121);  // soft mint green
    public static final String CORAL      = fg(209);  // coral/salmon red
    public static final String SLATE      = fg(245);  // muted grey
    public static final String SAND       = fg(230);  // warm off-white

    // ─── Badge Backgrounds ────────────────────────────────────────────────────
    public static final String SUCCESS_BG = bg(22);   // dark green
    public static final String ERROR_BG   = bg(88);   // dark red
    public static final String WARN_BG    = bg(130);  // dark amber
    public static final String INFO_BG    = bg(23);   // dark teal (NOT blue)

    // ─── Layout ───────────────────────────────────────────────────────────────
    public static final int    BOX_WIDTH  = 52;
    private static final String THICK     = "═".repeat(BOX_WIDTH);
    private static final String THIN      = "─".repeat(BOX_WIDTH);

    // ─── Spinner frames ───────────────────────────────────────────────────────
    private static final String[] SPIN = {"⠋","⠙","⠹","⠸","⠼","⠴","⠦","⠧","⠇","⠏"};

    // ─── Timestamp ────────────────────────────────────────────────────────────
    private static final DateTimeFormatter TS_FMT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy  HH:mm:ss");

    // ══════════════════════════════════════════════════════════════════════════
    //  SCREEN CONTROL
    // ══════════════════════════════════════════════════════════════════════════

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HEADERS & DIVIDERS
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Centred double-line header box — full BOX_WIDTH wide, no indent.
     *
     *  ╔════════════════════════════════════════════════════╗
     *  ║                   VIEW ACCOUNT                     ║
     *  ╚════════════════════════════════════════════════════╝
     */
    public static void printHeader(String title) {
        String upper = title.toUpperCase();
        int    inner = BOX_WIDTH;
        int    lPad  = (inner - upper.length()) / 2;
        int    rPad  = inner - upper.length() - lPad;

        System.out.println();
        System.out.println(TEAL + "╔" + THICK + "╗" + RESET);
        System.out.printf(TEAL + "║" + RESET
                + BOLD + SAND + "%" + (lPad + upper.length()) + "s"
                + "%-" + rPad + "s" + RESET
                + TEAL + "║" + RESET + "%n", upper, "");
        System.out.println(TEAL + "╚" + THICK + "╝" + RESET);
    }

    /** Left-bar section sub-label:  │ New Account Registration */
    public static void printSectionLabel(String label) {
        System.out.println(AMBER + "  │ " + BOLD + BRIGHT_WHITE + label + RESET);
        System.out.println();
    }

    /** Thin full-width divider. */
    public static void printDivider() {
        System.out.println(SLATE + THIN + RESET);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  STATUS BADGES
    // ══════════════════════════════════════════════════════════════════════════

    /** ✔  SUCCESS  message */
    public static void printSuccess(String message) {
        System.out.println();
        System.out.println(SUCCESS_BG + BOLD + BRIGHT_GREEN + " ✔  SUCCESS " + RESET
                + "  " + BRIGHT_GREEN + message + RESET);
        System.out.println();
    }

    /** ✘  ERROR  message */
    public static void printError(String message) {
        System.out.println();
        System.out.println(ERROR_BG + BOLD + BRIGHT_RED + " ✘  ERROR " + RESET
                + "  " + CORAL + message + RESET);
        System.out.println();
    }

    /** ℹ  INFO  message */
    public static void printInfo(String message) {
        System.out.println(INFO_BG + BOLD + TEAL_LIGHT + " ℹ  INFO " + RESET
                + "  " + TEAL_LIGHT + message + RESET);
    }

    /** ⚠  WARN  message */
    public static void printWarn(String message) {
        System.out.println(WARN_BG + BOLD + AMBER + " ⚠  WARN " + RESET
                + "  " + AMBER_SOFT + message + RESET);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  INPUT PROMPT
    // ══════════════════════════════════════════════════════════════════════════

    public static void printPrompt(String message) {
        System.out.print(AMBER + "  ▸ " + BRIGHT_WHITE + message + RESET);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SPINNER / LOADING
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * In-place braille spinner for {@code millis} ms.
     * Replaces itself with a ✔ done line when finished.
     */
    public static void showLoading(String message, int millis) {
        int frames = millis / 80;
        System.out.println();
        try {
            for (int i = 0; i < frames; i++) {
                System.out.print("\r  "
                        + TEAL + SPIN[i % SPIN.length] + "  "
                        + BRIGHT_WHITE + message + "..." + RESET + "   ");
                System.out.flush();
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.print("\r  " + MINT + "✔  " + DIM + message + " — done." + RESET
                + "                    \n");
    }

    /** Default 1 200 ms spinner. */
    public static void showLoading(String message) {
        showLoading(message, 1200);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ACCOUNT CARD
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Renders a ┌─┐ account summary card.
     */
    public static void printAccountCard(int accountId, String holderName, long balance) {
        String ts    = LocalDateTime.now().format(TS_FMT);
        String bal   = String.format("%,d", balance);
        String hLine = "─".repeat(BOX_WIDTH);

        System.out.println();
        System.out.println(TEAL + "  ┌" + hLine + "┐" + RESET);

        // Title row
        String idTag  = "#" + accountId;
        String title  = "ACCOUNT DETAILS";
        int    gap    = BOX_WIDTH - title.length() - idTag.length() - 2;
        System.out.println(TEAL + "  │ " + RESET
                + BOLD + SAND + title + " ".repeat(Math.max(0, gap))
                + SLATE + idTag + RESET
                + TEAL + " │" + RESET);

        // Sub-divider
        System.out.println(TEAL + "  │ " + DIM + "─".repeat(BOX_WIDTH - 2) + RESET + TEAL + " │" + RESET);

        cardRow("Holder",  BRIGHT_WHITE + holderName + RESET);
        cardRow("Balance", BRIGHT_GREEN + "₹ " + bal + RESET);

        // Timestamp row — right-aligned
        String tsLabel = "Last accessed: " + ts;
        int    tsPad   = BOX_WIDTH - tsLabel.length() - 1;
        System.out.println(TEAL + "  │" + RESET
                + " ".repeat(Math.max(0, tsPad))
                + DIM + tsLabel + RESET + " "
                + TEAL + "│" + RESET);

        System.out.println(TEAL + "  └" + hLine + "┘" + RESET);
        System.out.println();
    }

    private static void cardRow(String label, String value) {
        // visible value length (strip ANSI for padding)
        int visLen = value.replaceAll("\u001B\\[[;\\d]*m", "").length();
        int pad    = BOX_WIDTH - 2 - label.length() - 3 - visLen; // " │ label : value pad │"
        System.out.println(TEAL + "  │ " + RESET
                + SLATE + label + RESET + " : "
                + value
                + " ".repeat(Math.max(0, pad))
                + TEAL + " │" + RESET);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  BALANCE DELTA
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Before: ₹20,000  →  After: ₹25,000  (+₹5,000)
     */
    public static void printBalanceChange(long before, long after) {
        long   delta = after - before;
        String sign  = delta >= 0 ? "+" : "−";
        String color = delta >= 0 ? BRIGHT_GREEN : CORAL;
        System.out.printf("  "
                        + SLATE  + "Before: " + RESET + BRIGHT_WHITE + "₹%,d" + RESET
                        + SLATE  + "   →   " + RESET
                        + BRIGHT_WHITE + "After: ₹%,d" + RESET
                        + "   " + color + "(" + sign + "₹%,d)" + RESET + "%n",
                before, after, Math.abs(delta));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TRANSACTION TABLE
    // ══════════════════════════════════════════════════════════════════════════

    public static void printTransactionTableHeader() {
        System.out.println();
        System.out.println(SLATE + "  " + "─".repeat(88) + RESET);
        System.out.printf("  " + BOLD + SAND + "  %-36s  %-16s  %-12s  %-18s%n" + RESET,
                "Transaction ID", "Type", "Amount", "Timestamp");
        System.out.println(SLATE + "  " + "─".repeat(88) + RESET);
    }

    /**
     * @param isCredit true = green ▲, false = coral ▼
     */
    public static void printTransactionRow(String txId, String type,
                                           boolean isCredit, long amount,
                                           String timestamp) {
        String color = isCredit ? BRIGHT_GREEN : CORAL;
        String arrow = isCredit ? "▲" : "▼";
        System.out.printf("  " + DIM + "  %-36s" + RESET
                        + color + "  " + arrow + " %-15s" + RESET
                        + color + "  ₹%-11s" + RESET
                        + SLATE + "  %-18s%n" + RESET,
                txId,
                type,
                String.format("%,d", amount),
                timestamp);
    }

    public static void printTransactionTableFooter(int count) {
        System.out.println(SLATE + "  " + "─".repeat(88) + RESET);
        System.out.println("  " + DIM + "  " + count + " transaction(s)" + RESET);
        System.out.println();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  AUDIT TIMESTAMP
    // ══════════════════════════════════════════════════════════════════════════

    public static void printTimestamp() {
        System.out.println(SLATE + DIM + "  ⏱  "
                + LocalDateTime.now().format(TS_FMT) + RESET);
    }
}