package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.math.Vector2;
import org.junit.BeforeClass;
import org.junit.Test;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.assertEquals;

public class MovableBlockTest {
    @BeforeClass
    public static void setup() {
        GdxTestRunner.setupTests();
    }

    @Test
    public void testConstructor() {
        final int testValue = 50;
        MovableBlock block = new MovableBlock(new Vector2(testValue, testValue));

        assertEquals(Block.Type.MOVABLE_BLOCK, block.type);
        assertEquals(testValue, block.position.x, 0);
        assertEquals(testValue, block.position.y, 0);
    }
}
