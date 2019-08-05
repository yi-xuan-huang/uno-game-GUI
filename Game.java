// Game.java
/**
 *  This class represents a Game object used in a Uno game. It contains fields and methods related to the status of
 *  a game, including the value and color of the last card played, the current direction and numbers of draws
 *  and skip. It also contains a method to call on the action of the next player.
 *
 * @author  Yixuan Huang
 * @version Last modified 5/12/2019
 *
 */
import java.util.ArrayList;
class Game {

    // fields for the current game status
    private String currentColor;
    private int currentDirection;
    private String currentValue;
    private int currentDraw;
    private int currentSkip;
    private Card lastCardPlayed;
    private int turn;
    private boolean gameOver;

    // fields for players and decks in the game
    private Deck deck;
    private Deck discarded;
    private Hand [] hands;
    private Hand user;
    private Hand firstCom;
    private Hand secondCom;
    private Hand thirdCom;

    // constructor
    public Game (Uno uno) {
        currentColor = "";
        currentDirection = 1;
        currentValue = "";
        currentDraw = 0;
        currentSkip = 0;
        deck = new Deck();
        deck.shuffle();
        discarded = new Deck(0);
        firstCom = new Hand("Janet", 7);
        secondCom = new Hand("Bender", 7);
        thirdCom = new Hand("Dolores", 7);
        user = new Hand(uno.getName(), 7);
        hands = new Hand[4];
        hands[0] = user;
        hands[1] = firstCom;
        hands[2] = secondCom;
        hands[3] = thirdCom;
        turn = 0;
        gameOver = false;
        // give each player 7 cards to start
        for(int i=0; i<7; i++) {
            user.draw(deck, 1, discarded);
            firstCom.draw(deck, 1, discarded);
            secondCom.draw(deck, 1, discarded);
            thirdCom.draw(deck, 1, discarded);
        }
    }


    /** A method to have the next computer player to play one turn.
     *
     * @param       u, the Uno object that calls this method.
     **/
    public void play(Uno u) {

        Hand [] players = {user, firstCom, secondCom, thirdCom};
        String [] names = {"You", "Janet", "Bender", "Dolores"};

        // a computer player plays a card, which is passed to the GUI for display;
        u.setLastCardPlayed (players[turn].comPlay(this, deck, discarded));

        // if no card is left in the player's hand, end the game
        if (players[turn].getCards().size()<=0 || players[turn].getNumberOfCards() <= 0) {
            u.setWinner(names[turn]);
            endGame();
        }

        // determine next turn, which depends on the current game direction and number of skip
        turn += currentDirection;
        if (this.currentSkip == 1) {
                turn += currentDirection;
                this.setCurrentSkip(0);
        }
        // if turn is out of bound, change accordingly
        if (turn == -1) {
            turn = 3;
        } else if (turn == -2) {
            turn = 2;
        } else if (turn == 4) {
            turn = 0;
        } else if (turn == 5) {
            turn = 1;
        }
    }


    /** Set gameOver to true.
     **/
    public void endGame() {
        gameOver = true;
    }

    /** Get whether the game is over.
     *
     * @ return     a boolean
     **/
    public boolean getGameOver() {
        return gameOver;
    }


    /** Returns the deck of the game from which players draw cards.
     *
     * @ return     a Deck object
     **/
    public Deck getDeck() {
        return deck;
    }


    /** Return the discarded pile of the game to which cards played go.
     *
     * @ return     a Deck object
     **/
    public Deck getDiscarded() {
        return discarded;
    }


    /** Return the current color of the game as a String.
     **/
    public String getCurrentColor () {
        return currentColor;
    }

    /** Set the current color of the game as a String.
     **/
    public void setCurrentColor (String currentColor) {
        this.currentColor = currentColor;
    }


    /** Return the current value of the game.
     **/
    public String getCurrentValue () {
        return currentValue;
    }

    /** Set the current value.
     **/
    public void setCurrentValue (String currentValue) {
        this.currentValue = currentValue;
    }


    /** Return the current direction of the game.
     **/
    public int getCurrentDirection () {
        return currentDirection;
    }

    /** Set the current direction of the game.
     **/
    public void setCurrentDirection (int currentDirection) {
        this.currentDirection = currentDirection;
    }

    /** Return the current number of draws.
     **/
    public int getCurrentDraw () {
        return currentDraw;
    }

    /** Set the current number of draws.
     **/
    public void setCurrentDraw (int currentDraw) {
        this.currentDraw = currentDraw;
    }


    /** Return the current number of skip.
     **/
    public int getCurrentSkip () {
        return currentSkip;
    }

    /** Set the current number of skip.
     **/
    public void setCurrentSkip (int currentSkip) {
        this.currentSkip = currentSkip;
    }


    /** Return an array of all the hands in the game.
     **/
    public Hand [] getHands() {
        return hands;
    }


    /** Return a Hand object representing the user's hand.
     **/
    public Hand getUsersHand() {
        return user;
    }


    /** Return the next player.
     **/
    public int getTurn() {
        return turn;
    }

    /** Set the next player.
     **/
    public void setTurn(int turn) {
        this.turn = turn;
    }


    /** Return the Card object that is last played.
     **/
    public Card getLastCardPlayed () {
        return lastCardPlayed;
    }

    /** Set the current color of the game as a String.
     **/
    public void setLastCardPlayed (Card lastCardPlayed) {
        this.lastCardPlayed = lastCardPlayed;
    }
}