package com.studio.bookings.util;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Gives us our custom version rather than the standard Objectify one.
 *
 * @author Jeff Schnitzer
 */
public class TestObjectifyService
{
	    public static Objectify ofy() {
	        return ObjectifyService.ofy();
	    }

	    public static ObjectifyFactory factory() {
	        return ObjectifyService.factory();
	    }
}
