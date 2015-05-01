package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * This class is the move for moving from a column to any given pile.
 *
 * @author Aditya Nivarthi
 */
public abstract class ColumnToPileMove extends Move {
    Column sourceColumn;
    Card draggedCard;
    Pile targetPile;

    final String ACE_STR = "ace";
    final String KING_STR = "king";
    final String RESERVE_STR = "reserve";

    /**
     * Constructor for ColumnToPileMove class.
     * 
     * @param from
     *            The column to move from.
     * @param cardDragged
     *            The card being interacted with.
     * @param to
     *            The pile to move to.
     */
    public ColumnToPileMove(Column from, Card cardDragged, Pile to) {
        sourceColumn = from;
        draggedCard = cardDragged;
        targetPile = to;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#doMove(ks.common.games.Solitaire)
     * 
     * If valid, adds the dragged card to the target pile, and updates the score
     * depending on the source location.
     */
    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }
        targetPile.add(draggedCard);
        if (!sourceColumn.getName().contains(ACE_STR) && !sourceColumn.getName().contains(KING_STR)) {
            game.updateScore(1);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#undo(ks.common.games.Solitaire)
     * 
     * If valid, removes the card from the target pile and adds it back to the
     * source. Updates the score if necessary.
     */
    @Override
    public boolean undo(Solitaire game) {
        sourceColumn.add(targetPile.get());
        if (!sourceColumn.getName().contains(ACE_STR) && !sourceColumn.getName().contains(KING_STR)) {
            game.updateScore(-1);
        }
        return true;
    }
}
