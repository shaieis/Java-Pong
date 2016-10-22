/*
 * @author Shai Eisenbud
 */

package modelPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SquareBall implements ActionListener
{
	private final double _initialXPos, _initialYPos; // positions to return to when resetting
	private double _xPos, _yPos;
	private double _xVel, _yVel, _speed;
	private final int _width; // Ball is a square. width is also height.
	
	private Timer _movementTimer;
	private BallMovementListenerInteface _listener;
	
	// Ctor
	public SquareBall(int InitialXPos, int InitialYPos, int width)
	{
		_initialXPos = InitialXPos;
		_initialYPos = InitialYPos;
				
		_xPos = InitialXPos;
		_yPos = InitialYPos;
		
		_xVel = 0;
		_yVel = 0;
		
		_width = width;
	}
	
	// Public methods =================
	
	public void startMoving(double speed, double angleInDegrees, int refreshTimeMS)
	{
		setVel(speed, angleInDegrees);
		
		// Set timer. if timer doesn't exist, creat new timer
		if (_movementTimer != null)
		{
			_movementTimer.stop();
			_movementTimer.setDelay(refreshTimeMS);
		}
		else
		{
			_movementTimer = new Timer(refreshTimeMS, this);
		}
		
		_movementTimer.start();
	}
	
	// Pause movement
	public void pause()
	{
		if (_movementTimer == null)
			return;

		_movementTimer.stop();
	}
	
	// Resume movement
	public void resume()
	{
		if (_movementTimer == null)
			return;

		_movementTimer.start();
	}
	
	public void addListener(BallMovementListenerInteface lis)
	{
		_listener = lis;
	}
	
	public void returnToInitialPos()
	{
		_xPos = _initialXPos;
		_yPos = _initialYPos;	
	}
	
	// invert vertical movement speed to mimic a vertical collision
	public void verticalCollision()
	{
		_yVel *= -1;
	}
	
	public void horizontalCollision()
	{
		_xVel *= -1;
	}
	
	public void setVel(double speed, double angleInDegrees)
	{
		
		_speed = speed;
		
		// make sure speed is valid
		if (speed < 0)
		{
			_xVel = 0;
			_yVel = 0;
		}
		
		else
		{
			_xVel = speed * Math.cos(Math.toRadians(angleInDegrees));
			_yVel = speed * Math.sin(Math.toRadians(angleInDegrees));
		}
	}
	
	public void setAngle(double angleInDegrees)
	{
		_xVel = _speed * Math.cos(Math.toRadians(angleInDegrees));
		_yVel = _speed * Math.sin(Math.toRadians(angleInDegrees));
	}
		
	public int getWidth() 
	{
		return _width;
	}
	
	public int getXPos() 
	{
		return (int)_xPos;
	}

	public int getYPos() 
	{
		return (int)_yPos;
	}

	public void setPos(int xPos, int yPos) 
	{
		_xPos = xPos;
		_yPos = yPos;
	}
	
	// Action to be performed every time the timer ticks.
	public void actionPerformed(ActionEvent e)
	{
		_xPos += _xVel;
		_yPos += _yVel;
		
		// Inform listener
		if (_listener != null)
		{
			_listener.ballMoved();
		}
	}
	

	
	  
}
