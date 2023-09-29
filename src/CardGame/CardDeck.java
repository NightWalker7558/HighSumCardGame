package CardGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * CardDeck.java - A class that represents a deck of 52 playing cards.
 *
 * @author Muhammad Ashhub Ali
 * @author Adeel Ahmed
 * @author Ahmad Zafar
 * @version 1.10 - 28 Sept 2023
 */
public class CardDeck {

  /** The deck in use. */
  ArrayList<String> deck;

  /**  Constructs a new CardDeck. */
  public CardDeck() {
    this.deck = generateDeck();
  }

  /**
   * Create a deck of 52 playing cards.
   * For example:
   *            <blockquote>
   *            <pre>
   *            ["Spade Ace", "Spade 2", ... , "Diamond King"]
   *            </pre></blockquote>
   * @return a deck of cards as an ArrayList of Strings.
   */
  private ArrayList<String> generateDeck() {
    String[] suits = { "Spade", "Heart", "Club", "Diamond" };
    String[] values = {
      "Ace",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "Jack",
      "Queen",
      "King",
    };

    ArrayList<String> newCardDeck = new ArrayList<String>();
    for (String suit : suits) {
      for (String value : values) {
        newCardDeck.add(suit + " " + value);
      }
    }
    return newCardDeck;
  }

  /**
   * Shuffles the deck of cards.
   * Randomizes the deck of cards by swapping the position of each card.
   * @see Collections#shuffle
   * @implSpec Uses the <code>Collections.shuffle()</code> method to shuffle the deck.
   */
  public void shuffleDeck() {
    for (int i = 0; i < 3; i++) {
      Collections.shuffle(this.deck);
    }
  }

  /**
   * Passes a card from the deck.
   * @return a single card as a String.
   * @see ArrayList#remove
   * @implSpec Uses the <code>ArrayList.remove()</code> method to remove the card from the deck.
   *         The card is removed from the top of the deck.
   */
  public String passCard() {
    String card = this.deck.get(0);
    this.deck.remove(0);
    return card;
  }

  /**
   * Resets the deck of cards - generates a new deck of cards.
   * @see ArrayList#clear
   * @see #generateDeck
   * @implSpec Uses the <code>ArrayList.clear()</code> method to clear the deck.
   *          Uses the <code>generateDeck()</code> method to generate a new deck.
   */
  public void resetDeck() {
    this.deck.clear();
    this.deck = generateDeck();
  }
}
