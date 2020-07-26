package co.il.zmanim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

public class ManagerActivity extends AppCompatActivity {

    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        findViewById(R.id.MAA_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.getFruitList().setValue("333");


            }
        });

    }
}
