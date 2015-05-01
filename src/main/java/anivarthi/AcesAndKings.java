package anivarthi;

import controllers.AceFoundationController;
import controllers.DeckController;
import controllers.KingFoundationController;
import controllers.ReserveController;
import controllers.TableauController;
import controllers.WastePileController;
import ks.common.controller.SolitaireMouseMotionAdapter;
import ks.common.games.Solitaire;
import ks.common.games.SolitaireUndoAdapter;
import ks.common.model.Column;
import ks.common.model.Deck;
import ks.common.model.MultiDeck;
import ks.common.model.MutableInteger;
import ks.common.model.Pile;
import ks.common.view.CardImages;
import ks.common.view.ColumnView;
import ks.common.view.DeckView;
import ks.common.view.IntegerView;
import ks.common.view.PileView;
import ks.launcher.Main;

/**
 * This class is the main game creation and setup for Aces and Kings. This class
 * initializes the controllers, entities, views and game setup.
 *
 * @author Aditya Nivarthi
 */
public class AcesAndKings extends Solitaire {

    // Entities
    MultiDeck deck;
    Pile wastePile;
    Pile aceFoundations[];
    Pile kingFoundations[];
    Pile tableaus[];
    Column reserves[];

    // Views
    DeckView deckView;
    PileView wastePileView;
    PileView aceFoundationViews[];
    PileView kingFoundationViews[];
    PileView tableauViews[];
    ColumnView reserveViews[];
    IntegerView scoreView;
    IntegerView cardsLeftView;

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.games.Solitaire#getName()
     */
    @Override
    public String getName() {
        return "anivarthi - Aces And Kings";
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.games.Solitaire#hasWon()
     */
    @Override
    public boolean hasWon() {
        boolean aceCheck = true;
        boolean kingCheck = true;
        boolean tableauCheck = true;
        boolean reserveCheck = true;

        // Ace and king foundations are completely filled, and tableaus are all
        // empty.
        for (int i = 0; i < 4; i++) {
            aceCheck = aceCheck && aceFoundations[i].count() == 13;
            kingCheck = kingCheck && kingFoundations[i].count() == 13;
            tableauCheck = tableauCheck && tableaus[i].empty();
        }

        // Reserves are all empty.
        for (int i = 0; i < 2; i++) {
            reserveCheck = reserveCheck && reserves[i].empty();
        }

        // Deck and waste are empty, as well as previous conditions are all
        // true.
        return deck.empty() && wastePile.empty() && aceCheck && kingCheck && tableauCheck && reserveCheck;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ks.common.games.Solitaire#initialize()
     */
    @Override
    public void initialize() {
        initializeModel(getSeed());
        initializeView();
        initializeControllers();

        score = new MutableInteger(0);
        deck.shuffle(seed);

        // Add cards to tableaus
        for (int i = 0; i < 4; i++) {
            tableaus[i].add(deck.get());
            updateNumberCardsLeft(-1);
        }

        // Add cards to reserves
        for (int i = 0; i < 2; i++) {
            for (int cards = 0; cards < 13; cards++) {
                reserves[i].add(deck.get());
                updateNumberCardsLeft(-1);
            }
        }

        // Add card to waste pile
        wastePile.add(deck.get());
        updateNumberCardsLeft(-1);
    }

    /**
     * Initialize the controllers for the aces and kings game.
     */
    private void initializeControllers() {
        // Deck
        deckView.setMouseAdapter(new DeckController(this, deck, wastePile));
        deckView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
        deckView.setUndoAdapter(new SolitaireUndoAdapter(this));

        // Waste pile
        wastePileView.setMouseAdapter(new WastePileController(this, wastePileView));
        wastePileView.setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
        wastePileView.setUndoAdapter(new SolitaireUndoAdapter(this));

        // Piles
        for (int i = 0; i < 4; i++) {
            // Ace foundations
            aceFoundationViews[i].setMouseAdapter(new AceFoundationController(this, aceFoundationViews[i], deckView));
            aceFoundationViews[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
            aceFoundationViews[i].setUndoAdapter(new SolitaireUndoAdapter(this));

            // King foundations
            kingFoundationViews[i]
                    .setMouseAdapter(new KingFoundationController(this, kingFoundationViews[i], deckView));
            kingFoundationViews[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
            kingFoundationViews[i].setUndoAdapter(new SolitaireUndoAdapter(this));

            // Tableaus
            tableauViews[i].setMouseAdapter(new TableauController(this, tableauViews[i]));
            tableauViews[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
            tableauViews[i].setUndoAdapter(new SolitaireUndoAdapter(this));
        }

        // Reserves
        for (int i = 0; i < 2; i++) {
            reserveViews[i].setMouseAdapter(new ReserveController(this, reserveViews[i]));
            reserveViews[i].setMouseMotionAdapter(new SolitaireMouseMotionAdapter(this));
            reserveViews[i].setUndoAdapter(new SolitaireUndoAdapter(this));
        }
    }

    /**
     * Initialize the views of all the entities for the aces and kings game.
     */
    private void initializeView() {
        CardImages ci = getCardImages();

        // Deck view
        deckView = new DeckView(deck);
        deckView.setBounds(395, 30, ci.getWidth(), ci.getHeight());
        container.addWidget(deckView);

        // Ace views initialization
        aceFoundationViews = new PileView[4];

        // King views initialization
        kingFoundationViews = new PileView[4];

        // Tableau views initialization
        tableauViews = new PileView[4];

        // Reserve views initialization
        reserveViews = new ColumnView[2];

        // Views
        for (int i = 0; i < 4; i++) {
            // Ace views
            aceFoundationViews[i] = new PileView(aceFoundations[i]);
            aceFoundationViews[i].setBounds(30 + (i * (10 + ci.getWidth())), 420, ci.getWidth(), ci.getHeight());
            container.addWidget(aceFoundationViews[i]);

            // King views
            kingFoundationViews[i] = new PileView(kingFoundations[i]);
            kingFoundationViews[i].setBounds(395 + (i * (10 + ci.getWidth())), 420, ci.getWidth(), ci.getHeight());
            container.addWidget(kingFoundationViews[i]);

            // Tableau views
            tableauViews[i] = new PileView(tableaus[i]);
            tableauViews[i].setBounds(395 + (i * (10 + ci.getWidth())), 292, ci.getWidth(), ci.getHeight());
            container.addWidget(tableauViews[i]);
        }

        // Reserve views
        for (int i = 0; i < 2; i++) {
            reserveViews[i] = new ColumnView(reserves[i]);
            reserveViews[i].setBounds(30 + (i * (10 + ci.getWidth())), 30, ci.getWidth(), ci.getHeight() * 4);
            container.addWidget(reserveViews[i]);
        }

        // Waste view
        wastePileView = new PileView(wastePile);
        wastePileView.setBounds(395 + 10 + ci.getWidth(), 30, ci.getWidth(), ci.getHeight());
        container.addWidget(wastePileView);

        // Score view
        scoreView = new IntegerView(getScore());
        scoreView.setFontSize(20);
        scoreView.setBounds(600, 30, 100, 50);
        container.addWidget(scoreView);

        // Cards left view
        cardsLeftView = new IntegerView(getNumLeft());
        cardsLeftView.setFontSize(20);
        cardsLeftView.setBounds(600, 80, 100, 50);
        container.addWidget(cardsLeftView);
    }

    /**
     * Initializes the entities of the aces and kings game.
     * 
     * @param seed
     *            The seed to create the deck.
     */
    private void initializeModel(int seed) {
        // Deck
        deck = new MultiDeck(2);
        deck.create(seed);
        model.addElement(deck);

        // Ace foundations
        aceFoundations = new Pile[4];

        // King foundations
        kingFoundations = new Pile[4];

        // Tableaus
        tableaus = new Pile[4];

        // Reserves
        reserves = new Column[2];

        // Entities
        for (int i = 0; i < 4; i++) {
            // Ace foundations
            aceFoundations[i] = new Pile("ace" + i);

            // King foundations
            kingFoundations[i] = new Pile("king" + i);

            // Tableaus
            tableaus[i] = new Pile("tableau" + i);

            // Add all to model
            model.addElement(aceFoundations[i]);
            model.addElement(kingFoundations[i]);
            model.addElement(tableaus[i]);
        }

        // Add reserves to model
        for (int i = 0; i < 2; i++) {
            reserves[i] = new Column("reserve" + i);
            model.addElement(reserves[i]);
        }

        // Add waste to model
        wastePile = new Pile("waste");
        model.addElement(wastePile);

        updateScore(0);
        updateNumberCardsLeft(104);
    }

    /**
     * Main to run Solitaire variation.
     * 
     * @param args
     *            Runtime arguments.
     */
    public static void main(String[] args) {
        Main.generateWindow(new AcesAndKings(), Deck.OrderBySuit);
    }
}
