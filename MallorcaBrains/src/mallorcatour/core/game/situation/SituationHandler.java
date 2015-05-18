/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.core.game.situation;

import mallorcatour.core.equilator.StreetEquity;
import mallorcatour.core.equilator.brecher.EquilatorPreflop;
import mallorcatour.core.equilator.brecher.PokerEquilatorBrecher;
import mallorcatour.core.game.Action;
import mallorcatour.core.game.Card;
import mallorcatour.core.game.LimitType;
import mallorcatour.core.game.PokerStreet;
import mallorcatour.core.game.interfaces.IGameInfo;

/**
 *
 * @author Andrew
 */
public class SituationHandler implements ISituationHandler {

    protected IGameInfo gameInfo;  // general game information
    protected Card holeCard1, holeCard2, flop1, flop2, flop3, turn, river;
    protected int heroActionCount, countOfHeroAggressive, villainActionCount,
            countOfOppAggressive;
    protected boolean wasHeroPreviousAggressive, wasVillainPreviousAggressive;
    protected String heroName, villainName;
    private double strength, positivePotential, negativePotential;
    protected LimitType limitType;

    public SituationHandler(LimitType limitType) {
        this.limitType = limitType;
    }

    /**
     * An event called to tell us our hole cards and seat number
     * @param c1 your first hole card
     * @param c2 your second hole card
     * @param seat your seat number at the table
     */
    public void onHoleCards(Card c1, Card c2, String heroName, String villainName) {
        this.holeCard1 = c1;
        this.holeCard2 = c2;
		StreetEquity equity = EquilatorPreflop.equityVsRandom(holeCard1,
				holeCard2);
		strength = equity.strength;
		positivePotential = equity.positivePotential;
		negativePotential = equity.negativePotential;
        this.heroName = heroName;
        this.villainName = villainName;
    }

    private LocalSituation getHeroSituation() {
        LocalSituation result = null;
        boolean isOnButton;
        if (heroName.equals(gameInfo.getButtonName())) {
            isOnButton = true;
        } else {
            isOnButton = false;
        }
        double toCall = gameInfo.getHeroAmountToCall();
        double pot = gameInfo.getPotSize();
        double effectiveStack = gameInfo.getBankRollAtRisk();
        double potOdds = toCall / (toCall + pot);
        double stackProportion = gameInfo.getBankRoll(heroName)
                / (gameInfo.getBankRoll(heroName) + gameInfo.getBankRoll(villainName));

        if (gameInfo.isPreFlop()) {
            result = new LocalSituation(LocalSituation.PREFLOP, limitType);
        } else if (gameInfo.isFlop()) {
            result = new LocalSituation(LocalSituation.FLOP, limitType);
        } else if (gameInfo.isTurn()) {
            result = new LocalSituation(LocalSituation.TURN, limitType);
        } else if (gameInfo.isRiver()) {
            result = new LocalSituation(LocalSituation.RIVER, limitType);
        }
        result.setHeroAggresionActionCount(countOfHeroAggressive);
        result.setHeroActionCount(heroActionCount);
        result.setVillainAggresionActionCount(countOfOppAggressive);
        result.setVillainActionCount(villainActionCount);
        result.wasHeroPreviousAggresive(wasHeroPreviousAggressive);
        result.wasOpponentPreviousAggresive(wasVillainPreviousAggressive);
        result.setStrength(strength);
        result.setPositivePotential(positivePotential);
        result.setNegativePotential(negativePotential);
        result.setStackProportion(stackProportion);
        result.setPotOdds(potOdds);
        result.isOnButton(isOnButton);
        result.setPotToStackOdds((pot + toCall) / (pot + toCall + effectiveStack));
        result.setFLPotSize(1 - (2 * gameInfo.getBigBlindSize()) / pot);
        result.canRaise(gameInfo.canHeroRaise());
        return result;
    }

    public LocalSituation onHeroSituation() {
        LocalSituation result = getHeroSituation();
        return result;
    }

    public void onHeroActed(Action action) {
        if (action.isAggressive()) {
            wasHeroPreviousAggressive = true;
            countOfHeroAggressive++;
        } else if (action.isPassive()) {
            wasHeroPreviousAggressive = false;
        }
        heroActionCount++;
    }

    /**
     * A new betting round has started.
     */
    public void onStageEvent(PokerStreet street) {
        if (street == PokerStreet.FLOP) {
            flop1 = gameInfo.getBoard().get(0);
            flop2 = gameInfo.getBoard().get(1);
            flop3 = gameInfo.getBoard().get(2);
            StreetEquity flopEquity = PokerEquilatorBrecher.equityOnFlop(holeCard1,
                    holeCard2, flop1, flop2, flop3);
            strength = flopEquity.strength;
            positivePotential = flopEquity.positivePotential;
            negativePotential = flopEquity.negativePotential;
        } else if (street == PokerStreet.TURN) {
            turn = gameInfo.getBoard().get(3);
            StreetEquity turnEquity = PokerEquilatorBrecher.equityOnTurn(holeCard1,
                    holeCard2, flop1, flop2, flop3, turn);
            strength = turnEquity.strength;
            positivePotential = turnEquity.positivePotential;
            negativePotential = turnEquity.negativePotential;
        } else if (street == PokerStreet.RIVER) {
            river = gameInfo.getBoard().get(4);
            strength = PokerEquilatorBrecher.strengthOnRiver(holeCard1, holeCard2,
                    flop1, flop2, flop3, turn, river);
            positivePotential = 0;
            negativePotential = 0;
        }
    }

    /**
     * A new game has been started.
     * @param gi the game stat information
     */
    public void onHandStarted(IGameInfo gameInfo) {
        this.gameInfo = gameInfo;
        heroActionCount = 0;
        countOfHeroAggressive = 0;
        villainActionCount = 0;
        countOfOppAggressive = 0;
        wasHeroPreviousAggressive = false;
        wasVillainPreviousAggressive = false;
    }

    /**
     * An villain action has been observed.
     */
    public void onVillainActed(Action action, double toCall) {
        if (action.isAggressive()) {
            wasVillainPreviousAggressive = true;
            villainActionCount++;
            countOfOppAggressive++;
        } else if (action.isPassive()) {
            wasVillainPreviousAggressive = false;
            villainActionCount++;
        }
    }

    public void beforeHeroAction() {
        //do nothing
    }

    public void onHandEnded() {
        //do nothing
    }
}