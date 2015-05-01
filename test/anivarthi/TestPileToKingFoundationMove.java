package anivarthi;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;
import move.PileToKingFoundationMove;

/**
 * This test case handles all coverage for the PileToKingFoundationMove class.
 * 
 * @author Aditya Nivarthi
 */
public class TestPileToKingFoundationMove extends KSTestCase {

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
	 * Tests move from king foundation to another empty king foundation.
	 */
	public void testKingMove() {
		game.kingFoundations[0].add(new Card(13, 1));

		PileToKingFoundationMove move = new PileToKingFoundationMove(
				game.kingFoundations[0], game.kingFoundations[0].get(),
				game.kingFoundations[1], game.deck);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.kingFoundations[1].count(), 1);
		assertEquals(game.kingFoundations[0].count(), 0);
		
		// Move should be undone correctly
		assertTrue(move.undo(game));
		assertEquals(game.kingFoundations[1].count(), 0);
		assertEquals(game.kingFoundations[0].count(), 1);
	}

	/**
	 * Tests move from a king foundation to another empty king foundation where
	 * move is invalid because card is not of rank king.
	 */
	public void testInvalidMove() {
		game.kingFoundations[0].add(new Card(12, 1));
		
		PileToKingFoundationMove move = new PileToKingFoundationMove(
				game.kingFoundations[0], game.kingFoundations[0].get(),
				game.kingFoundations[1], game.deck);
		assertFalse(move.valid(game));

		// Move should not be completed
		move.doMove(game);
		assertEquals(game.kingFoundations[1].count(), 0);
	}

	/**
	 * Tests move from king foundation to another nonempty king foundation.
	 */
	public void testNextRankMove() {
		game.kingFoundations[0].add(new Card(11, 1));
		game.kingFoundations[0].add(new Card(12, 1));
		game.kingFoundations[1].add(new Card(13, 1));

		PileToKingFoundationMove move = new PileToKingFoundationMove(
				game.kingFoundations[0], game.kingFoundations[0].get(),
				game.kingFoundations[1], game.deck);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.kingFoundations[1].count(), 2);
	}

	/**
	 * Tests move from tableau to empty king foundation with automatic tableau
	 * fill.
	 */
	public void testNextRankTableauMove() {
		game.tableaus[0].get();
		game.tableaus[0].add(new Card(13, 1));

		PileToKingFoundationMove move = new PileToKingFoundationMove(
				game.tableaus[0], game.tableaus[0].get(),
				game.kingFoundations[0], game.deck);
		assertTrue(move.valid(game));

		// Move should be completed
		move.doMove(game);
		assertEquals(game.kingFoundations[0].count(), 1);
		assertEquals(game.tableaus[0].count(), 1);

		// Move should be undone correctly
		move.undo(game);
		assertEquals(game.kingFoundations[0].count(), 0);
		assertEquals(game.tableaus[0].count(), 1);
		assertEquals(game.deck.peek().getRank(), 9);
	}
}
