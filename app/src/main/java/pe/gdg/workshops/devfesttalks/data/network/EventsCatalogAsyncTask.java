package pe.gdg.workshops.devfesttalks.data.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.data.utils.JSONResourceReader;

/**
 * Asyntask for loading events
 */
public class EventsCatalogAsyncTask extends AsyncTask<Void, Integer, List<Event>> {

    /**
     * The M context.
     */
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    /**
     * The Callback.
     */
    @NonNull
    private CatalogTaskListener callback;

    /**
     * Instantiates a new Events async task.
     *
     * @param mContext the app context
     * @param callback the callback
     */
    public EventsCatalogAsyncTask(Context mContext, @NonNull CatalogTaskListener callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    protected List<Event> doInBackground(Void... voids) {
        JSONResourceReader jsonResourceReader = new JSONResourceReader(mContext.getResources(), R.raw.json_events);
        final Event[] events = jsonResourceReader.constructUsingGson(Event[].class);
        return Arrays.asList(events);
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        super.onPostExecute(events);
        callback.onPostExecute(events);
    }

    /**
     * The interface Catalog task listener.
     */
    public interface CatalogTaskListener {
        /**
         * On post execute.
         *
         * @param events the events
         */
        void onPostExecute(List<Event> events);
    }
}
