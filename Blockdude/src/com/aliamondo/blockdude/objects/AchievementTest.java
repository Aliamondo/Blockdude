package src.com.aliamondo.blockdude.objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class AchievementTest {
    private static final String achievementText = "test";
    private static final String testIcon = "star_first";

    @Test
    public void testDummyAchievement() {
        Achievement testAchievement = new Achievement();

        assertNotNull(testAchievement);
        assertEquals("", testAchievement.text);
        assertEquals(Achievement.ACHIEVEMENT_SHOW_TIME_LIMIT, testAchievement.t, 0);
        assertTrue(testAchievement.isDummyAchievement);
    }

    @Test
    public void testAchievement() {
        Achievement testAchievement = new Achievement(achievementText, testIcon);

        assertNotNull(testAchievement.bg);
        assertNotNull(testAchievement.font);
        assertNotNull(testAchievement.icon);

        assertEquals(achievementText, testAchievement.text);
        assertEquals(0, testAchievement.t, 0);
        assertFalse(testAchievement.isDummyAchievement);
    }

    @Test
    public void testShowAchievement() {
        Achievement testAchievement = new Achievement(achievementText, testIcon);

        testAchievement.show();
        assertFalse(testAchievement.doneShowing());

        testAchievement.t += Achievement.ACHIEVEMENT_SHOW_TIME_LIMIT; // manually add the time, because GDX is mocked
        testAchievement.show();
        assertTrue(testAchievement.doneShowing());
    }

    @Test
    public void testShowDummyAchievement() {
        Achievement testAchievement = new Achievement();

        testAchievement.show();
        assertTrue(testAchievement.doneShowing());
    }
}
