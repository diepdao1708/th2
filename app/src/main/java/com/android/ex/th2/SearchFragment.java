package com.android.ex.th2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.android.ex.R;
import com.android.ex.databinding.FragmentSearchBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, ItemAdapter.OnClickListener {

    FragmentSearchBinding binding;
    ItemAdapter itemAdapter;
    Database db;
    List<String> list = new ArrayList<String>() {{
        add("all");
        add("mua sam");
        add("tien dien");
        add("tien nuoc");
    }};

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        db = new Database(requireContext());

        itemAdapter = new ItemAdapter(this);
        binding.recyclerView.setAdapter(itemAdapter);
        binding.searchView.setOnQueryTextListener(this);
        binding.searchView.setOnCloseListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item, list);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String description = list.get(i);
                List<Spending> a;
                if (description.equals(list.get(0))) {
                    a = db.getAll();
                    double totalPrice = a.stream().mapToDouble(Spending::getPrice).sum();
                    binding.totalPriceTxt.setText("Tong tien: " + totalPrice + "K");
                } else {
                    a = db.searchByDescription(description);
                    double totalPrice = a.stream().mapToDouble(Spending::getPrice).sum();
                    binding.totalPriceTxt.setText("Tong tien: " + totalPrice + "K");
                }
                itemAdapter.reloadData(a);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // noop
            }
        });

        binding.searchBtn.setOnClickListener(view -> {
            String from = binding.timeFromEdt.getText().toString();
            String to = binding.timeToEdt.getText().toString();
            List<Spending> a = db.getByDateFromTo(from, to);
            double totalPrice = a.stream().mapToDouble(Spending::getPrice).sum();
            binding.totalPriceTxt.setText("Tong tien: " + totalPrice + "K");
            itemAdapter.reloadData(a);
        });

        Calendar newCalendar = Calendar.getInstance();
        binding.timeFromEdt.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (datePicker, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                binding.timeFromEdt.setText(sdf.format(newDate.getTime()));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        binding.timeToEdt.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (datePicker, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                binding.timeToEdt.setText(sdf.format(newDate.getTime()));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        return binding.getRoot();
    }

    @Override
    public boolean onClose() {
        itemAdapter.reloadData(new ArrayList<>());
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<Spending> list = db.searchByName(query);
        itemAdapter.reloadData(list);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<Spending> list = db.searchByName(newText);
        itemAdapter.reloadData(list);
        return false;
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