package pe.gdg.workshops.devfesttalks.ui.catalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import pe.gdg.workshops.devfesttalks.R;

/**
 * Catalog for Home Fragment class.
 */
public class HomeFragment extends BrowseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupUi();
    }

    private void setupUi() {
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
                Toast.makeText(getActivity(), "Search button clicked", Toast.LENGTH_LONG).show();
            }
        });
    }
}
