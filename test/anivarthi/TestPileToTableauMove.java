package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.PileToTableauMove;

/**
 * This test case handles all coverage for the PileToTableauMove class.
 * 
 * @author Aditya Nivarthi
 */
public class TestPileToTableauMove extends KSTestCase {

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
	 * Tests moving a card from the waste pile to the tableau when the deck is
	 * empty.
	 */
	public void testTableauMove() {
		while (!game.deck.empty()) {
			game.deck.get();
		}
		game.tableaus[0].get();

		// Check that setup is correct
		assertEquals(game.tableaus[0].count(), 0);
		assertEquals(game.deck.count(), 0);
		assertEquals(game.wastePile.count(), 1);

		PileToTableauMove move = new PileToTableauMove(game.wastePile,
				game.wastePile.get(), game.tableaus[0]);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.tableaus[0].count(), 1);
		assertEquals(game.wastePile.count(), 0);

		// Move should be undone correctly
		assertTrue(move.undo(game));
		assertEquals(game.tableaus[0].count(), 0);
		assertEquals(game.wastePile.count(), 1);
	}

	/**
	 * Tests moving a card from an ace foundation to a tableau and failing due
	 * to invalid source location.
	 */
	public void testTableauInvalidMove() {
		// Setup
		game.tableaus[0].get();
		game.aceFoundations[0].add(new Card(1, 1));

		// Verify setup
		assertEquals(game.tableaus[0].count(), 0);
		assertEquals(game.aceFoundations[0].count(), 1);

		// Create move
		PileToTableauMove move = new PileToTableauMove(game.aceFoundations[0],
				game.aceFoundations[0].get(), game.tableaus[0]);
		
		// Verify validity of move
		assertFalse(move.valid(game));

		// Move should not be completed
		move.doMove(game);
		assertEquals(game.tableaus[0].count(), 0);
	}
}
