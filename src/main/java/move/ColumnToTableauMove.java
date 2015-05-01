package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Pile;

/**
 * This class is the move for moving from a reserve to any given tableau.
 *
 * @author Aditya Nivarthi
 */
public class ColumnToTableauMove extends ColumnToPileMove {

    /**
     * Constructor for ColumnToTableauMove class.
     * 
     * @param from
     *            The reserve to move from.
     * @param cardDragged
     *            The card being interacted with.
     * @param to
     *            The tableau to move to.
     */
    public ColumnToTableauMove(Column from, Card cardDragged, Pile to) {
        super(from, cardDragged, to);
    }

    /*
     * (non-Javadoc)
     * 
     * @see move.ColumnToPileMove#doMove(ks.common.games.Solitaire)
     * 
     * If valid, moves the card to the target tableau. Updates the score
     * depending on the source location.
     */
    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }
        targetPile.add(draggedCard);
        if (!sourceColumn.getName().contains(ACE_STR) && !sourceColumn.getName().contains(KING_STR)
                && !sourceColumn.getName().contains(RESERVE_STR)) {
            game.updateScore(1);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see move.ColumnToPileMove#undo(ks.common.games.Solitaire)
     * 
     * If valid, moves the card from the target pile to the source column.
     * Updates the score as necessary.
     */
    @Override
    public boolean undo(Solitaire game) {
        sourceColumn.add(targetPile.get());
        if (!sourceColumn.getName().contains(ACE_STR) && !sourceColumn.getName().contains(KING_STR)
                && !sourceColumn.getName().contains(RESERVE_STR)) {
            game.updateScore(-1);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#valid(ks.common.games.Solitaire)
     * 
     * Determines if the move is valid.
     */
    @Override
    public boolean valid(Solitaire game) {
        if (targetPile.empty() && !sourceColumn.getName().contains(ACE_STR)
                && !sourceColumn.getName().contains(KING_STR)) {
            return true;
        }
        return false;
    }
}
