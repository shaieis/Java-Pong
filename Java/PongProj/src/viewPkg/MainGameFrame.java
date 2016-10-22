/*
 * @author Shai Eisenbud
 */

package viewPkg;

import javax.swing.JFrame;
import EnumPkg.PlayersEnum;
import modelPkg.ScreenComponentsParamsConfig;

public class MainGameFrame extends JFrame implements ViewInterface
{

	
	ScreenComponentsParamsConfig _params;
	MainGamePanel _panel;

	// Ctor
	public MainGameFrame()
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		
		setVisible(true);      // Without this setVisible sometimes getWidth() = 0

		_panel = new MainGamePanel(getWidth(), getHeight());
		add(_panel);

		setVisible(true);    // Without this setVisible sometimes panel is not shown - grey screen
		
	}

	// Public methods =================
	
	public void movePaddle(PlayersEnum player, int YPos) 
	{
		_panel.movePaddle(player, YPos);
	}

	public void setParameters(ScreenComponentsParamsConfig params) 
	{
		_params = params;
		_panel.setParams(_params);
	}

	public void setScore(PlayersEnum player, int score)
	{
		_panel.setScore(player, score);
		
	}

	public void moveBall(int xPos, int yPos)
	{
		_panel.moveBall(xPos, yPos);	
	}

	public void showPauseMsg()
	{
		_panel.showMsg("PAUSE");
	}
	
	public void hidePauseMsg()
	{
		_panel.showMsg("");
	}
}
