package com.example.dogapp.view;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dogapp.R;
import com.example.dogapp.adapter.DogAdapter;
import com.example.dogapp.databinding.FragmentListBinding;
import com.example.dogapp.model.DogBread;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ListFragment extends Fragment {
    private DogAdapter dogsAdapter;
    private DogsApiService apiService;
    private ArrayList<DogBread> dogList;
    private ArrayList<DogBread> allDogs;
    private FragmentListBinding binding;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView  = view.findViewById(R.id.rvDog);
        dogList = new ArrayList<>();
        allDogs = new ArrayList<>();
        dogsAdapter = new DogAdapter(dogList);
        recyclerView.setAdapter(dogsAdapter);
        recyclerView.setHasFixedSize(true);
        apiService = new DogsApiService();
        if(dogList.size() == 0) {
            apiService.getDogs()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<DogBread>>() {
                        @Override
                        public void onSuccess(@NonNull List<DogBread> dogBreads) {
                            for (DogBread dog : dogBreads) {
                                dogList.add(dog);
                                dogsAdapter.notifyDataSetChanged();
                            }
                            allDogs.addAll(dogList);

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d("DEBUG11", e.getMessage());
                        }
                    });
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ItemTouchHelper.SimpleCallback touchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.purple_200));
            @Override
            public boolean onMove(@androidx.annotation.NonNull RecyclerView recyclerView, @androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@androidx.annotation.NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                showMenu(viewHolder.getAdapterPosition());
//                Toast.makeText(getActivity().getApplicationContext(), "moew moew", Toast.LENGTH_SHORT).show();
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;

    }
    void search_name(String search_text){
        ArrayList<DogBread> dList = new ArrayList<>();
        for (int i = 0; i < allDogs.size(); i++) {
            if(allDogs.get(i).getName().toUpperCase().contains(search_text.toUpperCase())){
                dList.add(allDogs.get(i));
            }
        }
        dogList.clear();
        dogList.addAll(dList);
        dogsAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        MenuInflater in = getActivity().getMenuInflater();
        in.inflate(R.menu.list_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search hear");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search_name(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    public void showMenu(int position) {

        dogList.get(position).setSHOW_ITEM(1);
        dogsAdapter.notifyDataSetChanged();
    }
}