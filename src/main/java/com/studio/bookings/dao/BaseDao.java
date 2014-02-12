package com.studio.bookings.dao;
import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

//http://stackoverflow.com/questions/21036934/objectify-the-list-from-query-result-contains-null

public class BaseDao<T> {
	
	private Class<T> t;

	public BaseDao(Class<T> t){
		this.t = t;
	}

	public Long save(T e){
		return ofy().save().entity(e).now().getId();
	}
	
	public void delete(Long id)
	{
		ofy().delete().type(t).id(id);
	}

	public List<T> list()
	{
		return ofy().load().type(t).list();
	}
	
	public T retrieve(Long id)
	{
		return ofy().load().type(t).id(id).now();
	}
}