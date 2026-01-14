package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton;
    Button delButton;
    int selectedPosition = ListView.INVALID_POSITION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ListyCity");
        }

        addButton = findViewById(R.id.addButton);
        delButton = findViewById(R.id.deleteButton);
        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        addButton.setOnClickListener(v -> showAddCityDialog());
        delButton.setOnClickListener(v -> deleteCity());
    }

    private void deleteCity() {
        if (selectedPosition != ListView.INVALID_POSITION) {
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            cityList.clearChoices();
            selectedPosition = ListView.INVALID_POSITION;
            cityList.invalidateViews();
        }
    }

    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        final EditText input = new EditText(this);
        input.setHint("Enter city name");

        int padding = (int) (16 * getResources().getDisplayMetrics().density);

        ConstraintLayout container = new ConstraintLayout(this);
        container.setPadding(padding, padding, padding, padding);

        container.addView(input,
                new ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
        );

        builder.setView(container);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String text = input.getText().toString().trim();
            if (!text.isEmpty()) {
                dataList.add(text);
                cityAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}