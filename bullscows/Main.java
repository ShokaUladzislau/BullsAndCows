package bullscows;

import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int bulls = 0;
        int cows = 0;
        String numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz";
        System.out.println("Input the length of the secret code:");
        int secretLength = 0;
        String value = "";

        try {
            value = sc.next();
            secretLength = Integer.parseInt(value);
            if (secretLength == 0) {
                writeErrMessage("Error: " + value + " isn't a valid number.");
            }
        } catch (Exception e) {
            writeErrMessage("Error: " + value + " isn't a valid number.");
        }

        System.out.println("Input the number of possible symbols in the code:");
        int possibleSymbols = sc.nextInt();
        if (possibleSymbols < secretLength) {
            writeErrMessage("Error: it's not possible to generate a code with a length of " + secretLength + " with " + possibleSymbols + " unique symbols.");
        } else if (possibleSymbols > 36) {
            writeErrMessage("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        }

        String secret = randomGenerator(secretLength);
        
        if (possibleSymbols < 10) {
            System.out.println("The secret is prepared: " + Stream.generate(() -> "*").limit(secretLength).collect(Collectors.joining()) + " (0-" + numbersAndLetters.charAt(possibleSymbols - 1) + ").");
        } else {
            System.out.println("The secret is prepared: " + Stream.generate(() -> "*").limit(secretLength).collect(Collectors.joining()) + " (0-9, a-" + numbersAndLetters.charAt(possibleSymbols - 1) + ").");
        }

        int attempts = 0;
        System.out.println("Okay, let's start a game!");
        while (bulls != secretLength) {
            attempts++;
            bulls = 0;
            cows = 0;
            System.out.printf("Turn %d:\n", attempts);
            String guess = sc.next();
            for (int i = 0; i < guess.length(); i++) {
                if (secret.charAt(i) == guess.charAt(i)) {
                    bulls++;
                } else {
                    for (int j = 0; j < guess.length(); j++) {
                        if (secret.charAt(i) == guess.charAt(j)) {
                            cows++;
                        }
                    }
                }
            }
            System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
        }
    }

    public static String randomGenerator(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        while (result.length() != length) {
            int randomNumber = random.nextInt(10);
            if (!result.toString().contains(String.valueOf(randomNumber))) {
                result.append(randomNumber);
            }
        }
        return result.toString();
    }

    private static void writeErrMessage(String message) {
        System.out.println(message);
        System.exit(0);
    }
}