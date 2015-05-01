package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.MultiDeck;
import ks.common.model.Pile;

/**
 * This class is the move for moving from any given pile to any given ace
 * foundation.
 *
 * @author Aditya Nivarthi
 */
public class PileToAceFoundationMove extends PileToPileMove {

    MultiDeck deck;
    final String TABLEAU_STR = "tableau";

    /**
     * Constructor for PileToAceFoundationMove class.
     * 
     * @param from
     *            The pile to move from.
     * @param cardDragged
     *            The card being interacted with.
     * @param to
     *            The ace foundation to move to.
     */
    public PileToAceFoundationMove(Pile from, Card cardDragged, Pile to, MultiDeck deck) {
        super(from, cardDragged, to);
        this.deck = deck;
    }

    /*
     * (non-Javadoc)
     * 
     * @see move.PileToPileMove#doMove(ks.common.games.Solitaire)
     * 
     * If valid, moves the card to the target foundation. Automatically fills
     * the tableau if the source was a tableau.
     */
    @Override
    public boolean doMove(Solitaire game) {
        boolean superMove = super.doMove(game);

        if (!superMove) {
            return false;
        } else if (sourcePile.getName().contains(TABLEAU_STR)) {
            AutoStockCardToTableauMove move = new AutoStockCardToTableauMove(deck, sourcePile);
            if (move.valid(game)) {
                move.doMove(game);
            }
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see move.PileToPileMove#undo(ks.common.games.Solitaire)
     * 
     * If valid, moves the card that filled the tableau if the source was a
     * tableau. Moves the card from the foundation to the tableau.
     */
    @Override
    public boolean undo(Solitaire game) {
        if (sourcePile.getName().contains(TABLEAU_STR)) {
            AutoStockCardToTableauMove move = new AutoStockCardToTableauMove(deck, sourcePile);
            if (!sourcePile.empty()) {
                move.undo(game);
            }
        }
        super.undo(game);
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
        if (draggedCard.isAce() && targetPile.empty()) {
            return true;
        } else if (!targetPile.empty() && draggedCard.getRank() == targetPile.peek().getRank() + 1) {
            return true;
        } else if (sourcePile.empty()) {
            return false;
        }
        return false;
    }
}
