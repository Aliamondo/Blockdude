package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Block {
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

        blockTexture = new TextureRegion(new Texture(Gdx.files.internal(textureName)), 0, 0, 96, 96);
        this.position = pos;
        this.type = type;
        this.rect = new Rectangle(position.x, position.y, 1f, 1f);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void render(SpriteBatch batch) {
        batch.draw(blockTexture, (getPosition().x) * 96, getPosition().y * 96);
    }

    public void setPosition(Vector2 newPos) {
        this.position = newPos;
        this.rect.setX(newPos.x);
        this.rect.setY(newPos.y);
    }

    void setPosition(float x, float y) {
        this.position = new Vector2(x, y);
        this.rect.setX(x);
        this.rect.setY(y);
    }

    public void dispose() {
        blockTexture.getTexture().dispose();
    }
}