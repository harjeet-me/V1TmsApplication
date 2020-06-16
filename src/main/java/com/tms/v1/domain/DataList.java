package com.tms.v1.domain;

import java.io.Serializable;
import java.util.List;


public class DataList  implements Serializable {

	private static final long serialVersionUID = 1L;
    
	private List<DataObject> dataObjects;

	public List<DataObject> getDataObjects() {
		return dataObjects;
	}

	public void setDataObjects(List<DataObject> dataObjects) {
		this.dataObjects = dataObjects;
	}

	@Override
	public String toString() {
		return "DataList [dataObjects=" + dataObjects + "]";
	}
	
	
	
}
