package pe.gdg.workshops.devfesttalks.ui.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.data.models.Event;
import pe.gdg.workshops.devfesttalks.data.network.EventsCatalogAsyncTask;
import pe.gdg.workshops.devfesttalks.listeners.catalog.CatalogCardClickedListener;
import pe.gdg.workshops.devfesttalks.presenters.catalog.CatalogCardPresenter;
import pe.gdg.workshops.devfesttalks.ui.search.SearchActivity;

/**
 * Catalog for Home Fragment class.
 */
public class HomeFragment extends BrowseFragment {

    /**
     * The rows adapter.
     */
    private ArrayObjectAdapter rowsAdapter;

    /**
     * The Background manager.
     */
    private BackgroundManager backgroundManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepararUi();
        cargarDatos();
        prepararbackgroundManager();
        prepararEventosUi();
    }

    /**
     * Preparar eventos de UI.
     */
    private void prepararEventosUi() {
        setOnItemViewClickedListener(new CatalogCardClickedListener());
    }

    /**
     * Preparar background manager.
     */
    protected void prepararbackgroundManager() {
        backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        backgroundManager.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.catalog_default_background));
    }

    /**
     * Cargar datos.
     */
    protected void cargarDatos() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        new EventsCatalogAsyncTask(getActivity(), new EventsCatalogAsyncTask.CatalogTaskListener() {
            @Override
            public void onPostExecute(List<Event> events) {
                rowsAdapter.clear();
                final CatalogCardPresenter cardPresenter = new CatalogCardPresenter();
                final HashMap<String, List<Event>> categoryEvents = new HashMap<>();

                for (int position = 0; position < events.size(); ++position) {
                    final Event item = events.get(position);
                    if (!categoryEvents.containsKey(item.getCategory())) {
                        categoryEvents.put(item.getCategory(), new ArrayList<Event>());
                    }
                    categoryEvents.get(item.getCategory()).add(item);
                }

                final List<String> keySet = new ArrayList<>(categoryEvents.keySet());
                Collections.sort(keySet, new Comparator<String>() {
                    @Override
                    public int compare(String key1, String key2) {
                        return key1.compareTo(key2);
                    }
                });
                for (final String category : keySet) {
                    final List<Event> list = categoryEvents.get(category);
                    final HeaderItem cardPresenterHeader = new HeaderItem(category);
                    ArrayObjectAdapter itemAdapter = new ArrayObjectAdapter(cardPresenter);
                    itemAdapter.addAll(0, list);
                    rowsAdapter.add(new ListRow(cardPresenterHeader, itemAdapter));
                }
            }
        }).execute();
        setAdapter(rowsAdapter);
    }

    /**
     * Preparar ui.
     */
    protected void prepararUi() {
        /* Agregar banner a la parte superior derecha de la pantalla*/
        setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.tv_banner));

        /* Agregar titulo de la vista de catalogo
        (si el banner fue colocado, este ultimo auita esto) */
        setTitle(getActivity().getResources().getString(R.string.catalog_title));

        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        /* Fijar el color de fondo del fastLane (o de los encabezamientos)*/
        setBrandColor(getResources().getColor(R.color.color_app_fastlane_background));

        /* Establecer el color del icono de búsqueda*/
        setSearchAffordanceColor(getResources().getColor(R.color.color_app_search_opaque));

        /* Establecer el evento click del boton de busqueda.<br/>
        sin esto, dicho boton no aparece en la UI*/
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
