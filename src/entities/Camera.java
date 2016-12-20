/* Since there isn't a camera that can be moved, the game world itself
 * has to move instead. When a camera is moving to the right, the view
 * is moving to the left. It moves in the opposite direction. This class
 * does not create a camera, but instead, it simulates a camera*/

package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Mouse;

public class Camera {
	
	private int initialX = Mouse.getX();
	private int initialY = Mouse.getY();
	
	private Vector3f position = new Vector3f(0, 10, 0);
	private float pitch;	//rotation around the x, y, and z axis
	private float yaw = 150; 		//how much left or right the camera is aiming
	private float roll;		//how much it is tilted to one side
	public Camera(){
		
	}//end of constructor
	
	//Moves the camera according to the keys pressed
	private boolean moveMouse = true;
	
	public void move(){
		if(moveMouse == false) return;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z -= 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x += 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x -= 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.5f;
		}
		/*
		if(Mouse.getX() > initialX) {
			yaw += .5f;
			initialX = Mouse.getX();
		} else if(Mouse.getX() < initialX) {
			yaw -= .5f;
			initialX = Mouse.getX();
		}
		
		if(!Mouse.isInsideWindow()) {
			moveMouse = false;
			Mouse.setCursorPosition(1270/2, 720/2);
			moveMouse = true;
		}*/
	}//end of method

	
	//GETTER METHODS
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	
}
