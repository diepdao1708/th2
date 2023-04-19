package com.android.ex.th2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.ex.databinding.FragmentHistoryBinding;

import java.util.List;

public class HistoryFragment extends Fragment implements ItemAdapter.OnClickListener {

    FragmentHistoryBinding binding;
    ItemAdapter itemAdapter;
    Database db;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        db = new Database(requireContext());
        List<Spending> list = db.getAll();
        itemAdapter = new ItemAdapter(this);
        itemAdapter.reloadData(list);
        binding.historyRecyclerView.setAdapter(itemAdapter);

        return binding.getRoot();
    }

    @Override
    public void OnItemClick(Spending spending, View view) {
        Intent intent = new Intent(requireContext(), EditActivity.class);
        intent.putExtra("spending", spending);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Spending> list = db.getAll();
        itemAdapter.reloadData(list);
    }
}