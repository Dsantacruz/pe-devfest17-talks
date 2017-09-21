package pe.gdg.workshops.devfesttalks.data.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.util.concurrent.ExecutionException;

import pe.gdg.workshops.devfesttalks.data.models.Event;

/**
 * The type Detail row builder task.
 */
public class DetailRowBuilderTask extends AsyncTask<Event, Integer, DetailsOverviewRow> {

    /**
     * The Context.
     */
    @SuppressLint("StaticFieldLeak")
    private Context context;

    /**
     * The Callback.
     */
    private DetailRowBuilderTaskListener callback;

    /**
     * Instantiates a new Detail row builder task.
     *
     * @param context  context
     * @param callback callback
     */
    public DetailRowBuilderTask(Context context, DetailRowBuilderTaskListener callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected DetailsOverviewRow doInBackground(Event... events) {
        final Event item = events[0];
        final DetailsOverviewRow overviewRow = new DetailsOverviewRow(item);

        try {
            FutureTarget<Bitmap> futureTarget = Glide.with(context).asBitmap().load(item.getCardImage()).submit();
            overviewRow.setImageBitmap(context, futureTarget.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.e(DetailRowBuilderTask.class.getSimpleName(), e.getMessage());
        }

        return overviewRow;
    }

    @Override
    protected void onPostExecute(DetailsOverviewRow detailsOverviewRow) {
        super.onPostExecute(detailsOverviewRow);
        if (callback != null) {
            callback.onPostExecute(detailsOverviewRow);
        }
    }

    /**
     * The interface Detail row builder task listener.
     */
    public interface DetailRowBuilderTaskListener {
        /**
         * On post execute.
         *
         * @param detailsOverviewRow the details overview row
         */
        void onPostExecute(@NonNull DetailsOverviewRow detailsOverviewRow);
    }
}
