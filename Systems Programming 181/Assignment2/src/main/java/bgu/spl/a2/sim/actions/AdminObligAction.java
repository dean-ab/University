package bgu.spl.a2.sim.actions;

        import bgu.spl.a2.Action;
        import bgu.spl.a2.PrivateState;
        import bgu.spl.a2.sim.Computer;
        import bgu.spl.a2.sim.SuspendingMutex;
        import bgu.spl.a2.sim.Warehouse;
        import bgu.spl.a2.sim.privateStates.StudentPrivateState;

        import java.util.Collection;
        import java.util.LinkedList;
        import java.util.List;

public class AdminObligAction extends Action{

    private List<String> students;
    private List<String> courses;
    private String computerType;

    public AdminObligAction(List<String> students, List<String> courses, String computerType) {
        setActionName("Administrative Check");
        this.students = students;
        this.courses = courses;
        this.computerType = computerType;
    }

    @Override
    protected void start() {
        Collection actions = new LinkedList();
        for (String student: students
             ) {
            Action checkStudent = new CheckStudentsAction(computerType, courses);
            actions.add(checkStudent);
            PrivateState ps = pool.getPrivateState(student);
            if(ps == null) {
                ps = new StudentPrivateState();
            }
            sendMessage(checkStudent, student, ps);
        }
        then(actions, () -> {
            complete(true);
        });

    }
}