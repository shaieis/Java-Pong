/*
 * @author Shai Eisenbud
 */

package modelPkg;

import EnumPkg.PlayersEnum;

import viewPkg.ViewInterface;

public class Model implements ModelInterface, BallMovementListenerInteface
{
	private static final double BALL_SPEED = 10; 
	private static final double BALL_START_MAX_ANGLE = 20; 
	private static final int BALL_TIMER_UPDATE_INTERVAL_MS = 10;
	private Paddle[] _paddles;
	private SquareBall _ball;

	
	private int[] _score;
	private InputParamsConfig _inputParams;
	private ScreenComponentsParamsConfig _screenParams;
	
	private boolean _lostBall; // set to true when ball has passed the paddle and player lost the round
	private boolean _isPause;
	
	private ViewInterface _viewListener;
	
	// Ctor
	public Model()
	{
		_inputParams = new InputParamsConfig();
		_screenParams = new ScreenComponentsParamsConfig();
		
		// Initialize paddles
		_paddles = new Paddle[PlayersEnum.values().length];
		
		_paddles[PlayersEnum.playerOne.ordinal()] = new Paddle(_screenParams.PADDLE_X_INDENT, 
															  _screenParams.PADDLE_WIDTH, 
															  _screenParams.PADDLE_HEIGHT, 30);
		
		_paddles[PlayersEnum.playerTwo.ordinal()] = new Paddle(_screenParams.MODEL_SCREEN_WIDTH - 1 - _screenParams.PADDLE_WIDTH - _screenParams.PADDLE_X_INDENT, 
															  _screenParams.PADDLE_WIDTH, 
															  _screenParams.PADDLE_HEIGHT, 30);
		
		// Initialize ball
		_ball = new SquareBall(_screenParams.BALL_START_X_POS, 
							   _screenParams.BALL_START_Y_POS, 
							   _screenParams.BALL_WIDTH);
		_ball.addListener(this);

		// Initialize score array
		_score = new int[PlayersEnum.values().length];

		
		_inputParams = new InputParamsConfig();
		_screenParams = new ScreenComponentsParamsConfig();
	}
	
	// Public methods =================
	
	public void startGame(int playerOneInputPos, int playerTwoInputPos)
	{
		// Get current player inputs to set paddle correctly at the beggining
		recieveInput(PlayersEnum.playerOne, playerOneInputPos);
		recieveInput(PlayersEnum.playerTwo, playerTwoInputPos);
		
		// Set score to 0:0
		setScore(PlayersEnum.playerOne, 0);
		setScore(PlayersEnum.playerTwo, 0);
		
		_lostBall = false;
		_isPause = false;
		
		// Start ball movement
		_ball.startMoving(BALL_SPEED, randomizeBallAngle(), BALL_TIMER_UPDATE_INTERVAL_MS);
	}
	

	// A method with which the model can recieve input
	public void recieveInput(PlayersEnum player, int yInput)
	{
		// Check input range
		if ((yInput < _inputParams.MIN_PADDLE_INPUT) || (yInput > _inputParams.MAX_PADDLE_INPUT))
			return;
		
		// Convert input to y position
		int inputRange = _inputParams.MAX_PADDLE_INPUT - _inputParams.MIN_PADDLE_INPUT + 1;
		int screenYRange = _screenParams.MODEL_SCREEN_HEIGHT - _screenParams.PADDLE_HEIGHT + 1;

		double convertRanges = ((double) screenYRange) / inputRange;

		int screenYPos =  (int) ((yInput - _inputParams.MIN_PADDLE_INPUT) * convertRanges) + (_screenParams.PADDLE_HEIGHT/2);
		
		// Set y position to paddle
		_paddles[player.ordinal()].setYPos(screenYPos);
		
		// inform view
		if (_viewListener != null)
		{
			_viewListener.movePaddle(player, screenYPos);
		}
		
		
	}
	
	public void addViewListener(ViewInterface lis)
	{
		_viewListener = lis;
		_viewListener.setParameters(_screenParams);
	}



	public InputParamsConfig getInputParamsConfig() 
	{
		return _inputParams;
	}



	public ScreenComponentsParamsConfig getScreenComponentsParamsConfig() 
	{
		return _screenParams;
	}



