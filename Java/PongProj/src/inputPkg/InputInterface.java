/*
 * @author Shai Eisenbud
 */

package inputPkg;

import controllerPkg.InputListener;

public interface InputInterface 
{
	void addInputListener(InputListener input);
	int getPos();
}
