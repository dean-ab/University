
public class EngineeringStudent extends Student { // You need to add inheritance
	
	private static final int ENGINEER_CREDIT_POINTS = 160;

	public EngineeringStudent(String firstName, String lastName, int id) {
		super(firstName, lastName, id);
	}
	
	// Returns the total credit points required for degree
	public int getTotalCreditRequired(){
		return ENGINEER_CREDIT_POINTS;
	}

}
