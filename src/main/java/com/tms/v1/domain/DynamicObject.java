package com.tms.v1.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class DynamicObject  implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, DynamicData> data ;
	
	public Map<String, DynamicData> getData() {
		return data;
	}

	public void setData(Map<String, DynamicData> data) {
		if(this.data==null) {
			this.data= new HashMap<>();
		}
		this.data = data;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicObject other = (DynamicObject) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DynamicObject [data=" + data + "]";
	}

	
	
	
}
