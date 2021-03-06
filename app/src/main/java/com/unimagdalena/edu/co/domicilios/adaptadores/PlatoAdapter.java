package com.unimagdalena.edu.co.domicilios.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unimagdalena.edu.co.domicilios.R;
import com.unimagdalena.edu.co.domicilios.modelo.Plato;
import com.unimagdalena.edu.co.domicilios.modelo.Restaurante;
import com.unimagdalena.edu.co.domicilios.vista.RestauranteActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<Plato> platos;
    private ArrayList<Restaurante> restaurantes;
    private Boolean esconderBotones;
    private RestauranteActivity.PlatoFragment platoFragment;

    public PlatoAdapter(Activity activity, ArrayList<Restaurante> restaurantes, ArrayList<Plato> platos, Boolean esconderBotones) {
        this.activity = activity;
        this.restaurantes = restaurantes;
        this.platos = platos;
        this.esconderBotones = esconderBotones;
        this.platoFragment = RestauranteActivity.PlatoFragment.getInstance();
    }

    public PlatoAdapter(Activity activity, ArrayList<Plato> platos, Boolean esconderBotones) {
        this.activity = activity;
        this.platos = platos;
        this.esconderBotones = esconderBotones;
        this.platoFragment = RestauranteActivity.PlatoFragment.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View platos = layoutInflater.inflate(R.layout.plato_item_row, parent, false);

        return new ViewHolder(platos);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Plato plato = platos.get(position);

        holder.nombre_plato.setText(plato.getNombre());
        holder.descripcion_plato.setText(plato.getDescripcion());
        holder.precio_plato.setText(NumberFormat.getCurrencyInstance(new Locale("es", "CO")).format(plato.getPrecio()));

        holder.botonQuitar.setVisibility(esconderBotones ? View.INVISIBLE : View.VISIBLE);
        holder.botonQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platoFragment.establecerCostoTotal(plato, 1);
                platoFragment.establecerCantidadPlatos(plato, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return platos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nombre_plato;
        public TextView descripcion_plato;
        public TextView precio_plato;
        public Button botonQuitar;

        public ViewHolder(View itemView) {
            super(itemView);

            this.nombre_plato = (TextView) itemView.findViewById(R.id.nombre_plato);
            this.descripcion_plato = (TextView) itemView.findViewById(R.id.descripcion_plato);
            this.precio_plato = (TextView) itemView.findViewById(R.id.precio_plato);
            this.botonQuitar = (Button) itemView.findViewById(R.id.botonQuitar);
        }

        @Override
        public void onClick(View v) {
            if (esconderBotones) {
                Plato plato = platos.get(getLayoutPosition());

                for (Restaurante restaurante : restaurantes) {
                    if (restaurante.getMenu().contains(plato)) {
                        Intent intent = new Intent(activity, RestauranteActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("restaurante", restaurante);

                        intent.putExtras(bundle);

                        activity.startActivity(intent);

                        break;
                    }
                }
            } else {
                platoFragment.establecerCostoTotal(platos.get(getLayoutPosition()), 0);
                platoFragment.establecerCantidadPlatos(platos.get(getLayoutPosition()), 0);
            }
        }
    }
}