	public void pause(PlayersEnum player)
	{
		if (_isPause) // game is paused, resume
		{
			_ball.resume();
			_isPause = false;
			_viewListener.hidePauseMsg();
			
		}
		else // game is running, pause game
		{
			_ball.pause();
			_isPause = true;
			_viewListener.showPauseMsg();

		}
	}


	// Method to be called by ball when moved
	public void ballMoved()
	{
		checkBallVerticalCollition();
		checkBallPaddleCollition();
		
		if (_ball.getXPos() < 0)
		{
			ballOutOfScreen(PlayersEnum.playerTwo);
		}
		if (_ball.getXPos() > _screenParams.MODEL_SCREEN_WIDTH)
		{
			ballOutOfScreen(PlayersEnum.playerOne);			
		}
		
		if (_viewListener != null)
		{
			_viewListener.moveBall(_ball.getXPos(), _ball.getYPos());
		}
		
	}
	
	// Private methods =================
	
	private void ballOutOfScreen(PlayersEnum playerWhoWon)
	{
		incrementScore(playerWhoWon);
		
		_ball.setAngle(randomizeBallAngle());
		_ball.returnToInitialPos();
		_lostBall = false;
	}
	private void checkBallVerticalCollition()
	{
		if ( (_ball.getYPos() + (_ball.getWidth() / 2) >= _screenParams.MODEL_SCREEN_HEIGHT) )
		{
			// Ball hit top
			_ball.verticalCollision();
		}
			
		else if ( (_ball.getYPos() - (_ball.getWidth() / 2) <= 0 ) )
		{
			// Ball hit botom
			_ball.verticalCollision();
		}
	}
	
	private void checkBallPaddleCollition()
	{
		if (_lostBall)
			return;
		
		// Check if ball hit right side paddle
		if (_ball.getXPos() - (_ball.getWidth()/2) <= _screenParams.PADDLE_X_INDENT)
		{
			if ( (_ball.getYPos() + (_ball.getWidth()/2) >=_paddles[PlayersEnum.playerOne.ordinal()].getYPos() - (_paddles[PlayersEnum.playerOne.ordinal()].getHeight()/2)) &&
			     (_ball.getYPos() - (_ball.getWidth()/2) <=_paddles[PlayersEnum.playerOne.ordinal()].getYPos() + (_paddles[PlayersEnum.playerOne.ordinal()].getHeight() / 2)) )
			{
				_ball.setAngle(_paddles[PlayersEnum.playerOne.ordinal()].getHitAngle(_ball.getYPos()));
			}
			else
			{
				_lostBall = true; // paddle is not in right vertical position, player will lose
			}
				
		}
		
		// Check if ball hit left side paddle
		else if (_ball.getXPos() + (_ball.getWidth()/2) >= _screenParams.MODEL_SCREEN_WIDTH - _screenParams.PADDLE_X_INDENT)
		{
			if ( (_ball.getYPos() + (_ball.getWidth()/2) >=_paddles[PlayersEnum.playerTwo.ordinal()].getYPos() - (_paddles[PlayersEnum.playerTwo.ordinal()].getHeight()/2)) &&
				 (_ball.getYPos() - (_ball.getWidth()/2) <=_paddles[PlayersEnum.playerTwo.ordinal()].getYPos() + (_paddles[PlayersEnum.playerTwo.ordinal()].getHeight() / 2)) )
			{
				_ball.setAngle(180 - _paddles[PlayersEnum.playerTwo.ordinal()].getHitAngle(_ball.getYPos()));
			}
			else
			{
				_lostBall = true; // paddle is not in right vertical position, player will lose
			}
		}

	}
	
	private void setScore(PlayersEnum player, int score)
	{
		_score[player.ordinal()] = score;
		
		if (_viewListener != null)
		{
			_viewListener.setScore(player, score);
		}
	}
	
	private void incrementScore(PlayersEnum player)
	{
		++(_score[player.ordinal()]);
		
		if (_viewListener != null)
		{
			_viewListener.setScore(player, _score[player.ordinal()]);
		}
	}
	
	// Randomize ball angle when starting from center
	private double randomizeBallAngle()
	{
		double deg = ((Math.random()) * BALL_START_MAX_ANGLE) - (BALL_START_MAX_ANGLE / 2);
		
		if (Math.random() < 0.5) // Randomize between moving towards right player and left player
		{
			deg += 180;
		}
			

		return deg;
	}

}

	

