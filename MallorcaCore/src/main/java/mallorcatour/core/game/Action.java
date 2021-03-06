package mallorcatour.core.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mallorcatour.core.game.advice.IAdvice;

public class Action implements Serializable, IAdvice {
	private static final long serialVersionUID = 1L;
	private static final double THRESHOLD_FOR_GOING_ALLIN = 0.6D;
	private static final double PERCENT_OF_POT_FOR_BET = 0.7D;
	private static final double PERCENT_OF_POT_FOR_RAISE = 0.7D;
	private double amount;
	/**
	 * Only for raise action
	 */
	private double toCall;
	private Type type;
	private double percentOfPot;
	private boolean isAllin = false;

	private Action(Type type, double amount) {
		this.type = type;
		this.amount = amount;
	}

	private Action(Type type) {
		this.type = type;
	}

	public double getAmount() {
		return this.amount;
	}

	public double getAmountToCall() {
		return this.toCall;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPercentOfPot() {
		return this.percentOfPot;
	}

	public boolean isFold() {
		return this.type == Type.FOLD;
	}

	public boolean isPassive() {
		return this.type == Type.PASSIVE;
	}

	public boolean isAggressive() {
		return this.type == Type.AGGRESSIVE;
	}

	public boolean isAllin() {
		return isAllin;
	}

	public static Action passive() {
		Action result = new Action(Type.PASSIVE);
		return result;
	}

	public static Action aggressive() {
		Action result = new Action(Type.AGGRESSIVE);
		return result;
	}

	public static Action createBetAction(double pot, double effectiveStack, double percent) {
		return createRaiseAction(0, pot, effectiveStack, percent);
	}

	public static Action createBetAction(double pot, double effectiveStack) {
		return createBetAction(pot, effectiveStack, PERCENT_OF_POT_FOR_BET);
	}

	public static Action createRaiseAction(double toCall, double pot, double effectiveStack) {
		return createRaiseAction(toCall, pot, effectiveStack, PERCENT_OF_POT_FOR_RAISE);
	}

	public static Action createRaiseAction(double toCall, double pot, double effectiveStack, double percent) {
		double raiseAmount = Math.round(percent * (toCall + pot));
		if (effectiveStack - raiseAmount < THRESHOLD_FOR_GOING_ALLIN * (pot + toCall + 2 * raiseAmount)) {
			raiseAmount = effectiveStack;
			percent = raiseAmount / (pot + toCall);
		}
		Action result = new Action(Type.AGGRESSIVE, raiseAmount);
		result.toCall = toCall;
		result.percentOfPot = percent;
		return result;
	}

	public static Action createRaiseAction(double amount, double percent) {
		Action result = new Action(Type.AGGRESSIVE, amount);
		result.percentOfPot = percent;
		return result;
	}

	public static Action allIn() {
		Action result = new Action(Type.AGGRESSIVE, Double.MAX_VALUE);
		result.isAllin = true;
		return result;
	}

	public static Action fold() {
		return new Action(Type.FOLD);
	}

	public static Action checkAction() {
		Action result = new Action(Type.PASSIVE, 0d);
		result.toCall = 0;
		return result;
	}

	public static Action betAction(double amount) {
		return new Action(Type.AGGRESSIVE, amount);
	}

	public static Action raiseAction(double amount) {
		return new Action(Type.AGGRESSIVE, amount);
	}

	public static Action callAction(double toCall) {
		Action result = new Action(Type.PASSIVE, toCall);
		result.toCall = toCall;
		return result;
	}

	public boolean isCall() {
		return (this.type == Type.PASSIVE) && (this.amount != 0.0D);
	}

	public boolean isCheck() {
		return (this.type == Type.PASSIVE) && (this.amount == 0.0D);
	}

	public String getActString() {
		if (isCall()) {
			return "calls " + getAmount();
		} else if (isCheck()) {
			return "checks";
		} else if (isAggressive()) {
			return "raises " + getAmount();
		} else if (isFold()) {
			return "folds";
		}
		return null;
	}

	public String toString() {
		if (this.type == Type.AGGRESSIVE) {
			if (!isAllin()) {
				return "Raise " + this.amount;
			}
			return "All-in";
		}
		if (this.type == Type.PASSIVE) {
			if (this.amount == 0.0D) {
				return "Check";
			}

			return "Call " + this.amount;
		}

		return this.type.toString();
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Action other = (Action) obj;
		if ((other.type == Type.FOLD) && (this.type == Type.FOLD)) {
			return true;
		}
		if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
			return false;
		}
		if ((this.type != other.type) && ((this.type == null) || (!this.type.equals(other.type)))) {
			return false;
		}
		if (Double.doubleToLongBits(this.percentOfPot) != Double.doubleToLongBits(other.percentOfPot)) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int hash = 5;
		return hash;
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		if (this.type.equals(Type.FOLD))
			this.type = Type.FOLD;
		else if (this.type.equals(Type.PASSIVE))
			this.type = Type.PASSIVE;
		else if (this.type.equals(Type.AGGRESSIVE))
			this.type = Type.AGGRESSIVE;
		else
			throw new IllegalArgumentException();
	}


	@Override
	public Action getAction() {
		return this;
	}

	@Override
	public double getFold() {
		return isFold() ? 1 : 0;
	}

	@Override
	public double getPassive() {
		return isPassive() ? 1 : 0;
	}

	@Override
	public double getAggressive() {
		return isAggressive() ? 1 : 0;
	}

	private static class Type implements Serializable {
		private static final long serialVersionUID = 1L;
		private String action;
		public static final Type FOLD = new Type("Fold");
		public static final Type PASSIVE = new Type("Passive");
		public static final Type AGGRESSIVE = new Type("Aggressive");

		private Type(String action) {
			this.action = action;
		}

		public String toString() {
			return this.action;
		}

		public boolean equals(Object other) {
			if (!(other instanceof Type)) {
				return false;
			}
			if (!((Type) other).action.equals(this.action)) {
				return false;
			}
			return true;
		}
	}

	@Override
	public List<Number> getValues() {
		List<Number> result = new ArrayList<Number>();
        result.add(getFold());
        result.add(getPassive());
        result.add(getAggressive());
        return result;
	}
}
