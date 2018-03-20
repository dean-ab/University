package bgu.spl.a2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */

public class ActorThreadPool {

	private Queue<Thread> threads;
	private Map<String, Queue<Action>> qMap;
	private Map<String, PrivateState> psMap;
	private VersionMonitor vm;
	private boolean isShutDown;
	// True means the Actor is occupied by thread.
	private Map<String, AtomicBoolean> isAvailable;
	private CountDownLatch threadLatch;

	/**
	 * creates a {@link ActorThreadPool} which has nthreads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */
	public ActorThreadPool(int nthreads) {
		// Set up our data structures
		threads = new LinkedList<>();
		qMap = new ConcurrentHashMap<>();
		psMap = new ConcurrentHashMap<>();
		isAvailable = new ConcurrentHashMap<>();
		vm = new VersionMonitor();
		isShutDown = false;
		threadLatch = new CountDownLatch(nthreads);

		// Setting Runnable object for each thread
		for (int i = 0 ; i < nthreads ; i ++) {
			Thread t = new Thread(() -> {
				while (!isShutDown) {
					int tmpVer = vm.getVersion();
					Action toDo = searchForAction();
					if (toDo != null) {
						toDo.handle(this, toDo.getActorId(), psMap.get(toDo.getActorId()));
					} else {
						try {
//							if (isShutDown) {
//								Thread.currentThread().interrupt();
//							}
//							if (isSomeoneAv()) {
//								System.out.println("/////////////////////////////OOOOOOOOOOOOOOOOOOOOO");
//							}
//							vm.inc();
							vm.await(tmpVer);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				Thread.currentThread().interrupt();
				threadLatch.countDown();

			});
			threads.add(t);
		}
	}

	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 */
	public void submit(Action<?> action, String actorId, PrivateState actorState) {
//		System.out.println("start to submit, " + actorId + ", " + action.getActionName());
		isAvailable.putIfAbsent(actorId, new AtomicBoolean(true));
		psMap.putIfAbsent(actorId, actorState);
		qMap.putIfAbsent(actorId, new ConcurrentLinkedDeque<>());
		action.setActorId(actorId);
		qMap.get(actorId).add(action);
		vm.inc();
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	public void shutdown() throws InterruptedException {
		isShutDown = true;

		vm.inc();

		while (threadLatch.getCount() != 0) {
			vm.inc();
		}
	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		if (!isShutDown) {
			for (Thread t : threads) {
				t.start();
			}
		}
	}

	private Action searchForAction () {
		for (Map.Entry<String, Queue<Action>> q : qMap.entrySet()) {
			AtomicBoolean tmpAtomic = isAvailable.get(q.getKey());
			if(tmpAtomic.compareAndSet(true, false)) {
				if (!q.getValue().isEmpty()) {
					return q.getValue().poll();
				} else {
					tmpAtomic.set(true);
				}
			}
		}
		return null;
	}

	public PrivateState getPrivateState(String actorId) {
	    return psMap.get(actorId);
    }

    public void freeActor(String actorID) {
		isAvailable.get(actorID).set(true);
		vm.inc();
	}

	public Map<String, PrivateState> getActors() {
		return psMap;
	}

	public synchronized boolean isSomeoneAv () {
		for (Map.Entry<String, Queue<Action>> q : qMap.entrySet()) {
			boolean tmpAtomic = isAvailable.get(q.getKey()).get();
			if(tmpAtomic == true) {
				if (!q.getValue().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

}
