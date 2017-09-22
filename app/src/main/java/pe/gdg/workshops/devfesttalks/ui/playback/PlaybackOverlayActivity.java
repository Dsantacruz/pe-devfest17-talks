package pe.gdg.workshops.devfesttalks.ui.playback;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import pe.gdg.workshops.devfesttalks.R;

/**
 * The type Playback overlay activity.
 */
public class PlaybackOverlayActivity extends Activity {

    /**
     * The Pip listeners.
     */
    private List<PictureInPictureListener> pipListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_overlay);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        for (PictureInPictureListener listener : pipListeners) {
            listener.onPictureInPictureModeChanged(isInPictureInPictureMode);
        }
    }

    /**
     * Register picture in picture listener.
     *
     * @param listener the listener
     */
    public void registerPictureInPictureListener(PictureInPictureListener listener) {
        pipListeners.add(listener);
    }

    /**
     * Unregister picture in picture listener.
     *
     * @param listener the listener
     */
    public void unregisterPictureInPictureListener(PictureInPictureListener listener) {
        pipListeners.remove(listener);
    }

    /**
     * The interface Picture in picture listener.
     */
    public interface PictureInPictureListener {
        /**
         * On picture in picture mode changed.
         *
         * @param isInPictureInPictureMode the is in picture in picture mode
         */
        void onPictureInPictureModeChanged(boolean isInPictureInPictureMode);
    }
}
