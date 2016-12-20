/*Represents 3D model stored in memory*/

package models;

public class RawModel {

	//To know the unique ID of the VAO location and the number of vertices
	private int vaoID;
	private int vertexCount;
	
	//Constructor
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}//end of constructor
	
	//GETTER METHODS
	public int getVaoID() {
		return vaoID;
	}
	public int getVertexCount() {
		return vertexCount;
	}
}//end of class
