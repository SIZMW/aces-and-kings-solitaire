package anivarthi;

import java.awt.event.MouseEvent;

import ks.client.gamefactory.GameWindow;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.launcher.Main;
import ks.tests.KSTestCase;

/**
 * This test case handles all coverage for mouse events on king foundations.
 * 
 * @author Aditya Nivarthi
 */
public class TestKingFoundationController extends KSTestCase {

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
	 * Tests pressing on a king foundation and releasing on a king foundation to
	 * move a card.
	 */
	public void testKingFoundationToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.kingFoundationViews[0],
				1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[1],
				1, 1);

		// Setup
		Card kingCard = new Card(13, 1);
		game.kingFoundations[0].add(kingCard);

		// Handle mouse events
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[1].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(kingCard, game.kingFoundations[1].peek());
	}

	/**
	 * Tests pressing on an ace foundation and releasing on an ace foundation to
	 * move a card but failing due to rank.
	 */
	public void testInvalidKingFoundationToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.kingFoundationViews[0],
				1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[1],
				1, 1);

		// Setup foundation
		Card kingCard = new Card(2, 1);
		game.kingFoundations[0].add(kingCard);

		// Handle mouse events
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[1].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(game.kingFoundations[1].count(), 0);
	}

	/**
	 * Tests pressing on a king foundation and releasing on an ace foundation to
	 * move a card.
	 */
	public void testAceFoundationToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.aceFoundationViews[0],
				1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[0],
				1, 1);

		// Setup foundation
		Card kingCard = new Card(13, 1);
		game.aceFoundations[0].add(kingCard);

		// Handle mouse events
		game.aceFoundationViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(kingCard, game.kingFoundations[0].peek());
	}

	/**
	 * Tests pressing on the waste pile and releasing on an ace foundation to
	 * move a card.
	 */
	public void testWastePileToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.wastePileView, 1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[0],
				1, 1);

		// Setup foundation
		Card kingCard = new Card(13, 1);
		game.wastePile.add(kingCard);

		// Handle mouse events
		game.wastePileView.getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(kingCard, game.kingFoundations[0].peek());
	}

	/**
	 * Tests pressing on a reserve and releasing on an ace foundation to move a
	 * card.
	 */
	public void testReserveToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.reserveViews[0], 1, 360);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[0],
				1, 1);

		// Setup foundation
		Card kingCard = new Card(13, 1);
		game.reserves[0].get();
		game.reserves[0].add(kingCard);

		// Handle mouse events
		game.reserveViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(kingCard, game.kingFoundations[0].peek());
	}

	/**
	 * Tests pressing on a reserve and releasing on an ace foundation to move a
	 * card but failing due to rank.
	 */
	public void testInvalidReserveToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.reserveViews[0], 1, 360);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[0],
				1, 1);

		// Setup
		Card kingCard = new Card(2, 1);
		game.reserves[0].get();
		game.reserves[0].add(kingCard);

		// Handle mouse events
		game.reserveViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(game.kingFoundations[0].count(), 0);
	}

	/**
	 * Tests pressing on a view and not retrieving a card and releasing on an
	 * ace foundation to move a card but failing due to incorrect press
	 * location.
	 */
	public void testPressNothingToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.reserveViews[0], 1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[0],
				1, 1);

		// Handle mouse events
		game.reserveViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(game.kingFoundations[0].count(), 0);
	}

	/**
	 * Tests pressing on an empty view and not retrieving a card and releasing
	 * on an ace foundation to move a card but failing due to no card selected.
	 */
	public void testPressEmptyPileToKingFoundation() {
		// Create mouse events
		MouseEvent mep = this.createPressed(game, game.kingFoundationViews[0],
				1, 1);
		MouseEvent mer = this.createReleased(game, game.kingFoundationViews[1],
				1, 1);

		// Handle mouse events
		game.kingFoundationViews[0].getMouseManager().handleMouseEvent(mep);
		game.kingFoundationViews[1].getMouseManager().handleMouseEvent(mer);

		// Verify result
		assertEquals(game.kingFoundations[1].count(), 0);
	}
}
