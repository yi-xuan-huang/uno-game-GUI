// Hand.java
/**
 *  This class represents a Hand object used in a Uno game. It is a subclass of Deck. It contains methods
 *  realted to actions of a computer player.
 *
 * @author  Yixuan Huang
 * @version Last modified 5/12/2019
 *
 */
import java.util.*;
class Hand extends Deck {

    // unique field for the name of the hand holder
    private String name;

    // constructor
    public Hand (String name, int numberOfCards) {
        super (numberOfCards);
        this.name = name;
    }


    /** Set the name of the hand
     **/
    public void setName (String name) {
        this.name = name;
    }

    /** Return the name of the hand
     **/
    public String getName () {
        return name;
    }

    /** A method to define the strategies of what cart a computer player will play next, and
     *  play that card.
     *
     * @param    game, the current game object
     * @param    deck
     * @param    discarded
     *
     * @return   a Card object representing the card played (or how many cards drew)
     **/
    public boolean isLegal (Game game, Card card) {
        if ((game.getCurrentValue().equals("Draw2") || game.getCurrentValue().equals("WDraw4"))
            && game.getCurrentDraw()>0) {
            if (game.getCurrentValue().equals(card.getValue())) return true;
            else return false;
        }

        if (game.getCurrentValue().equals(card.getValue()) |
            game.getCurrentColor().equals(card.getColor())) {
            return true;
        }

        if (card.getValue().equals("Wild") || card.getValue().equals("WDraw4")) {
            return true;
        }
        return false;
    }


    /** A method to determine whether a card has a numeric value.
     *
     * @param    card,      Card object of interest
     *
     * @return   a boolean
     **/
    public boolean isNormal (Card card) {
        return card.getValue().length() < 2;
    }


    /** A method to find the position of the card for the computer to play next, based on a set of
     *  strategies. If no card can be played, return -1.
     *
     * @param    game, the current game object
     *
     * @return   the index of the card in the hand
     **/
    public int locateCardToPlay (Game game) {

        ArrayList<Integer> playableNormal = new ArrayList<Integer>();
        ArrayList<Integer> playableOther = new ArrayList<Integer>();
        ArrayList<Integer> wilds = new ArrayList<Integer>();

        // iterate through all the cards and categorize the legal cards into numeric cards,
        // action cards, and wild cards
        for (int i=0; i<this.getCards().size();i++) {
            if ( isLegal (game, this.getCards().get(i)) ) {
                if ( isNormal(this.getCards().get(i)) ) {
                    playableNormal.add(i);
                } else if ( this.getCards().get(i).getValue().charAt(0)!='W' ) {
                    playableOther.add(i);
                } else {
                    wilds.add(i);
                }
            }
        }

        // choose from legal action cards first, if there are multiple, locate a random one;
        // if no legal action cards are present, locate a random numeric card;
        // if no numeric cards either, play a wild card;
        // if not, return -1 to draw
        int cardPosition = -1;
        if (playableNormal.size() > 0) {
            if (playableNormal.size() == 1) {
                cardPosition = playableNormal.get(0);
            } else {
                int which = Uno.rint(0, playableNormal.size()-1);
                cardPosition = playableNormal.get(which);
            }
        } else if ( playableOther.size() > 0) {
            if (playableOther.size() == 1) {
                cardPosition = playableOther.get(0);
            } else {
                int which = Uno.rint(0, playableOther.size()-1);
                cardPosition = playableOther.get(which);
            }
        } else if ( wilds.size() > 0 ) {
            if (wilds.size() == 1) {
                cardPosition = wilds.get(0);
            } else {
                int which = Uno.rint(0, wilds.size()-1);
                cardPosition = wilds.get(which);
            }
        }
        return cardPosition;
    }


    /** Choose a color for the wild card played based on numbers of each color in the hand.
     *
     * @param    played, the wild card to play
     **/
    public void setWildCardColor (Card played) {
        // strategy for choosing a color for the wild cards: which ever color of cards the player
        // currently has the most
        int red = 0;
        int blue = 0;
        int green = 0;
        int yellow = 0;
        // iterate through all the cards and count the colors
        for (int i=0; i<this.getCards().size(); i++) {
            if (this.getCards().get(i).getColor().equals("R")) red++;
            else if (this.getCards().get(i).getColor().equals("B")) blue++;
            else if (this.getCards().get(i).getColor().equals("G")) green++;
            else if (this.getCards().get(i).getColor().equals("Y")) yellow++;
        }
        if (red >= blue && red >= green && red >= yellow) played.setColor("R");
        else if (blue >= red && blue >= green && blue >= yellow) played.setColor("B");
        else if (green >= red && green >= blue && green >= yellow) played.setColor("G");
        else if (yellow >= red && yellow >= green && yellow >= blue) played.setColor("Y");
        // if all are equal, then choose red
        else {played.setColor("R");}

    }


    /** A method to draw cards from the deck
     *
     * @param    deck,      to draw cards from
     * @param    num,       numbers of cards to draw
     * @param    discarded, if the deck runs out of cards, the discarded pile becomes the new deck
     *
     * @return   a Card object representing the card played (or how many cards drew)
     **/
    public void draw (Deck deck, int num, Deck discarded) {
        for (int i=0; i<num; i++) {
            // if no more cards in the deck, make the discarded pile the deck and shuffle
            if (deck.getCards().size() < 1 || deck.getNumberOfCards() < 1) {
                deck = new Deck (discarded);
                deck.shuffle();
            }
            this.addCard(deck.removeCard(0));
        }
    }


    /** A method to perform the action of either playing a card or drawing one or more cards.
     *
     * @param    game, the current game object
     * @param    deck, the deck to draw cards from
     * @param    discarded, where the played card goes to
     *
     * @return   a Card object representing the card played (or how many cards drew)
     **/
    public Card comPlay (Game game, Deck deck, Deck discarded) {

        // get the position of the card to play
        int cardPosition = locateCardToPlay (game);

        // if the player plays a card, change the current game color, value, direction, draw, skip
        if (cardPosition != -1) {
            Card played = this.removeCard(cardPosition);

            // strategy for choosing a color for the wild cards: which ever color of cards the player
            // currently has the most
            if(played.getColor().equals("W") && played.getValue().charAt(0) == 'W') {
                setWildCardColor (played);
                game.setCurrentColor( played.getColor() );
            } else if (!played.getColor().equals("")) {
                game.setCurrentColor( played.getColor() );
            }
            game.setCurrentValue (played.getValue());
            game.setCurrentDirection( played.getDirection()*game.getCurrentDirection());
            game.setCurrentDraw( played.getDraw() + game.getCurrentDraw());
            game.setCurrentSkip( played.getSkip() );

            // add the card played to the discareded pile
            discarded.addCard (played);
            return played;

        } else {

            // if the draws are because of draw cards, draw the according number; otherwise draw 1
            if (game.getCurrentDraw() > 0) {
                draw(deck, game.getCurrentDraw(), discarded);
                int d = game.getCurrentDraw();
                game.setCurrentDraw(0);
                // return a Card object used in the display
                return new Card ("", "Drew " + d, 1, 0, 0);
            } else {
                draw (deck, 1, discarded);
                return new Card ("", "Drew 1", 1, 0, 0);
            }
        }
    }
}