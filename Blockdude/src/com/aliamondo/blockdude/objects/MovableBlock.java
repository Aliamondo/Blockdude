package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.math.Vector2;

public class MovableBlock extends Block {

	public MovableBlock(Vector2 pos) {
		super(pos, Type.MOVABLE_BLOCK);
	}

}
