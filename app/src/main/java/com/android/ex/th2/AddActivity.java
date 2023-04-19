package com.android.ex.th2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.R;
import com.android.ex.databinding.ActivityAddBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Database db = new Database(this);
        List<String> list = new ArrayList<String>() {{
            add("mua sam");
            add("tien dien");
            add("tien nuoc");
        }};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, list);
        binding.spinner.setAdapter(adapter);

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
                Spending spending = new Spending(name, description, totalPrice, date);
                db.insertSpending(spending);
                Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Nhap lai gia", Toast.LENGTH_SHORT).show();
            }
        });

        binding.cancelBtn.setOnClickListener(view -> finish());
    }
}