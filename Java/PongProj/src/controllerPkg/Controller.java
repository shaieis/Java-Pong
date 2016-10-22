/*
 * @author Shai Eisenbud
 */

package controllerPkg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import EnumPkg.PlayersEnum;
import inputPkg.DualAnalogInputButtonMapping;
import inputPkg.DualButtonAnalogKeyboardInput;
import inputPkg.InputInterface;
import modelPkg.Model;
import modelPkg.ModelInterface;
import viewPkg.MainGameFrame;
import viewPkg.ViewInterface;

public class Controller implements InputListener
{
	
	private InputInterface[] _inputs;
	private ModelInterface _model;
	private ViewInterface _frame;
	
	// Public methods =================
	
	public void startGame()
	{
		// Create model
		_model = new Model();
		
		// Create and config inputs
		_inputs = new InputInterface[PlayersEnum.values().length];
		
		DualAnalogInputButtonMapping keyMappingPlayerOne = new DualAnalogInputButtonMapping(KeyEvent.VK_W, 
															    							KeyEvent.VK_S, 
															    							-1, 
															    							-1);

		DualAnalogInputButtonMapping keyMappingPlayerTwo = new DualAnalogInputButtonMapping(KeyEvent.VK_UP, 
																			   			 	KeyEvent.VK_DOWN, 
																			   			 	KeyEvent.VK_ESCAPE, 
																			   			 	KeyEvent.VK_ENTER);
		

		_inputs[PlayersEnum.playerOne.ordinal()] = new DualButtonAnalogKeyboardInput(_model.getInputParamsConfig(), keyMappingPlayerOne);
		_inputs[PlayersEnum.playerTwo.ordinal()] = new DualButtonAnalogKeyboardInput(_model.getInputParamsConfig(), keyMappingPlayerTwo);
		
		// Start listening to inputs
		_inputs[PlayersEnum.playerOne.ordinal()].addInputListener(this);
		_inputs[PlayersEnum.playerTwo.ordinal()].addInputListener(this);
		
		// Create and config view
		_frame = new MainGameFrame();
		_frame.addKeyListener((KeyListener) _inputs[PlayersEnum.playerOne.ordinal()]);
		_frame.addKeyListener((KeyListener) _inputs[PlayersEnum.playerTwo.ordinal()]);
		
		// add view to model listener
		_model.addViewListener(_frame);
		_model.startGame(_inputs[PlayersEnum.playerOne.ordinal()].getPos(), 
						 _inputs[PlayersEnum.playerTwo.ordinal()].getPos());
				
	}

	// Recieve input position from input and send data to model
	public void inputPos(InputInterface caller, int pos) 
	{
		_model.recieveInput(findPlayerByInput(caller), pos);
	}
	
	// Recieve pause press from input and send data to model
	public void pause(InputInterface caller)
	{
		_model.pause(findPlayerByInput(caller));
	}
	
	// Recieve exit press from input and send data to model
	public void exit(InputInterface caller)
	{
		System.exit(0);
	}
	
	
	// Private methods =================
	
	// Return the player that uses input
	private PlayersEnum findPlayerByInput(InputInterface input)
	{
		if (input == _inputs[PlayersEnum.playerOne.ordinal()])
		{
			return PlayersEnum.playerOne;
		}
		
		else
		{
			return PlayersEnum.playerTwo;
		}
	}
	
} 

