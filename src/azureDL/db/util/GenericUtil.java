package azureDL.db.util;

import azureDL.db.GenericInstance;


public interface GenericUtil<DataBean extends GenericInstance> {
	
	public void saveObject(DataBean obj);
	public void updateObject(DataBean obj);

}
