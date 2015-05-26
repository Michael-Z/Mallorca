/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.core.game;

import java.util.List;

import mallorcatour.core.game.interfaces.IPlayerGameInfo;

/**
 *
 * @author Andrew
 */
public class BaseGameInfo implements IPlayerGameInfo {

	public LimitType limitType;
	public int[] raisesOnStreet = new int[4];
    public PlayerInfo heroInfo, villainInfo;
    public PokerStreet street;
    public boolean canHeroRaise;
    public double pot;
    public double bigBlind;
    public double heroAmountToCall;
    public List<Card> board;
    public double bankrollAtRisk;
	public boolean onButton;

    public BaseGameInfo() {
        for (int i = 0; i < 4; i++) {
            raisesOnStreet[i] = 0;
        }
    }

    public double getBigBlindSize() {
        return bigBlind;
    }

    @Override
    public PokerStreet getStage() {
        return street;
    }

    @Override
    public boolean isPreFlop() {
        return street == PokerStreet.PREFLOP;
    }

    @Override
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

    public double getHeroAmountToCall() {
        return heroAmountToCall;
    }

    @Override
    public double getBankRollAtRisk() {
        return bankrollAtRisk;
    }

    @Override
    public boolean canHeroRaise() {
        if (limitType == LimitType.NO_LIMIT) {
            return getBankRollAtRisk() > 0;
        } else {
            return raisesOnStreet[street.intValue()] < 4;
        }
    }

    @Override
    public int getNumRaises() {
        return raisesOnStreet[street.intValue()];
    }

    @Override
    public LimitType getLimitType() {
        return limitType;
    }

	@Override
	public PlayerInfo getVillain() {
		return villainInfo;
	}

	@Override
	public PlayerInfo getHero() {
		return heroInfo;
	}

	@Override
	public Flop getFlop() {
		return new Flop(board.get(0), board.get(1), board.get(2));
	}

	@Override
	public Card getTurn() {
		return board.get(3);
	}

	@Override
	public Card getRiver() {
		return board.get(4);
	}

	@Override
	public boolean onButton() {
		return onButton;
	}

	@Override
	public boolean isVillainSitOut() {
		return false;
	}
}
