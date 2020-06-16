package com.tms.v1.domain;

import java.io.Serializable;

import com.tms.v1.domain.enumeration.ObjectType;


public class DynamicData  implements Serializable {

	private static final long serialVersionUID = 1L;

	private ObjectType objectType ;
	
	private Object value;

	public DynamicData() {
	}
	
	public DynamicData(ObjectType objectType, Object value) {
		this.objectType = objectType;
		this.value = value;
	}


	public ObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		DynamicData other = (DynamicData) obj;
		if (objectType != other.objectType)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	
	
}
