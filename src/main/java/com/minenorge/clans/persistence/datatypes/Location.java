package com.minenorge.clans.persistence.datatypes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

	@Column(name="WorldId")
	private UUID worldId;

	@Column(name = "X")
	private double x;

	@Column(name = "Y")
	private double y;

	@Column(name = "Z")
	private double z;

	@Column(name = "Pitch")
    private float pitch;

	@Column(name = "Yaw")
    private float yaw;

	public void setLocation(org.bukkit.Location loc) {
		this.worldId = loc.getWorld().getUID();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		this.pitch = loc.getPitch();
		this.yaw = loc.getYaw();
	}

	public void setWorld(World world) {
		this.worldId = world.getUID();
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public World getWorld() {
		return Bukkit.getWorld(this.worldId);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public float getPitch() {
		return this.pitch;
	}

	public float getYaw() {
		return this.yaw;
	}
}