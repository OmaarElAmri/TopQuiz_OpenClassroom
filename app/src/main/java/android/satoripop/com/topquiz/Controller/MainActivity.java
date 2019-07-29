package android.satoripop.com.topquiz.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.satoripop.com.topquiz.Model.History;
import android.satoripop.com.topquiz.Model.User;
import android.satoripop.com.topquiz.R;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton,mHistoryButton;
    private User mUser;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;

    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";
    public static final String PREF_KEY_LIST_HISTORY = "PREF_KEY_LIST_HISTORY";

    private History mHistory;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            mUser.setScore(score);
            mHistory.addUser(mUser);


            Gson gson =new Gson();
            String json =gson.toJson(mHistory.getUserHistory());
            mPreferences.edit().putString(PREF_KEY_LIST_HISTORY,json).apply();


            greetUser();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences=getPreferences(MODE_PRIVATE);
        //Referencement des objet
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mHistoryButton= findViewById(R.id.activity_main_history_btn);
        //Disable Buttons
        mPlayButton.setEnabled(false);

        //Init
        mUser = new User();
        mHistory=new History();







        greetUser();



        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPlayButton.setEnabled(s.toString().length() != 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.setFirstName(mNameInput.getText().toString());
                startActivityForResult(new Intent(MainActivity.this, GameActivity.class), GAME_ACTIVITY_REQUEST_CODE);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstName()).apply();
                greetUser();
            }
        });


        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent intent=  new Intent(MainActivity.this,HistoryActivity.class);
              Gson gson =new Gson();
              String json =gson.toJson(mHistory.getUserHistory());
              intent.putExtra("History", json);
              startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        init_History(load_History());
        Enable_Disable_History();





    }

    private void Enable_Disable_History() {

        if(mHistory.getUserHistory().size()!=0)
        mHistoryButton.setEnabled(true);
        else
        mHistoryButton.setEnabled(false);



    }


    private List<User> load_History()
    {
        Gson gson =new Gson();
        String json =gson.toJson(mHistory.getUserHistory());
        json=  mPreferences.getString(PREF_KEY_LIST_HISTORY,null);
        Type type=new TypeToken<ArrayList<User>>(){}.getType();
        return gson.fromJson(json,type);

    }


    private void init_History(List<User> history) {
        if(history==null)
        {mHistory=new History();}
        else
        {mHistory=new History(history);}
    }

    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + "!\nYour last score was " + score
                    + ", will you do better this time?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);

        }
    }





}

