/*Main method here*/
package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();//creates the display
		//Objects
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		//ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i = 0; i < 500; i++)
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 ,0,random.nextFloat() * 600),0,0,0,3));
		
		//ModelTexture texture = staticModel.getTexture();
		//texture.setShineDamper(1);
		//texture.setReflectivity(0);
		
		//Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 160, 0, 1);
		
		Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));
		
		Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		
		/*List<Entity> allStalls = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 200; i++){
			float x = random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * - 300;
			allStalls.add(new Entity(staticModel, new Vector3f(x, y, z), random.nextFloat() * 180f,
					random.nextFloat() * 180f, 0f, 1f));
		}*/
		
		MasterRenderer renderer = new MasterRenderer();
		//game loop that exits with the user exits out of the game
		while(!Display.isCloseRequested()){
			//entity.increaseRotation(0, 1, 0);
			camera.move();
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			for(Entity entity: entities){
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();//updates display
		}//end of while
		
		renderer.cleanUp();
		loader.cleanUp(); //deletes the vaos and vbos
		DisplayManager.closeDisplay();
	}//end of main

}//end of class
