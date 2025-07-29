import java.util.ArrayList;

/**
 * MediumAI extends AI. It will keep track of all previous guesses so it will not do repeat guesses.
 **/

public class MediumAI extends AI {

    /**
     * It will keep track of all previous guesses so it will not do repeat guesses.
     */
    @Override
    public String getComputerGuess() {
        while (true) {
            String guess = getRandomCode();
            if (!guessLog.contains(guess)) {
                updateGuessLog(guess);
                return guess;
            }
        }
    }

    /**
     * Returns a description of the bulls and cows game.
     */
    @Override
    public String toString() {
        return "This is bulls & cows game with medium difficulty";
    }

}
