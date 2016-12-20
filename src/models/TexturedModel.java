//Contains both model and the texture data.

package models;

import textures.ModelTexture;

public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	
	//Constructor that takes in the model and the texture for that model
	public TexturedModel(RawModel model, ModelTexture texture){
		this.rawModel = model;
		this.texture = texture;
	}//end of constructor

	//GETTER METHODS
	public RawModel getRawModel() {
		return rawModel;
	}
	public ModelTexture getTexture() {
		return texture;
	}
}//end of class
