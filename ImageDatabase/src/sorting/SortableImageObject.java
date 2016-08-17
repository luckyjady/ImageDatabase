package sorting;

import javax.lang.model.element.QualifiedNameable;

import sorting.Quick.Quickable;
import idb.ImageObject;

public class SortableImageObject extends ImageObject implements Quickable{

	public int value;
	
	public SortableImageObject(ImageObject rawData,ImageObject compareObj){
		super(rawData.colorFeature,rawData.path);
		this.value = Math.abs(compareObj.colorFeature - rawData.colorFeature);
	}
	
	@Override
	public int value() {
		// TODO Auto-generated method stub
		return value;
	}
	
}
