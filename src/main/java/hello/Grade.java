package hello;

public class Grade {
    private int studentID;
    private String courseID;
    private int score;
    private String grade;

    public Grade(int studentID, String courseID, int score, String grade) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.score = score;
        this.grade = grade;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getCourseID() {
        return courseID;
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

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }

//    @Override
//    public String toString() {
//        return "firstName: " + firstName + ", lastName: " + lastName;
//    }

}
