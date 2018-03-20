package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ParticipatingInCourseAction extends Action {

    private String student;
    private String course;
    private Integer grade;

    public ParticipatingInCourseAction(String student, String course) {
        setActionName("Participate In Course");
        this.student = student;
        this.course = course;
        grade = -1;
    }

    public ParticipatingInCourseAction(String student, String course, int grade) {
        setActionName("Participate In Course");
        this.grade = grade;
        this.student = student;
        this.course = course;
    }

    @Override
    protected void start() {
        CoursePrivateState coursePrivateState = (CoursePrivateState) getPrivateState();
        if (coursePrivateState.getAvailableSpots() > 0 & !coursePrivateState.isRegistered(student)) {
            AddCourseToStudentAction addCourseToStudentAction = new AddCourseToStudentAction(coursePrivateState.getPrequisites(), course, grade);
            List<Action> actions = new LinkedList();
            actions.add(addCourseToStudentAction);
            PrivateState ps = getPrivateState(student);
            if (ps == null) {
                ps = new StudentPrivateState();
            }
            sendMessage(addCourseToStudentAction, student, ps);
            then(actions, () -> {
                if( (boolean)actions.get(0).getResult().get() == true) {
                    if (coursePrivateState.registerStudent(student)) {
                        complete(true);
                    } else {
                        if (!coursePrivateState.isRegistered(student)) {
                            RemoveCourseGradeAction cancel = new RemoveCourseGradeAction(course);
                            sendMessage(cancel, student, getPrivateState(student));
                            Collection actions2 = new LinkedList();
                            actions2.add(cancel);
                            then(actions2, () -> {
                                complete(false);
                            });
                        } else {
                            complete(false);
                        }
                    }
                } else {
                    complete(false);
                }
            });
        } else {
            complete(false);
        }
    }
}
