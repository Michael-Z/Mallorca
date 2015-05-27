package mallorcatour.core.game.interfaces;

import mallorcatour.core.game.OpenPlayerInfo;
import mallorcatour.core.game.situation.IPlayerInfoStrip;

public interface IPlayerGameInfo extends IGameInfo, IPlayerInfoStrip {

	boolean isVillainSitOut();

	public boolean canHeroRaise();

	OpenPlayerInfo getVillain();

	OpenPlayerInfo getHero();

}