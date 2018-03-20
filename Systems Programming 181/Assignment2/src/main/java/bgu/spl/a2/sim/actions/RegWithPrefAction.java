package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.Collection;
import java.util.LinkedList;

public class RegWithPrefAction extends Action {

    LinkedList<String> courses;
    LinkedList<Integer> grades;

    public RegWithPrefAction (LinkedList<String> courses) {
        setActionName("Register With Preferences");
        this.courses = courses;
        grades = new LinkedList<>();
        for (int i =0; i < courses.size(); i++) {
            grades.add(-1);
        }
    }

    public RegWithPrefAction (LinkedList<String> courses, LinkedList<Integer> grades) {
        setActionName("Register With Preferences");
        this.courses = courses;
        this.grades = grades;
    }

    @Override
    protected void start() {
        if(courses.isEmpty()) {
            complete(false);
            return;
        }
        String course = courses.poll();
        Integer grade = grades.poll();
        Collection actions = new LinkedList();
        ParticipatingInCourseAction participatingInCourse = new ParticipatingInCourseAction(getActorId(), course, grade);
        actions.add(participatingInCourse);
        Promise result = sendMessage(participatingInCourse, course, getPrivateState(course));
        then(actions, () -> {
            if ((boolean)result.get() == false) {
                start();
            } else {
                complete(true);
            }
        });

    }
}