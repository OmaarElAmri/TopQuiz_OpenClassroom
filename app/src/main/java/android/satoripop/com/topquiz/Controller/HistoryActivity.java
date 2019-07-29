package android.satoripop.com.topquiz.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.satoripop.com.topquiz.Model.User;
import android.satoripop.com.topquiz.R;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {


    private List<User> mListUser =new ArrayList<>();
    private ListView mListView;
    private Button mButtonPerAlphabet;
    private Button mButtonPerScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mButtonPerAlphabet=findViewById(R.id.activity_history_btn_az);
        mButtonPerScore=findViewById(R.id.activity_history_btn_score);
        mListView=findViewById(R.id.activity_history_list_view);

        Gson gson =new Gson();
        Intent intent=getIntent();
        String History=intent.getStringExtra("History");
        Type type=new TypeToken<ArrayList<User>>(){}.getType();
        mListUser=  gson.fromJson(History,type);




        DisplayByHigh();




        mButtonPerScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DisplayByHigh();

            }
        });

        mButtonPerAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             DisplayByAlpha();


            }
        });








}

    private void DisplayByAlpha() {
        Collections.sort(mListUser, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });
        if(mListUser.size()>5)
            mListUser=mListUser.subList(0,5);
        UserAdapter adapter= new UserAdapter(getApplicationContext(),R.layout.item_list,mListUser);
        mListView.setAdapter(adapter);
    }

    private void DisplayByHigh() {

        Collections.sort(mListUser, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getScore()-o1.getScore();
            }
        });
        if(mListUser.size()>5)
            mListUser=mListUser.subList(0,5);
        UserAdapter adapter= new UserAdapter(getApplicationContext(),R.layout.item_list,mListUser);
        mListView.setAdapter(adapter);
    }
}
