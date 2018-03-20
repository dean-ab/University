package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.LinkedList;
import java.util.List;

public class NewPlaceInCourseAction extends Action {

    private Integer spots;

    public NewPlaceInCourseAction(Integer newSpots) {
        setActionName("Add Spaces");
        this.spots = newSpots;
    }

    @Override
    protected void start() {
        CoursePrivateState coursePrivateState = (CoursePrivateState) getPrivateState();

        coursePrivateState.addAvailableSpots(spots);
        complete(true);
    }
}