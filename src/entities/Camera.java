package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 30;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,30,-10);
	private float pitch = 20;
	private float yaw;
	private float roll;
	
	private Player player;
	
	private boolean maxZoom = (distanceFromPlayer<50);
	//private boolean minZoom = (distanceFromPlayer>10);
			
	public Camera(Player player){
		this.player = player;
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float vertDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + vertDistance + 10;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		if(distanceFromPlayer>=10 && distanceFromPlayer<=80){
			distanceFromPlayer -= zoomLevel;
		}else if (distanceFromPlayer < 10){
			distanceFromPlayer = distanceFromPlayer + 0.1f;
		}else if (distanceFromPlayer >50){
			distanceFromPlayer = distanceFromPlayer - 0.1f;
		}
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float pitchChange = Mouse.getDY() * 0.1f;
			if(pitch <= 90 && pitch >= 0) pitch -= pitchChange;
			else if (pitch > 90){
				pitch = pitch - 0.1f;
			}else if (pitch < 10){
				pitch = pitch + 0.1f;
			}
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(1)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}

}
