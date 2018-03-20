package bgu.spl.a2.sim.mainJSON;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phase3 implements Phase {

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

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getCourse() {
        return course;
    }

    @Override
    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public int getSpace() {
        return space;
    }

    @Override
    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public LinkedList<String> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public void setPrerequisites(LinkedList<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Override
    public String getStudent() {
        return student;
    }

    @Override
    public void setStudent(String student) {
        this.student = student;
    }

    @Override
    public LinkedList<String> getGrade() {
        return grade;
    }

    @Override
    public void setGrade(LinkedList<String> grade) {
        this.grade = grade;
    }

    @Override
    public List<String> getStudents() {
        return students;
    }

    @Override
    public void setStudents(List<String> students) {
        this.students = students;
    }

    @Override
    public String getComputer() {
        return computer;
    }

    @Override
    public void setComputer(String computer) {
        this.computer = computer;
    }

    @Override
    public List<String> getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public LinkedList<String> getPreferences() {
        return preferences;
    }

    @Override
    public void setPreferences(LinkedList<String> preferences) {
        this.preferences = preferences;
    }

}