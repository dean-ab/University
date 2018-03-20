package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;

public class CheckStudentsAction extends Action {

    private String computer;
    private List<String> courses;

    public CheckStudentsAction(String computer, List<String> courses) {
        setActionName("CheckStudentsAction");
        this.computer = computer;
        this.courses = courses;
    }

    @Override
    protected void start() {
        StudentPrivateState studentPrivateState = (StudentPrivateState) getPrivateState();
        Promise<Computer> promise = Warehouse.getInstance().getSuspendingMutex(computer).down();
        promise.subscribe(() -> {
            long sign = promise.get().checkAndSign(courses, studentPrivateState.getGrades());
            studentPrivateState.setSignature(sign);
            Warehouse.getInstance().getSuspendingMutex(computer).up();
        });
        complete(true);
    }
}