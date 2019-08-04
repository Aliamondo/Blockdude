package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BlockTest {
    public static class BlockTestType extends Block {
        private String expectedTextureName;

        BlockTestType(Block.Type type, String expectedTextureName) {
            super(new Vector2(), type);

            this.expectedTextureName = expectedTextureName + ".png";
        }

        String getExpectedTextureName() {
            return expectedTextureName;
        }
    }

    @BeforeClass
    public static void setup() {
        GdxTestRunner.setupTests();
    }

    @Test
    public void testBlock() {
        BlockTestType[] tests = new BlockTestType[]{
                new BlockTestType(Block.Type.STATIC_BLOCK, "block01"),
                new BlockTestType(Block.Type.FINISH_BLOCK, "door"),
                new BlockTestType(Block.Type.MOVABLE_BLOCK, "block"),
        };

        for (BlockTestType testBlock : tests) {
            assertEquals(testBlock.getExpectedTextureName(), testBlock.textureName);
        }
    }

    @Test
    public void testGetPosition() {
        BlockTestType testBlock = new BlockTestType(Block.Type.STATIC_BLOCK, "");

        assertEquals(new Vector2(), testBlock.getPosition());
    }

    @Test
    public void testRender() {
        BlockTestType testBlock = new BlockTestType(Block.Type.STATIC_BLOCK, "");
        testBlock.setPosition(1, 1);

        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        testBlock.render(batch);
        Mockito.verify(batch, Mockito.times(1)).draw(Mockito.any(TextureRegion.class), Mockito.anyFloat(), Mockito.anyFloat());
    }

    @Test
    public void testSetPositionVector2() {
        BlockTestType testBlock = new BlockTestType(Block.Type.STATIC_BLOCK, "");
        testBlock.setPosition(new Vector2(100, 100));

        assertEquals(new Vector2(100, 100), testBlock.getPosition());
    }

    @Test
    public void testSetPositionFloatFloat() {
        BlockTestType testBlock = new BlockTestType(Block.Type.STATIC_BLOCK, "");
        testBlock.setPosition(100, 100);

        assertEquals(new Vector2(100, 100), testBlock.getPosition());
    }

    @Test
    public void testDispose() {
        BlockTestType testBlock = new BlockTestType(Block.Type.STATIC_BLOCK, "");

        testBlock.dispose(); // the texture should be deleted from managed assets, but the object should still exist
        assertNotNull(testBlock.blockTexture.getTexture());
    }

}
