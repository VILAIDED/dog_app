package com.example.dogapp.adapter;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBread;
import com.example.dogapp.view.DetailFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<DogBread> dogList;
    public DogAdapter(ArrayList<DogBread> dogList){
        this.dogList = dogList;
    }
    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 0;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType==HIDE_MENU){
            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dog_item, parent, false);
            return new ViewHolder(v,dogList);
        }else{
            v= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dog_info, parent, false);
            return new DogViewHolder(v,dogList);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).tvName.setText(dogList.get(position).getName());
            ((ViewHolder)holder).tvOrigin.setText(dogList.get(position).getOrigin());
            Picasso.get().load(dogList.get(position).getUrl()).into(((ViewHolder)holder).dogImg);
        }
        if(holder instanceof DogViewHolder){
            ((DogViewHolder)holder).tvName.setText(dogList.get(position).getName());
            ((DogViewHolder)holder).tvOrigin.setText(dogList.get(position).getOrigin());
            ((DogViewHolder)holder).tvTemperament.setText(dogList.get(position).getTemperament());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dogList.get(position).getSHOW_ITEM();
    }
    //    @Override
//    public void onBindViewHolder(@NonNull DogAdapter.ViewHolder holder, int position) {

//    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }
    public  class DogViewHolder extends RecyclerView.ViewHolder{
        private ArrayList<DogBread> dogList;
        public TextView tvName;
        public TextView tvOrigin;
        public TextView tvTemperament;
        public DogViewHolder(@NonNull View itemView, ArrayList<DogBread> dogList) {
            super(itemView);
            this.dogList = dogList;
            tvTemperament = itemView.findViewById(R.id.tv_temperament);
            tvName= itemView.findViewById(R.id.tv_name);
            tvOrigin = itemView.findViewById(R.id.tv_origin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DogBread dog = dogList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("dog",dog);
                    DetailFragment detailFragment = new DetailFragment();

                    detailFragment.setArguments(bundle);
                    Navigation.findNavController(itemView).navigate(R.id.detailFragment,bundle);
                }
            });
        }
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        private ArrayList<DogBread> dogList;
        public ImageView dogImg;
        public TextView tvName;
        public TextView tvOrigin;
        public ViewHolder(@NonNull View itemView, ArrayList<DogBread> dogList) {
            super(itemView);
            this.dogList = dogList;
            dogImg = itemView.findViewById(R.id.dog_img);
            tvName= itemView.findViewById(R.id.dog_name);
            tvOrigin = itemView.findViewById(R.id.origin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DogBread dog = dogList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("dog",dog);
                    DetailFragment detailFragment = new DetailFragment();

                    detailFragment.setArguments(bundle);
                    Navigation.findNavController(itemView).navigate(R.id.detailFragment,bundle);
                }
            });
        }
    }
}


