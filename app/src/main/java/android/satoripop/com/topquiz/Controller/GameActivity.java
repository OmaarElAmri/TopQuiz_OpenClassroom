package android.satoripop.com.topquiz.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.satoripop.com.topquiz.Model.Question;
import android.satoripop.com.topquiz.Model.QuestionBank;
import android.satoripop.com.topquiz.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionText;
    private Button mAnswer1,mAnswer2,mAnswer3,mAnswer4;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;
    private int mNumberOfQuestions;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";
    private boolean mEnableTouchEvents;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_STATE_SCORE,mScore);
        outState.putInt(BUNDLE_STATE_QUESTION,mNumberOfQuestions);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mQuestionText= findViewById(R.id.activity_game_question_text);
        mAnswer1= findViewById(R.id.activity_game_answer1_btn);
        mAnswer2= findViewById(R.id.activity_game_answer2_btn);
        mAnswer3= findViewById(R.id.activity_game_answer3_btn);
        mAnswer4= findViewById(R.id.activity_game_answer4_btn);
        mEnableTouchEvents = true;

//Remlpissage Des Questions
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 5;
        }
        mQuestionBank = this.generateQuestions();
        mCurrentQuestion=mQuestionBank.getQuestion();
        displayQuestion(mCurrentQuestion);

        // Use the tag property to 'name' the buttons
        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);
        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

    }

    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        mQuestionText.setText(question.getQuestion());
        mAnswer1.setText(question.getChoiceList().get(0));
        mAnswer2.setText(question.getChoiceList().get(1));
        mAnswer3.setText(question.getChoiceList().get(2));
        mAnswer4.setText(question.getChoiceList().get(3));

    }

    private QuestionBank generateQuestions() {

        Question question1 = new Question("What is the name of the current french president?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("How many countries are there in the European Union?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Who is the creator of the Android operating system?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("What is the capital of Romania?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);
        Question question10 = new Question("Who is the creator of this Application?",
                Arrays.asList("El PAPPI",
                        "Nogo Jari",
                        "Bolbol Moulinex",
                        "Sabri Las Vegas"),
                3);
        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10));

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

            return mEnableTouchEvents && super.dispatchTouchEvent(ev);

    }

    @Override
    public void onClick(View v) {
        //Get Button Clicked !
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            // Good answer
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            // Wrong answer
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 1000); // LENGTH_SHORT is usually 1 second long





    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }


}








