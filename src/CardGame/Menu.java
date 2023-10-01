package CardGame;

import java.io.IOException;

/**
 * Menu.java - A class that represents the menu of the HighSumCardGame.
 *
 * @version 1.10 - 30 Sept 2023
 * @author Muhammad Ashhub Ali
 * @author Adeel Ahmed
 * @author Ahmad Zafar
 */
class Menu {

  /** An instance of the HighSumCardGame. */
  HighSumCardGame highSum;
  /**
   * Control instance to check whether the user wants to contune playing another
   * game or not.
   */
  boolean exit;
  /**
   * Determines the number of times (-) should be repeated in the console to
   * create a border.
   */
  private final int COUNT = 100;
  /**
   * Starting coins the participants will have in a high sum card game.
   */
  private final int STARTING_COINS = 100;

  /** Constructs a Menu. */
  public Menu() {
    this.highSum = new HighSumCardGame();
    this.exit = false;
  }

  /**
   * Displays all the possible options of the menu to the user.
   * @exception IOException if an input or output exception occurrs
   */
  public void newGamePrompt() {
    while (true) {
      System.out.println("Do you want to play again? (Y/N)");
      try {
        String choice = highSum.bf.readLine();
        if (
          choice.equals("Y") ||
          choice.equals("y") ||
          choice.equals("yes") ||
          choice.equals("Yes")
        ) {
          highSum.resetGame();
          this.newGame();
        } else {
          System.exit(0);
        }
      } catch (Exception e) {
        System.out.println("Invalid Input! Please try again...");
        continue;
      }
      break;
    }
  }

  /**
   * Displays the stats of the Game on the Console
   */
  public void displayResults() {
    DisplayBorder();
    System.out.println(" Game Ends - Dealer reveals cards.");
    DisplayBorder();
    highSum.viewDealerCardsEnd();
    System.out.println();
    highSum.viewUserCards();
    System.out.println();

    // check the winner
    if (
      highSum.calcValue(highSum.playerCards) ==
      highSum.calcValue(highSum.dealerCards)
    ) {
      System.out.println("Draw!");
      System.out.println(
        "Original bet returned to both dealer and " +
        this.highSum.playerName +
        "."
      );
      highSum.playerChips = STARTING_COINS;
      highSum.dealerChips = STARTING_COINS;
    } else if (
      highSum.calcValue(highSum.playerCards) >
      highSum.calcValue(highSum.dealerCards)
    ) {
      System.out.println(this.highSum.playerName + " Wins!");
      highSum.playerChips += highSum.totalBet;
      System.out.println(
        this.highSum.playerName + " has " + highSum.playerChips + " chips."
      );
    } else {
      System.out.println("Dealer Wins!");
      highSum.dealerChips += highSum.totalBet;
      System.out.println("The dealer has " + highSum.dealerChips + " chips.");
    }

    System.out.println();
  }

  /**
   * Starts a new High Sum Card Game and checks if the player wants to continue
   * playing the game or not.
   */
  public void newGame() {
    this.exit = false;
    DisplayBorder();
    System.out.println("HighSum GAME");
    DisplayBorder();
    System.out.println("You have " + highSum.playerChips + " chips.");
    DisplayBorder();
    System.out.println("Game Starts - Dealer shuffles deck.");
    highSum.cardDeck.shuffleDeck();

    for (int i = 0; i < 4; i++) {
      highSum.runRound(i + 1);
      if (highSum.isGameCancelled) {
        System.out.println("Game Cancelled!");
        this.exit = true;
        break;
      }
    }

    if (!exit) this.displayResults();
    newGamePrompt();
  }

  /**
   * Initializes the start of a new game , by creating a new instance of
   * HighSumCardGame
   * @see #newGame()
   */
  public void Starter() {
    System.out.println("HighSum GAME");
    DisplayBorder();

    while (true) {
      System.out.println("Enter your name: ");
      try {
        highSum.playerName = highSum.bf.readLine();
        break;
      } catch (Exception e) {
        System.out.println("Invalid Input! Please try again...");
        continue;
      }
    }

    this.newGame();
  }

  /**
   * Creates the border in the Console Output
   */
  private void DisplayBorder() {
    System.out.println(("=").repeat(COUNT));
  }

  /**
   * Starting point of the program
   */
  public static void main(String[] args) {
    Menu menu = new Menu();
    menu.Starter();
  }
}
