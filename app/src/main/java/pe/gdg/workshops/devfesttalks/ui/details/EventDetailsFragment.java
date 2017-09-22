package pe.gdg.workshops.devfesttalks.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Arrays;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.data.network.DetailRowBuilderTask;
import pe.gdg.workshops.devfesttalks.presenters.details.EventDescriptionPresenter;
import pe.gdg.workshops.devfesttalks.ui.playback.PlaybackOverlayActivity;
import pe.gdg.workshops.devfesttalks.ui.playback.PlaybackOverlayFragment;

/**
 * The type Event details fragment.
 */
public class EventDetailsFragment extends DetailsFragment {

    /**
     * The constant EVENT_REF.
     */
    public static final String EVENT_REF = "eventItem";
    /**
     * The constant ACTION_PLAYBACK.
     */
    public static final int ACTION_PLAYBACK = 0;

    /**
     * The Full width details overview row presenter.
     */
    private FullWidthDetailsOverviewRowPresenter overviewRowPresenter;

    /**
     * The Presenter selector.
     */
    private ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
    /**
     * The Selected event.
     */
    private Event selectedEvent;
    /**
     * The Detail actions.
     */
    private String[] detailActions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedEvent = getActivity().getIntent().getParcelableExtra(EVENT_REF);
        overviewRowPresenter = new FullWidthDetailsOverviewRowPresenter(new EventDescriptionPresenter());
        detailActions = getActivity().getResources().getStringArray(R.array.detail_actions);

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

        /*Agregar eventos a las acciones de la vista de detalle*/
        overviewRowPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
                manejarAccionDetalleSeleccionado(action.getLabel1().toString());
            }
        });

        presenterSelector.addClassPresenter(DetailsOverviewRow.class, overviewRowPresenter);

        /*Agregar adapter general a la vista de detalle*/
        ArrayObjectAdapter overviewAdapter = new ArrayObjectAdapter(presenterSelector);
        overviewAdapter.add(detailsOverviewRow);
        setAdapter(overviewAdapter);
    }

    /**
     * Manejar accion detalle seleccionado.
     *
     * @param actionTitle the action title
     */
    private void manejarAccionDetalleSeleccionado(@NonNull String actionTitle) {
        int actionPosition = Arrays.asList(detailActions).indexOf(actionTitle);
        String textAction = actionTitle.concat(" (" + actionPosition + ")");
        switch (actionPosition) {
            case ACTION_PLAYBACK:
                Intent intent = new Intent(getActivity(), PlaybackOverlayActivity.class);
                intent.putExtra(EVENT_REF, selectedEvent);
                intent.putExtra(PlaybackOverlayFragment.SHOULD_START, true);
                startActivity(intent);
                break;
            case 1:
                Toast.makeText(getActivity(), textAction, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(), textAction, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
