/**
 * www.TheAIGames.com 
 * Heads Up Omaha pokerbot
 *
 * Last update: May 07, 2014
 *
 * @author Jim van Eeden, Starapple
 * @version 1.0
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package bot;

import java.util.Scanner;

import mallorcatour.bot.C;
import mallorcatour.bot.interfaces.IBotFactory;
import mallorcatour.bot.math.NLMathBotFactory;
import mallorcatour.core.game.Action;
import mallorcatour.core.game.GameContextImpl;
import mallorcatour.core.game.PokerStreet;
import mallorcatour.core.game.advice.AdvisorListener;
import mallorcatour.core.player.interfaces.Player;
import mallorcatour.equilator.preflop.PreflopEquilatorImpl;
import mallorcatour.equilator.preflop.PreflopEquilatorImpl.LoadFrom;
import mallorcatour.neural.modeller.PlayerStatModel;
import mallorcatour.tools.FileUtils;
import mallorcatour.tools.Log;

/**
 * Class that reads the engine's input and asks the bot Class to calculate the
 * next move. Then returns that move to the engine.
 */
public class BotParser {

	final Scanner scan;

	final Player bot;

	public BotParser(Player bot) {
		this.scan = new Scanner(System.in);
		this.bot = bot;
	}

	public void run() {
		GameContextImpl gameInfo = new GameContextImpl();
		AiGamesController controller = new AiGamesController(gameInfo, bot);
		while (scan.hasNextLine()) {
			String line = scan.nextLine().trim();
			if (line.length() == 0) {
				continue;
			}
			String[] parts = line.split("\\s+");
			if (parts.length == 3 && parts[0].equals(C.ACTION)) {
				PokerStreet previous = gameInfo.getStage().previous();
				if (previous != null && gameInfo.getPot(previous) == -1) {
					double previousPot = gameInfo.pot - controller.heroAmountToCall;
					gameInfo.setPot(previous, previousPot);
					Log.d("Previous " + C.POT + " for " + previous + ": " + previousPot);
				}
				Action move = bot.getAction();
				System.out.println(actionToString(move));
				System.out.flush();
			} else if (parts.length == 3 && parts[0].equals(C.SETTINGS)) {
				controller.updateSetting(parts[1], parts[2]);
			} else if (parts.length == 3 && parts[0].equals(C.MATCH)) {
				controller.updateMatch(parts[1], parts[2]);
			} else if (parts.length == 3 && parts[0].startsWith(C.PLAYER)) {
				controller.updateMove(parts[0], parts[1], parts[2]);
			} else {
				System.err.printf("Unable to parse line ``%s''" + FileUtils.LINE_SEPARATOR, line);
			}
		}
	}

	private static String actionToString(Action action) {
		String actionStr;
		if (action.isFold()) {
			actionStr = C.FOLD;
		} else if (action.isPassive()) {
			if (action.isCheck()) {
				actionStr = C.CHECK;
			} else {
				actionStr = C.CALL;
			}
		} else {
			actionStr = C.RAISE;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(actionStr);
		builder.append(" ");
		builder.append((int) action.getAmount());
		return builder.toString();
	}

	public static void main(String[] args) {
		Log.WRITE_TO_ERR = true;
		PreflopEquilatorImpl.loadFrom = LoadFrom.CODE;
		PlayerStatModel model = new PlayerStatModel(PlayerStatModel.GERMANY_NL_NEURAL, true);
		IBotFactory factory = new NLMathBotFactory(model);
		BotParser parser = new BotParser(factory.createBot(model, AdvisorListener.NONE,
				"Mallorca", ""));
		parser.run();
	}
}
