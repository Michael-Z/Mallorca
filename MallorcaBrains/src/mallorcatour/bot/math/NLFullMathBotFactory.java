package mallorcatour.bot.math;

import mallorcatour.bot.interfaces.IBotFactory;
import mallorcatour.bot.interfaces.IDecisionListener;
import mallorcatour.bot.interfaces.IPlayer;
import mallorcatour.bot.interfaces.ISpectrumListener;
import mallorcatour.bot.villainobserver.SpectrumPlayerObserver;
import mallorcatour.brains.IAdvisor;
import mallorcatour.brains.math.StrengthManager;
import mallorcatour.core.game.GameObservers;
import mallorcatour.core.game.HoleCardsObservers;
import mallorcatour.core.game.LimitType;
import mallorcatour.core.game.situation.SituationHandler;

public class NLFullMathBotFactory implements IBotFactory {

	@Override
	public IPlayer createBot(IAdvisor villainModel, ISpectrumListener spectrumListener,
			IDecisionListener decisionListener, String name, String debug) {
		StrengthManager strengthManager = new StrengthManager(false);
		IProfitCalculator profitCalculator = new NLProfitCalculator(villainModel, strengthManager);
		SpectrumPlayerObserver villainObserver = new SpectrumPlayerObserver(villainModel, strengthManager,
				spectrumListener, name, false);

		NLMathBot bot = new NLMathBot(IAdvisor.UNSUPPORTED, IAdvisor.UNSUPPORTED, profitCalculator, name, debug,
				villainObserver);
		SituationHandler situationHandler = new SituationHandler(LimitType.NO_LIMIT, true, name);
		bot.set(situationHandler, new GameObservers(situationHandler, villainObserver, strengthManager),
				new HoleCardsObservers(villainObserver, situationHandler));
		return bot;
	}
}