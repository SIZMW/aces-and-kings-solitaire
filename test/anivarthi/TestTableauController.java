package anivarthi;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

/**
 * This test case handles all coverage for mouse events on the tableaus.
 * 
 * @author Aditya Nivarthi
 */
public class TestTableauController extends KSTestCase {

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
	 * Tests pressing on a tableau and releasing on an ace foundation to move a
	 * card.
	 */
	public void testTableauToAceFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.tableauViews[0], 1, 1);
		MouseEvent mer = this.createReleased(game, game.aceFoundationViews[0],
				1, 1);

		// Setup
		Card aceCard = new Card(1, 1);
		game.tableaus[0].get();
		game.tableaus[0].add(aceCard);

		// Handle mouse events
		game.tableauViews[0].getMouseManager().handleMouseEvent(mep);
		game.aceFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(aceCard, game.aceFoundations[0].peek());
	}

	/**
	 * Tests pressing on a tableau and releasing on a king foundation to move a
	 * card.
	 */
	public void testTableauToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.tableauViews[0], 1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[0],
				1, 1);

		// Setup
		Card kingCard = new Card(13, 1);
		game.tableaus[0].get();
		game.tableaus[0].add(kingCard);

		// Handle mouse events
		game.tableauViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(kingCard, game.kingFoundations[0].peek());
	}

	/**
	 * Tests pressing on the waste pile and releasing on a tableau to move a
	 * card.
	 */
	public void testWastePileToTableau() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.wastePileView, 1, 1);
		MouseEvent mer = this.createReleased(game, game.tableauViews[0], 1, 1);

		// Setup
		game.tableaus[0].get();
		Card topCard = game.wastePile.peek();

		// Handle mouse events
		game.wastePileView.getMouseManager().handleMouseEvent(mep);
		game.tableauViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(topCard, game.tableaus[0].peek());
	}

	/**
	 * Tests pressing on a reserve and releasing on a tableau to move a card.
	 */
	public void testReserveToTableau() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.reserveViews[0], 1, 360);
		MouseEvent mer = this.createReleased(game, game.tableauViews[0], 1, 1);

		// Setup
		game.tableaus[0].get();
		Card topCard = game.reserves[0].peek();

		// Handle mouse events
		game.reserveViews[0].getMouseManager().handleMouseEvent(mep);
		game.tableauViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(topCard, game.tableaus[0].peek());
	}

	/**
	 * Tests pressing on an empty pile and relasing on a tableau to move a card
	 * but failing due to empty pile and invalid move.
	 */
	public void testPressEmptyPileToTableau() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.tableauViews[0], 1, 1);
		MouseEvent mer = this.createReleased(game, game.aceFoundationViews[1],
				1, 1);

		// Setup
		game.tableaus[0].get();

		// Handle mouse events
		game.tableauViews[0].getMouseManager().handleMouseEvent(mep);
		game.tableauViews[1].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(game.aceFoundations[1].count(), 0);
	}
}
