package com.studio.bookings.service;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.EventCategory;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class EventCategoryService extends BaseService {
	
	public static AccessControlListService aclService = new AccessControlListService();
	Permission aclPermission = Permission.CALENDAR;
	
	@ApiMethod(name = "account.insertEventCategory", path="account/{account_id}/eventcategory",  httpMethod = HttpMethod.POST)
	public EventCategory insertEventCategory( 
		@Named("name") String name,  
		@Named("account_id") Long accountId,
		User user) {
		
		EventCategory eventCategory = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowInsert(accountId, aclPermission.toString(), user).get(0)) {
				Account account =  accountDao.retrieve(accountId);
				eventCategory = new EventCategory(name, account);
				eventCategoryDao.save(eventCategory);
    		//}
		//}
	    return eventCategory; 
	}
	
	@ApiMethod(name = "account.getEventCategory", path="account/{account_id}/eventcategory/{id}",  httpMethod = HttpMethod.GET)
	public EventCategory getEventCategory(
		@Named("id") Long eventCategoryId, 
		@Named("account_id") Long accountId,
		User user) {

		EventCategory eventCategory = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowView(accountId, aclPermission.toString(), user).get(0)) {
    			Account account = accountDao.retrieve(accountId);
    			eventCategory = eventCategoryDao.retrieveAncestor(eventCategoryId, account);
    		//}
		//}
		return eventCategory;
	}
	
	@ApiMethod(name = "account.listEventCategorys", path="account/{account_id}/eventcategory",  httpMethod = HttpMethod.GET)
	public List<EventCategory> listEventCategory(
			@Named("account_id") Long accountId,
			User user) {
		
		List<EventCategory> eventCategoryList = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowViewAll(accountId, aclPermission.toString(), user).get(0)) {
    			Account accountFetched = accountDao.retrieve(accountId);
    			eventCategoryList = eventCategoryDao.listAncestors(accountFetched);
    		//}
		//}
		return eventCategoryList;
	}
	
	@ApiMethod(name = "eventCategory.updateEventCategory", path="account/{account_id}/eventcategory/{id}", httpMethod = HttpMethod.PUT)
	public EventCategory updateEventCategory(@Named("id") Long eventCategoryId,  @Named("account_id") Long accountId, 
			@Named("name") String name, User user) {
		EventCategory eventCategory = null;
		//if(user != null) {	
			//if (aclService.allowUpdate(accountId, aclPermission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				eventCategory = eventCategoryDao.retrieveAncestor(eventCategoryId, accountFetched);
				eventCategory.setName(name);
				eventCategoryDao.save(eventCategory);
			//}
		//}
		return eventCategory;
	}
	
	@ApiMethod(name = "eventCategory.removeEventCategory", path="account/{account_id}/eventcategory/{id}", httpMethod = HttpMethod.DELETE)
	public void removeEventCategory(
			@Named("id") String eventCategoryId,
			@Named("account_id") String accountId,
			User user) {
		
		//if(user != null) {	
			//if (aclService.allowDelete(Long.valueOf(accountId), aclPermission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(Long.valueOf(accountId));
				eventCategoryDao.deleteAncestors(Long.valueOf(eventCategoryId), accountFetched);
			//}
		//}
	}

}
