/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mallorcatour.neural.modeller;

import mallorcatour.core.stats.PokerStats;
import mallorcatour.core.vector.IDistanceCalculator;

/**
 *
 * @author Andrew
 */
public class PokerStatsDistance implements IDistanceCalculator<PokerStats> {

	@Override
	public double getDistance(PokerStats one, PokerStats other) {
		double sum = 0;
		if (!Double.isNaN(one.getAggressionFrequency()) && !Double.isNaN(other.getAggressionFrequency())) {
			sum += Math.pow(one.getAggressionFrequency() - other.getAggressionFrequency(), 2);
		}
		if (!Double.isNaN(one.getFoldFrequency()) && !Double.isNaN(other.getFoldFrequency())) {
			sum += Math.pow(one.getFoldFrequency() - other.getFoldFrequency(), 2);
		}
		if (!Double.isNaN(one.getVpip()) && !Double.isNaN(other.getVpip())) {
			sum += Math.pow(one.getVpip() - other.getVpip(), 2);
		}
		if (!Double.isNaN(one.getPfr()) && !Double.isNaN(other.getPfr())) {
			sum += Math.pow(one.getPfr() - other.getPfr(), 2);
		}
		return Math.sqrt(sum);
	}
}
