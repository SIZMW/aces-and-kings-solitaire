package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.PileToAceFoundationMove;

/**
 * This test case handles all coverage for the PileToAceFoundationMove class.
 * 
 * @author Aditya Nivarthi
 */
public class TestPileToAceFoundationMove extends KSTestCase {

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
	 * Tests move from ace foundation to another ace king foundation.
	 */
	public void testAceMove() {
		game.aceFoundations[0].add(new Card(1, 1));

		PileToAceFoundationMove move = new PileToAceFoundationMove(
				game.aceFoundations[0], game.aceFoundations[0].get(),
				game.aceFoundations[1], game.deck);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.aceFoundations[0].count(), 0);
		assertEquals(game.aceFoundations[1].count(), 1);

		// Move should be undone correctly
		assertTrue(move.undo(game));
		assertEquals(game.aceFoundations[1].count(), 0);
		assertEquals(game.aceFoundations[0].count(), 1);
	}

	/**
	 * Tests move from an ace foundation to another empty ace foundation where
	 * move is invalid because card is not of rank ace.
	 */
	public void testInvalidMove() {
		game.aceFoundations[0].add(new Card(2, 1));

		PileToAceFoundationMove move = new PileToAceFoundationMove(
				game.aceFoundations[0], game.aceFoundations[0].get(),
				game.aceFoundations[1], game.deck);
		assertFalse(move.valid(game));

		// Move should not be completed
		move.doMove(game);
		assertEquals(game.aceFoundations[1].count(), 0);
	}

	/**
	 * Tests move from ace foundation to another nonempty ace foundation.
	 */
	public void testNextRankMove() {
		game.aceFoundations[0].add(new Card(3, 1));
		game.aceFoundations[0].add(new Card(2, 1));
		game.aceFoundations[1].add(new Card(1, 1));

		PileToAceFoundationMove move = new PileToAceFoundationMove(
				game.aceFoundations[0], game.aceFoundations[0].get(),
				game.aceFoundations[1], game.deck);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.aceFoundations[1].count(), 2);
	}

	/**
	 * Tests move from tableau to empty ace foundation with automatic tableau
	 * fill.
	 */
	public void testNextRankTableauMove() {
		game.tableaus[0].get();
		game.tableaus[0].add(new Card(1, 1));

		PileToAceFoundationMove move = new PileToAceFoundationMove(
				game.tableaus[0], game.tableaus[0].get(),
				game.aceFoundations[0], game.deck);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.aceFoundations[0].count(), 1);
		assertEquals(game.tableaus[0].count(), 1);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.aceFoundations[0].count(), 0);
		assertEquals(game.tableaus[0].count(), 1);
		assertEquals(game.deck.peek().getRank(), 9);
	}
}
