package com.courier.commons.entity;

/**
 * 统一定义id的entity基类.
 * 
 * @author Ryan
 */
public abstract class IdEntity extends BaseEntity{
	private static final long serialVersionUID = -4773828905817521539L;

	public abstract void setId(Long id);

}
