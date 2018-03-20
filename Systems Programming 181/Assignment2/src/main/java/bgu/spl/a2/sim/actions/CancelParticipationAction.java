package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.Collection;
import java.util.LinkedList;

public class CancelParticipationAction extends Action {


    // 1. Check if the student enrolled to the course.
    // 2. Yes:
    //      a) Update the students' list of the course.
    //      b) remove the course from the grades sheet of the student
    //      c) increases the number of available spaces

    private String student;
    private String course;

    public CancelParticipationAction(String student, String course) {
        setActionName("Cancel");
        this.student = student;
        this.course = course;
    }

    @Override
    protected void start() {
        CoursePrivateState coursePrivateState = (CoursePrivateState) getPrivateState();
        if (coursePrivateState.isRegistered(student)) {
            coursePrivateState.unregisterStudent(student);
            Collection actions = new LinkedList<>();
            RemoveCourseGradeAction removeCourseGradeAction = new RemoveCourseGradeAction(course);
            actions.add(removeCourseGradeAction);
            StudentPrivateState studentPrivateState = (StudentPrivateState) pool.getPrivateState(student);
            sendMessage(removeCourseGradeAction, student, studentPrivateState);
            then (actions, () -> {
                complete(true);
            });
        } else {
            complete(false);
        }
    }
}