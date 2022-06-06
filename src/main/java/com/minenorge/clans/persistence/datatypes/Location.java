package com.minenorge.clans.persistence.datatypes;

import org.bukkit.Location;

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

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}

	public float getPitch() {
		return this.pitch;
	}

	public float getYaw() {
		return this.yaw;
	}
}