package com.example.makeus.ViewModel.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.makeus.Model.Squad;
import com.example.makeus.Module.DBHelper;
import com.example.makeus.R;
import com.example.makeus.ViewModel.Adapter.SquadAdapter;
import com.example.makeus.ViewModel.SquadViewModel;

import java.util.List;

public class SquadFragment extends Fragment {

    private SquadViewModel mViewModel;
    private SquadAdapter mSquadAdapter;

    public static SquadFragment newInstance() {
        return new SquadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragement = inflater.inflate(R.layout.fragment_squad, container, false);
        return fragement;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SquadViewModel.class);
        mViewModel.updateDataFromDB(new DBHelper(getContext()));

        if(mSquadAdapter == null) {
            mSquadAdapter = new SquadAdapter(this.getContext(), mViewModel.getLiveDataSquads().getValue(), mViewModel, getActivity().getSupportFragmentManager());
        }

        // TODO: Use the ViewModel
        GridView gridView = getView().findViewById(R.id.grid);
        gridView.setAdapter(mSquadAdapter);

        mViewModel.getLiveDataSquads().observe(this, new Observer<List<Squad>>() {
            @Override
            public void onChanged(@Nullable List<Squad> squads) {
                mSquadAdapter.mSquads = squads;
                mSquadAdapter.notifyDataSetChanged();
            }
        });
    }

}
