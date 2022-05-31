package com.minenorge.clans.persistence.datatypes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

	@Column(name = "X")
	private float x;

	@Column(name = "Y")
	private float y;

	@Column(name = "Z")
	private float z;

	@Column(name = "Pitch")
    private float pitch;

	@Column(name = "Yaw")
    private float yaw;
}