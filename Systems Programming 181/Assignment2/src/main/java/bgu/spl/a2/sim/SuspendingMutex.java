package bgu.spl.a2.sim;
import bgu.spl.a2.Promise;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 *
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {

	private AtomicBoolean isFree;
	private LinkedList<Promise> promises;
	private Computer computer;

	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		isFree = new AtomicBoolean(true);
		promises = new LinkedList<>();
		this.computer = computer;

	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 *
	 * @return a promise for the requested computer
	 */
	public synchronized Promise<Computer> down(){
		Promise<Computer> promise = new Promise<>();
		if(isFree.compareAndSet(true, false)) {
			promise.resolve(computer);
		} else {
			promises.add(promise);
		}
		return promise;
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public synchronized void up(){
		if (promises.isEmpty()) {
			isFree.set(true);
			return;
		} else {
			Promise pro = promises.poll();
			pro.resolve(computer);
		}
	}
}