package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * This class is an activity to create a new job lead.
 */
public class StartNewQuizActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;

    public static final String DEBUG_TAG = "StartNewQuizActivity";

    private String state;
    private String capital;
    private String answer;
    private int result;
    boolean pointGiven = false;
    Button submitButton;

    String tempCapital = "";

    Random random = new Random();
    private QuestionsData questionsData = null;

    TextView stateView;

    //RadioButton rb1,rb2,rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_quiz);

        questionsData = new QuestionsData( this );
        questionsData.open();

        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.state_capitals_updated);

            // read the CSV data
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextLine;

            while((nextLine = reader.readNext()) != null) {
               Question question = new Question(nextLine[0], nextLine[1], nextLine[2], nextLine[3], "");
               new QuizQuestionDBWriterTask().execute(question);
            }
        } catch (Exception e) {
            Log.e( "cvsReader", e.toString() );
        }

//        Cursor cursor = questionsData.fetch();
//        int size = random.nextInt(49);
//        cursor.moveToFirst();
//        for (int i = 0; i <= size; i++) {
//            cursor.moveToNext();
//        }
//        state = cursor.getString(0);
//
//        stateView = findViewById(R.id.questionTextView);
//        stateView.setText("What is the capital of " + state + "?");
//        rb1 = findViewById(R.id.RadioButton01);
//        rb2 = findViewById(R.id.RadioButton02);
//        rb3 = findViewById(R.id.RadioButton03);
//
//        int indexradio1 = random.nextInt(4);
//        int indexradio2 = random.nextInt(4);
//        int indexradio3 = random.nextInt(4);
//
//        while(indexradio1 == 0)
//            indexradio1 = random.nextInt(4);
//
//        while(indexradio2 == indexradio1 || indexradio2 == 0)
//            indexradio2 = random.nextInt(4);
//
//        while((indexradio3 == indexradio1 || indexradio3 == indexradio2) || indexradio3 == 0)
//            indexradio3 = random.nextInt(4);
//
//        Log.i("indexradio1",String.valueOf(indexradio1));
//        Log.i("indexradio2",String.valueOf(indexradio2));
//        Log.i("indexradio3",String.valueOf(indexradio3));
//
//        capital = cursor.getString(1);
//
//        rb1.setText("1) " + cursor.getString(indexradio1));
//        rb2.setText("2) " + cursor.getString(indexradio2));
//        rb3.setText("3) " + cursor.getString(indexradio3));
//
       //
        //
         //submitButton.setOnClickListener(new ShowResultButtonClickListener());

        //added
        int numOfPages = 6;
        mActionBar = getSupportActionBar();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), numOfPages);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    // added
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final int mSize;

        public SectionsPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.mSize = size;
        }

        @Override
        public Fragment getItem(int position) {
//            if(position == 5) {
//                submitButton.setEnabled(true);
//            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int questionNum = position + 1;
            return String.valueOf("Question " + questionNum);
        }
    } // SectionPageAdapter


    // added
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        private static final String ARG_SECTION_NUMBER = "section_number";

        private Button submitButton;
        private TextView stateView;
        private RadioButton rb1;
        private RadioButton rb2;
        private RadioButton rb3;

        ResultsData qnqData;
        Question currQuestion;
        Result quiz;
        private int score = 0;
        private int indexGlobal;
        private List<Question> questions = new ArrayList<Question>();
        //private PageViewModel pageViewModel;
        private Button restart;
        private Button pastQuizzes;



        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                // mImageNum = getArguments().getInt(ARG_SECTION_NUMBER);
            } else {
                //mImageNum = -1;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            //do what you want to do when button is clicked
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);

            String date = formatDate.format(ldt);

            Intent intent = new Intent(view.getContext(),QuizSubmission.class);
            intent.putExtra("result",4);
            intent.putExtra("date",date);
            view.getContext().startActivity(intent);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_start_new_quiz, container, false);

