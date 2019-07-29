package android.satoripop.com.topquiz.Controller;

import android.content.Context;
import android.satoripop.com.topquiz.Model.User;
import android.satoripop.com.topquiz.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class UserAdapter extends ArrayAdapter<User> {
private Context mContext;
private int mResource;

    public UserAdapter(Context context, int resource ,List<User> objects) {
        super(context, resource, objects);

        mContext=context;
        mResource=resource;

    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

    String name = getItem(position).getFirstName();
    int score = getItem(position).getScore();

    User user = new User(name, score);

    LayoutInflater inflater = LayoutInflater.from(mContext);
    convertView = inflater.inflate(mResource, parent, false);

    TextView pseudoView = convertView.findViewById(R.id.pseudo);
    TextView scoreView = convertView.findViewById(R.id.score);

    pseudoView.setText(name);
    scoreView.setText("Score :" + String.valueOf(score));


        return convertView;


    }
}
