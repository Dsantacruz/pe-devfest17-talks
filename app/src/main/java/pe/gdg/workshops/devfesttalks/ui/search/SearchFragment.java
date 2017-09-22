package pe.gdg.workshops.devfesttalks.ui.search;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.util.Log;

import pe.gdg.workshops.devfesttalks.data.network.EventsTextSearchAsyncTask;
import pe.gdg.workshops.devfesttalks.data.utils.Utils;

/**
 * The type Search fragment.
 */
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment
        implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider {

    /**
     * The Tag.
     */
    private static final String TAG = SearchFragment.class.getSimpleName();
    /**
     * The Request speech.
     */
    private static final int REQUEST_SPEECH = 0x00000010;

    /**
     * The Rows adapter.
     */
    private ArrayObjectAdapter rowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manejarPermisoGrabarAudio();

        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        setSearchResultProvider(this);
    }

    /**
     * Manejar permiso grabar audio.
     */
    private void manejarPermisoGrabarAudio() {
        if (!Utils.hasPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
            /* SpeechRecognitionCallback no es necesario y si no se proporciona, el reconocimiento
            * se manejará usando el reconocimiento de voz interno, en cuyo caso debe tener
            * permiso de RECORD_AUDIO */
            setSpeechRecognitionCallback(new SpeechRecognitionCallback() {
                @Override
                public void recognizeSpeech() {
                    Log.v(TAG, "recognizeSpeech");
                    try {
                        startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
                    } catch (ActivityNotFoundException e) {
                        Log.e(TAG, "Cannot find activity for speech recognizer", e);
                    }
                }
            });
        }
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return rowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        new EventsTextSearchAsyncTask(getActivity(), new EventsTextSearchAsyncTask.SearchTaskListener() {
            @Override
            public void onPreExecute() {
                rowsAdapter.clear();
            }

            @Override
            public void onPostExecute(@NonNull ListRow events) {
                rowsAdapter.add(events);
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
        return false;
    }
}
