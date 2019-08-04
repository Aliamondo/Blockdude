package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

import src.com.aliamondo.blockdude.objects.Block.Type;
import src.com.aliamondo.blockdude.objects.Dude.State;
import src.com.aliamondo.blockdude.screens.GameScreen;
import src.com.aliamondo.blockdude.screens.LevelCompleteScreen;
import src.com.aliamondo.blockdude.screens.LevelPickerScreen;

public class WorldRenderer {

	private World world;
	private Dude dude;
	private GameScreen gameScreen;
	private OrthographicCamera cam;
	private Block movableBlock;
	private enum Movement{
		IDLE, LEFT, RIGHT, BLOCK_ACTION
	}

	private Movement currentMove = Movement.IDLE;
	private boolean blockActionWasTouched = false;
	private boolean leftActionWasTouched = false;
	private boolean rightActionWasTouched = false;
	private boolean finished = false;
	private boolean firstRun = true;
	private boolean keyZooming = false;
	private boolean backKeyPressed = false;
	private boolean needToStepOnRightBlock = false;
	private boolean needToStepOnLeftBlock = false;

	private Texture restartTexture;
	private Texture rightTexture;
	ScreenGestureListener screenGestureListener = new ScreenGestureListener();

	private int starsCollected = 0;
	private int maximumXForLeft = -1;

	private Achievement achievement = new Achievement();

	SpriteBatch batch;
	SpriteBatch buttonsBatch;

	public WorldRenderer(World world, GameScreen gameScreen) {
		this.world = world;
		this.dude = world.getDude();
		this.gameScreen = gameScreen;
		this.cam = new OrthographicCamera(96*10, 96*6);
		if (world.HORIZONTAL_TILES <= 10 && world.VERTICAL_TILES <=6) {
			this.cam.position.set(96*world.HORIZONTAL_TILES/2, 96*(world.VERTICAL_TILES)/2, 0);
		} else {
			this.cam.position.set(96*(dude.getPosition().x), 96*(dude.getPosition().y+1), 0);
		}
		this.cam.update();
		this.batch = new SpriteBatch();
		this.buttonsBatch = new SpriteBatch();
		//Gdx.input.setCatchMenuKey(true);
		restartTexture = new Texture(Gdx.files.internal("restart.png"));
		rightTexture = new Texture(Gdx.files.internal("right.png"));

		Gdx.input.setInputProcessor(new GestureDetector(screenGestureListener));
		screenGestureListener.setCamera(cam);
	}

	public void checkGravity() {
		//Gravity check
		if ((!checkIfBlockExistsAt((float)Math.floor(dude.position.x), (float)Math.ceil(dude.position.y - 1))) && (!checkIfBlockExistsAt((float)Math.ceil(dude.position.x), (float)Math.ceil(dude.position.y - 1)))){
			//System.out.println("falling down...");
			dude.setPosition(dude.getPosition().x, dude.getPosition().y - 10 * Gdx.graphics.getDeltaTime());
		}
		if (movableBlock != null && dude.getState() == State.WITHOUT_BLOCK) {
			if ((!checkIfBlockExistsAt((float)Math.floor(movableBlock.position.x), (float)Math.ceil(movableBlock.position.y - 1))) && (!checkIfBlockExistsAt((float)Math.ceil(movableBlock.position.x), (float)Math.ceil(movableBlock.position.y - 1)))){
				//System.out.println("block is falling down...");
				movableBlock.setPosition(movableBlock.getPosition().x, movableBlock.getPosition().y - 10 * Gdx.graphics.getDeltaTime());
			}
		}
	}

