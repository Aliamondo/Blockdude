package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import org.junit.runner.RunWith;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class StaticBlockTest {
    @Test
    public void testConstructor() {
        final int testValue = 50;
        StaticBlock block = new StaticBlock(new Vector2(testValue, testValue), Block.Type.STATIC_BLOCK);

        assertEquals(Block.Type.STATIC_BLOCK, block.type);
        assertEquals(testValue, block.position.x, 0);
        assertEquals(testValue, block.position.y, 0);

        block = new StaticBlock(new Vector2(testValue, testValue), Block.Type.FINISH_BLOCK);

        assertEquals(Block.Type.FINISH_BLOCK, block.type);
        assertEquals(testValue, block.position.x, 0);
        assertEquals(testValue, block.position.y, 0);
    }
}
