package bgu.spl.a2.sim.mainJSON;


import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phase2 implements Phase {

    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("Course")
    @Expose
    private String course;
    @SerializedName("Space")
    @Expose
    private int space;
    @SerializedName("Number")
    @Expose
    private int number;
    @SerializedName("Prerequisites")
    @Expose
    private LinkedList<String> prerequisites = null;
    @SerializedName("Student")
    @Expose
    private String student;
    @SerializedName("Grade")
    @Expose
    private LinkedList<String> grade = null;
    @SerializedName("Students")
    @Expose
    private List<String> students = null;
    @SerializedName("Computer")
    @Expose
    private String computer;
    @SerializedName("Conditions")
    @Expose
    private List<String> conditions = null;
    @SerializedName("Preferences")
    @Expose
    private LinkedList<String> preferences = null;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int space) {
        this.number = number;
    }

    public LinkedList<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(LinkedList<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public LinkedList<String> getGrade() {
        return grade;
    }

    public void setGrade(LinkedList<String> grade) {
        this.grade = grade;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public LinkedList<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(LinkedList<String> preferences) {
        this.preferences = preferences;
    }

}