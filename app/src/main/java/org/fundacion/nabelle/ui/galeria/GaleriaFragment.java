package org.fundacion.nabelle.ui.galeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.ui.login.LoginActivity;

public class GaleriaFragment extends Fragment {
    View vista;
    Button botonGaleria;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        vista =  inflater.inflate(R.layout.fragment_galeria, container, false);
        botonGaleria = (Button)vista.findViewById(R.id.galeriaId);
        botonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Galeria.class);
                startActivity(intent);
            }
        });

        return vista;

    }
}