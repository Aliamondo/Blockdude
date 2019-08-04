package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import org.junit.runner.RunWith;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(GdxTestRunner.class)
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

        SpriteBatch batch = new SpriteBatch();
        batch.setProjectionMatrix(new OrthographicCamera().combined);
        batch.begin();

        testBlock.render(batch);

        batch.end();

        assertEquals(1, batch.totalRenderCalls);
        assertEquals(1, batch.maxSpritesInBatch);
        batch.dispose();
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
