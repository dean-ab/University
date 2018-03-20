package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.HashMap;
import java.util.List;

public class AddCourseToStudentAction extends Action {

    private List<String> prequisites;
    private String course;
    private int grade;

    public AddCourseToStudentAction(List<String> prequisites, String course, Integer grade) {
        setActionName("AddCourseToStudentAction");
        this.prequisites = prequisites;
        this.course = course;
        this.grade = grade;
    }

    @Override
    protected void start() {
        StudentPrivateState studentPrivateState = (StudentPrivateState)getPrivateState();

//        System.out.println("student - " + getActorId() + ", course - " + course + " === " + studentPrivateState.getGrades().get(course));
//        if (studentPrivateState.getGrades().get(course) != null && studentPrivateState.getGrades().get(course) == -2) {
//            studentPrivateState.getGrades().remove(course);
//            complete(true);
//            return;
//        }


        if(hasPrequisites()) {
            if(studentPrivateState.getGrades().get(course) == null) {
                studentPrivateState.getGrades().put(course, grade);
            } else {
                if (studentPrivateState.getGrades().get(course) == -2) {
//                    studentPrivateState.getGrades().remove(course);
                } else {
                    studentPrivateState.getGrades().put(course, grade);
                }
            }
            complete(true);
        } else {
            complete(false);
        }
    }

    private boolean hasPrequisites () {
        HashMap<String, Integer> grades = ((StudentPrivateState) getPrivateState()).getGrades();
        for (String pre : prequisites
             ) {
            if(grades.get(pre) == null) {
                return false;
            }
        }
        return true;
    }
}
