package hect.preciosapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnSearch, btnAdd, btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.ButtonMain1);
        btnAdd = findViewById(R.id.ButtonMain2);
        btnModify = findViewById(R.id.ButtonMain3);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(1);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(2);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(3);
            }
        });
    }

    private void nextActivity(int opcion){
        Intent intent;
        switch (opcion){
            case 1: intent = new Intent(this, PricesActivity.class);
                startActivity(intent);
                break;
            case 2: intent = new Intent(this, AddProductActivity.class);
                startActivity(intent);
                break;
            case 3: intent = new Intent(this, ModifyProductActivity.class);
                startActivity(intent);
        }
    }
}
