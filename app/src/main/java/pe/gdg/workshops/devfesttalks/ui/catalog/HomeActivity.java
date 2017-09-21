package pe.gdg.workshops.devfesttalks.ui.catalog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import pe.gdg.workshops.devfesttalks.R;

/**
 * The type Home activity.
 */
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toast.makeText(this, R.string.app_name, Toast.LENGTH_LONG).show();
    }

}
