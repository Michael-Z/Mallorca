package mallorcatour.brains.neural.cuba;

import mallorcatour.bot.Player;
import mallorcatour.brains.neural.BaseBotFactory;
import mallorcatour.core.equilator.PokerEquilatorBrecher;
import mallorcatour.core.equilator.preflop.PreflopEquilatorImpl;
import mallorcatour.core.player.interfaces.IPlayer;
import mallorcatour.brains.IActionChecker;
import mallorcatour.brains.IAdvisor;
import mallorcatour.brains.neural.NeuralAdvisor;
import mallorcatour.core.game.advice.AdvisorListener;
import mallorcatour.core.game.interfaces.ISpectrumListener;
import mallorcatour.core.game.situation.observer.SituationHandler;

public class CubaFactory extends BaseBotFactory {

	@Override
	public IPlayer createBot(IAdvisor villainModel, ISpectrumListener spectrumListener,
			AdvisorListener villainListener, AdvisorListener heroListener, String name, String debug) {
		Cuba cuba = new Cuba();
		NeuralAdvisor advisor = new NeuralAdvisor(cuba, cuba, "Cuba");
		Player player = new Player(IAdvisor.UNSUPPORTED, IAdvisor.UNSUPPORTED, advisor, IActionChecker.EMPTY, name,
				debug);
		player.setStudent(heroListener);
		SituationHandler handler = new SituationHandler(true, name, new PokerEquilatorBrecher(), new PreflopEquilatorImpl());
		player.set(handler, handler, handler);
		return player;
	}

}