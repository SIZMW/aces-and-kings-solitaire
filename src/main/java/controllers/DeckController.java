package controllers;

import anivarthi.AcesAndKings;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;
import move.DealStockCardToWastePileMove;

/**
 * This class is the controller for handling moves from the deck.
 *
 * @author Aditya Nivarthi
 */
public class DeckController extends SolitaireReleasedAdapter {
    protected Pile wastePile;
    protected Deck deck;

    /**
     * Constructor for DeckController class.
     * 
     * @param theGame
     *            The AcesAndKings game.
     * @param d
     *            The deck.
     * @param wastePile
     *            The waste pile.
     */
    public DeckController(AcesAndKings theGame, Deck d, Pile wastePile) {
        super(theGame);
        this.wastePile = wastePile;
        deck = d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     * 
     * Handles mouse pressed events on the deck. Creates a move to deal the card
     * when pressed.
     */
    @Override
    public void mousePressed(java.awt.event.MouseEvent me) {
        Move m = new DealStockCardToWastePileMove(deck, wastePile);
        if (m.doMove(theGame)) {
            theGame.pushMove(m);
            theGame.refreshWidgets();
        }
    }

}
