package bgu.spl.a2.sim.mainJSON;

import java.util.LinkedList;
import java.util.List;

public interface Phase {
    String getAction();

    void setAction(String action);

    String getDepartment();

    void setDepartment(String department);

    String getCourse();

    void setCourse(String course);

    int getSpace();

    void setSpace(int space);

    int getNumber();

    void setNumber(int number);

    LinkedList<String> getPrerequisites();

    void setPrerequisites(LinkedList<String> prerequisites);

    String getStudent();

    void setStudent(String student);

    LinkedList<String> getGrade();

    void setGrade(LinkedList<String> grade);

    List<String> getStudents();

    void setStudents(List<String> students);

    String getComputer();

    void setComputer(String computer);

    List<String> getConditions();

    void setConditions(List<String> conditions);

    LinkedList<String> getPreferences();

    void setPreferences(LinkedList<String> preferences);
}
