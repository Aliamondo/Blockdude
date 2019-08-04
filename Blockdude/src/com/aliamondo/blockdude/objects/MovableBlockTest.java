package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import org.junit.runner.RunWith;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class MovableBlockTest {
    @Test
    public void testConstructor() {
        final int testValue = 50;
        MovableBlock block = new MovableBlock(new Vector2(testValue, testValue));

        assertEquals(Block.Type.MOVABLE_BLOCK, block.type);
        assertEquals(testValue, block.position.x, 0);
        assertEquals(testValue, block.position.y, 0);
    }
}
