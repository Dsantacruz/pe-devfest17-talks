package pe.gdg.workshops.devfesttalks.listeners.catalog;

import android.content.Context;
import android.content.Intent;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.ui.details.EventDetailsActivity;
import pe.gdg.workshops.devfesttalks.ui.details.EventDetailsFragment;

/**
 * The type Catalog card clicked listener.
 */
public class CatalogCardClickedListener implements OnItemViewClickedListener {
    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof Event) {
            Event eventItem = (Event) item;
            Context context = rowViewHolder.view.getContext();
            Intent intent = new Intent(context, EventDetailsActivity.class);
            intent.putExtra(EventDetailsFragment.EVENT_REF, eventItem);
            context.startActivity(intent);
        }
    }
}
