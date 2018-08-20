package cameras;

import static maths.Maths.clamp;

import gameobjects.GameObject;
import maths.Vec2;
import maths.Vec4;

public class CameraFollow extends Camera{
	
	private GameObject toFollow;
	
	public CameraFollow(GameObject toFollow, Vec4 bounds, Vec2 screenDimensions) {
		super(bounds, screenDimensions);
		this.toFollow = toFollow;
	}
	
	public void tick() {
		if(toFollow != null) {
			int xx = (int)toFollow.getX() - (sWidth / 2) + (toFollow.getWidth() / 2);
			int yy =  (int)toFollow.getY() - (sHeight / 2) + (toFollow.getHeight() / 2);
			x = clamp(xx, xstart, xend - sWidth);
			y = clamp(yy, ystart, yend - sHeight);
		}
	}

}
