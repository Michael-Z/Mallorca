package mallorcatour.core.game.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mallorcatour.core.game.PokerStreet;
import mallorcatour.core.game.interfaces.IAggressionInfo;
import mallorcatour.core.vector.IVector;
import mallorcatour.core.vector.VectorUtils;

/**
 * Определяет текущее состояние раздачи.
 */
public class HandState implements IVector, Serializable, IAggressionInfo {

    public static final double DEFAULT_POTENTIAL = 0.1;
    private final static long serialVersionUID = -762999739785198026L;
    private final int street;
    private double strength = -1;
    private boolean isOnButton;
    private boolean canRaise;
    private double toCall;
    private double heroAggresionFrequency = -1;
    private double opponentAggresionFrequency = -1;
    private int heroAggresionActionCount = 0;
	private int villainAggresionActionCount = 0;
	private int heroActionCount = 0;
	private int villainActionCount = 0;
    private boolean wasHeroPreviousAggresive;
    private boolean wasOpponentPreviousAggresive;
    private double stackProportion = -1;
    /**
     * only for No Limit
     */
    private double potToStackOdds = -1;
    /**
     * only for Fixed Limit holdem on postflop
     */
    private double FLPotSize;
    private double potOdds = -1;
    private double positivePotential = DEFAULT_POTENTIAL;
    private double negativePotential = DEFAULT_POTENTIAL;

    @Override
	public int getHeroAggresionActionCount() {
    	return heroAggresionActionCount;
    }
    
    public void setHeroAggresionActionCount(int heroAggresionActionCount) {
    	this.heroAggresionActionCount = heroAggresionActionCount;
    }
    
    @Override
	public int getVillainAggresionActionCount() {
    	return villainAggresionActionCount;
    }
    
    public void setVillainAggresionActionCount(int opponentAggresionActionCount) {
    	this.villainAggresionActionCount = opponentAggresionActionCount;
    }

    public int getStreet() {
        return street;
    }

    public HandState(int street) {
        this.street = street;
    }

    /**
     * Constructor for creating HandState has been read from file.
     * @param vector
     * @param street
     */
    public HandState(IVector vector, int street) {
        this.street = street;
        int i = 0;
        List<Number> values = vector.getValues();
        strength = values.get(i++).doubleValue();
        isOnButton = values.get(i++).doubleValue() == 1;
        heroAggresionActionCount = values.get(i++).intValue();
        heroActionCount = values.get(i++).intValue();
        villainAggresionActionCount = values.get(i++).intValue();
        villainActionCount = values.get(i++).intValue();
        wasHeroPreviousAggresive = values.get(i++).doubleValue() == 1;
        wasOpponentPreviousAggresive = values.get(i++).doubleValue() == 1;
        potToStackOdds = values.get(i++).doubleValue();
		potOdds = values.get(i++).doubleValue();
		positivePotential = values.get(i++).doubleValue();
		negativePotential = values.get(i++).doubleValue();
    }

    public HandState(HandState other) {
        this.street = other.street;
        this.strength = other.strength;
        this.isOnButton = other.isOnButton;
        this.wasHeroPreviousAggresive = other.wasHeroPreviousAggresive;
        this.heroAggresionFrequency = other.heroAggresionFrequency;
        this.wasOpponentPreviousAggresive = other.wasOpponentPreviousAggresive;
        this.opponentAggresionFrequency = other.opponentAggresionFrequency;
        this.villainAggresionActionCount = other.villainAggresionActionCount;
        this.heroAggresionActionCount = other.heroAggresionActionCount;
        this.potOdds = other.potOdds;
        this.potToStackOdds = other.potToStackOdds;
        this.FLPotSize = other.FLPotSize;
        this.stackProportion = other.stackProportion;
        this.positivePotential = other.positivePotential;
        this.negativePotential = other.negativePotential;
    }

    /**
     * Method for write HandState as list of numbers.
     * Used for write it to file.
     */
    public List<Number> getValues() {
        List<Number> result = new ArrayList<Number>();
        result.add(getStrength());
        result.add(isHeroOnButton() ? 1 : 0);
        result.add(getHeroAggresionActionCount());
        result.add(getHeroActionCount());
        result.add(getVillainAggresionActionCount());
        result.add(getVillainActionCount());
        result.add(wasHeroPreviousAggresive() ? 1 : 0);
        result.add(wasVillainPreviousAggresive() ? 1 : 0);
        result.add(getPotToStackOdds());
		result.add(getPotOdds());
		result.add(getPositivePotential());
		result.add(getNegativePotential());
		return result;
    }

