package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class World {

	Array<Block> blocks = new Array<Block>();
	Array<Star> stars = new Array<Star>();

	Dude dude;
	public int HORIZONTAL_TILES = 1;
	public int VERTICAL_TILES = 1;

	public Array<Block> getBlocks() {
		return blocks;
	}

	public Array<Star> getStars() {
		return stars;
	}

	public Dude getDude() {
		return dude;
	}

	public World(int level) {
		createWorld(level);
		//createDemoWorld();
	}

	private void createWorld(int level) {
		String[] contents = new String[1];
		try {
			FileHandle file = Gdx.files.internal( "levels/Level"+level+".txt" );
			contents = file.readString().split("\\r?\\n");
		} catch (Exception e) {
			System.out.println(e.toString());
			if (level <= 1) System.exit(1);
			createWorld(level-1);
			return;
		}
		VERTICAL_TILES = contents.length;
		HORIZONTAL_TILES = contents[0].split(" ").length;
		int j = 1;
		dude = new Dude(new Vector2(0, 0));

		for (String line : contents) {
			String[] items = line.split(" ");
			int i = 0;
			for (String item : items) {
				if (item.equals("1")) {
					blocks.add(new StaticBlock(new Vector2(i, VERTICAL_TILES-j), Block.Type.STATIC_BLOCK));
				} else if (item.equals("2")) {
					blocks.add(new MovableBlock(new Vector2(i, VERTICAL_TILES-j)));
				} else if (item.equals("3")) {
					dude.setPosition(i, VERTICAL_TILES-j);
					dude.setFacingRight(false);
				} else if (item.equals("4")) {
					blocks.add(new StaticBlock(new Vector2(i, VERTICAL_TILES-j), Block.Type.FINISH_BLOCK));
					stars.add(new Star(new Vector2(i, VERTICAL_TILES-j)));
				} else if (item.equals("5")) {
					stars.add(new Star(new Vector2(i, VERTICAL_TILES-j)));
				}
				//System.out.print(item+" ");
				i++;
			}
			//System.out.println();
			j++;
		}

	}

//	private void createDemoWorld() {
//		HORIZONTAL_TILES = 10;
//		VERTICAL_TILES = 6;
//		
//		dude = new Dude(new Vector2(8, 1));
//		dude.setFacingRight(false);
//
//		for (int i=0; i < HORIZONTAL_TILES; i++) {
//			blocks.add(new StaticBlock(new Vector2(i, 0), Block.Type.STATIC_BLOCK));
//			blocks.add(new StaticBlock(new Vector2(i, VERTICAL_TILES-1), Block.Type.STATIC_BLOCK));
//		}
//		for (int i=1; i < VERTICAL_TILES-1; i++) {
//			blocks.add(new StaticBlock(new Vector2(0, i), Block.Type.STATIC_BLOCK));
//			blocks.add(new StaticBlock(new Vector2(HORIZONTAL_TILES-1, i), Block.Type.STATIC_BLOCK));
//		}
//		blocks.add(new StaticBlock(new Vector2(6, 1), Block.Type.STATIC_BLOCK));
//		blocks.add(new StaticBlock(new Vector2(3, 1), Block.Type.STATIC_BLOCK));
//		blocks.add(new StaticBlock(new Vector2(3, 2), Block.Type.STATIC_BLOCK));
//
//		blocks.add(new StaticBlock(new Vector2(1, 1), Block.Type.FINISH_BLOCK));
//
//		blocks.add(new MovableBlock(new Vector2(7, 1)));
//
//		stars.add(new Star(new Vector2(1, 1)));
//		stars.add(new Star(new Vector2(3, 3)));
//		stars.add(new Star(new Vector2(6, 2)));
//		//blocks.add(new StaticBlock(new Vector2(5, 2), Block.Type.STATIC_BLOCK));
//	}
}