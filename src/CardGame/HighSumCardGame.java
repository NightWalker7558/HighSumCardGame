package CardGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * HighSumCardGame.java - A class that allows the user to play a game of High Sum.
 *
 * @version 1.10 - 1 Oct 2023
 * @author Muhammad Ashhub Ali
 * @author Adeel Ahmed
 * @author Ahmad Zafar
 */
public class HighSumCardGame {

  BufferedReader bf;
  ArrayList<String> dealerCards;
  ArrayList<String> playerCards;
  CardDeck cardDeck;
  String playerName;
  int playerChips, dealerChips;
  int totalBet, playerBet, dealerBet;
  Boolean isGameCancelled;

  final int MIN_BET_FOR_DEALER = 10;
  final int MAX_BET_FOR_DEALER = 30;

  // Constructor
  public HighSumCardGame() {
    this.bf = new BufferedReader(new InputStreamReader(System.in));
    this.dealerCards = new ArrayList<>();
    this.playerCards = new ArrayList<>();
    this.cardDeck = new CardDeck();
    this.playerChips = this.dealerChips = 100;
    this.totalBet = this.playerBet = this.dealerBet = 0;
    this.isGameCancelled = false;
    this.playerName = "Player";
  }

  /**
   * Allows to pass the cards to the player and dealer decks.
   * @see CardDeck#passCard()
   * @see ArrayList#add(Object)
   */
  public void makePass() {
    this.playerCards.add(this.cardDeck.passCard());
    this.dealerCards.add(this.cardDeck.passCard());
  }

  /**
   * Returns the suit value of a card.
   * @param suit the suit of the card
   * @return the suit value of a card
   */
  public int returnSuitValue(String suit) {
    return switch (suit) {
      case "Spade" -> 3;
      case "Heart" -> 2;
      case "Club" -> 1;
      default -> 0;
    };
  }

  /**
   * Returns the card value of a card.
   * @param val the value of the card
   * @return the card value of a card
   * @see String#equals(Object)
   * @see Integer#parseInt(String)
   */
  public int returnCardValue(String val) {
    if (val.equals("Ace")) return 1; else if (
      val.equals("King") ||
      val.equals("Queen") ||
      val.equals("Jack") ||
      val.equals("10")
    ) return 10; else {
      return Integer.parseInt(val);
    }
  }

  /**
   * Calculates the value at hand.
   * @param cards the cards at hand
   * @return the value at hand
   * @see String#split(String)
   * @see #returnCardValue(String)
   */
  public int calcValue(ArrayList<String> cards) {
    int value = 0;
    for (String card : cards) {
      value += returnCardValue((card.split("\\W+"))[1]);
    }
    return value;
  }

  /**
   * Display the player's cards.
   * @see #calcValue(ArrayList)
   */
  public void viewUserCards() {
    System.out.println(this.playerName);
    for (String card : this.playerCards) {
      System.out.print("<" + card + "> ");
    }
    System.out.println();
    System.out.println("Value: " + calcValue(this.playerCards));
  }

  /**
   * Display the dealer's cards. (Hides the first card)
   * @see #calcValue(ArrayList)
   */
  public void viewDealerCards() {
    System.out.println("Dealer");
    System.out.print("<HIDDEN CARD> ");
    for (int i = 1; i < this.dealerCards.size(); i++) {
      System.out.print("<" + this.dealerCards.get(i) + "> ");
    }
    System.out.println();
  }

  /**
   * Display the dealer's cards. (Shows all the cards)
   * @see #calcValue(ArrayList)
   */
  public void viewDealerCardsEnd() {
    System.out.println("Dealer");
    for (String card : this.dealerCards) {
      System.out.print("<" + card + "> ");
    }
    System.out.println();
    System.out.println("Value: " + calcValue(this.dealerCards));
  }

  /**
   * Verifies the bet placed by the player.
   * @param bet the bet placed by the player
   * @see #playerCall()
   */
  public void verifyBet(int bet) {
    if (bet > this.playerChips) {
      System.out.println(
        "Insufficient Chips! You have " +
        this.playerChips +
        " chips.\nPlease try again..."
      );
      this.playerCall();
    } else if (bet <= 0) {
      System.out.println(
        "Invalid Input! Please enter a positive whole number..."
      );
      this.playerCall();
    } else {
      this.playerChips -= bet;
      this.dealerChips -= bet;
      this.totalBet += 2 * bet;
      System.out.println("You are left with:" + this.playerChips + " chips.");
      System.out.println("Bet on the Table:" + this.totalBet + " chips.");
    }
  }

