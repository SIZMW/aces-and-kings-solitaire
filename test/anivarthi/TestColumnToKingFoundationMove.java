package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.ColumnToKingFoundationMove;

/**
 * This test case handles all coverage for moving from a reserve to a king
 * foundation.
 * 
 * @author Aditya Nivarthi
 */
public class TestColumnToKingFoundationMove extends KSTestCase {

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
	 * Tests moving from a reserve to a king foundation.
	 */
	public void testKingMove() {
		// Setup
		game.reserves[0].get();
		game.reserves[0].add(new Card(13, 1));

		// Create move
		ColumnToKingFoundationMove move = new ColumnToKingFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.kingFoundations[0]);

		// Verify setup
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.reserves[0].count(), 12);
		assertEquals(game.kingFoundations[0].count(), 1);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.reserves[0].count(), 13);
		assertEquals(game.kingFoundations[0].count(), 0);
	}

	/**
	 * Tests moving from a reserve to a king foundation and failing due to rank.
	 */
	public void testAceInvalidMove() {
		// Create move
		ColumnToKingFoundationMove move = new ColumnToKingFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.kingFoundations[0]);

		// Verify setup
		assertFalse(move.valid(game));

		// Move should not be completed
		move.doMove(game);
		assertEquals(game.kingFoundations[0].count(), 0);
	}

	/**
	 * Tests moving from a reserve to a king foundation where the ace foundation
	 * is not empty.
	 */
	public void testAceNextRankMove() {
		// Setup
		game.reserves[0].get();
		game.reserves[0].add(new Card(12, 1));
		game.kingFoundations[0].add(new Card(13, 1));

		// Create move
		ColumnToKingFoundationMove move = new ColumnToKingFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.kingFoundations[0]);

		// Verify setup
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.reserves[0].count(), 12);
		assertEquals(game.kingFoundations[0].count(), 2);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.reserves[0].count(), 13);
		assertEquals(game.kingFoundations[0].count(), 1);
	}

	/**
	 * Tests moving from an reserve to a king foundation where the ace
	 * foundation is not empty and failing due to rank.
	 */
	public void testAceNextRankInvalidMove() {
		// Setup
		game.kingFoundations[0].add(new Card(13, 1));

		// Create move
		ColumnToKingFoundationMove move = new ColumnToKingFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.kingFoundations[0]);

		// Verify setup
		assertFalse(move.valid(game));

		// Move should not be done
		move.doMove(game);
		assertEquals(game.kingFoundations[0].count(), 1);
	}
}
