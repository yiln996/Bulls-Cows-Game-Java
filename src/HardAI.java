import java.util.ArrayList;
import java.util.Iterator;

/**
 * HardAI extends AI.  An advanced strategy for bulls and cows.
 * It will narrow down guesses based on feedback from previous attempts.
 */


public class HardAI extends AI {
    // Stores all possible 4-digit codes with non-repeating digits
    public ArrayList<String> possibleCodes = new ArrayList<>();

    // Stores the number of bulls and cows from the last computer guess
    public int[] numBullsCows;

    /**
     * Constructor initializes the list of possible codes.
     */
    public HardAI() {
        possibleCandidates();
    }


    /**
     * Generates all 4-digit codes with unique digits (non-repeating).
     */
    public void possibleCandidates() {
        String guess = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        if (!(i == j || i == k || i == l || j == k || j == l || k == l)) {
                            guess = Integer.toString(i) + Integer.toString(j) + Integer.toString(k) + Integer.toString(l);
                            possibleCodes.add(guess);
                        }
                    }
                }
            }
        }
    }


    /**
     * Selects the computer's next guess.
     * First guess is random. After that, filters possible codes using previous result.
     */

    @Override
    public String getComputerGuess() {
        // First guess is random
        if (guessLog.isEmpty()) {
            String code = getRandomCode();
            updateGuessLog(code); // track guess
            return code;
        }
        // Filter possible codes using result from the last guess
        String previousGuess = guessLog.get(guessLog.size() - 1);
        filterPossibleCandidates(previousGuess, numBullsCows[0], numBullsCows[1]);

        // Pick next guess randomly from remaining candidates
        int index = (int) (Math.random() * possibleCodes.size());
        String nextGuess = possibleCodes.get(index);
        updateGuessLog(nextGuess); // track guess
        return nextGuess;
    }

    /**
     * Filters possibleCodes by removing any that wouldn't match the same
     * number of bulls and cows as the previous guess result.
     */
    public void filterPossibleCandidates(String guess, int bulls, int cows) {
        Iterator<String> myIterator = possibleCodes.iterator();
        while (myIterator.hasNext()) {
            String code = myIterator.next();
            int[] result = compareGuess(guess, code);
            if (result[0] != bulls || result[1] != cows || guess.equals(code)) {
                myIterator.remove(); // Removes codes from the list that do not match the feedback from the previous guess
            }
        }
    }

    /**
     * Stores the number of bulls and cows returned from the last guess.
     * Used for filtering future guesses.
     */
    public void storeResult(String guess, int bulls, int cows) {
        numBullsCows = new int[]{bulls, cows};
    }


    /**
     * Returns a description of the bulls and cows game.
     */
    @Override
    public String toString() {
        return "This is bulls & cows game with hard difficulty";
    }

    /**
     * Resets the internal state of HardAI before a new round.
     * Clears guess log and regenerates all possible codes.
     */
    @Override
    public void reset() {
        super.reset();
        possibleCodes.clear();
        possibleCandidates();
        numBullsCows = null;
    }
}
