package azureDL.db.util;

import azureDL.db.DataLakeTable;



public class DataLakeTableUtilImpl extends GenericUtilImpl<DataLakeTable> implements DataLakeTableUtil{
	
	public DataLakeTableUtilImpl(){
		super.setEntityClass(DataLakeTable.class);
	}
}
