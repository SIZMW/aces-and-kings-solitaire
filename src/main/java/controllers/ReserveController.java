package controllers;

import java.awt.event.MouseEvent;

import anivarthi.AcesAndKings;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Column;
import ks.common.view.CardView;
import ks.common.view.ColumnView;
import ks.common.view.Container;
import ks.common.view.Widget;

/**
 * This class is the controller for handling moves from any given reserve.
 *
 * @author Aditya Nivarthi
 */
public class ReserveController extends SolitaireReleasedAdapter {

    ColumnView src;

    /**
     * Constructor for ReserveController class.
     * 
     * @param theGame
     *            The AcesAndKings game.
     * @param src
     *            The reserve to be interacted with.
     */
    public ReserveController(AcesAndKings theGame, ColumnView src) {
        super(theGame);
        this.src = src;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     * 
     * Handles mouse pressed events on reserve views. Removes the card from the
     * view and enables the card for dragging.
     */
    @Override
    public void mousePressed(MouseEvent me) {
        Container c = theGame.getContainer();

        // Get the source of the card to drag
        Column column = (Column) src.getModelElement();
        if (column.count() == 0) {
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
                    "ReserveController::mousePressed(): Unexpectedly encountered a dragging object during a mouse press.");
            return;
        }

        c.setActiveDraggingObject(cardView, me);
        c.setDragSource(src);
        src.redraw();
    }
}
