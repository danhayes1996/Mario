package cameras;

import maths.Vec2;
import maths.Vec4;
import static maths.Maths.*;

public class CameraScroll extends Camera{

	private double xScroll;
	
	public CameraScroll(Vec4 bounds, Vec2 screenDimensions, double scrollSpeed) {
		super(bounds, screenDimensions);
		this.xScroll = scrollSpeed;
	}

	public void tick() {
		x = clamp(x + xScroll, xstart, xend);
	}

}
