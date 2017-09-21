package pe.gdg.workshops.devfesttalks.ui.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.content.ContextCompat;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.data.network.DetailRowBuilderTask;
import pe.gdg.workshops.devfesttalks.presenters.details.EventDescriptionPresenter;

/**
 * The type Event details fragment.
 */
public class EventDetailsFragment extends DetailsFragment {

    /**
     * The constant EVENT_REF.
     */
    public static final String EVENT_REF = "eventItem";

    /**
     * The Full width details overview row presenter.
     */
    private FullWidthDetailsOverviewRowPresenter overviewRowPresenter;

    /**
     * The Presenter selector.
     */
    private ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
    private Event selectedEvent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedEvent = getActivity().getIntent().getParcelableExtra(EVENT_REF);
        overviewRowPresenter = new FullWidthDetailsOverviewRowPresenter(new EventDescriptionPresenter());

        new DetailRowBuilderTask(getActivity(), new DetailRowBuilderTask.DetailRowBuilderTaskListener() {
            @Override
            public void onPostExecute(@NonNull DetailsOverviewRow detailsOverviewRow) {
                handleDetailsOverviewData(detailsOverviewRow);
            }
        }).execute(selectedEvent);
    }

    /**
     * Handle details overview data.
     *
     * @param detailsOverviewRow the details overview row
     */
    private void handleDetailsOverviewData(@NonNull DetailsOverviewRow detailsOverviewRow) {
        /*Acciones en la vista de detalle*/
        final String[] detailActions = getActivity().getResources().getStringArray(R.array.detail_actions);
        final SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
        for (int position = 0; position < detailActions.length; ++position) {
            sparseArrayObjectAdapter.set(position, new Action(position, detailActions[position]));
        }

        detailsOverviewRow.setActionsAdapter(sparseArrayObjectAdapter);

        /*Preparar vista de detalle para mostrarla en pantalla*/
        overviewRowPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);

        final int areaBackgroundColor = selectedEvent.getType().equalsIgnoreCase("talk")
                ? R.color.color_app_catalog_item_area_bg : R.color.color_app_catalog_item_area_bg_alt;

        final int actionsBackgroundColor = selectedEvent.getType().equalsIgnoreCase("talk")
                ? R.color.color_app_catalog_item_area_bg_dark : R.color.color_app_catalog_item_area_bg_dark_alt;

        overviewRowPresenter.setActionsBackgroundColor(ContextCompat.getColor(getActivity(), actionsBackgroundColor));
        overviewRowPresenter.setBackgroundColor(ContextCompat.getColor(getActivity(), areaBackgroundColor));

        presenterSelector.addClassPresenter(DetailsOverviewRow.class, overviewRowPresenter);

        /*Agregar adapter general a la vista de detalle*/
        ArrayObjectAdapter overviewAdapter = new ArrayObjectAdapter(presenterSelector);
        overviewAdapter.add(detailsOverviewRow);
        setAdapter(overviewAdapter);
    }
}
