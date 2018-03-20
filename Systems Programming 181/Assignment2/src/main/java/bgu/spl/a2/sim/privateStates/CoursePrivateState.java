package bgu.spl.a2.sim.privateStates;

import java.util.LinkedList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState{

	private Integer availableSpots;
	private Integer registered;
	private List<String> regStudents;
	private List<String> prequisites;

	/**
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		super();
		availableSpots = 0;
		registered = 0;
		regStudents = new LinkedList<String>();
		prequisites = new LinkedList<>();
	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}

	public void addAvailableSpots(Integer toAdd) {
		availableSpots = availableSpots + toAdd;
	}

	public void setPrequisites(LinkedList<String> prequisites) {
		this.prequisites = prequisites;
	}

	public boolean registerStudent(String student) {
		if (availableSpots > 0 & !isRegistered(student)) {
			availableSpots--;
			registered++;
			regStudents.add(student);
			return true;
		}
		return false;
	}

	public boolean isRegistered(String studentName) {

		return (regStudents.contains(studentName));
	}

	public void unregisterStudent(String studentName) {
		regStudents.remove(studentName);
		registered--;
		if (availableSpots != -1) {
			availableSpots++;
		}
	}

}