    @Override
    public String toString() {
        return VectorUtils.toString(this);
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public void setFLPotSize(double FLPotSize) {
        this.FLPotSize = FLPotSize;
    }

    public double getFLPotSize() {
        return FLPotSize;
    }

    public boolean canRaise() {
        return canRaise;
    }

    public void canRaise(boolean canRaise) {
        this.canRaise = canRaise;
    }

    public boolean isHeroOnButton() {
        return isOnButton;
    }

    public void isOnButton(boolean isOnButton) {
        this.isOnButton = isOnButton;
    }

    public double getAmountToCall(){
        return toCall;
    }

    public HandState setToCall(double toCall) {
        this.toCall = toCall;
        return this;
    }

    @Override
	public int getHeroActionCount() {
		return heroActionCount;
	}

	public void setHeroActionCount(int heroActionCount) {
		this.heroActionCount = heroActionCount;
	}

	@Override
	public int getVillainActionCount() {
		return villainActionCount;
	}

	public void setVillainActionCount(int villainActionCount) {
		this.villainActionCount = villainActionCount;
	}

	public void setAggressionInfoOnlyCount(IAggressionInfo info, boolean revert) {
		if (revert) {
			villainActionCount = info.getHeroActionCount();
			heroActionCount = info.getVillainActionCount();
			villainAggresionActionCount = info.getHeroAggresionActionCount();
			heroAggresionActionCount = info.getVillainAggresionActionCount();
		} else {
			villainActionCount = info.getVillainActionCount();
			heroActionCount = info.getHeroActionCount();
			villainAggresionActionCount = info.getVillainAggresionActionCount();
			heroAggresionActionCount = info.getHeroAggresionActionCount();
		}
	}

    /**
     * @return the wasOpponentPreviousAggresive
     */
    @Override
	public boolean wasVillainPreviousAggresive() {
        return wasOpponentPreviousAggresive;
    }

    /**
     * @param wasOpponentPreviousAggresive the wasOpponentPreviousAggresive to set
     */
	public void wasOpponentPreviousAggresive(boolean wasOpponentPreviousAggresive) {
        this.wasOpponentPreviousAggresive = wasOpponentPreviousAggresive;
    }

    /**
     * @return the wasIPreviousAggresive
     */
    @Override
	public boolean wasHeroPreviousAggresive() {
        return wasHeroPreviousAggresive;
    }

    /**
     * @param wasIPreviousAggresive the wasIPreviousAggresive to set
     */
	public void wasHeroPreviousAggresive(boolean wasIPreviousAggresive) {
        this.wasHeroPreviousAggresive = wasIPreviousAggresive;
    }

    /**
     * @return the potOdds
     */
    public double getPotOdds() {
        return potOdds;
    }

    /**
     * @param potOdds the potOdds to set
     */
    public void setPotOdds(double potOdds) {
        this.potOdds = potOdds;
    }

    /**
     * @return the potToStackOdds
     */
    public double getPotToStackOdds() {
        return potToStackOdds;
    }

    /**
     * @param potToStackOdds the potToStackOdds to set
     */
    public void setPotToStackOdds(double potToStackOdds) {
        this.potToStackOdds = potToStackOdds;
    }

    /**
     * @return the positivePotential
     */
    public double getPositivePotential() {
        return positivePotential;
    }

    /**
     * @param positivePotential the positivePotential to set
     */
    public void setPositivePotential(double positivePotential) {
        this.positivePotential = positivePotential;
    }

    /**
     * @return the negativePotential
     */
    public double getNegativePotential() {
        return negativePotential;
    }

    /**
     * @param negativePotential the negativePotential to set
     */
    public void setNegativePotential(double negativePotential) {
        this.negativePotential = negativePotential;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HandState)) {
            return false;
        }
        final HandState other = (HandState) obj;
        if (this.hashCode() != other.hashCode()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return VectorUtils.toString(this).hashCode();
    }

    /**
     * @param stackProportion the stackProportion to set
     */
    public void setStackProportion(double stackProportion) {
        this.stackProportion = stackProportion;
    }

	public String toSmartString() {
		StringBuilder builder = new StringBuilder();
		builder.append("At ");
		builder.append(PokerStreet.valueOf(getStreet()).toString());
		builder.append(" ");
		if (isHeroOnButton()) {
			builder.append("On button");
		} else {
			builder.append("OOP");
		}
		builder.append(" ");
		if (getStreet() == PokerStreet.PREFLOP_VALUE && getPotOdds() == 0) {
			builder.append("opp call SB");
		} else {
			if (isHeroOnButton() && getHeroActionCount() == 0) {
				builder.append("UO");
			} else {
				builder.append("vs raise");
			}
		}
		return builder.toString();
   }
}
