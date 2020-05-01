package org.fundacion.nabelle.ui.galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.fundacion.nabelle.R;

public class DetalleGaleriaActivity extends AppCompatActivity {

    String fotoUrl;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalle_galeria);
        imagen = findViewById(R.id.imagenGaleria);

        Bundle bundle=getIntent().getExtras();
        fotoUrl = bundle.getString("fotoUrl");

        Glide.with(this )
                .load(fotoUrl)
                .into(imagen);
    }
}
