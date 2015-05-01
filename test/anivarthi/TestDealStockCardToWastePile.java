package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.DealStockCardToWastePileMove;

/**
 * This test case handles all coverage for dealing a card from the stock to the
 * waste pile.
 * 
 * @author Aditya Nivarthi
 */
public class TestDealStockCardToWastePile extends KSTestCase {

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
	 * Tests dealing stock card to waste pile.
	 */
	public void testDealStockCardToWastePile() {
		// Setup
		Card topCard = game.deck.peek();

		// Create move
		DealStockCardToWastePileMove move = new DealStockCardToWastePileMove(
				game.deck, game.wastePile);

		// Verify setup
		assertTrue(move.valid(game));
		assertEquals(73, game.getNumLeft().getValue());

		// Move should be completed
		move.doMove(game);
		assertEquals(72, game.deck.count());
		assertEquals(topCard, game.wastePile.peek());
		assertEquals(72, game.getNumLeft().getValue());

		// Move should be undone correctly
		move.undo(game);
		assertEquals(73, game.deck.count());
		assertEquals(73, game.getNumLeft().getValue());
	}

	/**
	 * Tests dealing stock card to waste pile with empty deck.
	 */
	public void testDealStockWithEmptyDeck() {
		// Setup
		while (!game.deck.empty()) {
			game.deck.get();
		}

		// Create move
		DealStockCardToWastePileMove move = new DealStockCardToWastePileMove(
				game.deck, game.wastePile);

		// Move should not be completed
		assertFalse(move.valid(game));
		assertFalse(move.doMove(game));
	}
}
