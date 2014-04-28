package com.studio.bookings.dao;
import static com.studio.bookings.util.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;

//http://stackoverflow.com/questions/21036934/objectify-the-list-from-query-result-contains-null

public class ChildBaseDao<T, S> extends BaseDao<T> {
	
	private Class<T> t;
	private Class<S> s;
	

	
	public ChildBaseDao(Class<T> t, Class<S> s){
		this.t = t;
		this.s = s;
	}

	public T retrieveAncestor(Long tId, S e) {
		return (T) ofy().load().type(t).parent(e).id(tId).now();
	}
	
	
	public List<T> listAncestors(S e) {
		return ofy().load().type(t).ancestor(e).list();
	}
	
	public T doubleFilterAncestorQuery(String filter, String value, String filter2, String value2, S e) {
        return ofy().load().type(t).ancestor(e).filter(filter, value).filter(filter2, value2).first().now();

	}
}