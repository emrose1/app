package com.studio.bookings.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode
public class EventItem {
	
	@Getter @Setter
   	private Long id;
	
	@Getter @Setter
   	private String startCode;
	
	@Getter @Setter
   	private Date start;
	
   	@Getter @Setter
   	private Date end;
   	
   	@Getter @Setter
   	private Event event;
   	
   	public EventItem(Long id, Date start, Date end, Event event) {
   		
   		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    this.startCode = formatter.format(start);
   		this.id = id;
   		this.start = start;
   		this.end = end;
   		this.event = event;
   	};

}