/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.core.game.situation;

import mallorcatour.bot.interfaces.ISpectrumListener;
import mallorcatour.core.spectrum.Spectrum;
import mallorcatour.util.DateUtils;
import mallorcatour.util.SerializatorUtils;

/**
 *
 * @author Andrew
 */
public class SavingSpectrumListener implements ISpectrumListener {

    public void onSpectrumChanged(Spectrum spectrum, String log) {
        SerializatorUtils.save("spectrum_" + DateUtils.getDate(false) + ".spectr", spectrum);
    }
}
