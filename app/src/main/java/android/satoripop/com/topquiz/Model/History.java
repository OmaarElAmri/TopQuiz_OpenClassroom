package android.satoripop.com.topquiz.Model;

import java.util.ArrayList;
import java.util.List;

public class History {

    List<User> mUserHistory ;

    public History() {

        mUserHistory=new ArrayList<>();
    }

    public History(List<User> userHistory) {
        mUserHistory = userHistory;

    }

    public List<User> getUserHistory() {
        return mUserHistory;
    }

    public void setUserHistory(List<User> userHistory) {
        mUserHistory = userHistory;
    }


    public void addUser(User user)
    {

        mUserHistory.add(user);


    }





    @Override
    public String toString() {
        return "History{" +
                "mUserHistory=" + mUserHistory +
                '}';
    }
}
