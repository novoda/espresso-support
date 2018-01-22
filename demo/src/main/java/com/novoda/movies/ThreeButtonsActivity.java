package com.novoda.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ThreeButtonsActivity extends AppCompatActivity {

    private TextView reactionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_buttons);

        View a = findViewById(R.id.button_a);
        setText(a, "a");

        View b = findViewById(R.id.button_b);
        setText(b, "b");

        View c = findViewById(R.id.button_c);
        setText(c, "c");

        reactionView = (TextView) findViewById(R.id.reaction_view);
    }

    private void setText(View button, final String text) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reactionView.setText("clicked " + text);
            }
        });
    }
}
