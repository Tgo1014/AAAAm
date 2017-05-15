package tgo1014.aguaaguaaguamineral.Activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tgo1014.aguaaguaaguamineral.R;

public class BaseActivity extends AppCompatActivity {


    protected void configuraToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
