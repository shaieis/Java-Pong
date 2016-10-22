/*
 * @author Shai Eisenbud
 */

package controllerPkg;

import inputPkg.InputInterface;

public interface InputListener 
{

	void inputPos(InputInterface caller, int pos);
	void pause(InputInterface caller);
	void exit(InputInterface caller);
}
