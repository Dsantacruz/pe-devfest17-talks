package pe.gdg.workshops.devfesttalks.ui.catalog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import pe.gdg.workshops.devfesttalks.R;
import pe.gdg.workshops.devfesttalks.ui.search.SearchActivity;

/**
 * The type Home activity.
 */
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onSearchRequested() {
        startActivity(new Intent(this, SearchActivity.class));
        return true;
    }

}
