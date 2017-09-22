package pe.gdg.workshops.devfesttalks.ui.playback;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.PlaybackFragmentGlueHost;
import android.support.v17.leanback.app.PlaybackSupportFragment;
import android.support.v17.leanback.app.VideoFragment;
import android.support.v17.leanback.media.PlaybackGlue;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.player.VideoPlayerGlue;

import static pe.gdg.workshops.devfesttalks.ui.details.EventDetailsFragment.EVENT_REF;

/**
 * The type Playback overlay fragment.
 */
public class PlaybackOverlayFragment extends VideoFragment {

    /**
     * The constant SHOULD_START.
     */
    public static final String SHOULD_START = "shouldStart";

    /**
     * The constant BACKGROUND_TYPE.
     */
    private static final int BACKGROUND_TYPE = PlaybackSupportFragment.BG_LIGHT;
    /**
     * The Update delay.
     */
    private static final int UPDATE_DELAY = 16;
    /**
     * The Player glue.
     */
    private VideoPlayerGlue playerGlue;
    /**
     * The Player adapter.
     */
    private LeanbackPlayerAdapter playerAdapter;
    /**
     * The Exo player.
     */
    private SimpleExoPlayer exoPlayer;
    /**
     * The Track selector.
     */
    private TrackSelector trackSelector;
    /**
     * The Selected event.
     */
    private Event selectedEvent;

    /**
     * The Video player action listener.
     */
    private VideoPlayerGlue.OnActionClickedListener videoPlayerActionListener;
    /**
     * The Player view.
     */
    private SimpleExoPlayerView playerView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerView = getActivity().findViewById(R.id.video_view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackgroundType(BACKGROUND_TYPE);
        selectedEvent = getActivity().getIntent().getParcelableExtra(EVENT_REF);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            inicializarReproductor();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            inicializarReproductor();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            detenerReproductor();
        }
    }

    @Override
    public void onPictureInPictureModeChanged(
            boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        playerGlue.setControlsOverlayAutoHideEnabled(!isInPictureInPictureMode);

        if (isInPictureInPictureMode) {
            setControlsOverlayAutoHideEnabled(true);
            hideControlsOverlay(false);
        }
    }

    /**
     * Inicializar reproductor.
     */
    private void inicializarReproductor() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                trackSelector, new DefaultLoadControl());
        playerView.setPlayer(exoPlayer);


        playerAdapter = new LeanbackPlayerAdapter(getActivity(), exoPlayer, UPDATE_DELAY);

        /*Preparar video player glue*/
        videoPlayerActionListener = new VideoPlayerGlue.OnActionClickedListener() {
            @Override
            public void onPrevious() {

            }

            @Override
            public void onNext() {

            }
        };
        playerGlue = new VideoPlayerGlue(getActivity(), playerAdapter, videoPlayerActionListener);
        playerGlue.setHost(new PlaybackFragmentGlueHost(this));
        playerGlue.addPlayerCallback(new PlaybackGlue.PlayerCallback() {
            @Override
            public void onPreparedStateChanged(PlaybackGlue glue) {
                super.onPreparedStateChanged(glue);
                if (glue.isPrepared()) {
                    glue.removePlayerCallback(this);
                    glue.play();
                }
            }
        });

        reproducir(selectedEvent);
    }

    /**
     * Detener reproductor.
     */
    private void detenerReproductor() {
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
            trackSelector = null;
            playerGlue = null;
            playerAdapter = null;
        }
    }

    /**
     * Reproducir.
     *
     * @param eventItem the event item
     */
    private void reproducir(@NonNull Event eventItem) {
        final String eventSubtitle = eventItem.getDate().concat(" - ")
                .concat(eventItem.getLocation()).concat(" - ")
                .concat(eventItem.getLevel());

        playerGlue.setTitle(eventItem.getTitle());
        playerGlue.setSubtitle(eventSubtitle);
        /*TODO obtener url video para cada evento
        * prepararContenidoMultimedia(Uri.parse(eventItem.getVideoUrl()));
        */
        final String videoUrl = getString(R.string.playback_dummy_videoclip_url);
        prepararContenidoMultimedia(Uri.parse(videoUrl));
    }

    /**
     * Preparar contenido multimedia.
     *
     * @param mediaSourceUri the media source uri
     */
    private void prepararContenidoMultimedia(@NonNull Uri mediaSourceUri) {
        String userAgent = Util.getUserAgent(getActivity(), "VideoPlayerGlue");
        MediaSource mediaSource =
                new ExtractorMediaSource(
                        mediaSourceUri,
                        new DefaultDataSourceFactory(getActivity(), userAgent),
                        new DefaultExtractorsFactory(),
                        null,
                        null);

        exoPlayer.prepare(mediaSource);
    }
}
