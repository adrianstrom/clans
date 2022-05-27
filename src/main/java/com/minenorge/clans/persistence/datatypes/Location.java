package com.minenorge.clans.persistence.datatypes;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

	private float x;

	private float y;

	private float z;

    private float pitch;

    private float yaw;
}