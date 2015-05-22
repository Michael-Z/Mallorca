/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.bot.modeller;

import mallorcatour.bot.villainobserver.IVillainListener;
import mallorcatour.bot.villainobserver.VillainStatistics;
import mallorcatour.brains.IAdvisor;
import mallorcatour.brains.neural.NeuralAdvisor;
import mallorcatour.brains.neural.gusxensen.GusXensen;
import mallorcatour.core.game.HoleCards;
import mallorcatour.core.game.LimitType;
import mallorcatour.core.game.advice.Advice;
import mallorcatour.core.game.situation.LocalSituation;
import mallorcatour.util.Log;

/**
 * 
 * @author Andrew
 */
public class VillainModel implements IAdvisor, IVillainListener {

	// TODO create correct neurals with different players or change villain
	// modelling approach
	private static IAdvisor DEFAULT_NL_NEURAL;
	static {
		GusXensen player = new GusXensen();
		DEFAULT_NL_NEURAL = new NeuralAdvisor(player, player, "Gus Xensen");
	}
	private static final IAdvisor DEFAULT_FL_NEURAL = null;
	private static final int REQUIRED_HANDS_FOR_MODELLING = 5;
	private VillainStatistics villainStatistics;
	private IAdvisor currentVillainNeural;
	private final PreflopSpectrumModeller preflopVillainModeller;
	private final LimitType limitType;
	private boolean isVillainKnown;
	private final String DEBUG_PATH;

	public VillainModel(LimitType limitType, String debug) {
		this.limitType = limitType;
		preflopVillainModeller = new PreflopSpectrumModeller();
		currentVillainNeural = limitType == LimitType.NO_LIMIT ? DEFAULT_NL_NEURAL : DEFAULT_FL_NEURAL;
		this.DEBUG_PATH = debug;
	}

	@Override
	public Advice getAdvice(LocalSituation situation, HoleCards cards) {
		int street = situation.getStreet();
		if (street == LocalSituation.PREFLOP && isVillainKnown && villainStatistics.isPreflopLearned()) {
			return preflopVillainModeller.getAdvice(situation, cards, villainStatistics.getPrefloNeuralNetwork());
		}
		Advice result = currentVillainNeural.getAdvice(situation, cards);
		return result;
	}

	@Override
	public void onVillainChange(VillainStatistics villain) {
		villainStatistics = villain;
		Log.f(DEBUG_PATH,
				"Villain changed. Name: " + villainStatistics.getName() + ". Aggression frequency: "
						+ villainStatistics.getAggressionFrequency() + ". Fold frequency: "
						+ villainStatistics.getFoldFrequency() + ". AF: " + villainStatistics.getAggressionFactor()
						+ ". Wtsd: " + villainStatistics.getWtsd() + ". Hands: " + villainStatistics.getHandsCount());
		if (villainStatistics.getHandsCount() >= REQUIRED_HANDS_FOR_MODELLING) {
			chooseModellingNeural();
		}
	}

	@SuppressWarnings("unused")
	private void chooseModellingNeural() {
		double minError = Double.MAX_VALUE;
		PokerStatsDistance distance = new PokerStatsDistance();
		double error = distance.getDistance(villainStatistics, DEFAULT_NL_NEURAL);
		//TODO add real choosing of neural to model opp
		currentVillainNeural = DEFAULT_FL_NEURAL;
		Log.f(DEBUG_PATH, "Modelling by " + currentVillainNeural.getName());
		Log.f(DEBUG_PATH, "Distance to modelling bot: " + minError);
	}

	@Override
	public void onVillainKnown(boolean known) {
		isVillainKnown = known;
		if (!known) {
			currentVillainNeural = limitType == LimitType.NO_LIMIT ? DEFAULT_NL_NEURAL : DEFAULT_FL_NEURAL;
		}
	}

	@Override
	public double getAggressionFactor() {
		return villainStatistics.getAggressionFactor();
	}

	@Override
	public double getWtsd() {
		return villainStatistics.getWtsd();
	}

	@Override
	public double getAggressionFrequency() {
		return villainStatistics.getAggressionFrequency();
	}

	@Override
	public double getFoldFrequency() {
		return villainStatistics.getFoldFrequency();
	}

	@Override
	public String getName() {
		return villainStatistics.getName();
	}
}