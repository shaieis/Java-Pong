/*
 * @author Shai Eisenbud
 */

package modelPkg;

public class Paddle 
{
	private int  _yPos;
	private final int _xPos, _width, _height;
	private double _maxHitAngle;
	
	// Ctor
	public Paddle(int xPos, int width, int height, double maxHitAngle)
	{
		_xPos = xPos;
		_width = width;
		_height = height;
		_maxHitAngle = maxHitAngle;
	}
	// Public methods =================
	
	
	/* getHitAngle returns the new angle of the ball after hit (assuming ball hit from right side, and returns moving right after hit).
	 * The angle is determined by the distance of the hit from center of paddle. maxHitAngle returned when hitting the top-right corner
	 */ 
	public double getHitAngle(int hitYPos)
	{
		if (hitYPos < _yPos - _width)
		{
			hitYPos = _yPos - _width;
		}
		else if (hitYPos > _yPos + _width)
		{
			hitYPos = _yPos + _width;
		}
		
		return ( (hitYPos - _yPos) / (_width / 2) ) * _maxHitAngle;
	}

	public void setMaxHitAngle(double maxHitAngle)
	{
		_maxHitAngle = maxHitAngle;
	}
	
	public double getMaxHitAngle() 
	{
		return _maxHitAngle;
	}
	
	public int getWidth() 
	{
		return _width;
	}
	
	public int getHeight() 
	{
		return _height;
	}

	public int getXPos() 
	{
		return _xPos;
	}

	public int getYPos() 
	{
		return _yPos;
	}

	public void setYPos(int yPos) 
	{
		_yPos = yPos;
	}

}
