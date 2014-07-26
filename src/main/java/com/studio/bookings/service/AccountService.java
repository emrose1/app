package com.studio.bookings.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.studio.bookings.entity.Account;
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
	
	
	//////////----------------------------//////////////
	////////// --------- CRUD ------------//////////////
	//////////----------------------------/////////////

	@ApiMethod(name = "account.insertAccount", path="account")
	public Account insertAccount(
			@Named("name") String name,
			User user) {
		Account account = null;
		
		//if(ofy().load().type(Person.class).filter("userId", userId).filter("userType", "OWNER") == null) { 
			account = new Account(name);
			accountDao.save(account);
		//}
		return account;
	}
	
	@ApiMethod(name = "account.getAccount", path="account/{id}", httpMethod = HttpMethod.GET)
	public Account getAccount(
			@Named("id") Long accountId, 
			User user) {
		Account accountFetched = null;
		// superadmin
		//if (user != null && aclService.allowView(accountId, permission.toString(), user).get(0)) {
			accountFetched = accountDao.retrieve(accountId);
		//}
		return accountFetched;
	}
	
	@ApiMethod(name = "account.listAccounts", path="account", httpMethod = HttpMethod.GET)
	public List<Account> listAccounts(User user) {
		// TODO create allowDeleteAll for SuperAdmin 
		List<Account> aclList = null;
		if(accountDao.list().size() == 0) {
			DummySetupService ds = new DummySetupService();
			List<Account> dus = ds.dummyUsers();
		}
		//if (user != null) {
	 		//if (aclService.allowViewAll(new Long(accountId), permission.toString(), user).get(0)) {
	 			aclList = accountDao.list();
			//} 
		//}
 		return aclList;
	}
	
	@ApiMethod(name = "account.updateAccount", path="account/{id}", httpMethod = HttpMethod.PUT)
	public Account updateAccount(
			@Named("id") String accountId,
			@Named("name") String name, 
			User user) {
		Account accountFetched = null;
		// TODO create allowUpdateAll for SuperAdmin 
		//if (user != null && aclService.allowUpdateAll(permission.toString(), user).get(0)) {
			accountFetched = accountDao.retrieve(Long.valueOf(accountId));
			accountFetched.setName(name);
			accountDao.save(accountFetched);
		//}
		return accountFetched;
	}
	
	@ApiMethod(name = "account.removeAccount", path="account/{id}", httpMethod = HttpMethod.DELETE)
	public void removeAccount(
			@Named("id") String accountId, 
			User user) throws UnauthorizedException {
		// TODO create allowDeleteAll for SuperAdmin 
		//if (user != null && aclService.allowDeleteAll(permission.toString(), user).get(0)) {
 			accountDao.delete(new Long(accountId));
 		//} else {
			//throw new UnauthorizedException("You are not authorized.");
		//}
	}
	
	//////////----------------------------//////////////
	////////// ---- HELPER METHODS -------//////////////
	//////////----------------------------/////////////
	
	@ApiMethod(name = "account.listAccountsWithoutUser", path="account.listAccountsWithoutUser", httpMethod = HttpMethod.GET)
	public List<Account> listTestAccountsWithoutUser() {
		// must be superadmin 
		//if (user != null && aclService.allowViewAll(accountId, permission.toString(), user).get(0)) {
			return accountDao.list();
		//} else {
			//return null;
		//}
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
}
