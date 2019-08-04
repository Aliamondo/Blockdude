package src.com.aliamondo.blockdude;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.GdxNativesLoader;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class GdxTestRunner extends BlockJUnit4ClassRunner implements ApplicationListener {

    private final Map<FrameworkMethod, RunNotifier> invokeInRender = new HashMap<>();

    public static void setupTests() {
        Gdx.gl = mock(GL20.class);
        Gdx.graphics = mock(Graphics.class);
        GdxNativesLoader.load();
        Gdx.files = new Files() {
            @Override
            public FileHandle getFileHandle(String path, FileType type) {
                return null;
            }

            @Override
            public FileHandle classpath(String path) {
                return null;
            }

            @Override
            public FileHandle internal(String path) {
                return new FileHandle(new File(".").getAbsolutePath() + "\\Blockdude-android\\assets\\" + path);
            }

            @Override
            public FileHandle external(String path) {
                return null;
            }

            @Override
            public FileHandle absolute(String path) {
                return null;
            }

            @Override
            public FileHandle local(String path) {
                return null;
            }

            @Override
            public String getExternalStoragePath() {
                return null;
            }

            @Override
            public boolean isExternalStorageAvailable() {
                return false;
            }

            @Override
            public String getLocalStoragePath() {
                return null;
            }

            @Override
            public boolean isLocalStorageAvailable() {
                return false;
            }
        };
    }

    public GdxTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        new LwjglApplication(this, cfg);
        setupTests();
    }

    @Override
    public void create() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render() {
        synchronized (invokeInRender) {
            for (Map.Entry<FrameworkMethod, RunNotifier> each : invokeInRender.entrySet()) {
                super.runChild(each.getKey(), each.getValue());
            }
            invokeInRender.clear();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        synchronized (invokeInRender) {
            // add for invoking in render phase, where gl context is available
            invokeInRender.put(method, notifier);
        }
        // wait until that test was invoked
        waitUntilInvokedInRenderMethod();
    }

    /**
     *
     */
    private void waitUntilInvokedInRenderMethod() {
        try {
            while (true) {
                Thread.sleep(10);
                synchronized (invokeInRender) {
                    if (invokeInRender.isEmpty()) break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}