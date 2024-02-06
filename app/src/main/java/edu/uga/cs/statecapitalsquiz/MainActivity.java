package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The main activity class.  It just sets listeners for the two buttons.
 */
public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_TYPE = "edu.uga.cs.statecapitalsquiz.QUIZ_DATA";

    private Button startNewQuizButton;
    private Button reviewQuizResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNewQuizButton = findViewById(R.id.button1);
        reviewQuizResultsButton = findViewById(R.id.button2);

        startNewQuizButton.setOnClickListener(new StartNewQuizButtonClickListener());
        reviewQuizResultsButton.setOnClickListener(new ReviewQuizResultsButtonClickListener());
    }

    private class StartNewQuizButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),StartNewQuizActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class ReviewQuizResultsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),ReviewQuizResultsActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}