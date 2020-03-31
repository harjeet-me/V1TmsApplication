package com.tms.v1.domain;

import java.io.Serializable;


public class IdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	public IdEntity() {
	}

	public IdEntity(Long id) {
		this.id=id;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
