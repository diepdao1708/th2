package com.android.ex.th2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.ex.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment implements ItemAdapter.OnClickListener {

    FragmentHomeBinding binding;
    ItemAdapter itemAdapter;
    Database db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        db = new Database(requireContext());
        List<Spending> list = db.getAllToDay();
        double totalPrice = list.stream().mapToDouble(Spending::getPrice).sum();
        itemAdapter = new ItemAdapter(this);
        binding.homeRecyclerView.setAdapter(itemAdapter);
        binding.totalPriceTxt.setText("Tong tien: " + totalPrice + "K");

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
        List<Spending> list = db.getAllToDay();
        double totalPrice = list.stream().mapToDouble(Spending::getPrice).sum();
        binding.totalPriceTxt.setText("Tong tien: " + totalPrice + "K");
        itemAdapter.reloadData(list);
    }
}