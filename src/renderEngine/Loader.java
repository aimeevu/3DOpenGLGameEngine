/*Loads 3D models into memory by storing positional data about the model in VAO*/ 

package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.RawModel;

public class Loader {
	
	/* VAO: array that stores the data (position, color, normal vectors, texture co-ords) 
	 * about a 3d model within the slots called attribute lists (about 16). Has a unique ID.
	 * 
	 * VBO: array of numbers that represent data of position, colors, normals. These can be
	 * stored within the VAO.
	 * 
	 * 3D models will be made from triangles (3 vertices in 3d space). This data is stored in 
	 * the VBO that is in turn stored in the attribute list of the VAO.*/
	
	/*memory management to keep track of the vaos and vbos made in order to delete them*/
	private List<Integer> vaos = new ArrayList<Integer>(); //vertex array objects
	private List<Integer> vbos = new ArrayList<Integer>(); //vertex buffer objects
	private List<Integer> textures = new ArrayList<Integer>();
	
	/* Takes position of the model's vertices, load it into a VAO, and return 
	 * info about the VAO as a rawmodel object*/
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}//end of method
	
	//loads texture into memory and return the ID of the texture for access
	public int loadTexture(String fileName){
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}//end of try-catch
		int textureID = texture.getTextureID();//retrieves ID of the texture
		textures.add(textureID);//adds to the texture list
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		return textureID;
	}//end of method
	
	//loops through the lists and deletes each item in the list
	public void cleanUp(){
		for (int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}//end of method
	
	//Create a new empty VAO and returns the vaoID that is made
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();//creates an empty VAO
		vaos.add(vaoID); //stores it into the vao list
		GL30.glBindVertexArray(vaoID);//activates the VAO
		return vaoID;
	}//end of method
	
	//Take in the number of the attribute list and stores the data from that list
	public void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
		int vboID = GL15.glGenBuffers(); //creates an empty VBO
		vbos.add(vboID);//stores it into the vbo list
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //binds vbo
		
			//Converts the data into floatbuffer
		FloatBuffer buffer = storeDatainFloatBuffer(data);
		
			//stores into the VBO as static data so that it can't be edited once it's stored in the VBO
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
			/* stores the vbo into the vao (number of the atrribute list to store the data, length of each 
			 * vertex (3d model), type of data (float), data is normalized or not, distance between each of
			 * the vertices, offset (beginning of the data))*/
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		
			//unbinds the vbo
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}//end of method
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}//end of method
	
	/* Loads the indices buffer into a new vbo (Index Buffer) and binds it
	 * to a VAO*/
	private void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers(); //creates an empty vbo
		vbos.add(vboID); //adds to list
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);//stores into a buffer
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);//stores into a vbo
		
	}//end of method
	
	//Converts int array to int buffer
	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length); //creates new intbuffer
		buffer.put(data);
		buffer.flip();
		return buffer;
	}//end of method
	
	//Converts float array of data into a float buffer
	private FloatBuffer storeDatainFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();//prepares it to be read from and knows that the writing is done
		return buffer;
	}//end of method

}//end of class
