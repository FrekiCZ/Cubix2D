package cz.sam.cubix.entity;

import cz.sam.cubix.util.Vector2f;
import cz.sam.cubix.util.Vector3f;
import cz.sam.cubix.world.World;

public abstract class Entity {
	
	private static int nextEntityID = -1;
	
	private int id;
	private float x;
	private float y;
	
	private float prewX;
	private float prewY;
	
	private float yaw;
	private float pitch;
	
	private float prewYaw;
	private float prewPitch;
	
	private boolean onGround;
	
	private boolean isCollidedHorizontally;
	private boolean isCollidedVertically;
	private boolean isCollided;
	
	private boolean isDead;
	
	public Entity() {
		this.id = nextEntityID++;
	}
	
	public void update() {
		this.prewX = this.x;
		this.prewY = this.y;
		this.prewYaw = this.yaw;
		this.prewPitch = this.pitch;
	}
	
	public Vector2f getLocation() {
		return new Vector2f(this.x, this.y);
	}
	
	public Vector2f getPrewLocation() {
		return new Vector2f(this.prewX, this.prewY);
	}
	
	public void setLocation(Vector2f location) {
		this.x = location.getX();
		this.y = location.getY();
	}
	
	public float getDistanceToEntitySquared(Entity entity) {
		float x = this.x - entity.getX();
		float y = this.y - entity.getY();
		return x * x + y * y;
	}
	
	public float getDistanceToEntitySquared(float xE, float yE) {
		float x = this.x - xE;
		float y = this.y - yE;
		return x * x + y * y;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getPrewYaw() {
		return prewYaw;
	}

	public float getPrewPitch() {
		return prewPitch;
	}

	public boolean isCollidedHorizontally() {
		return isCollidedHorizontally;
	}

	public boolean isCollidedVertically() {
		return isCollidedVertically;
	}

	public boolean isCollided() {
		return isCollided;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setCollidedHorizontally(boolean isCollidedHorizontally) {
		this.isCollidedHorizontally = isCollidedHorizontally;
	}

	public void setCollidedVertically(boolean isCollidedVertically) {
		this.isCollidedVertically = isCollidedVertically;
	}

	public void setCollided(boolean isCollided) {
		this.isCollided = isCollided;
	}

	public int getEntityId() {
		return this.id;
	}
	
}
