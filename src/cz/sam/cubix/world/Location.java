package cz.sam.cubix.world;

public class Location {
	
	private World world;
	
	private float x;
	private float y;
	private float z;
	
	private float yaw;
	private float pitch;
	
	public Location() {
		
	}
	
	public Location(World world, float x, float y, float z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void min(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	
	public void addPitch(float pitch) {
		this.pitch += pitch;
	}
	
	public void addYaw(float yaw) {
		this.yaw += yaw;
	}
	
	public void minPitch(float pitch) {
		this.pitch -= pitch;
	}
	
	public void minYaw(float yaw) {
		this.yaw -= yaw;
	}

	public World getWorld() {
		return world;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Location setX(float x) {
		this.x = x;
		return this;
	}

	public Location setY(float y) {
		this.y = y;
		return this;
	}

	public Location setZ(float z) {
		this.z = z;
		return this;
	}

	public Location setYaw(float yaw) {
		this.yaw = yaw;
		return this;
	}

	public Location setPitch(float pitch) {
		this.pitch = pitch;
		return this;
	}
	
}