//            stateView =  (TextView) rootView.findViewById( R.id.questionTextView );
//            option1 =  (TextView)  rootView.findViewById( R.id.RadioButton01 );
//            option2 =  (TextView)  rootView.findViewById( R.id.RadioButton02);
//            option3 =  (TextView)  rootView.findViewById( R.id.RadioButton03);

            stateView = rootView.findViewById(R.id.questionTextView);

            rb1 = rootView.findViewById(R.id.RadioButton01);
            rb2 = rootView.findViewById(R.id.RadioButton02);
            rb3 = rootView.findViewById(R.id.RadioButton03);

            submitButton = (Button) rootView.findViewById(R.id.submitButton);
            submitButton.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (StartNewQuizActivity.class.isInstance(getActivity())) {
                //final int resId = mImageNum;
//                Question question = new Question("georgia", "city1", "city2", "city3", "");
//                //final String description = imageDescriptions[mImageNum - 1];
//                String option1String="atl";
//                String option2String="savannah";
//                String option3String="nyc";

                ((StartNewQuizActivity) getActivity()).loadView(stateView,rb1,rb2,rb3);


            }
        }
    }

    public void loadView(TextView stateView,RadioButton rb1,RadioButton rb2,RadioButton rb3) {
//        String state = question.getState();
//        questionView.setText("What is the capital of " + state + "?");
//
//        option1View.setText("something");
//        option2View.setText("anything");
//        option3View.setText("at all");

        Cursor cursor = questionsData.fetch();
        int size = random.nextInt(49);
        cursor.moveToFirst();
        for (int i = 0; i <= size; i++) {
            cursor.moveToNext();
        }
        state = cursor.getString(0);

//        stateView = findViewById(R.id.questionTextView);
        stateView.setText("What is the capital of " + state + "?");
//        rb1 = findViewById(R.id.RadioButton01);
//        rb2 = findViewById(R.id.RadioButton02);
//        rb3 = findViewById(R.id.RadioButton03);

        int indexradio1 = random.nextInt(4);
        int indexradio2 = random.nextInt(4);
        int indexradio3 = random.nextInt(4);

        while(indexradio1 == 0)
            indexradio1 = random.nextInt(4);

        while(indexradio2 == indexradio1 || indexradio2 == 0)
            indexradio2 = random.nextInt(4);

        while((indexradio3 == indexradio1 || indexradio3 == indexradio2) || indexradio3 == 0)
            indexradio3 = random.nextInt(4);

        Log.i("indexradio1",String.valueOf(indexradio1));
        Log.i("indexradio2",String.valueOf(indexradio2));
        Log.i("indexradio3",String.valueOf(indexradio3));

        capital = cursor.getString(1);

        rb1.setText("1) " + cursor.getString(indexradio1));
        rb2.setText("2) " + cursor.getString(indexradio2));
        rb3.setText("3) " + cursor.getString(indexradio3));

    }








    public int onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        RadioButton rb = null;
        switch(view.getId()) {
            case R.id.RadioButton01:
                if (checked) {
                    rb = (RadioButton) findViewById(R.id.RadioButton01);
                    answer = (String) rb.getText();
                }
                break;
            case R.id.RadioButton02:
                if (checked) {
                    rb = (RadioButton) findViewById(R.id.RadioButton02);
                    answer = (String) rb.getText();
                }
                break;
            case R.id.RadioButton03:
                if (checked) {
                    rb = (RadioButton) findViewById(R.id.RadioButton03);
                    answer = (String) rb.getText();
                }
                break;
        }

        answer = answer.substring(3);
        tempCapital = capital;

        if(answer.equals(capital) && !pointGiven) { // maybe move this to the end of quiz?
            result++;
            pointGiven = true;
        }

        Log.i("answer",answer);
        Log.i("capital",capital);
        Log.i("result",String.valueOf(result));
        Log.i("tempCapital",tempCapital);

        return result;
    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
    private class QuizQuestionDBWriterTask extends AsyncTask<Question, Void, Question> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected Question doInBackground(Question... question) {
            questionsData.storeQuizQuestion(question[0]);
            return question[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute(Question question) {
            super.onPostExecute(question);
            Log.i("StartNewQuizActivity","stored question in db");
        }
    }

    private class ShowResultButtonClickListener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {

            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH);

            String date = formatDate.format(ldt);

            Intent intent = new Intent(view.getContext(),QuizSubmission.class);
            intent.putExtra("result",result);
            intent.putExtra("date",date);
            view.getContext().startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        Log.d(DEBUG_TAG, "StartNewQuizActivity.onResume()");
        // open the database in onResume
        if(questionsData != null)
            questionsData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "StartNewQuizActivity.onPause()" );
        // close the database in onPause
        if(questionsData != null )
            questionsData.close();
        super.onPause();
    }

    // The following activity callback methods are not needed and are for
    // educational purposes only.
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "StartNewQuizActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "StartNewQuizActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "StartNewQuizActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "StartNewQuizActivity.onRestart()" );
        super.onRestart();
    }

}