  /**
   * Allows the player to call or quit.
   * @exception IOException if an input or output exception occurrs
   * @see #verifyBet(int)
   */
  public void playerCall() {
    while (true) {
      try {
        System.out.println("Do you want to [C]all or [Q]uit? > ");
        String choice = bf.readLine();
        if (
          !choice.toLowerCase(Locale.ROOT).equals("c") &&
          !choice.toLowerCase(Locale.ROOT).equals("call")
        ) {
          dealerChips += totalBet;
          System.out.println("Dealer WINS!");
          System.out.println("Dealer Chips: " + dealerChips);
          System.out.println();

          isGameCancelled = true;
          return;
        }
      } catch (Exception e) {
        continue;
      }
      break;
    }

    while (true) {
      try {
        System.out.println(this.playerName + " calls , Enter bet amount: ");
        this.playerBet = Integer.parseInt(this.bf.readLine());
        this.verifyBet(this.playerBet);
      } catch (Exception e) {
        System.out.println(
          "Invalid Input! Please try again.. (Enter a positive whole number))"
        );
        continue;
      }
      break;
    }
  }

  /**
   * Allows the dealer to call or quit.
   * @see #dealerCall()
   */
  public void dealerCall() {
    int min = MIN_BET_FOR_DEALER;
    int max = MAX_BET_FOR_DEALER;
    if (this.dealerChips < 30) {
      max = this.dealerChips;
      min = 3;
    }
    Random rand = new Random();
    int dealerBet = rand.nextInt(max - min + 1) + min;

    while (true) {
      try {
        System.out.println("Dealer call , bet amount: " + dealerBet);
        System.out.println("Do you want to follow? (Y/N)");
        String choice = this.bf.readLine();
        if (
          choice.equals("y") ||
          choice.equals("Y") ||
          choice.equals("yes") ||
          choice.equals("Yes")
        ) {
          this.dealerChips -= dealerBet;
          this.playerChips -= dealerBet;
          this.totalBet += 2 * dealerBet;
          System.out.println(
            this.playerName + " is left with: " + this.playerChips + " chips."
          );
          System.out.println("Bet on the Table: " + this.totalBet + " chips.");
        } else {
          this.dealerChips += this.totalBet;
          System.out.println("Dealer WINS!");
          System.out.println("Dealer Chips: " + this.dealerChips);
          System.out.println();
          this.isGameCancelled = true;
        }
      } catch (Exception e) {
        System.out.println("Invalid Input! Please try again...");
        continue;
      }
      break;
    }
  }

  /**
   * Selects the caller based on the cards in previous round.
   * @see #returnSuitValue(String)
   * @see #dealerCall()
   * @see #playerCall()
   */
  public void selectCaller(int round) {
    if (
      returnSuitValue((playerCards.get(round).split("\\W+"))[0]) >
      returnSuitValue((dealerCards.get(round).split("\\W+"))[0])
    ) {
      playerCall();
    } else if (
      returnSuitValue((playerCards.get(round).split("\\W+"))[0]) <
      returnSuitValue((dealerCards.get(round).split("\\W+"))[0])
    ) {
      dealerCall();
    } else {
      if (
        returnCardValue((playerCards.get(round).split("\\W+"))[1]) >
        returnCardValue((dealerCards.get(round).split("\\W+"))[1])
      ) {
        playerCall();
      } else {
        dealerCall();
      }
    }
  }

  /**
   * Runs a round of the game.
   * @param round the round of the game
   * @see #makePass()
   * @see #selectCaller(int)
   * @see #viewDealerCards()
   * @see #viewUserCards()
   */
  public void runRound(int round) {
    System.out.println(("-").repeat(100));
    System.out.println("Dealer dealing cards - ROUND " + round);
    System.out.println(("-").repeat(100));

    this.makePass();
    if (round == 1) this.makePass();

    viewDealerCards();
    System.out.println();
    viewUserCards();

    System.out.println();
    selectCaller(round);
  }

  /**
   * Resets the game stats.
   */
  public void resetGame() {
    dealerCards.clear();
    playerCards.clear();
    playerChips = dealerChips = 100;
    cardDeck.resetDeck();
    this.totalBet = this.playerBet = this.dealerBet = 0;
    this.isGameCancelled = false;
  }
}
