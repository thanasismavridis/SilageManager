package com.example.silagemanager.Apotelesmata.Paragogos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silagemanager.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ApotelesmataParagogouAdapter extends RecyclerView.Adapter<ApotelesmataParagogouAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<HashMap<String, String>> list;

    ApotelesmataParagogouAdapter(Context context, ArrayList list)
                             {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.istoriko_paragogou_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String id = list.get(position).get("id");
        String date = list.get(position).get("date");
        String mikto = list.get(position).get("mikto");
        String autokinito = list.get(position).get("autokinito");
        String ensiroma = list.get(position).get("ensiroma");
        String ofelimo = list.get(position).get("ofelimo");
        String apovaro = list.get(position).get("apovaro");



        holder.id.setText(id);
        holder.date.setText(date);
        holder.ensiroma.setText(ensiroma);
        holder.mikto.setText(mikto + " Kg");
        holder.ofelimo.setText(ofelimo + " Kg");
        holder.autokinito.setText(autokinito);
        holder.apovaro.setText(apovaro + " Kg");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_txt, date, ensiroma_txt, ensiroma, autokinito_txt, autokinito, mikto_txt, mikto, ofelimo_txt, ofelimo, id_txt, id, apovaro;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date_txt = itemView.findViewById(R.id.date_txt);
            date = itemView.findViewById(R.id.date);
            ensiroma_txt = itemView.findViewById(R.id.ensiroma_txt);
            ensiroma = itemView.findViewById(R.id.ensiroma);
            autokinito_txt = itemView.findViewById(R.id.autokinito_txt);
            autokinito = itemView.findViewById(R.id.autokinito);
            mikto_txt = itemView.findViewById(R.id.mikto_txt);
            mikto = itemView.findViewById(R.id.mikto);
            ofelimo_txt = itemView.findViewById(R.id.ofelimo_txt);
            ofelimo = itemView.findViewById(R.id.ofelimo);
            id = itemView.findViewById(R.id.id);
            id_txt = itemView.findViewById(R.id.id_txt);
            apovaro = itemView.findViewById(R.id.apovaro);
        }
    }
}
