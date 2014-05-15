package com.studio.bookings.dao;
import static com.studio.bookings.util.OfyService.ofy;

import java.util.List;

import com.studio.bookings.entity.Application;

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
	
	public T oneFilterAncestorQuery(String filter, String value, S e) {
        return ofy().load().type(t).ancestor(e).filter(filter, value).first().now();

	}
	
	public T twoFilterAncestorQuery(String filter, String value, String filter2, String value2, S e) {
        return ofy().load().type(t).ancestor(e).filter(filter, value).filter(filter2, value2).first().now();
	}
	
	
	public void deleteAncestors(List<Long> aclIds, S e) {
		ofy().delete().type(t).parent(e).ids(aclIds);
	}

}