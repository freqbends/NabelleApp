package org.fundacion.nabelle.ui.galeria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.model.Upload;
import org.fundacion.nabelle.utils.ImagesAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Galeria extends AppCompatActivity implements View.OnClickListener {

    private
    RecyclerView mRecyclerView;
    private ImagesAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference storageRef;
    private ImageButton botonAtras;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        botonAtras = findViewById(R.id.botonAtrasGaleria);
        botonAtras.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarGaleria);
        progressBar.setProgress(20);

        storageRef = FirebaseStorage.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mUploads = new ArrayList<>();

        final CollectionReference coleccion = FirebaseFirestore.getInstance().collection("Galeria");

        coleccion.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);
                    for (DocumentSnapshot document : task.getResult()) {
                        Upload upload =  new Upload();
                        Log.d(TAG, document.getId() + " =>**** " + document.getString("fotoUrl"));

                        upload.setmImageUrl(document.getString("fotoUrl"));
                        mUploads.add(upload);

                    }
                    mAdapter = new ImagesAdapter(Galeria.this, mUploads);

                    mAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent detalleGaleria = new Intent(Galeria.this, DetalleGaleriaActivity.class);
                            detalleGaleria.putExtra("fotoUrl", mUploads.get(mRecyclerView.getChildAdapterPosition(v)).getmImageUrl());
                            startActivity(detalleGaleria);

                        }
                    });


                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botonAtrasGaleria:
                finish();
                progressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
