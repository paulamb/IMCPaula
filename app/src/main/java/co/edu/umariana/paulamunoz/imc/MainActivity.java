package co.edu.umariana.paulamunoz.imc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.edu.umariana.paulabucheli.imc.AcercaDe;
import co.edu.umariana.paulabucheli.imc.Guardar;
import co.edu.umariana.paulamunoz.imc.R;

public class MainActivity extends AppCompatActivity {

    private EditText et_peso;
    private EditText et_altura;
    private TextView tv_resultado;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_peso = (EditText) findViewById(R.id.et_peso);
        et_altura = (EditText) findViewById(R.id.et_altura);
        tv_resultado = (TextView) findViewById(R.id.tv_resultado);
        imagen = (ImageView) findViewById(R.id.imagen);

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        et_peso.setText(prefe.getString("Peso",""));
        et_altura.setText(prefe.getString("Altura",""));
    }
    public void calcular(View view) {
        DecimalFormat decimales = new DecimalFormat("0.00");
        double bajo_peso = 18.50;
        double normal = 24.99;
        double sobrepeso = 25.0;
        double obesidad = 30.0;

        Double peso, estatura, IMC;
        peso = Double.parseDouble(et_peso.getText().toString());
        estatura = Double.parseDouble(et_altura.getText().toString());

        //Formula para calcular el IMC de una persona es IMC=Peso/altura m^2
        IMC = peso / Math.pow(estatura, 2);
        tv_resultado.setText("Para su peso: " + peso + " y su estatura: " + estatura + " su IMC es: " + IMC);
        if (IMC <= bajo_peso && IMC < 16.00) {
            tv_resultado.setText(getString(R.string.segun_bajo_peso) + decimales.format(IMC));
            Picasso.with(getApplicationContext()).load(R.drawable.delgado).into(imagen);
        }
        if (IMC > bajo_peso && IMC < normal) {
            tv_resultado.setText(getString(R.string.segun_peso_normal) + decimales.format(IMC));
            Picasso.with(getApplicationContext()).load(R.drawable.normal).into(imagen);
        }

        if (IMC >= sobrepeso && IMC < obesidad) {
            tv_resultado.setText(getString(R.string.segun_sobre_peso) + decimales.format(IMC));
            Picasso.with(getApplicationContext()).load(R.drawable.gordo).into(imagen);
        }
        if (IMC > obesidad) {
            tv_resultado.setText(getString(R.string.segun_peso_obesidad) + decimales.format(IMC));
            Picasso.with(getApplicationContext()).load(R.drawable.obesos).into(imagen);
        }

        SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("Peso", et_peso.getText().toString());
        editor.putString("Altura", et_altura.getText().toString());
        editor.commit();
    }

    public void acercade(View view) {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }

    public void historico(View v) {
        Date fechaActual = new Date();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy: hh:mm");

        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(
                    "IMC.txt", Activity.MODE_PRIVATE));
            archivo.write("Fecha: "+formatoFecha.format(fechaActual)+"\n");
            archivo.write("Peso: "+et_peso.getText().toString()+ "\n");
            archivo.write("Altura: "+et_altura.getText().toString()+ "\n");
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
        }
        Toast t = Toast.makeText(this, "Los datos fueron almacenados",
                Toast.LENGTH_SHORT);
        t.show();
        Intent i = new Intent(this, Guardar.class);
        startActivity(i);

    }
}