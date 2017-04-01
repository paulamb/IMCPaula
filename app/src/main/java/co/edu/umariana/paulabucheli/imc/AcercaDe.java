package co.edu.umariana.paulabucheli.imc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.edu.umariana.paulamunoz.imc.R;

public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
    }
    public void salir(View v) {

        finish();
    }

}
