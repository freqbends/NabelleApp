package org.fundacion.nabelle.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.model.TipoServicios;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterServiPromo extends RecyclerView.Adapter<AdapterServiPromo.ImageViewHolder> implements View.OnClickListener  {

    private Context mContext;
    private List<TipoServicios> listaTipoServicio;
    private View.OnClickListener listener;

    public AdapterServiPromo(Context context, List<TipoServicios> listaTipoServicio){
        this.mContext = context;
        this.listaTipoServicio = listaTipoServicio;
    }

    @NonNull
    @Override
    public AdapterServiPromo.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.tipo_servicios_item,parent,false);
        v.setOnClickListener(this);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServiPromo.ImageViewHolder holder, int position) {

        TipoServicios servicioCurrent = listaTipoServicio.get(position);
        holder.textViewName.setText(servicioCurrent.getNombre());
        Glide.with(mContext )
                .load(listaTipoServicio.get(position).getIcono())
                .into(holder.imageView);

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
        public TextView textViewName;
        public ImageView imageView;
        public  ImageViewHolder(View itemView){
            super(itemView);

            textViewName = itemView.findViewById(R.id.desc_corta);
            imageView = itemView.findViewById(R.id.imagen_tipo_servicio);

        }

    }
}