	public boolean checkCollisions() {
		if (dude.getPosition().x < 0) dude.getPosition().x = 0;
		if (dude.getPosition().x > world.HORIZONTAL_TILES - 1) dude.getPosition().x = world.HORIZONTAL_TILES - 1;
		if (dude.getPosition().y < 0) dude.getPosition().y = 0;
		if (dude.getPosition().y > world.VERTICAL_TILES - 1) dude.getPosition().y = world.VERTICAL_TILES - 1;
		if (dude.getPosition().x % 1 >= 0.945) dude.position.x = (float)Math.ceil(dude.position.x);
		if (dude.getPosition().x % 1 <= 0.055) dude.position.x = (float)Math.floor(dude.position.x);

		checkGravity();
		//		achievement.show();
		gameScreen.getData().showNextAchievement();

		for (Star star : world.getStars()) {
			if (Intersector.overlapRectangles(dude.rect, star.rect)) {
				if (star.isVisible()) {
					starsCollected++;
					gameScreen.getData().setStarsCollected(starsCollected);
					if (gameScreen.getData().getTotalStars() + starsCollected == 1) {
						achievement = new Achievement("First star collected!", "star_first");
						gameScreen.getData().addAchievementInQueue(achievement);
					}
					else if ((gameScreen.getData().getTotalStars() + starsCollected) % 5 == 0) {
						achievement = new Achievement((gameScreen.getData().getTotalStars() + starsCollected) + " stars collected!", "star_2");
						gameScreen.getData().addAchievementInQueue(achievement);
					}
					star.setVisibility(false);
				}
			}
		}

		for (Block block : world.getBlocks()) {
			if (Intersector.overlapRectangles(dude.rect, block.rect)) {
				if (block.type == Type.FINISH_BLOCK && !finished) {
					//					System.out.println("Level won!");
					//					System.out.println("Stars collected: " + starsCollected);

					finished = true;
					winGame();

					return true;
					//Gdx.app.exit();
				}
				else if (dude.isFacingRight()) {
					if (dude.position.x < block.position.x && dude.position.y == block.position.y) {
						dude.setPosition(block.position.x - 1, dude.position.y);
					}
					else if (specialRound(dude.position.y, 0.05f) > block.position.y && block.position.x == (Math.round(dude.position.x))) {
						dude.setPosition(dude.position.x, block.position.y+1);
					}
					else if (specialRound(dude.position.y, 0.05f) > block.position.y) {
						dude.setPosition(dude.position.x, block.position.y);
					}
					//					else if (dude.position.y < block.position.y) {
					//						dude.setPosition(dude.position.x, block.position.y - 1);
					//					}
				}
				else {
					if (dude.position.x > block.position.x && dude.position.y == block.position.y) {
						dude.setPosition(block.position.x + 1, dude.position.y);
					}
					else if (specialRound(dude.position.y, 0.05f) > block.position.y && (block.position.x == Math.round(dude.position.x))) {
						maximumXForLeft = (int) block.position.x;
						//dude.setPosition(dude.position.x, block.position.y+1);
					}
					else if (specialRound(dude.position.y, 0.05f) > block.position.y && block.position.x == (Math.round(dude.position.x-1))) {
						maximumXForLeft = (int) block.position.x;
						//dude.setPosition(dude.position.x, block.position.y);
					}
					//										else if (dude.position.y < block.position.y) {
					//											System.out.println("wtf yo");
					//											dude.setPosition(dude.position.x, block.position.y - 1);
					//										}
				}
			}
			if (movableBlock != null) {
				if (Intersector.overlapRectangles(movableBlock.rect, block.rect)) {
					if (block.type == Type.FINISH_BLOCK) {
						//System.out.println("This is SO stupid, but whatever...");
						return false;
					}
					else if (movableBlock.position.y > block.position.y) {
						movableBlock.setPosition(movableBlock.position.x, block.position.y + 1);
					}
				}
			}
		}
		if (maximumXForLeft >= 0) {
			Block block = getBlock(maximumXForLeft, (int)dude.position.y);
			if (specialRound(dude.position.y, 0.05f) > block.position.y && (block.position.x == Math.round(dude.position.x))) {
				dude.setPosition(dude.position.x, block.position.y+1);
			}
			else if (specialRound(dude.position.y, 0.05f) > block.position.y && block.position.x == (Math.round(dude.position.x-1))) {
				dude.setPosition(dude.position.x, block.position.y);
			}
		}
		maximumXForLeft = -1;
		return false;
	}

	public float specialRound(float x, float min) {
		if (x%1 < min) return (float)Math.round(x);
		else return (float)Math.ceil(x);
	}

