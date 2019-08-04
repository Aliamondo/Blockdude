package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import src.com.aliamondo.blockdude.GdxTestRunner;

import static org.junit.Assert.*;

public class AchievementTest {
    private static final String achievementText = "test";
    private static final String testIcon = "star_first";

    @BeforeClass
    public static void setup() {
        GdxTestRunner.setupTests();
    }

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
        testAchievement.batch = Mockito.mock(SpriteBatch.class);
        testAchievement.setTesting();

        testAchievement.show();
        assertFalse(testAchievement.doneShowing());

        testAchievement.t += Achievement.ACHIEVEMENT_SHOW_TIME_LIMIT; // manually add the time, because GDX is mocked
        testAchievement.show();
        assertTrue(testAchievement.doneShowing());
        // achievement should be shown only once => batch.draw() should be called only once
        Mockito.verify(testAchievement.batch, Mockito.times(1)).draw(
                Mockito.any(TextureRegion.class),
                Mockito.anyFloat(),
                Mockito.anyFloat(),
                Mockito.anyFloat(),
                Mockito.anyFloat());
    }

    @Test
    public void testShowDummyAchievement() {
        Achievement testAchievement = new Achievement();

        testAchievement.show();
        assertTrue(testAchievement.doneShowing());
    }
}
