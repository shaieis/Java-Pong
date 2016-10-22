/*
 * @author Shai Eisenbud
 */

package modelPkg;

import EnumPkg.PlayersEnum;
import viewPkg.ViewInterface;

public interface ModelInterface 
{
	
	InputParamsConfig getInputParamsConfig();
	ScreenComponentsParamsConfig getScreenComponentsParamsConfig();
	void addViewListener(ViewInterface view);
	void startGame(int playerOneInputPos, int playerTwoInputPos);
	void recieveInput(PlayersEnum player, int yInput);
	void pause(PlayersEnum player);
	
}
