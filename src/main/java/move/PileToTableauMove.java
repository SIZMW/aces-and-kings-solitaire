package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Pile;

/**
 * This class is the move for moving from any given pile to any given tableau
 * pile.
 *
 * @author Aditya Nivarthi
 */
public class PileToTableauMove extends PileToPileMove {

    /**
     * Constructor for PileToTableauMove class.
     * 
     * @param from
     *            The pile to move from.
     * @param cardDragged
     *            The card being interacted with.
     * @param to
     *            The tableau to move to.
     */
    public PileToTableauMove(Pile from, Card cardDragged, Pile to) {
        super(from, cardDragged, to);
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
        if (targetPile.empty() && !sourcePile.getName().contains(ACE_STR) && !sourcePile.getName().contains(KING_STR)) {
            return true;
        }
        return false;
    }
}
