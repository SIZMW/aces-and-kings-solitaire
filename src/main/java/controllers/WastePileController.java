package controllers;

import java.awt.event.MouseEvent;

import anivarthi.AcesAndKings;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;

/**
 * This class is the controller for handling moves from the waste pile.
 *
 * @author Aditya Nivarthi
 */
public class WastePileController extends SolitaireReleasedAdapter {

    PileView src;

    /**
     * Constructor for the WastePileController class.
     * 
     * @param theGame
     *            The AcesAndKings game.
     * @param src
     *            The waste pile to be interacted with.
     */
    public WastePileController(AcesAndKings theGame, PileView src) {
        super(theGame);
        this.src = src;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     * 
     * Handles mouse pressed events on the waste view. Removes the card from the
     * view and enables the card for dragging.
     */
    @Override
    public void mousePressed(MouseEvent me) {
        Container c = theGame.getContainer();

        // Get the source of the card to drag
        Pile pile = (Pile) src.getModelElement();
        if (pile.count() == 0) {
            c.releaseDraggingObject();
            return;
        }

        // Get the card to drag
        CardView cardView = src.getCardViewForTopCard(me);

        if (cardView == null) {
            c.releaseDraggingObject();
            return;
        }

        // Set the card for dragging
        Widget w = c.getActiveDraggingObject();
        if (w != Container.getNothingBeingDragged()) {
            System.err.println(
                    "WastePileController::mousePressed(): Unexpectedly encountered a dragging object during a mouse press.");
            return;
        }

        c.setActiveDraggingObject(cardView, me);
        c.setDragSource(src);
        src.redraw();
    }
}
