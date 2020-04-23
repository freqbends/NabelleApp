package org.fundacion.nabelle.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.model.Servicio;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterImagenesServicios extends RecyclerView.Adapter<AdapterImagenesServicios.ImageViewHolder> implements View.OnClickListener  {

    private Context mContext;
    private List<Servicio> listaTipoServicio;
    private View.OnClickListener listener;

    public AdapterImagenesServicios(Context context, List<Servicio> listaTipoServicio){
        this.mContext = context;
        this.listaTipoServicio = listaTipoServicio;
    }

    @NonNull
    @Override
    public AdapterImagenesServicios.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.servicios_item,parent,false);
        v.setOnClickListener(this);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImagenesServicios.ImageViewHolder holder, int position) {

        Servicio servicioCurrent = listaTipoServicio.get(position);
        holder.textViewName.setText(servicioCurrent.getDescripcionCorta());
        Glide.with(mContext )
                .load(listaTipoServicio.get(position).getFotoPrincipalServicio())
                .into(holder.imageView);
        holder.textDescLarga.setText(servicioCurrent.getDescripcionLarga());
        holder.textPrecio.setText(servicioCurrent.getPrecio());

    }

    @Override
    public int getItemCount() {
        return listaTipoServicio.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName,textDescLarga, textPrecio;
        public ImageView imageView;
        public  ImageViewHolder(View itemView){
            super(itemView);

            textViewName = itemView.findViewById(R.id.desc_corta_servicio);
            imageView = itemView.findViewById(R.id.imagen_servicio);
            textDescLarga = itemView.findViewById(R.id.desc_larga_servicio);
            textPrecio = itemView.findViewById(R.id.precio_servicio);

        }

    }
}
