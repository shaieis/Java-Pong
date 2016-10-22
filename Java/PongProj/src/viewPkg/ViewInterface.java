/*
 * @author Shai Eisenbud
 */

package viewPkg;



import java.awt.event.KeyListener;

import EnumPkg.PlayersEnum;

import modelPkg.ScreenComponentsParamsConfig;

public interface ViewInterface
{
	
	void setParameters(ScreenComponentsParamsConfig params);
	void movePaddle(PlayersEnum player, int YPos);
	void moveBall(int xPos, int yPos);
	
	void setScore(PlayersEnum player, int score);
	void addKeyListener(KeyListener l);
	void showPauseMsg();
	void hidePauseMsg();

}
