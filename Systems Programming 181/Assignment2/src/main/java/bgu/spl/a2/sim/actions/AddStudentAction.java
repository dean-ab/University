package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.Collection;
import java.util.LinkedList;

public class AddStudentAction extends Action {

    String studentName;

    public AddStudentAction(String studentName) {
        setActionName("Add Student");
        this.studentName = studentName;
    }

    @Override
    protected void start() {
        DepartmentPrivateState privateState = (DepartmentPrivateState) getPrivateState();
        privateState.getStudentList().add(studentName);
        Collection actions = new LinkedList();
        if(getPrivateState(studentName) == null) {
            Action initStudent = new Action() {
                @Override
                protected void start() {
                    setActionName("InitStudent");
                    complete(true);
                }
            };
            actions.add(initStudent);
            sendMessage(initStudent, studentName, new StudentPrivateState());
            then(actions, () -> {
                complete(true);
            });
        } else {
            complete(true);
        }
    }
}
