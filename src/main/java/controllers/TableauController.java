package controllers;

import java.awt.event.MouseEvent;

import anivarthi.AcesAndKings;
import ks.common.controller.SolitaireReleasedAdapter;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;
import ks.common.view.CardView;
import ks.common.view.Container;
import ks.common.view.PileView;
import ks.common.view.Widget;
import move.ColumnToTableauMove;
import move.PileToTableauMove;

/**
 * This class is the controller for handling moves from and to any given
 * tableau.
 *
 * @author Aditya Nivarthi
 */
public class TableauController extends SolitaireReleasedAdapter {

    final String RESERVE_STR = "reserve";

    PileView src;

    /**
     * Constructor for the TableauController class.
     * 
     * @param theGame
     * @param tableau
     */
    public TableauController(AcesAndKings theGame, PileView tableau) {
        super(theGame);
        src = tableau;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
     * 
     * Handles mouse pressed events on the tableau views. Removes the card from
     * the view and enables the card for dragging.
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
                    "TableauController::mousePressed(): Unexpectedly encountered a dragging object during a mouse press.");
            return;
        }

        c.setActiveDraggingObject(cardView, me);
        c.setDragSource(src);
        src.redraw();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
     * 
     * Handles mouse released events on the tableau views. Determines the move
     * to be made based on the source of the card and the tableau view released
     * on.
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        Container c = theGame.getContainer();

        // Get the currently dragged card
        Widget draggingWidget = c.getActiveDraggingObject();
        if (draggingWidget == Container.getNothingBeingDragged()) {
            System.err.println("TableauController::mouseReleased() unexpectedly found nothing being dragged.");
            c.releaseDraggingObject();
            return;
        }

        // Get the source location of dragging card
        Widget fromWidget = c.getDragSource();
        if (fromWidget == null) {
            System.err.println("TableauController::mouseReleased(): somehow no drag source in container.");
            c.releaseDraggingObject();
            return;
        }

        // Handle source being a reserve.
        if (fromWidget.getModelElement().getName().contains(RESERVE_STR)) {
            Pile targetPile = (Pile) src.getModelElement();
            Column sourceColumn = (Column) fromWidget.getModelElement();

            CardView cardView = (CardView) draggingWidget;
            Card draggedCard = (Card) cardView.getModelElement();
            Move move = new ColumnToTableauMove(sourceColumn, draggedCard, targetPile);

            if (move.doMove(theGame)) {
                theGame.pushMove(move);
                theGame.refreshWidgets();
            } else {
                fromWidget.returnWidget(draggingWidget);
            }
        } else {
            // Handle source being a pile.
            Pile targetPile = (Pile) src.getModelElement();
            Pile sourcePile = (Pile) fromWidget.getModelElement();

            CardView cardView = (CardView) draggingWidget;
            Card draggedCard = (Card) cardView.getModelElement();
            Move move = new PileToTableauMove(sourcePile, draggedCard, targetPile);

            if (move.doMove(theGame)) {
                theGame.pushMove(move);
                theGame.refreshWidgets();
            } else {
                fromWidget.returnWidget(draggingWidget);
            }
        }

        c.releaseDraggingObject();
        c.repaint();
    }
}
