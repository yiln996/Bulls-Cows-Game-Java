import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The Wordle class handles the logic for the wordle game.
 * It initializes to load dictionary with valid words.
 * It also ensures the dictionary is loaded successfully before the game begins.
 */

public class Wordle extends Game {

    public int MAX_GUESS = 6;
    public List<String> validWords = new LinkedList<String>();
    public boolean dictionaryLoaded = false;  // Flag to indicate if the dictionary was successfully loaded

    /**
     * Constructor for Wordle game.
     * It attempts to load the dictionary of valid words.
     * An error message will display while failure.
     */
    public Wordle() {
        try {
            readWordleDictionary();
            dictionaryLoaded = true;
        } catch (IOException e) {
            System.out.println("Error reading dictionary file: " + e.getMessage());
        }
    }

    /**
     * Load dictionary data. Add valid words to the list.
     * If the file does not exist, it will print out error message.
     */
    public void readWordleDictionary() throws IOException {
        File file = new File("dictionary-v1.txt");
        // Check if the file exists, throw an IOException if not
        if (!file.exists()) {
            throw new IOException("Dictionary file not found.");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String word = scanner.next().toLowerCase(); // convert to lowercase
                if (isValidWord(word)) {
                    validWords.add(word);
                }
            }
        }
    }

    /**
     * Checks the validation of a word.
     * It must be exactly 5-letter without duplicated letter.
     */
    public boolean isValidWord(String word) {
        word = word.toLowerCase();
        // Check if exactly five letters
        if (!word.matches("[a-zA-Z]{5}")) {
            return false;
        }

        // Check if no duplicate letters
        Set<Character> characters = new TreeSet<>();
        for (char c : word.toCharArray()) {
            if (!characters.add(c)) return false;  // duplicate found
        }
        return true;
    }

    /**
     * Randomly selects a word within the valid words list.
     */
    public String getComputerWord() {
        int random = (int) (Math.random() * validWords.size());
        return validWords.get(random);
    }

    /**
     * Returns the maximum number of guesses allowed in the game
     */
    public int getMaxGuess() {
        return MAX_GUESS;
    }

    /**
     * Returns a description of the Wordle game.
     * */
    public String toString() {
        return "This is wordle game";
    }


}
