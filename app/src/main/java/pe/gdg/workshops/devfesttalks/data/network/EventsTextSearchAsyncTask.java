package pe.gdg.workshops.devfesttalks.data.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.data.utils.JSONResourceReader;
import pe.gdg.workshops.devfesttalks.presenters.catalog.CatalogCardPresenter;

/**
 * The type Events text search async task.
 */
public class EventsTextSearchAsyncTask extends AsyncTask<String, Void, ListRow> {

    /**
     * The Context.
     */
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    /**
     * The Callback.
     */
    @NonNull
    private final SearchTaskListener callback;

    /**
     * Instantiates a new Events text search async task.
     *
     * @param context  the context
     * @param callback the callback
     */
    public EventsTextSearchAsyncTask(Context context,
                                     @NonNull SearchTaskListener callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected ListRow doInBackground(String... texts) {
        final String queryText = texts[0].toLowerCase();
        JSONResourceReader jsonResourceReader = new JSONResourceReader(context.getResources(), R.raw.json_events);
        final Event[] events = jsonResourceReader.constructUsingGson(Event[].class);
        final List<Event> eventList = Arrays.asList(events);
        final List<Event> foundEvents = new ArrayList<>();
        for (final Event item : eventList) {
            final boolean containsQueryText = item.getTitle().toLowerCase().contains(queryText)
                    || item.getCategory().toLowerCase().contains(queryText);
            if (containsQueryText) {
                foundEvents.add(item);
            }
        }
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CatalogCardPresenter());
        listRowAdapter.addAll(0, foundEvents);
        HeaderItem header = new HeaderItem(context.getString(R.string.text_search_results));
        return new ListRow(header, listRowAdapter);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreExecute();
    }

    @Override
    protected void onPostExecute(ListRow events) {
        super.onPostExecute(events);
        callback.onPostExecute(events);
    }

    /**
     * The interface Search task listener.
     */
    public interface SearchTaskListener {

        /**
         * On pre execute.
         */
        void onPreExecute();

        /**
         * On post execute.
         *
         * @param events the events
         */
        void onPostExecute(@NonNull ListRow events);
    }
}
