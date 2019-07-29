package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class ScreenGestureListener implements GestureListener {

	OrthographicCamera cam;
	float origDistance;
	float origZoom;
	public boolean zooming = false;

	public void setCamera(OrthographicCamera cam) {
		this.cam = cam;
	}

	@Override
	public boolean zoom (float originalDistance, float currentDistance) {
		if(originalDistance != origDistance){
			origDistance = originalDistance;
			origZoom = cam.zoom;
		}
		//System.out.println("origDist: " + originalDistance + ", currDist: " + currentDistance);
		cam.zoom = origZoom*originalDistance/currentDistance;

		if (cam.zoom < 0.5f) cam.zoom = 0.5f;
		else if (cam.zoom > 2.5f) cam.zoom = 2.5f;
		
//		if (cam.zoom < 1) {
//			Vector3 pos0 = new Vector3(Gdx.input.getX(0), Gdx.input.getY(0), 0);
//			Vector3 pos1 = new Vector3(Gdx.input.getX(1), Gdx.input.getY(1), 0);
//			
//			cam.unproject(pos0);
//			cam.unproject(pos1);
//			
//			cam.position.set((pos0.x + pos1.x)/2, (pos0.y + pos1.y + 2)/2, 0);
//			//Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 1);
//		}
		zooming = true;
		return false;
	}

	@Override
	public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
		zooming = false;
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		zooming = false;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		zooming = false;
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		zooming = false;
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		zooming = false;
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		zooming = false;
		return false;
	}
}