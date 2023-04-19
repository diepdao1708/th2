package com.android.ex;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ex.databinding.ActivitySqliteBinding;

import java.util.List;
import java.util.stream.Collectors;

public class SQLiteActivity extends AppCompatActivity {

    ActivitySqliteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySqliteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Database db = new Database(this);

//        db.insertCategory(new Category("Giao Duc"));
//        db.insertCategory(new Category("Kim Dong"));
//        db.insertCategory(new Category("Ha Noi"));

        List<Category> categories = db.getAllCategory();
        List<String> list = categories.stream().map(category -> category.getId() + "-" + category.getName()).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, list);
        binding.spinner.setAdapter(adapter);
    }
}