	public Block checkIfMovableBlockIsAvailable() {
		for (Block block : world.getBlocks()) {
			if (block.type == Type.MOVABLE_BLOCK) {
				//System.out.println("Dude.          x: " + dude.position.x + ", y: " + dude.position.y);
				//System.out.println("Movable block. x: " + block.position.x + ", y: " + block.position.y);
				if (dude.isFacingRight()) {
					if (block.position.x == (specialRound(dude.position.x, 0.5f) + 1) && (float)Math.ceil(block.position.y) == dude.position.y) {
						if (!checkIfBlockExistsAt(block.position.x, block.position.y + 1)) {
							return block;
						}
					}
				}
				else {
					if (block.position.x == (specialRound(dude.position.x, 0.5f) - 1) && (float)Math.ceil(block.position.y) == dude.position.y) {
						if (!checkIfBlockExistsAt(block.position.x, block.position.y + 1)) {
							return block;
						}
					}
				}
			}
		}
		return null;
	}

	public Block getBlock(int x, int y) {
		for (Block block : world.getBlocks()) {
			if (block.position.x == x && block.position.y == y) {
				return block;
			}
		}
		return null;
	}

	public boolean checkIfBlockExistsAt(float x, float y) {
		for (Block block : world.getBlocks()) {
			if (block.position.x == x && block.position.y == y && block.type != Type.FINISH_BLOCK) {
				return true;
			}
		}
		//System.out.println("No block found beneath me");
		return false;
	}

	public boolean checkIfStaticBlockExistsAt(float x, float y) {
		for (Block block : world.getBlocks()) {
			if (block.position.x == x && block.position.y == y && block.type != Type.FINISH_BLOCK && block.type != Type.MOVABLE_BLOCK) {
				return true;
			}
		}
		//System.out.println("No block found beneath me");
		return false;
	}

	public void restart() {
		buttonsBatch.dispose();
		batch.dispose();
		dispose();
		gameScreen.show();

	}

	public boolean checkAndroidZooming() {
		return screenGestureListener.zooming;
	}

	public void winGame() {
		finished = true;
		FileHandle file = Gdx.files.local( "data.txt" );
		gameScreen.getData().setLevel(gameScreen.getData().getLevel() + 1);
		file.writeString(gameScreen.getData().getLevel()+"\n", false);
		file.writeString((gameScreen.getData().getTotalStars()+starsCollected)+"\n", true);
		file.writeString((gameScreen.getData().getMaxLevel())+"\n", true);
		file.writeString(gameScreen.getData().getGameComplete() + "\n", true);
		gameScreen.getData().setStarsCollected(starsCollected);
		gameScreen.getData().setTotalStars(gameScreen.getData().getTotalStars() + starsCollected);

		dispose();
		gameScreen.getGame().setScreen(new LevelCompleteScreen(gameScreen.getGame(), gameScreen.getData(), gameScreen));
	}

