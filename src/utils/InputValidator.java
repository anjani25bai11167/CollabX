package collabx.utils;

import java.util.Scanner;

public final class InputValidator {
    private InputValidator() {}

    public static String readNonEmpty(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty.");
        }
    }

    public static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            int value = readInt(scanner, message);
            if (value > 0) return value;
            System.out.println("Value must be greater than zero.");
        }
    }
}
