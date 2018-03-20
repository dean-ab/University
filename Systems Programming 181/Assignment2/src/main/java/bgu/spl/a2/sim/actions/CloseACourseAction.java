package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CloseACourseAction extends Action {

    // This action should close a course. Should unregister all the registered students in the
    //course and remove the course from the department courses' list and from the grade sheets of the
    //students. The number of available spaces of the closed course should be updated to -1. DO NOT
    //remove its actor. After closing the course, all the request for registration should be denied.

    private String course;

    public CloseACourseAction(String course) {
        setActionName("Close Course");
        this.course = course;
    }

    @Override
    protected void start() {
        DepartmentPrivateState departmentPrivateState = (DepartmentPrivateState) getPrivateState();
        departmentPrivateState.getCourseList().remove(course);
        Collection actions = new LinkedList<>();

        // Set the available spots to -1
        Action setAvailableSlotsToMinusOne = new Action() {
            @Override
            protected void start() {
                setActionName("setAvailableSlotsToMinusOne");
                CoursePrivateState coursePrivateState = (CoursePrivateState) getPrivateState();
                coursePrivateState.addAvailableSpots(-(coursePrivateState.getAvailableSpots()+1));
                complete(true);
            }
        };

        // Unregister all students from the course and from the grade sheet.
        Action unregisterAllStudents = new Action() {
            @Override
            protected void start() {
                setActionName("unregisterAllStudents");
                Collection actions2 = new LinkedList();
                CoursePrivateState coursePrivateState = (CoursePrivateState) getPrivateState();
                List<String> regStudents = coursePrivateState.getRegStudents();
                for (String studentId : regStudents ) {
                    UnregisterStudentAction unregister = new UnregisterStudentAction(studentId, course);
                    actions2.add(unregister);
                    sendMessage(unregister, course, pool.getPrivateState(course));
                }
                then(actions2, () -> {
                    complete(true);
                });
            }
        };

        actions.add(setAvailableSlotsToMinusOne);
        actions.add(unregisterAllStudents);
        sendMessage(setAvailableSlotsToMinusOne, course, pool.getPrivateState(course));
        sendMessage(unregisterAllStudents, course, pool.getPrivateState(course));
        then(actions, () -> {
            complete(true);
        });
    }
}