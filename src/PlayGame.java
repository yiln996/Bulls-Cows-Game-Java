import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The PlayGame class handles the main game flow for Bulls & Cows and Wordle.
 * It initializes the game mode, runs the game, logs results, and handles saving the game state to a file.
 */

public class PlayGame {
    public int mode;         // Game mode selected by player (1 = Bulls & Cows, 2 = Wordle, 3 = Quit)
    public Player player = new Player();
    public Game currentGame; // Holds the currently selected game instance (Bulls & Cows or Wordle)
    public boolean gameOver = false;
    public ArrayList<String> log = new ArrayList<>();

    /**
     * Initializes the game
     */
    public static void main(String[] args) {
        PlayGame game = new PlayGame();
        game.start();

    }

    /**
     * Starts the game loop. Continuously asks the player for game choices and runs the selected game
     * until the player chose to quit.
     */
    public void start() {
        int round = 1;
        while (true) {
            gameOver = false;                 // Reset gameOver at the start of each round
            displayMenu();                    // get game type and level from player

            if (mode == 1) {                  // player choose to play bulls & cows game
                roundOfGame(round);
                String computerCode = ((AI) currentGame).getComputerSecretCode();
                logMessage("Computer Secret Code: " + computerCode, OutputType.SAVE);
                playBullCowGame(computerCode);

            } else if (mode == 2) {           // player choose to play wordle game
                if (!((Wordle) currentGame).dictionaryLoaded) {
                    System.out.println("Choose another game to play.");
                    continue;
                }
                roundOfGame(round);
                String computerWord = ((Wordle) currentGame).getComputerWord();
                logMessage("\nComputer Word: " + computerWord, OutputType.SAVE);
                playWordleGame(computerWord);

            } else if (mode == 3) {
                processExit();         // Player chose to quit the game; prompt to save the results
                return;                //the player choose to quit, end game
            }
            round++;
        }
    }

    /**
     * Displays the game menu and get the player to select a game mode and difficulty level.
     */
    private void displayMenu() {

        this.mode = getGameMode();
        if (mode == 1) {
            int level = getGameLevel();
            switch (level) {
                case 1:
                    currentGame = new EasyAI();  // EasyAI extends Game
                    break;
                case 2:
                    currentGame = new MediumAI();  // MediumAI extends Game
                    break;
                case 3:
                    currentGame = new HardAI(); // HardAI extends Game
                    break;
            }
            ((AI)currentGame).reset(); // Reset guess log and possible guessing candidates before each new game
        } else if (mode == 2) {
            currentGame = new Wordle();   // Wordle extends Game and will attempt to load dictionary
        }

    }

    /**
     * Prompts player to select a game mode.
     * int representing game mode (1 = Bulls & Cows, 2 = Wordle, 3 = Quit)
     */
    private int getGameMode() {
        String message = "Please select a game to play: 1. Bulls & Cows  2. Wordle  3. Quit\n" +
                "Enter your choice (1, 2, or 3): ";
        return InputHandler.getValidIntInput(message, 1, 3);
    }

    /**
     * Get player to select a level for bulls & cows.
     * 1 = easy, 2 = medium, 3 = hard
     */
    private int getGameLevel() {
        String message = "Your choice is a Bulls & Cows. Please select level of difficulty: 1. Easy 2. Medium 3. Hard\n" +
                "Enter your choice (1, 2, or 3): ";
        return InputHandler.getValidIntInput(message, 1, 3);
    }

    /**
     * Plays the Bulls & Cows game by allowing the player and computer to take turns.
     * The game continues until either the player or the computer wins, or a draw occurs.
     */
    public void playBullCowGame(String computerCode) {
        String playerSecretCode = player.getPlayerSecretCode();
        logMessage("Your secret code: " + playerSecretCode, OutputType.SAVE);

        for (int i = 0; i < ((AI) currentGame).getMaxGuess(); i++) {
            logMessage("---\n" + "Turn " + (i + 1) + ":", OutputType.BOTH);

            String playerGuess = player.getValidBullsCowsPlayerGuess(); //get valid bulls & cows guess from player
            handlePlayerTurn(playerGuess, computerCode);
            if (gameOver) {
                logMessage("You win! :)\n", OutputType.BOTH);
                return;
            }

            handleComputerTurn(playerSecretCode);
            if (gameOver) {
                logMessage("Computer Won! You lose! :(\n", OutputType.BOTH);
                return;
            }
        }
        logMessage("\nIt’s a draw.", OutputType.BOTH);
    }