	public void render() {
		batch.begin();
		batch.setProjectionMatrix(cam.combined);

		for (Block block : world.getBlocks()) {
			//block.render(batch, world.HORIZONTAL_TILES, world.VERTICAL_TILES);
			block.render(batch);
		}

		for (Star star : world.getStars()) {
			if (star.isVisible()) {
				star.render(batch, world.HORIZONTAL_TILES, world.VERTICAL_TILES);
			}
		}

		dude.render(batch);
		
		//		batch.draw(restartTexture, cam.position.x + 325*cam.zoom, cam.position.y + 150*cam.zoom, 96*cam.zoom,96*cam.zoom);
		//		batch.draw(rightTexture, cam.position.x - 325*cam.zoom, cam.position.y + 150*cam.zoom, -96*cam.zoom,96*cam.zoom);
		batch.end();
		
		buttonsBatch.begin();
		buttonsBatch.draw(restartTexture, convertScreenWidth(400 + 270), convertScreenHeight(240 + 125), convertScreenWidth(80), convertScreenHeight(80));
		buttonsBatch.draw(rightTexture, convertScreenWidth(400 - 270), convertScreenHeight(240 + 125), convertScreenWidth(-80), convertScreenHeight(80));
		buttonsBatch.end();
		
		if (firstRun) {
			firstRun = false;
			if (gameScreen.getData().isFirstRunEver()) {
				achievement = new Achievement("It all starts here!", "dude_1");
				gameScreen.getData().addAchievementInQueue(achievement);
			}
			if (!Gdx.input.justTouched() && !Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
				return;
			}
		}

		if(Gdx.input.isTouched() && !screenGestureListener.zooming) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(touchPos);
			//System.out.println("touch.x: " + touchPos.x + ", touch.y: " + touchPos.y);
			if (touchPos.x >= cam.position.x+325*cam.zoom && touchPos.x <= cam.position.x + (325+96) *cam.zoom) {
				if (touchPos.y >= cam.position.y+150*cam.zoom && touchPos.y <= cam.position.y+(150+96)*cam.zoom) {
					restart();
					return;
				}
				else {
					currentMove = Movement.RIGHT;
				}
				//System.out.println("touch.y: " + touchPos.y + ", cam.y: " + cam.position.y+150);
			}
			else if (touchPos.x >= cam.position.x-(325+96)*cam.zoom && touchPos.x <= cam.position.x - (325) *cam.zoom) {
				if (touchPos.y >= cam.position.y+150*cam.zoom && touchPos.y <= cam.position.y+(150+96)*cam.zoom) {
					gameScreen.getGame().setScreen(new LevelPickerScreen(gameScreen.getGame(), gameScreen.getData(), gameScreen));
					return;
				}
				else {
					currentMove = Movement.LEFT;
				}
				//System.out.println("touch.y: " + touchPos.y + ", cam.y: " + cam.position.y+150);
			}
			else if (touchPos.x < cam.position.x-250*cam.zoom) {
				currentMove = Movement.LEFT;
			}
			else if (touchPos.x > cam.position.x+250*cam.zoom) {
				currentMove = Movement.RIGHT;
			}
			else if (touchPos.x >= cam.position.x-250*cam.zoom && touchPos.x <= cam.position.x+250*cam.zoom) {
				currentMove = Movement.BLOCK_ACTION;
			}
		}

