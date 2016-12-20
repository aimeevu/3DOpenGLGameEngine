//Generic shader program containing all the attributes and methods for all shader programs

package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	//Constructor
	public ShaderProgram(String vertexFile, String fragmentFile){
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();//ties the two shaders together
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);//links
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}//end of constructor
	
	
	protected abstract void getAllUniformLocations();
	
	//returns an int that represents the location of the uniform variable
	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programID, uniformName);
	}//end of method
	
	public void start(){
		GL20.glUseProgram(programID);
	}//end of method
	
	public void stop(){
		GL20.glUseProgram(0);
	}//end of method
	
	public void cleanUp(){
		stop(); //stops anything that is running
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}//end of method
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName){
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}//end of method
	
	//loads values to the uniform locations (float, vector, boolean)
	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}//end of method
	protected void loadVector(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}//end of method
	protected void loadBoolean(int location, boolean value){
		float toLoad = 0;//false
		if(value){
			toLoad = 1;//true
		}//end of if
		GL20.glUniform1f(location, toLoad);
	}//end of method
	protected void loadMatrix(int location, Matrix4f matrix){
		matrix.store(matrixBuffer);//stores to the matrixBuffer
		matrixBuffer.flip();//to read 
		GL20.glUniformMatrix4(location, false, matrixBuffer);//loads to the uniform variable
	}//end of method
	
	/*loads shader source code files and read in all the lines and connects them
	 * together. It will compiles it and then return the shaderID*/
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine())!=null){
				shaderSource.append(line).append("\n");
			}//end of while
			reader.close();
		}catch(IOException e){
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}//end of try-catch
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}//end of if
		return shaderID;
	}//end of method
}//end of class
