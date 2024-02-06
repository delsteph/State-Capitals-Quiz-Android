package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is facilitates storing and restoring job leads stored.
 */
public class QuestionsData {

    public static final String DEBUG_TAG = "JobLeadsData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase   db;
    private SQLiteOpenHelper quizDataDbHelper;
    private static final String[] allColumns = {
            QuizDataDBHelper.QUIZQUESTIONS_COLUMN_ID,
            QuizDataDBHelper.QUIZQUESTIONS_COLUMN_STATE,
            QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CAPITAL,
            QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CITY1,
            QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CITY2,
            QuizDataDBHelper.QUIZQUESTIONS_COLUMN_ANSWER
    };

    public QuestionsData(Context context) {
        this.quizDataDbHelper = QuizDataDBHelper.getInstance(context);
    }

    // Open the database
    public void open() {
        db = quizDataDbHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizData: db open" );
    }

    // Close the database
    public void close() {
        if( quizDataDbHelper != null ) {
            quizDataDbHelper.close();
            Log.d(DEBUG_TAG, "QuizData: db closed");
        }
    }

    public int getQuestionCount() {
        String countQuery = "SELECT  * FROM " + QuizDataDBHelper.TABLE_QUIZQUESTIONS;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Store a new job lead in the database.
    public Question storeQuizQuestion(Question question) {

        open();

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        if(getQuestionCount() < 50) {
            ContentValues values = new ContentValues();
            values.put(QuizDataDBHelper.QUIZQUESTIONS_COLUMN_STATE, question.getState());
            values.put(QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CAPITAL, question.getCapital());
            values.put(QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CITY1, question.getCity1());
            values.put(QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CITY2, question.getCity2());
            values.put(QuizDataDBHelper.QUIZQUESTIONS_COLUMN_ANSWER, question.getAnswer());

            // Insert the new row into the database table;
            // The id (primary key) is automatically generated by the database system
            // and returned as from the insert method call.
            long id = db.insert(QuizDataDBHelper.TABLE_QUIZQUESTIONS, null, values);

            // store the id (the primary key) in the JobLead instance, as it is now persistent
            question.setId(id);

            Log.d(DEBUG_TAG, "Stored new quiz question with id: " + String.valueOf(question.getId()));
        }
        return question;
    }

    public Cursor fetch() {
        Cursor cursor = db.query(QuizDataDBHelper.TABLE_QUIZQUESTIONS, new String[]{QuizDataDBHelper.QUIZQUESTIONS_COLUMN_STATE,QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CAPITAL,QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CITY1,QuizDataDBHelper.QUIZQUESTIONS_COLUMN_CITY2}, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
}