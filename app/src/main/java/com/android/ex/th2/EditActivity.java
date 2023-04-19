package com.android.ex.th2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.R;
import com.android.ex.databinding.ActivityEditBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;
    List<String> list = new ArrayList<String>() {{
        add("mua sam");
        add("tien dien");
        add("tien nuoc");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Database db = new Database(this);
        Spending spending = (Spending) getIntent().getSerializableExtra("spending");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, list);
        binding.spinner.setAdapter(adapter);

        binding.nameEdt.setText(spending.getName());
        binding.priceEdt.setText(String.valueOf(spending.getPrice()));
        binding.dateEdt.setText(spending.getDate());

        int positionSpinner = getIndex(spending.description);
        binding.spinner.setSelection(positionSpinner, true);


        Calendar newCalendar = Calendar.getInstance();
        binding.dateEdt.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                binding.dateEdt.setText(sdf.format(newDate.getTime()));
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        binding.updateBtn.setOnClickListener(view -> {
            String name = binding.nameEdt.getText().toString().trim();
            String description = binding.spinner.getSelectedItem().toString();
            double totalPrice;
            String date = binding.dateEdt.getText().toString().trim();
            try {
                totalPrice = Double.parseDouble(binding.priceEdt.getText().toString().trim());
                db.updateSpending(new Spending(spending.getId(), name, description, totalPrice, date));
                Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Nhap lai gia", Toast.LENGTH_SHORT).show();
            }
        });

        binding.removeBtn.setOnClickListener(view -> {
            db.deleteSpending(spending.getId());
            finish();
        });
        binding.backBtn.setOnClickListener(view -> finish());
    }

    private int getIndex(String description) {
        for (String s : list) {
            if (s.equals(description)) return list.indexOf(description);
        }
        return 0;
    }
}