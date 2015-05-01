package anivarthi;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

/**
 * This test case handles all coverage for testing mouse events on the deck.
 * 
 * @author Aditya Nivarthi
 */
public class TestDeckController extends KSTestCase {

	AcesAndKings game;
	GameWindow gameWindow;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() {
		game = new AcesAndKings();
		gameWindow = Main.generateWindow(game, Deck.OrderBySuit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() {
		game.dispose();
		gameWindow.dispose();
	}

	/**
	 * Tests clicking on the stock deck and dealing a card to the waste pile.
	 */
	public void testDealCardOnClick() {
		// Setup
		Card topCard = game.deck.peek();

		// Create mouse event
		MouseEvent me = this.createPressed(game, game.wastePileView, 1, 1);

		// Handle mouse event
		game.deckView.getMouseManager().handleMouseEvent(me);

		// Verify result
		assertEquals(topCard, game.wastePile.peek());
	}

	/**
	 * Tests clicking on the stock deck and dealing a card to the waste pile but
	 * failing since the deck is empty.
	 */
	public void testDealCardOnClickEmptyDeck() {
		// Setup
		Card topCard = game.wastePile.peek();
		while (!game.deck.empty()) {
			game.deck.get();
		}

		// Verify setup
		assertEquals(game.deck.count(), 0);

		// Create mouse event
		MouseEvent me = this.createPressed(game, game.wastePileView, 1, 1);

		// Handle mouse event
		game.deckView.getMouseManager().handleMouseEvent(me);

		// Verify result
		assertEquals(topCard, game.wastePile.peek());
		assertEquals(game.deck.count(), 0);
	}
}
