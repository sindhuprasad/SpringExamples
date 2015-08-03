package hello;

/**
 * Created by Sindhu on 8/2/15.
 */
public class Score {
    private int studentID;
    private String courseID;
    private int score;

    public Score(){

    }
    public Score(String courseID, int studentID, int score) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.score = score;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getCourseID() {
        return courseID;
    }

    public int getScore() {
        return score;
    }
}
