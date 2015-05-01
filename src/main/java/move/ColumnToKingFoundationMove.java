package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Column;
import ks.common.model.Pile;

/**
 * This class is the move for moving from a reserve to any given king
 * foundation.
 *
 * @author Aditya Nivarthi
 */
public class ColumnToKingFoundationMove extends ColumnToPileMove {

    /**
     * @brief Constructor for ColumnToKingFoundationMove
     * @param from
     *            The reserve to move from.
     * @param cardDragged
     *            The card being interacted with.
     * @param to
     *            The pile to move to.
     */
    public ColumnToKingFoundationMove(Column from, Card cardDragged, Pile to) {
        super(from, cardDragged, to);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#valid(ks.common.games.Solitaire)
     * 
     * Determines if this move is valid.
     */
    @Override
    public boolean valid(Solitaire game) {
        if (draggedCard.getRank() == Card.KING && targetPile.empty()) {
            return true;
        } else if (!targetPile.empty() && draggedCard.getRank() == targetPile.peek().getRank() - 1) {
            return true;
        } else if (sourceColumn.empty()) {
            return false;
        }
        return false;
    }
}
