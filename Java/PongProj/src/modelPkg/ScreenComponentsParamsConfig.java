/*
 * @author Shai Eisenbud
 */

package modelPkg;

public class ScreenComponentsParamsConfig
{
	
	// Screen values
	public final int MODEL_SCREEN_WIDTH;
	public final int MODEL_SCREEN_HEIGHT;
			
	// Paddle indent
	public final int PADDLE_X_INDENT;
	
	// Paddle and Ball sizes
	public final int PADDLE_HEIGHT;
	public final int PADDLE_WIDTH;
	public final int BALL_WIDTH; //Ball is a square
	
	// Ball starting Positions:

	
	public final  int BALL_START_X_POS;
	public final int BALL_START_Y_POS;
	
	
	ScreenComponentsParamsConfig()
	{
		MODEL_SCREEN_WIDTH = 1920;
		
		MODEL_SCREEN_HEIGHT = 1080;
			
		// Paddle indent
		PADDLE_X_INDENT = 100;
		
		// Paddle and Ball sizes
		PADDLE_HEIGHT = 121;
		PADDLE_WIDTH = 15;
		BALL_WIDTH = 30; //Ball is a square
		
		// Ball starting Positions:
		BALL_START_X_POS = ((MODEL_SCREEN_WIDTH-1) / 2);
		BALL_START_Y_POS = ((MODEL_SCREEN_HEIGHT-1) / 2);
		
	}
}
