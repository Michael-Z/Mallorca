package mallorcatour.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mallorcatour.core.game.PokerStreet;
import mallorcatour.core.game.advice.AdvisorListener;
import mallorcatour.core.game.advice.IAdvice;
import mallorcatour.core.game.state.HandState;
import mallorcatour.neural.core.PokerLearningExample;
import mallorcatour.neural.manager.SituationIO;
import mallorcatour.tools.Log;

public class WritingAdvisorListener implements AdvisorListener {

	private List<PokerLearningExample> examples = new ArrayList<>();

	@Override
	public void onAdvice(HandState situation, IAdvice advice) {
		examples.add(new PokerLearningExample(situation, advice));
	}

	public void reset() {
		examples.clear();
	}

	public void printAnalysis() {
		int preflop = 0, flop = 0, turn = 0, river = 0;
		for (PokerLearningExample item : examples) {
			if (item.getSituation().getStreet() == PokerStreet.PREFLOP_VALUE) {
				preflop++;
			}
			if (item.getSituation().getStreet() == PokerStreet.FLOP_VALUE) {
				flop++;
			}
			if (item.getSituation().getStreet() == PokerStreet.TURN_VALUE) {
				turn++;
			}
			if (item.getSituation().getStreet() == PokerStreet.RIVER_VALUE) {
				river++;
			}
		}
		Log.d("Preflop: " + preflop);
		Log.d("Flop: " + flop);
		Log.d("Turn: " + turn);
		Log.d("River: " + river);
	}

	public void save(String dirName) {
		File directory = new File(dirName);
		directory.mkdirs();
		SituationIO.writeToFiles(directory, true, examples);
		Log.d("Written " + examples.size() + " examples");
	}
}
