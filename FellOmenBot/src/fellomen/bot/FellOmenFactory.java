/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fellomen.bot;

import mallorcatour.bot.interfaces.IBotFactory;
import mallorcatour.core.player.interfaces.Player;
import mallorcatour.core.game.advice.AdvisorListener;

/**
 *
 * @author Andrew
 */
public class FellOmenFactory implements IBotFactory {

    public Player createBot(AdvisorListener villainListener, AdvisorListener heroListener, String name, String debug) {
        return new FellOmen2(debug);
    }
}
