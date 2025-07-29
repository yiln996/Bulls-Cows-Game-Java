import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This AI class is a subclass of Game and a parent class of EasyAI, MediumAI and HardAI.
 * It is controlling the base methods for the bulls and cows game.
 */

public class AI extends Game {

    ArrayList<String> guessLog = new ArrayList<>();
    int MAX_GUESS = 7;

    /**
     * This method randomly generates a 4 digits code (non-repeating)
     */
    public String getRandomCode() {
        String computerSecretCode = "";
        Integer[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> digitsList = new ArrayList<>(Arrays.asList(digits));
        int randomNumber = 0;

        for (int i = 0; i < 4; i++) {
            randomNumber = (int) (Math.random() * digitsList.size());
            computerSecretCode += digitsList.get(randomNumber);
            digitsList.remove(randomNumber);
        }
        return computerSecretCode;
    }

    /**
     * Gets the secret code by calling getRandomCode().
     */
    public String getComputerSecretCode() {
        return getRandomCode();
    }

    /**
     * Gets the guess from computer, for base AI it is selected randomly.
     * Subclasses can override this value if needed.
     */
    public String getComputerGuess() {
        return getRandomCode();
    }

    /**
     * Adds a guess to the AI's guess log. Track all previous guesses.
     */
    public void updateGuessLog(String guess) {
        guessLog.add(guess);
    }


    /**
     *  clears the guessLog
     * */
    public void reset() {
        guessLog.clear();
    }


    /**
     * Returns the maximum number of guesses allowed in the game
     */
    @Override
    public int getMaxGuess() {
        return MAX_GUESS;
    }

    /**
     * Returns a description of the bulls and cows game.
     */
    public String toString() {
        return "This is bulls & cows game";
    }

}
