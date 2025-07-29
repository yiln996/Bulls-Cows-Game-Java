import static java.util.regex.Pattern.matches;

/** The player class handles all interactions with the player's input.
 *  It provides methods to validate player input.
 * */

public class Player {

    /** Gets a valid guess for Bulls & Cows game from the player  */
    public String getValidBullsCowsPlayerGuess() {

        while (true) {
            String guess= InputHandler.getStringInput("Enter your guess: ");
            if (isValidCode(guess)) {
                return guess;
            }
            System.out.println("Invalid guess. It should be 4 non-repeating digits. Try again.");
        }
    }

    /** Prompts the player to enter a valid secret code (4 non-repeating digits)*/
    public String getPlayerSecretCode() {
        while (true) {
            //get user input secret code
            String input = InputHandler.getStringInput("Please enter your secret code (4 non-repeating digits): ");
            //Check if the player has chosen a valid secret code.
            if (isValidCode(input)) {
                return input;
            }
            System.out.print("Invalid secret code. It should be 4 non-repeating digits. Try again.");
        }
    }

    /** Validates that the input is exactly 4 digits with no repeats */
    public boolean isValidCode(String code) {
        //Check input length equals 4 and all of them are digits
        if (!code.matches("\\d{4}")) return false;

        //Check no duplicate
        for (int i = 0; i < code.length(); i++) {
            for (int j = 0; j < code.length(); j++) {
                if (i != j && code.charAt(i) == code.charAt(j))  return false;
            }
        }
        return true;
    }

    /** Gets a valid 5-letter word guess from the player for Wordle */
    public String getValidWordleGuess(Wordle wordleGame) {
        while (true) {
            String guessWord = InputHandler.getStringInput("Your guess: ");
            if (wordleGame.isValidWord(guessWord)) {
                return guessWord;
            } else {
                System.out.println("Invalid guess. It should be 5 non-repeating letter. Try again.");
            }
        }
    }

}
