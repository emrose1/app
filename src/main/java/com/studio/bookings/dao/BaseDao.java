package com.studio.bookings.dao;
import static com.studio.bookings.util.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.AccessControlList;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Application;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;

//http://stackoverflow.com/questions/21036934/objectify-the-list-from-query-result-contains-null

public class BaseDao<T> {

	static{
		ObjectifyService.register(AccessControlList.class);
		ObjectifyService.register(Account.class);
		ObjectifyService.register(Application.class);
		ObjectifyService.register(Calendar.class);
		ObjectifyService.register(Person.class);
	}
	
	private Class<T> t;
	
	public BaseDao(){}

	public BaseDao(Class<T> t){
		this.t = t;
	}

	public Long save(T e){
		return ofy().save().entity(e).now().getId();
	}
	
	public List<Key<T>> save(List<T> e) {		
		Map<Key<T>, T> map = ofy().save().entities(e).now();
		return new ArrayList<Key<T>>(map.keySet());
	}
	
	public void delete(Long id)
	{
		ofy().delete().type(t).id(id).now();
	}

	public List<T> list()
	{
		return ofy().load().type(t).list();
	}
	
	public T retrieve(Long id)
	{
		return ofy().load().type(t).id(id).now();
	}
	

	public T oneFilterQuery(String filter, String value) {
        return ofy().load().type(t).filter(filter, value).first().now();
	}
	
	public T twoFilterQuery(String filter, String value, String filter2, String value2) {
        return ofy().load().type(t).filter(filter, value).filter(filter2, value2).first().now();
	}
	
}