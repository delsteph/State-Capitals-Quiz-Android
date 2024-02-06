package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class QuizSubmission extends AppCompatActivity {

    int result;
    String date;
    TextView resultView;
    TextView dateView;
    private Button homeButton;
    private ResultsData resultsData = null;

    // retrieve most recent result from db or pass in params
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_submission);

        resultsData = new ResultsData( this );

        Intent intent = getIntent();
        result = intent.getIntExtra("result",0);
        date = intent.getStringExtra("date");

        Log.i("result",String.valueOf(result));
        Log.i("date",String.valueOf(date));

        resultView = findViewById(R.id.resultTextView);
        dateView = findViewById(R.id.dateTextView);

        resultView.setText("Result: " + String.valueOf(result) + " out of 6");
        dateView.setText("Date: " + date);

        homeButton = findViewById(R.id.returnHomeButton);
        homeButton.setOnClickListener(new ReturnHomeButtonClickListener());

        Result result1 = new Result(date,result);
        new QuizResultDBWriterTask().execute(result1);
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
    private class QuizResultDBWriterTask extends AsyncTask<Result, Void, Result> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected Result doInBackground(Result... result) {
            resultsData.storeQuizResult(result[0]);
            return result[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            Log.d( "QuizSubmission", "result saved in db");
        }
    }

    private class ReturnHomeButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(),MainActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
