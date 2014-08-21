package com.studio.bookings.service;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.EventAttribute;

import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;

@Api(
	    name = "booking",
	    version = "v1",
	    scopes = {Constants.EMAIL_SCOPE},
	    clientIds = {Constants.WEB_CLIENT_ID},
	    audiences = {Constants.ANDROID_AUDIENCE}
	)

public class EventAttributeService extends BaseService {
	
	public static AccessControlListService aclService = new AccessControlListService();
	Permission aclPermission = Permission.CALENDAR;
	
	@ApiMethod(name = "account.insertEventAttribute", path="account/{account_id}/eventattribute",  httpMethod = HttpMethod.POST)
	public EventAttribute insertEventAttribute( 
		@Named("name") String name,  
		@Named("account_id") Long accountId,
		User user) {
		
		EventAttribute eventAttribute = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowInsert(accountId, aclPermission.toString(), user).get(0)) {
				Account account =  accountDao.retrieve(accountId);
				eventAttribute = new EventAttribute(name, account);
				eventAttributeDao.save(eventAttribute);
    		//}
		//}
	    return eventAttribute; 
	}
	
	@ApiMethod(name = "account.getEventAttribute", path="account/{account_id}/eventattribute/{id}",  httpMethod = HttpMethod.GET)
	public EventAttribute getEventAttribute(
		@Named("id") Long eventAttributeId, 
		@Named("account_id") Long accountId,
		User user) {

		EventAttribute eventAttribute = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowView(accountId, aclPermission.toString(), user).get(0)) {
    			Account account = accountDao.retrieve(accountId);
    			eventAttribute = eventAttributeDao.retrieveAncestor(eventAttributeId, account);
    		//}
		//}
		return eventAttribute;
	}
	
	@ApiMethod(name = "account.listEventAttributes", path="account/{account_id}/eventattribute",  httpMethod = HttpMethod.GET)
	public List<EventAttribute> listEventAttribute(
			@Named("account_id") Long accountId,
			User user) {
		
		List<EventAttribute> eventAttributeList = null;
		//if(user != null) { 
    		// TODO THROW UNAUTHORIZED EXCEPTION
    		//if (aclService.allowViewAll(accountId, aclPermission.toString(), user).get(0)) {
    			Account accountFetched = accountDao.retrieve(accountId);
    			eventAttributeList = eventAttributeDao.listAncestors(accountFetched);
    		//}
		//}
		return eventAttributeList;
	}
	
	@ApiMethod(name = "eventAttribute.updateEventAttribute", path="account/{account_id}/eventattribute/{id}", httpMethod = HttpMethod.PUT)
	public EventAttribute updateEventAttribute(@Named("id") Long eventAttributeId,  @Named("account_id") Long accountId, 
			@Named("name") String name, User user) {
		EventAttribute eventAttribute = null;
		//if(user != null) {	
			//if (aclService.allowUpdate(accountId, aclPermission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(accountId);
				eventAttribute = eventAttributeDao.retrieveAncestor(eventAttributeId, accountFetched);
				eventAttribute.setName(name);
				eventAttributeDao.save(eventAttribute);
			//}
		//}
		return eventAttribute;
	}
	
	@ApiMethod(name = "eventAttribute.removeEventAttribute", path="account/{account_id}/eventattribute/{id}", httpMethod = HttpMethod.DELETE)
	public void removeEventAttribute(
			@Named("id") String eventAttributeId,
			@Named("account_id") String accountId,
			User user) {
		
		//if(user != null) {	
			//if (aclService.allowDelete(Long.valueOf(accountId), aclPermission.toString(), user).get(0)) {
				Account accountFetched = accountDao.retrieve(Long.valueOf(accountId));
				eventAttributeDao.deleteAncestors(Long.valueOf(eventAttributeId), accountFetched);
			//}
		//}
	}

}
