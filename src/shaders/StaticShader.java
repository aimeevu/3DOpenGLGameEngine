/* Vertex Shader: executes one time for each vertex in the object that's being rendered.
 * Uses the vertex data from the VAO as the input (positional data: x, y, z coords). It will
 * determine the vertex position on the screen. It will input the data, execute it, and output
 * data that is used in the fragment shader (floats, 3d vectors, matrices, color, etc...).
 * Always an RGB color value. 
 * 
 * Fragment Shader: executes one time for every pixel on the screen. Uses the output of the 
 * vertex shader to determine the final color of the pixel. The color output of the fragment
 * shader is linerally interpolated according to the distance of the vertex and the pixel
 * */

package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram{
	
	/*TEXT FILES (shader isn't programmed in java, it is programmed in GLSL)
	 * vertexShader: (version, inputs, outputs, main method)
	 * in: 3d vector position from the vao
	 * out: color to be outputted
	 * main: where to render the vector on the screen and fills a 4d vector with the 3d data
	 * 		color: determines the color for the vertex.
	 * 
	 * fragementShader: (version, input from vS, output pixel color, main)
	 * color to be rendered with an alpha of 1.0
	 * 
	 * */
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;//data for the transformations
	private int location_projectionMatrix;//data to change the view
	private int location_viewMatrix;//location of the uniform variable (viewMatrix)
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	
	//Constructor
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}//end of constructor
	
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}//end of method

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
	}//end of method
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	//Loads the matrices
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}//end of method
	
	public void loadLight(Light light){
		super.loadVector(location_lightPosition, light.getPosition());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}//end of method
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}//end of method
}//end of class
