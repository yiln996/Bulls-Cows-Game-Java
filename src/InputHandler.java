
/**
 * The InputHandler class provides static utility methods
 * for collecting and validating input from the player.
 */

public class InputHandler {

    /** Get user input for an integer input between min and max (inclusive) */
    public static int getValidIntInput(String message, int min, int max) {
        int inputNum = 0;
        while (true) {
            System.out.print("\n" + message);
            try {
                inputNum = Integer.parseInt(Keyboard.readInput());
                if (inputNum < min || inputNum > max) {
                    System.out.println("Invalid input. Try again.");
                } else {
                    return inputNum;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }

        }
    }

    /** Gets string input from the user */
    public static String getStringInput(String message) {
        System.out.print("\n" + message);
        return Keyboard.readInput().trim();
    }
}