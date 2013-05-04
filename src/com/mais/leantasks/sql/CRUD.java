package com.mais.leantasks.sql;

import java.util.List;

public interface CRUD {
	
	public long create(Object object);			// CREATE
	
	public Object select(long id);				// READ
	public List<?> select(String where);
	public List<?> selectAll();
	
	public void update(Object object);			// UPDATE
	
	public void delete(Object object);			// DELETE
	public void delete(long id);
	

}
