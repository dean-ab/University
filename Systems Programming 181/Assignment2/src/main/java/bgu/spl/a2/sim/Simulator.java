
package bgu.spl.a2.sim;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.mainJSON.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.*;
import org.json.simple.JsonObject;
import sun.awt.image.ImageWatched;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

	private static ActorThreadPool actorThreadPool;
	private static MainJSON parsedJson;
	private static CountDownLatch globalLatch = new CountDownLatch(1);

	/**
	 * Begin the simulation Should not be called before attachActorThreadPool()
	 */
	public static void start() {
		actorThreadPool.start();

		submitPhase(1);
		submitPhase(2);
		submitPhase(3);
		globalLatch.countDown();
	}

	private static void submitPhase(int phaseNo) {

		switch (phaseNo) {
			case 1:
				List<Phase1> phase1List = parsedJson.getPhase1();
				submitPhase1(phase1List);
				break;
			case 2:
				List<Phase2> phase2List = parsedJson.getPhase2();
				submitPhase(phase2List);
				break;
			case 3:
				List<Phase3> phase3List = parsedJson.getPhase3();
				submitPhase(phase3List);
				break;

		}

	}

	private static void submitPhase1(List<Phase1> phase1List) {
		String actionName;
		String department;
		String course;
		int space;
		LinkedList<String> prerequisites;
		String student;

		CountDownLatch l = new CountDownLatch(phase1List.size());


		for (Phase1 p : phase1List) {
			actionName = p.getAction();
			department = p.getDepartment();
			course = p.getCourse();
			space = p.getSpace();
			prerequisites = p.getPrerequisites();
			student = p.getStudent();

			switch (actionName) {
				case "Open Course":
					OpenANewCourseAction openCourse = new OpenANewCourseAction(department, course, space, prerequisites);
					openCourse.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(openCourse, department, new DepartmentPrivateState());
					break;
				case "Add Student":
					AddStudentAction addStudent = new AddStudentAction(student);
					addStudent.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(addStudent, department, new DepartmentPrivateState());
					break;
			}

		}
		try {
			l.await();
		} catch (InterruptedException ex) {
		}

	}


	private static void submitPhase(List phaseList) {
		String actionName;
		String department;
		String course;
		int space;
		int number;
		LinkedList<String> prerequisites;
		String student;
		LinkedList<Integer> grades;
		List<String> studentList;
		String computer;
		List<String> conditions;
		LinkedList<String> preferences;

		CountDownLatch l = new CountDownLatch(phaseList.size());

		for (Object q : phaseList) {
			Phase p = (Phase) q;
			actionName = p.getAction();
			department = p.getDepartment();
			course = p.getCourse();
			space = p.getSpace();
			number = p.getNumber();
			prerequisites = p.getPrerequisites();
			student = p.getStudent();
			grades = gradesToIntegers(p.getGrade());
			studentList = p.getStudents();
			computer = p.getComputer();
			conditions = p.getConditions();
			preferences = p.getPreferences();


			switch (actionName) {
				case "Open Course":
					OpenANewCourseAction openCourse = new OpenANewCourseAction(department, course, space, prerequisites);
					openCourse.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(openCourse, department, new DepartmentPrivateState());
					break;
				case "Add Student":
					AddStudentAction addStudent = new AddStudentAction(student);
					addStudent.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(addStudent, department, new DepartmentPrivateState());
					break;
				case "Participate In Course":
					ParticipatingInCourseAction participatingInCourse = new ParticipatingInCourseAction(student, course, grades.get(0).intValue());
					participatingInCourse.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(participatingInCourse, course, new CoursePrivateState());
					break;
				case "Add Spaces":
					NewPlaceInCourseAction openingNewPlacesInACourse =
							new NewPlaceInCourseAction(number);
					openingNewPlacesInACourse.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(openingNewPlacesInACourse, course, new CoursePrivateState());
					break;
				case "Register With Preferences":
					RegWithPrefAction registerWithPreferences = new RegWithPrefAction(preferences, grades);
					registerWithPreferences.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(registerWithPreferences, student, new StudentPrivateState());
					break;

				case "Unregister":
					UnregisterStudentAction unregister = new UnregisterStudentAction(student, course);
					unregister.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(unregister, course, new CoursePrivateState());
					break;
				case "Close Course":
					CloseACourseAction closeACourse = new CloseACourseAction(course);
					closeACourse.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(closeACourse, department, new DepartmentPrivateState());
					break;
				case "Administrative Check":
					AdminObligAction checkAdministrativeObligations =
							new AdminObligAction(studentList, conditions, computer);
					checkAdministrativeObligations.getResult().subscribe(() -> l.countDown());
					actorThreadPool.submit(checkAdministrativeObligations, department, new DepartmentPrivateState());
					break;

			}
		}
		try {
			l.await();
		} catch (InterruptedException ex) {
		}
	}

	/**
	 * attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
	 *
	 * @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
	 */
	public static void attachActorThreadPool(ActorThreadPool myActorThreadPool) {
		actorThreadPool = myActorThreadPool;
	}

	/**
	 * shut down the simulation
	 * returns list of private states
	 */
	public static HashMap<String, PrivateState> end() {
		try {
			actorThreadPool.shutdown();
		} catch (IllegalMonitorStateException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new HashMap<>(actorThreadPool.getActors());
	}

	/**
	 * Parsing a Json file into a POJO using GSON
	 */
	public static MainJSON parseJson(String filePath) {

		Gson gson = new Gson();

		// Get the file and parse it into classes
		try (FileReader reader = new FileReader(new File(filePath))) {

			JsonParser parser = new JsonParser();
			JsonElement tree = parser.parse(reader);

			MainJSON parsedJson = gson.fromJson(tree, MainJSON.class);

			return parsedJson;

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void addComputers() {
		// Add given computers to warehouse
		for (JsonComp cp : parsedJson.getComputers()) {

			Computer toAdd = new Computer(cp.getType());
			toAdd.setFailSig(Long.parseLong(cp.getSigFail()));
			toAdd.setSuccessSig(Long.parseLong(cp.getSigSuccess()));

			Warehouse.getInstance().addComputer(toAdd);
		}
	}

	public static void main(String[] args) {

		// Get filepath from args and parse the file
		String filePath = args[0];
		parsedJson = parseJson(filePath);


		// Construct new actor thread pool with n threads and attach it
		ActorThreadPool actorThreadPool = new ActorThreadPool(parsedJson.getThreads());
		attachActorThreadPool(actorThreadPool);

		addComputers();

		start();


		try {
			globalLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		HashMap<String, PrivateState> SimulationResult ;
		SimulationResult = end() ;

		///////
//		LinkedList<DepartmentPrivateState> departments = new LinkedList();
//		LinkedList<CoursePrivateState> courses = new LinkedList();
//		LinkedList<StudentPrivateState> students = new LinkedList();
//
//		for (Map.Entry<String, PrivateState> entry:
//			 SimulationResult.entrySet()) {
//			if (entry.getValue() instanceof DepartmentPrivateState) {
//				departments.add((DepartmentPrivateState)entry.getValue());
//			}
//			if (entry.getValue() instanceof C)
//		}

		//////

		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream( "result.ser");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(fout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			oos.writeObject(SimulationResult) ;
		} catch (IOException e) {
			e.printStackTrace();
		}



            
	}

	private static LinkedList<Integer> gradesToIntegers(List<String> grades){
		if (grades == null)
			return null;

		LinkedList<Integer> tmp = new LinkedList<>();
		for (String g: grades){
			if(g.equals("-"))
				tmp.add(-1);
			else
				tmp.add(Integer.parseInt(g));
		}
		return tmp;
	}


}