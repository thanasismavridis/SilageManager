package com.thanasis.silagemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IstorikoZigismatosAdapter extends RecyclerView.Adapter<IstorikoZigismatosAdapter.MyViewHolder> {

    private Context context;
    private ArrayList imerominia, eidos, tonoi, paragogos_epitheto, paragogos_onoma, metaforeas_epitheto, metaforeas_onoma, ar_kikloforias;

    IstorikoZigismatosAdapter(Context context, ArrayList imerominia,
                              ArrayList eidos,
                              ArrayList tonoi,
                              ArrayList paragogos_epitheto,
                              ArrayList paragogos_onoma,
                              ArrayList metaforeas_epitheto,
                              ArrayList metaforeas_onoma,
                              ArrayList ar_kikloforias){
        this.context = context;
        this.imerominia = imerominia;
        this.eidos = eidos;
        this.tonoi = tonoi;
        this.paragogos_epitheto = paragogos_epitheto;
        this.paragogos_onoma = paragogos_onoma;
        this.metaforeas_epitheto = metaforeas_epitheto;
        this.metaforeas_onoma = metaforeas_onoma;
        this.ar_kikloforias = ar_kikloforias;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imerominia_txt.setText(String.valueOf(imerominia.get(position)));
        holder.eidos_txt.setText(String.valueOf(eidos.get(position)));
        holder.tonoi_txt.setText(String.valueOf(tonoi.get(position)));
        holder.paragogos_epitheto_txt.setText(String.valueOf(paragogos_epitheto.get(position)));
        holder.paragogos_onoma_txt.setText(String.valueOf(paragogos_onoma.get(position)));
        holder.metaforeas_epitheto_txt.setText(String.valueOf(metaforeas_epitheto.get(position)));
        holder.metaforeas_onoma_txt.setText(String.valueOf(metaforeas_onoma.get(position)));
        holder.ar_kikloforias_txt.setText(String.valueOf(ar_kikloforias.get(position)));
    }

    @Override
    public int getItemCount() {
        return imerominia.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imerominia_txt, eidos_txt, tonoi_txt, paragogos_epitheto_txt, paragogos_onoma_txt, metaforeas_epitheto_txt, metaforeas_onoma_txt, ar_kikloforias_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imerominia_txt = itemView.findViewById(R.id.imerominia_txt);
            eidos_txt = itemView.findViewById(R.id.eidos_txt);
            tonoi_txt = itemView.findViewById(R.id.tonoi_txt);
            paragogos_epitheto_txt = itemView.findViewById(R.id.epitheto_paragogos_txt);
            paragogos_onoma_txt = itemView.findViewById(R.id.onoma_paragogos_txt);
            metaforeas_epitheto_txt = itemView.findViewById(R.id.epitheto_metaforeas_txt);
            metaforeas_onoma_txt = itemView.findViewById(R.id.onoma_metaforeas_txt);
            ar_kikloforias_txt = itemView.findViewById(R.id.ar_kikloforias_txt);

        }
    }
}
