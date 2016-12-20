//Manages the display

package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	//opens the display when the game is started
	public static void createDisplay(){
		
		ContextAttribs attribs = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try{
				//the size of the display
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
				//creates the display
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Our First Display");
		} catch(LWJGLException e){
			e.printStackTrace();
		}//end of try-catch
		
		//sets where to draw the display (top left and top right of display (whole display))
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}// end of method
	
	//updates the game per frame
	public static void updateDisplay(){
			//synchronizes at a set fps
		Display.sync(FPS_CAP);
		Display.update();
	} // end of method
	
	//closes the display when the game is exited
	public static void closeDisplay(){
		Display.destroy();
	} // end of method
}//end of class
