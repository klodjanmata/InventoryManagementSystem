package Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Helper {

    private static final Scanner sc = new Scanner(System.in);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String getStringFromUser(String message) {
        System.out.print(message + ": ");
        return sc.nextLine().trim();
    }

    public static char getCharFromUser(String message) {
        String input = getStringFromUser(message);
        return input.isEmpty() ? '\0' : input.charAt(0);
    }

    public static int getIntFromUser(String message) {
        while (true) {
            System.out.print(message + ": ");
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static float getFloatFromUser(String message) {
        while (true) {
            System.out.print(message + ": ");
            try {
                return Float.parseFloat(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid float value.");
            }
        }
    }

    public static boolean getBooleanFromUser(String message) {
        while (true) {
            System.out.print(message + " (true/false): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("false") || input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("❌ Invalid input. Please enter true/false or yes/no.");
            }
        }
    }

    public static LocalDate getLocalDateFromUser(String message) {
        while (true) {
            System.out.println("Expected date format: dd.MM.yyyy");
            System.out.print(message + ": ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                return null; // User chose not to provide a date
            }

            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Please try again (e.g., 01.10.2025).");
            }
        }
    }

    public static Long getLongFromUser(String message) {
        while (true) {
            System.out.print(message + ": ");
            String input = sc.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Please enter a valid long value.");
            }
        }
    }

    public static double getDoubleFromUser(String message) {
        while (true) {
            System.out.print(message + ": ");
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Please enter a valid decimal value.");
            }
        }
    }

    public static BigDecimal getBigDecimalFromUser(String message) {
        while (true) {
            System.out.print(message + ": ");
            String input = sc.nextLine().trim();
            try {
                return new BigDecimal(input); // avoids double precision issues
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Please enter a valid decimal value.");
            }
        }
    }

    public static String convertListToString(List<String> values) {
        return values == null || values.isEmpty()
                ? "[]"
                : "[" + String.join(", ", values) + "]";
    }

    public static boolean getYesNoFromUser(String message) {
        while (true) {
            System.out.print(message + " (yes/no): ");
            String input = sc.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("❌ Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }
}