package pe.gdg.workshops.devfesttalks.data.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.data.utils.JSONResourceReader;

/**
 * Asyntask for loading events
 */
public class EventsAsyncTask extends AsyncTask<Void, Integer, List<Event>> {

    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private EventsAsyncTaskListener callback;

    /**
     * Instantiates a new Events async task.
     *
     * @param mContext the app context
     * @param callback the callback
     */
    public EventsAsyncTask(Context mContext, EventsAsyncTaskListener callback) {
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
        if (callback != null) {
            callback.onPostExecute(events);
        }
    }

    /**
     * The interface Events async task listener.
     */
    public interface EventsAsyncTaskListener {
        /**
         * On post execute.
         *
         * @param events the events
         */
        void onPostExecute(List<Event> events);
    }
}
