
/**
 * The Game class provides base functionality for games like Bulls & Cows and Wordle.
 * It includes logic to compare guesses and count bulls and cows.
 */

public class Game {

    public Game() {
    }

    /**
     * Returns the default maximum number of allowed guesses.
     * Subclasses can override this value if needed.
     */
    public int getMaxGuess() {
        return 6;
    }

    /**
     * Compares a player's guess against the secret code and calculates:
     * - Bulls: correct digits in the correct position
     * - Cows: correct digits in the wrong position
     */
    public int[] compareGuess(String guess, String secretCode) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            for (int j = 0; j < secretCode.length(); j++) {
                if (guess.charAt(i) == secretCode.charAt(j)) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        return new int[]{bulls, cows};
    }
}
