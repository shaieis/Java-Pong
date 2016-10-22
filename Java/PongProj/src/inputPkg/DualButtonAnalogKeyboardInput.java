/*
 * @author Shai Eisenbud
 */

package inputPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import controllerPkg.InputListener;
import modelPkg.InputParamsConfig;

public class DualButtonAnalogKeyboardInput implements InputInterface, KeyListener, ActionListener
{
	private final static int SAMPLE_SPEED = 10;
	private final static int ANALOG_STEP = 10;
	
	private int _pos; // Holds current position
	int _vel = 0; // Holds current movement speed
	
	private InputListener _inputListener;
	
	private InputParamsConfig _params; // Holds parameters from model
	private DualAnalogInputButtonMapping _keyMapping;
	
	private Timer _timer; // Timer used to simulate analog input
	
	
	// Ctor
	public DualButtonAnalogKeyboardInput(InputParamsConfig params, DualAnalogInputButtonMapping keyMapping)
	{
		_params = params;
		_pos = (_params.MIN_PADDLE_INPUT + _params.MAX_PADDLE_INPUT) / 2;
		
		_keyMapping = keyMapping;
		_timer = new Timer(SAMPLE_SPEED, this);
	}
	
	// Public methods =================
	
	public int getPos()
	{
		return _pos;
	}
	
	
	public void keyPressed(KeyEvent arg0) 
	{
		if (arg0.getKeyCode() == _keyMapping.UP_BUTTON_KEY_CODE)
		{
			if (_pos > _params.MIN_PADDLE_INPUT)
			{
				_vel = -1 * ANALOG_STEP;
				_timer.start();
			}
		}
		else if (arg0.getKeyCode() == _keyMapping.DOWN_BUTTON_KEY_CODE)
		{
			if (_pos < _params.MAX_PADDLE_INPUT)
			{
				_vel = ANALOG_STEP;
				_timer.start();
			}
			
		}
	}

	public void keyReleased(KeyEvent arg0) 
	{
		if ( (arg0.getKeyCode() == _keyMapping.UP_BUTTON_KEY_CODE) && (_vel < 0) )
		{
			_vel= 0;
			_timer.stop();
		}
		else if ( (arg0.getKeyCode() == _keyMapping.DOWN_BUTTON_KEY_CODE) && (_vel > 0))
		{
			_vel= 0;
			_timer.stop();
		}
		
		else if (arg0.getKeyCode() == _keyMapping.ESCAPE_BUTTON_KEY_CODE)
		{
			_inputListener.exit(this);
			
		}		
		else if (arg0.getKeyCode() == _keyMapping.PAUSE_BUTTON_KEY_CODE)
		{
			_inputListener.pause(this);
		}
		
	}
	
	
	public void keyTyped(KeyEvent arg0) 
	{}

	
	public void addInputListener(InputListener input)
	{
		_inputListener = input;
	}
	

	// Actions to perform when timer ticks
	public void actionPerformed(ActionEvent arg0)
	{
		
		if (_vel > 0) // Going down
		{
			if (_pos + _vel > _params.MAX_PADDLE_INPUT)
			{
				_pos = _params.MAX_PADDLE_INPUT;
			}
			else
			{
				_pos += _vel;				
				}
		}
		else if (_vel < 0) // Going up
		{
			if (_pos + _vel < _params.MIN_PADDLE_INPUT)
			{
				_pos = _params.MIN_PADDLE_INPUT;
			}
			else
			{
				_pos += _vel;
			}	
		} 
			
		sendPosToListeners();
	}
		
	// Private methods =================
	
	private void sendPosToListeners()
	{
		if (_inputListener != null)
		{
			_inputListener.inputPos(this, _pos);
		}
	}

}
