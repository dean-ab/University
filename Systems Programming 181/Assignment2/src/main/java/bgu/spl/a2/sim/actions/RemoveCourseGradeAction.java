package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;


public class RemoveCourseGradeAction extends Action {

    private String course;

    public RemoveCourseGradeAction(String course) {
        setActionName("RemoveCourseGradeAction");
        this.course = course;
    }

    @Override
    protected void start() {
        StudentPrivateState studentPrivateState = (StudentPrivateState) getPrivateState();

        studentPrivateState.removeGrade(course);
//        if (studentPrivateState.getGrades().get(course) == null) {
//            studentPrivateState.getGrades().put(course, -2);
//        } else {
//            studentPrivateState.removeGrade(course);
//        }
        complete(true);
    }
}