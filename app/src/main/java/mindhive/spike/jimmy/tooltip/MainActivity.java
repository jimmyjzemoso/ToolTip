package mindhive.spike.jimmy.tooltip;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewGroup parent = findViewById(R.id.parent);
        findViewById(R.id.show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MHToolTip.getInstance(getApplicationContext())
                        .show(new Point(500,500),
                                "Hello World",
                                Color.parseColor("#ffffff"),
                                Color.parseColor("#000000"),
                                true,
                                true ,parent);
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
