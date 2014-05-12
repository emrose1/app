package com.studio.bookings.entity;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Application {

	@Getter @Setter
	@Id Long id;
}
