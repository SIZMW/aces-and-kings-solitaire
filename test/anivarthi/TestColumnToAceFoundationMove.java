package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.ColumnToAceFoundationMove;

/**
 * This test case handles all coverage for moving from a reserve to an ace
 * foundation.
 * 
 * @author Aditya Nivarthi
 */
public class TestColumnToAceFoundationMove extends KSTestCase {

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
	 * Tests moving from a reserve to an ace foundation.
	 */
	public void testAceMove() {
		// Setup
		game.reserves[0].get();
		game.reserves[0].add(new Card(1, 1));

		// Create move
		ColumnToAceFoundationMove move = new ColumnToAceFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.aceFoundations[0]);

		// Verify setup
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.reserves[0].count(), 12);
		assertEquals(game.aceFoundations[0].count(), 1);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.reserves[0].count(), 13);
		assertEquals(game.aceFoundations[0].count(), 0);
	}

	/**
	 * Tests moving from a reserve to an ace foundation and failing due to rank.
	 */
	public void testAceInvalidMove() {
		// Create move
		ColumnToAceFoundationMove move = new ColumnToAceFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.aceFoundations[0]);

		// Verify setup
		assertFalse(move.valid(game));

		// Move should not be completed
		move.doMove(game);
		assertEquals(game.aceFoundations[0].count(), 0);
	}

	/**
	 * Tests moving from a reserve to an ace foundation where the ace foundation
	 * is not empty.
	 */
	public void testAceNextRankMove() {
		// Setup
		game.reserves[0].get();
		game.reserves[0].add(new Card(2, 1));
		game.aceFoundations[0].add(new Card(1, 1));

		// Create move
		ColumnToAceFoundationMove move = new ColumnToAceFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.aceFoundations[0]);

		// Verify setup
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.reserves[0].count(), 12);
		assertEquals(game.aceFoundations[0].count(), 2);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.reserves[0].count(), 13);
		assertEquals(game.aceFoundations[0].count(), 1);
	}

	/**
	 * Tests moving from an reserve to an ace foundation where the ace
	 * foundation is not empty and failing due to rank.
	 */
	public void testAceNextRankInvalidMove() {
		// Setup
		game.aceFoundations[0].add(new Card(1, 1));

		// Create move
		ColumnToAceFoundationMove move = new ColumnToAceFoundationMove(
				game.reserves[0], game.reserves[0].get(),
				game.aceFoundations[0]);

		// Verify setup
		assertFalse(move.valid(game));

		// Move should not be done
		move.doMove(game);
		assertEquals(game.aceFoundations[0].count(), 1);
	}
}
