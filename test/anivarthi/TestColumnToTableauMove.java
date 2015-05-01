package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.ColumnToTableauMove;

/**
 * This test case handles all coverage for moving from a reserve to a tableau.
 * 
 * @author Aditya Nivarthi
 */
public class TestColumnToTableauMove extends KSTestCase {

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
	 * Tests moving a card from a reserve to an empty tableau.
	 */
	public void testTableauMove() {
		// Setup
		game.tableaus[0].get();

		// Create move
		ColumnToTableauMove move = new ColumnToTableauMove(game.reserves[0],
				game.reserves[0].get(), game.tableaus[0]);

		// Verify setup
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.tableaus[0].count(), 1);
		assertEquals(game.reserves[0].count(), 12);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.tableaus[0].count(), 0);
		assertEquals(game.reserves[0].count(), 13);
	}

	/**
	 * Tests moving a card from a reserve to a nonempty tableau.
	 */
	public void testTableauInvalidMove() {
		// Create move
		ColumnToTableauMove move = new ColumnToTableauMove(game.reserves[0],
				game.reserves[0].get(), game.tableaus[0]);

		// Verify setup
		assertFalse(move.valid(game));

		// Move should not be completed
		move.doMove(game);
		assertEquals(game.tableaus[0].count(), 1);
	}
}