		if(Gdx.input.isKeyPressed(Keys.LEFT) || currentMove == Movement.LEFT) {
			if (dude.isFacingRight() == false) {
				keyZooming = false;
				screenGestureListener.zooming = false;

				//dude.setFacingRight(false);
				currentMove = Movement.IDLE;

				if (checkIfBlockExistsAt(dude.position.x - 1, dude.position.y)) {
					if (!checkIfBlockExistsAt(dude.position.x - 1, dude.position.y + 1) && !checkIfStaticBlockExistsAt(dude.position.x, dude.position.y + 1)) {
						if (needToStepOnLeftBlock) {
							dude.setPosition(dude.position.x - 0.25f, dude.position.y + 1);
							needToStepOnLeftBlock = false;
						}
						else {
							needToStepOnLeftBlock = true;
						}
					}
				}

				//If there is a block at next X and Y+1
				if (dude.getState() == State.WITH_BLOCK) {
					if (checkIfBlockExistsAt(dude.position.x - 1, dude.position.y + 1) && !checkIfBlockExistsAt(dude.position.x - 1, dude.position.y)) {
						dude.setState(State.WITHOUT_BLOCK);
						movableBlock.setPosition(dude.position);
						dude.setPosition(dude.position.x - 1, dude.position.y);
					}
				}

				dude.setPosition(dude.getPosition().x - dude.SPEED * Gdx.graphics.getDeltaTime(), dude.getPosition().y);
				if (checkCollisions()) return;

				if (dude.getState() == State.WITH_BLOCK) {
					movableBlock.setPosition(dude.position.x, dude.position.y + 1);
				}
			}
			else {
				leftActionWasTouched = true;
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT) || currentMove == Movement.RIGHT) {
			if (dude.isFacingRight()) {
				keyZooming = false;
				screenGestureListener.zooming = false;

				//dude.setFacingRight(true);
				currentMove = Movement.IDLE;

				if (checkIfBlockExistsAt(dude.position.x + 1, dude.position.y)) {
					if (!checkIfBlockExistsAt(dude.position.x + 1, dude.position.y + 1) && !checkIfStaticBlockExistsAt(dude.position.x, dude.position.y + 1)) {
						if (needToStepOnRightBlock) {
							dude.setPosition(dude.position.x + 0.25f, dude.position.y + 1);
							needToStepOnRightBlock = false;
						}
						else {
							needToStepOnRightBlock = true;
						}
					}
				}

				if (dude.getState() == State.WITH_BLOCK) {
					if (checkIfBlockExistsAt(dude.position.x + 1, dude.position.y + 1) && !checkIfBlockExistsAt(dude.position.x + 1, dude.position.y)) {
						dude.setState(State.WITHOUT_BLOCK);
						movableBlock.setPosition(dude.position);
						dude.setPosition(dude.position.x + 1, dude.position.y);
					}
				}

				dude.setPosition(dude.getPosition().x + dude.SPEED * Gdx.graphics.getDeltaTime(), dude.getPosition().y);
				if (checkCollisions()) return;


				if (dude.getState() == State.WITH_BLOCK) {
					movableBlock.setPosition(dude.position.x, dude.position.y + 1);
				}
			}
			else {
				rightActionWasTouched = true;
			}
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN) || currentMove == Movement.BLOCK_ACTION) {
			//dude.setFacingRight(true);
			//dude.setPosition(dude.getPosition().x, dude.getPosition().y - 10 * Gdx.graphics.getDeltaTime());
			blockActionWasTouched = true;
		}
		else if(Gdx.input.isKeyPressed(Keys.Z)) {
			if (cam.zoom > 0.5) {
				cam.zoom-=0.05;
			}
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

			cam.unproject(pos);
			cam.position.set(pos);
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 1);

			keyZooming = true;
		}
		else if(Gdx.input.isKeyPressed(Keys.X)) {
			if (cam.zoom < 2.5) {
				cam.zoom+=0.05;
			}
			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

			cam.unproject(pos);
			cam.position.set(pos);
			Gdx.input.setCursorPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + 1);

			keyZooming = true;
		}
		else if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			backKeyPressed = true;
		}
		else if(Gdx.input.isKeyPressed(Keys.R)) {
			restart();
			return;
		}

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
				gameScreen.getData().setCheater(true);
				winGame();
				return;
			}
		}

		cam.update();


		if (backKeyPressed && !Gdx.input.isKeyPressed(Keys.BACK) && !Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			gameScreen.getGame().setScreen(new LevelPickerScreen(gameScreen.getGame(), gameScreen.getData(), gameScreen));
			return;
		}

		if (Gdx.input.isTouched() == false) {
			boolean nothingChanged = false;
			if (blockActionWasTouched && Gdx.input.isKeyPressed(Keys.DOWN) == false) {//we pressed a block action button and released it
				if (dude.getState() == State.WITHOUT_BLOCK && !checkIfBlockExistsAt(dude.position.x, dude.position.y + 1)) {
					//					this.movableBlock = checkIfBlockAvailable();
					this.movableBlock = checkIfMovableBlockIsAvailable();
					if (movableBlock != null) {
						movableBlock.setPosition(dude.position.x, dude.position.y + 1);
						dude.setState(State.WITH_BLOCK);
					}
					currentMove = Movement.IDLE;
				}
				else if (dude.getState() == State.WITH_BLOCK) {//with block
					if (dude.isFacingRight()) {
						if (checkIfBlockExistsAt(dude.position.x + 1, dude.position.y) && !checkIfBlockExistsAt(dude.position.x + 1, dude.position.y + 1)) {
							//movableBlock.setPosition(dude.position.x + 1,dude.position.y + 1);
							movableBlock.setPosition((float)Math.floor(dude.position.x + 1),dude.position.y + 1);
							dude.setPosition(movableBlock.position.x - 1, dude.position.y);
						}
						else if (!checkIfBlockExistsAt(dude.position.x + 1, dude.position.y) && !checkIfBlockExistsAt(dude.position.x + 1, dude.position.y + 1)) {
							//movableBlock.setPosition(dude.position.x + 1,dude.position.y);
							//System.out.println("Dude: right, NO BLOCK IN FRONT");
							movableBlock.setPosition((float)Math.floor(dude.position.x + 1),dude.position.y);
							dude.setPosition(movableBlock.position.x - 1, dude.position.y);
						}
						else {
							nothingChanged = true;
						}
					}
					else {
						if (checkIfBlockExistsAt(dude.position.x - 1, dude.position.y) && !checkIfBlockExistsAt(dude.position.x - 1, dude.position.y + 1)) {
							movableBlock.setPosition((float)Math.ceil(dude.position.x - 1),dude.position.y + 1);
							dude.setPosition(movableBlock.position.x + 1, dude.position.y);
						}
						else if (!checkIfBlockExistsAt(dude.position.x - 1, dude.position.y) && !checkIfBlockExistsAt(dude.position.x - 1, dude.position.y + 1)) {
							//movableBlock.setPosition(dude.position.x - 1,dude.position.y);
							movableBlock.setPosition((float)Math.ceil(dude.position.x - 1),dude.position.y);
							dude.setPosition(movableBlock.position.x + 1, dude.position.y);
						}
						else {
							nothingChanged = true;
						}
					}
					if (!nothingChanged) dude.setState(State.WITHOUT_BLOCK);
					currentMove = Movement.IDLE;
				}

				blockActionWasTouched = false;
			}
			else if(leftActionWasTouched && Gdx.input.isKeyPressed(Keys.LEFT) == false) {
				currentMove = Movement.IDLE;
				dude.setFacingRight(false);
				leftActionWasTouched = false;
			}
			else if(rightActionWasTouched && Gdx.input.isKeyPressed(Keys.RIGHT) == false) {
				currentMove = Movement.IDLE;
				dude.setFacingRight(true);
				rightActionWasTouched = false;
			}
		}

		//check if dude is floating in the air
		//		if (dude.isFacingRight()) {
		//			if (!checkIfBlockExistsAt(dude.position.x, dude.position.y-1)) {
		//				dude.setPosition(dude.getPosition().x, dude.getPosition().y - 10 * Gdx.graphics.getDeltaTime());
		//				if (dude.getState() == State.WITH_BLOCK) {
		//					movableBlock.setPosition(dude.position.x, dude.position.y + 1);
		//				}
		//				//System.out.println("I BELIEVE I CAN FLY!!!");
		//			}
		//		}
		//		else {
		//			if (!checkIfBlockExistsAt(dude.position.x, dude.position.y-1)) {
		//				dude.setPosition(dude.getPosition().x, dude.getPosition().y - 10 * Gdx.graphics.getDeltaTime());
		//				if (dude.getState() == State.WITH_BLOCK) {
		//					movableBlock.setPosition(dude.position.x, dude.position.y + 1);
		//				}
		//				System.out.println("I KNOOOOOOOOW I CAN FLY!!!");
		//			}
		//		}

		//Checking so that movable block is over dude
		if (dude.getState() == State.WITH_BLOCK) {
			movableBlock.setPosition(dude.position.x, dude.position.y + 1);
		}

		//		if (!checkIfBlockExistsAt(Math.round(dude.position.x), (float) Math.ceil(dude.position.y-1))) {
		//			dude.setPosition(dude.getPosition().x, dude.getPosition().y - 10 * Gdx.graphics.getDeltaTime());
		//		}
		if (checkCollisions()) return;

		//		if(dude.getPosition().x <= world.HORIZONTAL_TILES - 5 && dude.getPosition().x >= 5) {
		//			cam.position.set(dude.getPosition().x * 96, cam.position.y, 0);
		//		}
		//		if(dude.getPosition().y <= world.VERTICAL_TILES - 4 && dude.getPosition().y >= 2) {
		//			cam.position.set(cam.position.x, (dude.getPosition().y+1) * 96, 0);
		//		}
		if ((world.HORIZONTAL_TILES > 10 && world.VERTICAL_TILES > 6 && !keyZooming && !checkAndroidZooming()) || cam.zoom < 1 && !keyZooming && !checkAndroidZooming()) {
			cam.position.set(dude.getPosition().x*96, (dude.getPosition().y+1)*96, 0);
		}
	}

	public void dispose() {
		for (Star star:world.getStars()) {
			star.dispose();
			star = null;
		}
		world.stars = null;
		for (Block block:world.getBlocks()) {
			block.dispose();
			block = null;
		}
		world.blocks = null;
		dude.dispose();
		dude = null;
		restartTexture.dispose();
		rightTexture.dispose();
		achievement = new Achievement();
		screenGestureListener = null;
		cam = null;
	}
	
	public float convertScreenWidth(float x) {
		return (x / 800) * Gdx.graphics.getWidth();
	}
	public float convertScreenHeight(float y) {
		return (y / 480) * Gdx.graphics.getHeight();
	}
}