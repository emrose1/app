package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
import com.studio.bookings.entity.Calendar;
import com.studio.bookings.entity.Person;
import com.studio.bookings.enums.Permission;
import com.studio.bookings.util.Constants;

@Api(
    name = "booking",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)

public class AccountService extends BaseService {

	public static AccessControlListService aclService = new AccessControlListService();
	Permission permission = Permission.ACCOUNT;

	@ApiMethod(name = "account.addAccount", path="calendar.addAccount", httpMethod = "post")
	public Account insertAccount(
			@Named("name") String name,
			@Named("description") String description,			
			@Named("userId") String userId, 
			@Named("username") String username,
			@Named("email") String email,
			@Named("familyname") String family_name,
			@Named("givenname") String given_name,
			@Named("userType") String userType, // needs to be owner
			User user) {
		
		Account account = null;
		
		//if(ofy().load().type(Person.class).filter("userId", userId).filter("userType", "OWNER") == null) { 
			account = new Account(name);
			accountDao.save(account);
			Calendar calendar = new Calendar(description, account);
			calendarDao.save(calendar);
			Person p = new Person(account, user.getUserId(), username, email, family_name, given_name, userType);
			personDao.save(p);
		//}
		return account;
	}
	
	@ApiMethod(name = "account.findAccount", path="calendar.findAccount", httpMethod = "get")
	public Account findAccount(
			@Named("account") Long accountId,
			@Named("accountToFind") Long accountToFindId, 
			User user) {
		Account accountFetched = null;
		// superadmin
		if (user != null && aclService.allowView(accountId, permission.toString(), user).get(0)) {
			accountFetched = accountDao.retrieve(accountToFindId);
		}
		return accountFetched;
	}
	
	@ApiMethod(name = "account.listAccounts", path="account.listAccounts", httpMethod = "get")
	public List<Account> listAccounts(
			@Named("account") Long accountId, 
			User user) {
		// must be superadmin
		List<Account> aclList = null;
		if (user != null) {
	 		if (aclService.allowViewAll(new Long(accountId), permission.toString(), user).get(0)) {
	 			aclList = accountDao.list();
			} 
		}
 		return aclList;
	}
	
	@ApiMethod(name = "account.listTestUser", path="account.listTestUser", httpMethod = "get")
	public List<String> listTestUser(User user) {
		// must be superadmin 
		List<String> str = new ArrayList<String>();
 		if (user != null ) {//&& aclService.allowViewAll(accountId, permission.toString(), user).get(0)) {
 			str.add("user not null" + user.getEmail() + " " + user.getUserId());
		} else {
			str.add("user null" + user.getEmail() + " " + user.getUserId());
		}
 		return str;
	}
	

	@ApiMethod(name = "account.listAccountsWithoutUser", path="account.listAccountsWithoutUser", httpMethod = "get")
	public List<Account> listTestAccountsWithoutUser() {
		// must be superadmin 
		//if (user != null && aclService.allowViewAll(accountId, permission.toString(), user).get(0)) {
			return accountDao.list();
		//} else {
			//return null;
		//}
	}
	
	@ApiMethod(name = "account.updateAccount", path="calendar.updateAccount", httpMethod = "get")
	public Account updateAccount(
			@Named("account") Long accountId,
			@Named("accountToUpdate") Long accountToUpdateId, 
			@Named("name") String name, 
			User user) {
		Account accountFetched = null;
		// TODO create allowUpdateAll for SuperAdmin 
		// otherwise check accountId == accountToUpdateId
		// check if user is owner of this account
		// can be used by: owner, admin or superadmin
		
		if (user != null && aclService.allowUpdate(accountId, permission.toString(), user).get(0)) {
			accountFetched = accountDao.retrieve(accountToUpdateId);
			accountFetched.setName(name);
			accountDao.save(accountFetched);
		}
		return accountFetched;
	}
	
	@ApiMethod(name = "account.deleteAccount", path="calendar.deleteAccount", httpMethod = "get")
	public void deleteAccount(
			@Named("account") Long accountId, 
			@Named("accountDelete") Long accountToDeleteId, 
			User user) {
		// has to be superadmin
		if (user != null && aclService.allowDelete(accountId, permission.toString(), user).get(0)) {
			accountDao.delete(accountToDeleteId);
		}
	}
}
