package mallorcatour.bot.math;

import java.util.Map;

import mallorcatour.bot.actionpreprocessor.NLActionPreprocessor;
import mallorcatour.bot.interfaces.IPlayer;
import mallorcatour.bot.modeller.BasePokerNN;
import mallorcatour.bot.modeller.BaseSpectrumSituationHandler;
import mallorcatour.bot.modeller.BaseVillainModeller;
import mallorcatour.bot.modeller.NLSpectrumSituationHandler;
import mallorcatour.bot.preflop.IPreflopChart;
import mallorcatour.bot.preflop.NLPreflopChart;
import mallorcatour.core.game.Action;
import mallorcatour.core.game.Card;
import mallorcatour.core.game.HoleCards;
import mallorcatour.core.game.PokerStreet;
import mallorcatour.core.game.advice.Advice;
import mallorcatour.core.game.interfaces.IActionPreprocessor;
import mallorcatour.core.game.interfaces.IGameInfo;
import mallorcatour.core.game.situation.IDecisionListener;
import mallorcatour.core.game.situation.IPokerNN;
import mallorcatour.core.game.situation.ISpectrumListener;
import mallorcatour.core.game.situation.LocalSituation;
import mallorcatour.grandtorino.nn.danielxn.DanielxnNeurals;
import mallorcatour.util.Log;

/** 
 * This bot currently does not use global parameters.
 * 
 * @author Andrew
 */
public class NLMathBot implements IPlayer {

    private BaseAdviceCreatorFromMap adviceCreator;
    private IGameInfo gameInfo;  // general game information
    private BaseSpectrumSituationHandler situationHandler;
    @SuppressWarnings("unused")
	private String heroName, villainName;
    private IPokerNN preflopPokerNN;
    private IPreflopChart preflopBot;
    private Card heroCard1, heroCard2;
    private IActionPreprocessor actionPreprocessor;
    private final String DEBUG_PATH;

    public NLMathBot(BaseVillainModeller villainModeller,
            ISpectrumListener listener,
            IDecisionListener decisionListener, String debug) {
        adviceCreator = new AdviceCreatorFromMap();
        situationHandler = new NLSpectrumSituationHandler(villainModeller, 
        		true, true, listener, decisionListener, debug);
        preflopPokerNN = new BasePokerNN(new DanielxnNeurals(), true);
        preflopBot = new NLPreflopChart();
        actionPreprocessor = new NLActionPreprocessor();
        this.DEBUG_PATH = debug;
    }

    /**
     * An event called to tell us our hole cards and seat number
     * @param c1 your first hole card
     * @param c2 your second hole card
     * @param seat your seat number at the table
     */
    public void onHoleCards(Card c1, Card c2, String heroName, String villainName) {
        situationHandler.onHoleCards(c1, c2, heroName, villainName);
        this.heroCard1 = c1;
        this.heroCard2 = c2;
        this.heroName = heroName;
        this.villainName = villainName;
    }

    /**
     * Requests an Action from the player
     * Called when it is the Player's turn to act.
     */
    public Action getAction() {
        Advice advice;
        Action action = null;
        Log.f(DEBUG_PATH, "=========  Decision-making  =========");
        if (gameInfo.getBankRoll(villainName) == IGameInfo.SITTING_OUT) {
            Log.f(DEBUG_PATH, "Villain is sitting out");
            double percent = 0.5;
            action = Action.createRaiseAction(percent
                    * (gameInfo.getPotSize() + gameInfo.getHeroAmountToCall()), percent);
        } else if (gameInfo.isPostFlop()) {
            Map<Action, Double> map = situationHandler.getProfitMap();
            Log.f(DEBUG_PATH, "Map<Action, Profit>: " + map.toString());
            advice = adviceCreator.create(map);
            action = advice.getAction();
            Log.f(DEBUG_PATH, "Advice: " + advice.toString());
            Log.f(DEBUG_PATH, "Action: " + action.toString());
            action = actionPreprocessor.preprocessAction(action, gameInfo, villainName);
        } else //preflop
        {
            LocalSituation situation = situationHandler.onHeroSituation();
            Log.f(DEBUG_PATH, "Preflop: " + situation.toString());
            if (gameInfo.isPreFlop() && gameInfo.getBankRoll(villainName) != IGameInfo.SITTING_OUT) {
                action = preflopBot.getAction(situation, new HoleCards(heroCard1, heroCard2));
                if (action != null) {
                    action = actionPreprocessor.preprocessAction(action, gameInfo, villainName);
                }
            }
            if (action == null) {
                advice = preflopPokerNN.getAdvice(situation, new HoleCards(heroCard1, heroCard2));
                action = advice.getAction();
                Log.f(DEBUG_PATH, "Advice: " + advice.toString());
                action = actionPreprocessor.preprocessAction(action, gameInfo, villainName);
            }
        }
        Log.f(DEBUG_PATH, "=========  End  =========");
        situationHandler.onHeroActed(action);
        return action;
    }

    /**
     * A new betting round has started.
     */
    public void onStageEvent(PokerStreet street) {
        situationHandler.onStageEvent(street);
    }

    /**
     * A new game has been started.
     * @param gi the game stat information
     */
    public void onHandStarted(IGameInfo gameInfo, long handNumber) {
        this.gameInfo = gameInfo;
        situationHandler.onHandStarted(gameInfo);
    }

    /**
     * An villain action has been observed.
     */
    public void onVillainActed(Action action, double toCall) {
        situationHandler.onVillainActed(action, toCall);
    }
}