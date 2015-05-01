package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * This class is the move for moving from any given pile to any given pile.
 *
 * @author Aditya Nivarthi
 */
public abstract class PileToPileMove extends Move {
    Pile sourcePile;
    Card draggedCard;
    Pile targetPile;

    final String ACE_STR = "ace";
    final String KING_STR = "king";
    final String TABLEAU_STR = "tableau";

    /**
     * Constructor for PileToPileMove class.
     * 
     * @param from
     *            The pile to move from.
     * @param cardDragged
     *            The card being interacted with.
     * @param to
     *            The pile to move to.
     */
    public PileToPileMove(Pile from, Card cardDragged, Pile to) {
        sourcePile = from;
        draggedCard = cardDragged;
        targetPile = to;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#doMove(ks.common.games.Solitaire)
     * 
     * If valid, moves the dragged card to the target pile. Updates the score
     * depending on the source location.
     */
    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }
        targetPile.add(draggedCard);
        if (!sourcePile.getName().contains(ACE_STR) && !sourcePile.getName().contains(KING_STR)
                && !targetPile.getName().contains(TABLEAU_STR)) {
            game.updateScore(1);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#undo(ks.common.games.Solitaire)
     * 
     * If valid, moves the card from the target pile to the source pile. Updates
     * the score if necessary.
     */
    @Override
    public boolean undo(Solitaire game) {
        sourcePile.add(targetPile.get());
        if (!sourcePile.getName().contains(ACE_STR) && !sourcePile.getName().contains(KING_STR)
                && !targetPile.getName().contains(TABLEAU_STR)) {
            game.updateScore(-1);
        }
        return true;
    }
}
