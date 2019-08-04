package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.files.FileHandle;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class DataTest {

    @Test
    public void testData() {
        Data data = new Data();

        assertEquals(1, data.getLevel());
        assertEquals(0, data.getTotalStars());
        assertTrue(data.isFirstRunEver());
        assertFalse(data.getGameComplete());
    }

    @Test
    public void testAchievementQueue() {
        Data data = new Data();

        Achievement achievement = data.getAchievementFromQueue();
        assertNull(achievement);

        // add same dummy achievement twice
        data.addAchievementInQueue(new Achievement());
        data.addAchievementInQueue(new Achievement());
        achievement = data.getAchievementFromQueue();
        assertNotNull(achievement);
        // no achievement in the queue at the moment
        achievement = data.getAchievementFromQueue();
        assertNull(achievement);
    }

    @Test
    public void testLevels() {
        Data data = new Data();

        assertEquals(1, data.getLevel());
        assertEquals(1, data.getMaxLevel());

        data.setLevel(2);
        assertEquals(2, data.getLevel());
        assertEquals(2, data.getMaxLevel());

        data.setLevel(1);
        assertEquals(1, data.getLevel());
        assertEquals(2, data.getMaxLevel());
    }

    @Test
    public void testStars() {
        Data data = new Data();

        assertEquals(0, data.getTotalStars());
        assertTrue(data.isFirstRunEver());
        assertEquals(0, data.getStarsCollected());

        data.setTotalStars(1);
        assertEquals(1, data.getTotalStars());
        assertFalse(data.isFirstRunEver());
        assertEquals(0, data.getStarsCollected());

        data.setStarsCollected(2);
        assertEquals(1, data.getTotalStars());
        assertEquals(2, data.getStarsCollected());
    }

    @Test
    public void testAchievementQueueShow() {
        Data data = new Data();

        Achievement achievement1 = Mockito.mock(Achievement.class);
        achievement1.text = "test1";
        Achievement achievement2 = Mockito.mock(Achievement.class);
        achievement2.text = "test2";

        // set both achievements to be done showing for instant testing
        Mockito.when(achievement1.doneShowing()).thenReturn(true);
        Mockito.when(achievement2.doneShowing()).thenReturn(true);

        data.addAchievementInQueue(achievement1);
        data.addAchievementInQueue(achievement2);
        data.showNextAchievement();
        data.showNextAchievement();
        assertNull(data.getAchievementFromQueue());
    }

    @Test
    public void testNewDataFileLoad() {
        final int level = 5, totalStars = 10, maxLevel = 15;
        final boolean gameComplete = true;

        Gdx.files = Mockito.mock(Files.class);
        FileHandle testFile = new FileHandle(Data.DATA_FILE);
        testFile.writeString(level + "\n", false);
        testFile.writeString(totalStars + "\n", true);
        testFile.writeString(maxLevel + "\n", true);
        testFile.writeString(gameComplete + "\n", true);

        Mockito.when(Gdx.files.local(Data.DATA_FILE)).thenReturn(testFile);

        Data data = new Data();
        testFile.delete();

        assertEquals(level, data.getLevel());
        assertEquals(totalStars, data.getTotalStars());
        assertEquals(maxLevel, data.getMaxLevel());
        assertFalse(data.isFirstRunEver());
        assertTrue(data.getGameComplete());
    }

    @Test
    public void testWidthHeightConverters() {
        final int height = 100, width = 100;
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getHeight()).thenReturn(height);
        Mockito.when(Gdx.graphics.getWidth()).thenReturn(width);

        Data data = new Data();
        assertEquals(height, data.convertScreenHeight(Data.HEIGHT), 0);
        assertEquals(width, data.convertScreenWidth(Data.WIDTH), 0);
    }

    @Test
    public void testGettersAndSetters() {
        Data data = new Data();

        assertFalse(data.getCheater());
        data.setCheater(true);
        assertTrue(data.getCheater());

        assertFalse(data.getGameComplete());
        data.setGameComplete(true);
        assertTrue(data.getGameComplete());
    }
}
