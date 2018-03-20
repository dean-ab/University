package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Simulator;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.Collection;
import java.util.LinkedList;

public class OpenANewCourseAction extends Action{

    private String courseName;
    private Integer space;
    private LinkedList<String> prerequisites;

    public OpenANewCourseAction(String actorId, String courseName, int space, LinkedList<String> prerequisites) {
        setActorId(actorId);
        setActionName("Open Course");
        this.courseName = courseName;
        this.space = space;
        this.prerequisites = prerequisites;
    }

    protected void start() {
        ((DepartmentPrivateState)getPrivateState()).getCourseList().add(courseName);
        Collection actions = new LinkedList<Action>();
        CoursePrivateState coursePrivateState = new CoursePrivateState();
        Action initCourse = new Action() {
            @Override
            protected void start() {
                setActionName("InitCourse");
                coursePrivateState.addAvailableSpots(space);
                coursePrivateState.setPrequisites(prerequisites);
                complete(true);
            }
        };
        sendMessage(initCourse, courseName, coursePrivateState);
        actions.add(initCourse);
        then(actions, () -> {
            complete(true);
        });

    }
}
