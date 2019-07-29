package android.satoripop.com.topquiz.Model;

public class User {

    private String mFirstName;
    private int mScore;

    public User(String firstName, int score) {
        mFirstName = firstName;
        mScore = score;
    }

    public User() {

    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }
}
