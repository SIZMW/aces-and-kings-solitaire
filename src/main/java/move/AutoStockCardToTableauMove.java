package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * This class is the move for automatically dealing a card from the stock to the
 * empty tableau.
 *
 * @author Aditya Nivarthi
 */
public class AutoStockCardToTableauMove extends Move {

    Deck deck;
    Pile tableau;

    /**
     * Constructor for DealStockCardToWastePileMove class.
     * 
     * @param deck
     *            The deck to deal from.
     * @param waste
     *            The waste pile to deal to.
     */
    public AutoStockCardToTableauMove(Deck deck, Pile tableau) {
        this.deck = deck;
        this.tableau = tableau;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#doMove(ks.common.games.Solitaire)
     * 
     * If valid, deals a card from the stock to the empty tableau.
     */
    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }

        Card card = deck.get();
        tableau.add(card);
        game.updateNumberCardsLeft(-1);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#undo(ks.common.games.Solitaire)
     * 
     * If valid, removes the dealt card from the tableau and returns to the
     * stock.
     */
    @Override
    public boolean undo(Solitaire game) {
        Card card = tableau.get();
        deck.add(card);
        game.updateNumberCardsLeft(1);
        return true;
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
        return !deck.empty() && tableau.empty();
    }
}
