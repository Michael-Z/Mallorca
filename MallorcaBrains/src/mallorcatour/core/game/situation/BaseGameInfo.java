/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.core.game.situation;

import java.util.List;

import mallorcatour.core.game.Card;
import mallorcatour.core.game.LimitType;
import mallorcatour.core.game.PlayerInfo;
import mallorcatour.core.game.PokerStreet;
import mallorcatour.core.game.interfaces.IGameInfo;

/**
 *
 * @author Andrew
 */
public class BaseGameInfo implements IGameInfo {

	public LimitType limitType;
	public int[] raisesOnStreet = new int[4];
    public List<PlayerInfo> players;
    public PokerStreet street;
    public String buttonName;
    public boolean canHeroRaise;
    public double pot;
    public double bigBlind;
    public double heroAmountToCall;
    public List<Card> board;
    public double bankrollAtRisk;

    public BaseGameInfo() {
        for (int i = 0; i < 4; i++) {
            raisesOnStreet[i] = 0;
        }
    }

    public double getBigBlindSize() {
        return bigBlind;
    }

    public PokerStreet getStage() {
        return street;
    }

    public boolean isPreFlop() {
        return street == PokerStreet.PREFLOP;
    }

    public boolean isPostFlop() {
        return street != PokerStreet.PREFLOP;
    }

    public boolean isFlop() {
        return street == PokerStreet.FLOP;
    }

    public boolean isTurn() {
        return street == PokerStreet.TURN;
    }

    public boolean isRiver() {
        return street == PokerStreet.RIVER;
    }

    public List<Card> getBoard() {
        return board;
    }

    public double getPotSize() {
        return pot;
    }

    public String getButtonName() {
        return buttonName;
    }

    public double getHeroAmountToCall() {
        return heroAmountToCall;
    }

    public double getBankRollAtRisk() {
        return bankrollAtRisk;
    }

    public boolean canHeroRaise() {
        if (limitType == LimitType.NO_LIMIT) {
            return getBankRollAtRisk() > 0;
        } else {
            return raisesOnStreet[street.intValue()] < 4;
        }
    }

    public double getBankRoll(String name) {
        for (PlayerInfo player : players) {
            if (player.getName().equals(name)) {
                return player.getStack();
            }
        }
        throw new IllegalArgumentException("There is no player with name " + name);
    }

    public int getNumRaises() {
        return raisesOnStreet[street.intValue()];
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }
}