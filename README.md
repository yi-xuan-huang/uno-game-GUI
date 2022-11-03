# uno-game-GUI

## Overview
The popular card game Uno build with Java. In Uno, players take turns playing cards that match the previous card played on its value or color. Some cards have special utilities such as forcing the next player to skip their turn or draw cards, reversing the direction, or changing the current color. The first player to play all the cards in their hand wins the game.

This app utilizes the java Swing and AWT class to create a Graphic User Interface. This GUI simulates the card game and allows human players to compete with three computer players who understand the basic strategy of the game. 

## Demo
![image](demo.gif)

## Usage

Run the executable jar file directly or in CLI run

```
java -jar uno-1.1.jar
```

## Rules of the Game
While there are many variations of Uno, this program implements the basic set of “original” rules (found here). Here’s an explanation of the rules.

The game deck consists of 108 cards, most of which are associated with a specific color (red, blue, yellow, or green):
-	Four cards of the value 0 (one in each color)
-	Eight cards for each value between 1-9 (two in each color)
-	Eight Reverse cards (two in each color), which reverses the direction of play
-	Eight Skip cards (two in each color), which skip the next player’s turn
-	Eight Draw-Two cards (two in each color), which force the next player to draw two cards
-	Four Wild cards, which allow the player to change the color that must next be played
-	Four Wild-Draw-Four cards, which allow the player to change the color that must next be played and that force the next player to draw four cards

## Starting the Game
When the game starts, the deck is shuffled, and each player is dealt seven cards. In this implementation of the game, the human player always starts the game and can play any single card in his or her hand.

### Basic Play
For every subsequent turn, players can play a single card that matches the previous card in either its color (red, blue, yellow, or green) or its value, which can be a numeric value (a number 0-9) or action (Reverse, Skip, Draw-Two, Wild, Wild-Draw-Four). However, players can always play a Wild or Wild-Draw-Four card. If a player cannot play, they must draw one card from the deck.

Cards that are played are placed in a Discard Pile. If the game deck runs out of cards before the game is over, the cards are moved from the Discard Pile to the main deck, which is then shuffled. 

### Action Cards
If a player plays a Reverse, the direction of play reverses direction (and so the next player to play will be the person who played before the player who played the Reverse). If a player plays a Skip, the next player’s turn is skipped. If a player plays a Wild card, they can choose the color the next player must play on. If a player plays a Draw-Two or Wild-Draw-Four, the next player must draw two or four cards, respectively, and that player’s turn is skipped. In the case of a Wild-Draw-Four, the player can also choose the color that the next player must play on.

If a player plays a Draw-Two or Wild-Draw-Four, the next player can avoid the consequences of the action if they can play a card that matches the last card played on its action. Furthermore, the consequences of the action can then be chained, or summed, and applied to the next player. For instance, if a player plays a Draw-Two, the next player would normally have to draw two cards and could not then play a card. However, if that player has a Draw-Two (irrespective of color), they can play it (and therefore they are not skipped), and now the next player must draw four cards, if they do not also have a Draw-Two.

### Ending the Game
A player wins and the game ends when he or she has successfully played all the cards in his or her hand.

### Strategies of Computer Players
While human players can choose the cards they wish to play, computers must be programmed to play according to some algorithm. 

In this program, computers first determine which cards in their hand are playable. They then sort playable cards into three groups: cards with a numeric value (0-9), cards that have both a color and action, and all other cards (namely, Wilds and Wild-Draw-Fours). If there are playable cards with a numeric value, the computer will try to play one of them at random first. If there is no such playable card, the computer will choose from playable cards that have both a color and action (Reverse, Skip, or Draw-Two). If there are no such action cards or cards with a numeric value, only then will the computer play a Wild or Wild-Draw-Four. The three types of cards are considered in this order because probability-wise, a numeric card has the same chance to be a playable card as an action card (this, however, does not take into account all the cards that have been played by all the players), but one would probably want to save their actions cards for more useful occasions (such as when a Draw-Two is played by the last player). And since Wild cards can be played in any situation (unless the player is skipped), they should be saved for when the player can play no other cards. Finally, if there are no legally playable cards, the computer will draw a card. 

When a computer player plays a Wild or Wild-Draw-Four card, it must choose a color. The strategy for choosing a color is to consider what color is the most prominent one in that computer’s hand. 
