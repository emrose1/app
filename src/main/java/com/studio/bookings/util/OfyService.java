package com.studio.bookings.util;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.studio.bookings.entity.Event;
import com.studio.bookings.entity.Settings;

public class OfyService {	
    static {
        factory().register(Event.class);
        factory().register(Settings.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}