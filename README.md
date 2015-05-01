Aces And Kings Solitaire
==============

## Description
* The goal is to move all the card into the ace or king foundations. An ace foundation must have its first card be an ace, and a king foundation must have a king be its first card.
* Cards in the ace foundation must consecutively increase in rank until a king covers the top.
* Cards in the king foundation must consecutively decrease in rank until an ace covers the top.
* The stock pile can deal a card to the waste pile, but only until there are no cards left in the stock pile.

#### View
This program demonstrates the solitaire game Aces And Kings. The game consists of:
  * Stock pile (left most pile in the upper right hand corner with cards face down)
  * Waste pile (right most pile in the upper right hand corner with a card face up)
  * 4 tableaus (middle right hand side with a card each face up)
  * 4 ace foundations (lower left hand corner with no cards)
  * 4 king foundations (lower right hand corner with no cards)
  * 2 reserves (middle left hand side with 13 cards fanning downward face up).

## Requirements
* This project depends on Professor George Heineman's SolitairePluginTutorial project.
  * This project is compiled and included in the "res" folder as a standalone JAR file.

## Building
This project can be imported into Eclipse and built normally.

## Usage
To run the game, run the main class called "AcesAndKings.java".

## Testing
Test cases are located under the "test" source folder. Test cases can be run as JUnit test cases.