    /**
     * Handles the player's turn and evaluating the guess
     */
    private void handlePlayerTurn(String playerGuess, String computerCode) {
        logMessage("Your Guess: " + playerGuess, OutputType.SAVE);
        printGuessResult(playerGuess, computerCode);
    }

    /**
     * Handles the computer's turn in the Bulls & Cows game.
     * And evaluating the guess.
     */
    private void handleComputerTurn(String playerSecretCode) {
        String computerGuess = ((AI) currentGame).getComputerGuess();
        logMessage("\nComputer guess: " + computerGuess, OutputType.BOTH);
        int[] result = printGuessResult(computerGuess, playerSecretCode);
        if (currentGame instanceof HardAI) {
            ((HardAI) currentGame).storeResult(computerGuess, result[0], result[1]);
        }
    }

    /**
     * Compares a guess with the secret code and prints the result (bulls and cows).
     * If guesses correctly, it will update gameOver
     */
    public int[] printGuessResult(String guess, String secretCode) {
        int[] result = currentGame.compareGuess(guess, secretCode);
        logMessage("Result: " + result[0] + " bulls and " + result[1] + " cows.", OutputType.BOTH);

        if (result[0] == guess.length()) {
            gameOver = true;
        }
        return result;
    }


    /**
     * Logs a message based on the OutputType:
     * - "PRINT": only prints to console
     * - "SAVE": only saves to log with ArrayList<String> type
     * - "BOTH"
     */
    public void logMessage(String message, OutputType type) {
        if (type == OutputType.PRINT) {
            System.out.println(message);
        } else if (type == OutputType.SAVE) {
            log.add(message);
        } else if (type == OutputType.BOTH) {
            log.add(message);
            System.out.println(message);
        }

    }

    /**
     * Ask the player to decide whether to save the game results to a text file.
     */
    public boolean toSaveResult() {
        String response = InputHandler.getStringInput("Would you like to save the results to a text file? (y/n): ");
        if (response.toLowerCase().equals("y")) {
            return true;
        } else {
            System.out.print("ok, see ya! Bye!");
            return false;
        }
    }

    /**
     * get a valid filename from player
     */
    public String getSaveFilename() {

        while (true) {
            String filename = InputHandler.getStringInput("Enter the filename: ");
            if (isValidFilename(filename)) {
                return filename;
            }
            System.out.println("Invalid filename. Try again.");

        }
    }

    /**
     * Save all the playing game results to a text file
     */
    public void saveResultsToFile(String filename, ArrayList<String> log) {
        File logfile = new File(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logfile))) {
            for (String line : log) {
                writer.write(line + "\n");
            }
            System.out.println("Saved to file: " + filename + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * To check if the input filename provided by player is valid
     * A filename should not start with a digit
     */
    public boolean isValidFilename(String filename) {
        if (filename == null || filename.isEmpty() || Character.isDigit(filename.charAt(0))) {
            return false;
        }
        return true;
    }

    /**
     * Plays the Wordle game, the player guesses the word in a limited number of turns.
     */
    public void playWordleGame(String computerWord) {
        int turn = 1;
        while (turn < ((Wordle) currentGame).getMaxGuess() + 1) { // A limited number of attempts to guess
            logMessage("---\n" + "Turn " + turn + ":", OutputType.BOTH);

            String guessWord = player.getValidWordleGuess((Wordle) currentGame);
            handlePlayerTurn(guessWord.toLowerCase(), computerWord); // convert to lowercase here

            if (gameOver) {
                logMessage("\nYou win! :)\n", OutputType.BOTH);
                return;
            }
            turn++;
        }
        logMessage("\nYou lose! :(\n", OutputType.BOTH);

    }

    /**
     * Logs the start of a new round in the game.
     */
    private void roundOfGame(int round) {
        logMessage("\n======== Round " + round + " ========", OutputType.BOTH);
        logMessage(currentGame.toString(), OutputType.BOTH);
    }

    /**
     * Handles the exit process by getting the player to save the results if there is anything logged.
     */
    private void processExit() {
        // If there is anything logged, ask the player if they want to save
        if (!log.isEmpty()) {
            if (toSaveResult()) {
                String filename = getSaveFilename();  //When multiple games were played, all game results are saved into one file.
                saveResultsToFile(filename, log);
                log.clear();  //After saving the results to a text file, clear the log file
            }
        } else {
            System.out.println("\nBye, see ya!");
        }
    }

}
