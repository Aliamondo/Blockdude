package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Block {
    private static final int TEXTURE_WIDTH = 96;
    private static final int TEXTURE_HEIGHT = 96;
    private static final float WIDTH = 1f;
    private static final float HEIGHT = 1f;

    TextureRegion blockTexture;
    Rectangle rect;
    String textureName;
    Type type;
    Vector2 position;

    public enum Type {
        STATIC_BLOCK, FINISH_BLOCK, MOVABLE_BLOCK
    }

    public Block(Vector2 pos, Type type) {
        if (type == Type.STATIC_BLOCK) this.textureName = "block01.png";
        else if (type == Type.FINISH_BLOCK) this.textureName = "door.png";
        else this.textureName = "block.png";

        blockTexture = new TextureRegion(new Texture(Gdx.files.internal(textureName)), 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        this.position = pos;
        this.type = type;
        this.rect = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render(SpriteBatch batch) {
        batch.draw(blockTexture, (getPosition().x) * TEXTURE_WIDTH, getPosition().y * TEXTURE_HEIGHT);
    }

    public void setPosition(Vector2 newPos) {
        this.position = newPos;
        this.rect.setPosition(newPos);
    }

    void setPosition(float x, float y) {
        this.position = new Vector2(x, y);
        this.rect.setPosition(x, y);
    }

    public void dispose() {
        blockTexture.getTexture().dispose();
    }
}