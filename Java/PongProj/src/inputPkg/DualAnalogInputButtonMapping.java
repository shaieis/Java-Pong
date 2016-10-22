/*
 * @author Shai Eisenbud
 */

package inputPkg;

public class DualAnalogInputButtonMapping 
{
	public final int UP_BUTTON_KEY_CODE, DOWN_BUTTON_KEY_CODE, ESCAPE_BUTTON_KEY_CODE, PAUSE_BUTTON_KEY_CODE;
	
	// Ctor
	public DualAnalogInputButtonMapping(int upButtonKeyCode,
										int downButtonKeyCode,
										int escapeButtonKeyCode,
										int pauseButtonKeyCode)
	{
		UP_BUTTON_KEY_CODE = upButtonKeyCode;
		DOWN_BUTTON_KEY_CODE = downButtonKeyCode;
		ESCAPE_BUTTON_KEY_CODE = escapeButtonKeyCode;	
		PAUSE_BUTTON_KEY_CODE = pauseButtonKeyCode;
	}
} 
