package idb;

public class ImageObject {
	
	public int colorFeature;
	public String path;
	
	/*
	 * herhangi bir resmin color feature ve konumunu tutan adam
	 */
	public ImageObject(int colorFeature, String path) {
		this.colorFeature = colorFeature;
		this.path = path;
	}
	
	public String toString(){
		return colorFeature + "->" + path.substring(path.lastIndexOf("\\") + 1);
	}
}
