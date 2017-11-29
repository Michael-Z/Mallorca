/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.bot.preflop;

import mallorcatour.core.game.Action;
import mallorcatour.core.game.HoleCards;
import mallorcatour.core.game.situation.HandState;

/**
 *
 * @author Andrew
 */
public interface IPreflopChart {

    Action getAction(HandState situation, HoleCards cards);
}
