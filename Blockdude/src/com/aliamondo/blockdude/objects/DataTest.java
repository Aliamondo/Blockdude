package src.com.aliamondo.blockdude.objects;

import org.junit.Test;

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
}
