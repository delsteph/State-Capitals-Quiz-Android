package edu.uga.cs.statecapitalsquiz;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewQuizResultsActivity extends AppCompatActivity {

    private ResultsData resultsData = null;
    String datesPrint = "Dates";
    String resultsPrint = "Quiz Results";
    TextView datesView;
    TextView resultsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_quiz_results);

        datesView = findViewById(R.id.datesTextView);
        resultsView = findViewById(R.id.resultsTextView);

        resultsData = new ResultsData( this );
        resultsData.open();

        Cursor cursor = resultsData.fetch();

        int size = resultsData.getResultsCount();
        cursor.moveToFirst();
        for (int i = 0; i < size; i++) {
            datesPrint += "\n" + cursor.getString(0);
            resultsPrint += "\n" + cursor.getString(1);
            cursor.moveToNext();
        }

        Log.i("Cursor Dates Ouput",datesPrint);
        Log.i("Cursor Results Ouput",resultsPrint);

        datesView.setText(datesPrint);
        resultsView.setText(resultsPrint);

    }
}
