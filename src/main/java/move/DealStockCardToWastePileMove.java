package move;

import ks.common.games.Solitaire;
import ks.common.model.Card;
import ks.common.model.Deck;
import ks.common.model.Move;
import ks.common.model.Pile;

/**
 * This class is the move for dealing a card from the stock to the waste pile.
 *
 * @author Aditya Nivarthi
 */
public class DealStockCardToWastePileMove extends Move {

    Deck deck;
    Pile wastePile;

    /**
     * Constructor for DealStockCardToWastePileMove class.
     * 
     * @param deck
     *            The deck to deal from.
     * @param waste
     *            The waste pile to deal to.
     */
    public DealStockCardToWastePileMove(Deck deck, Pile waste) {
        this.deck = deck;
        wastePile = waste;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#doMove(ks.common.games.Solitaire)
     * 
     * If valid, moves the card from the stock to the waste pile. Updates the
     * cards left count.
     */
    @Override
    public boolean doMove(Solitaire game) {
        if (!valid(game)) {
            return false;
        }

        Card card = deck.get();
        wastePile.add(card);
        game.updateNumberCardsLeft(-1);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.model.Move#undo(ks.common.games.Solitaire)
     * 
     * If valid, moves the card from the waste pile to the stock. Updates the
     * cards left count.
     */
    @Override
    public boolean undo(Solitaire game) {
        Card card = wastePile.get();
        deck.add(card);
        game.updateNumberCardsLeft(1);
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
        return !deck.empty();
    }
}
