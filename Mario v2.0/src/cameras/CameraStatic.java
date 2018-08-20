package cameras;

import engine.Engine;
import maths.Vec2;
import maths.Vec4;

public class CameraStatic extends Camera{

	public CameraStatic(Vec2 startpos) {
		super(new Vec4(startpos.x, startpos.y, startpos.x + Engine.IMAGE_WIDTH, startpos.y + Engine.IMAGE_HEIGHT), new Vec2(Engine.IMAGE_WIDTH, Engine.IMAGE_HEIGHT));
	}

	public void tick() {
	}

}
