package bgu.spl.a2;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * an abstract class that represents an action that may be executed using the
 * {@link ActorThreadPool}
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R> the action result type
 */
public abstract class Action<R> {

    int co;

    private String actorId;
    private boolean isStarted = false;
    protected ActorThreadPool pool;
    private AtomicInteger counter;
    protected Promise<R> result = new Promise<>();
    private callback theLastCallback;
    private String actionName;
    private PrivateState privateState;

    /**
     * start handling the action - note that this method is protected, a thread
     * cannot call it directly.
     */
    protected abstract void start();


    /**
     *
     * start/continue handling the action
     *
     * this method should be called in order to start this action
     * or continue its execution in the case where it has been already started.
     *
     * IMPORTANT: this method is package protected, i.e., only classes inside
     * the same package can access it - you should *not* change it to
     * public/private/protected
     *
     */
    /*package*/ final void handle(ActorThreadPool pool, String actorId, PrivateState actorState) {
        if (!isStarted) {
            privateState = actorState;
            this.pool = pool;
            isStarted = true;
            this.actorId = actorId;
            start();
        } else {
            theLastCallback.call();
        }
        pool.freeActor(actorId);
    }


    /**
     * add a callback to be executed once *all* the given actions results are
     * resolved
     *
     * Implementors note: make sure that the callback is running only once when
     * all the given actions completed.
     *
     * @param actions
     * @param callback the callback to execute once all the results are resolved
     */
    protected final void then(Collection<? extends Action<?>> actions, callback callback) {
        if(actions.size() == 0){
            callback.call();
            return;
        }
        theLastCallback = callback;
        counter = new AtomicInteger(actions.size());
        for (Action action : actions) {
            action.getResult().subscribe(() -> {
                int val;
                do {
                    val = counter.get();
                } while (!counter.compareAndSet(val, val-1));
                if(val-1 == 0) {
                    pool.submit(this, this.getActorId(), pool.getPrivateState(this.getActorId()));
                }
            });
        }
    }

    /**
     * resolve the internal result - should be called by the action derivative
     * once it is done.
     *
     * @param result - the action calculated result
     */
    protected final void complete(R result) {
        this.result.resolve(result);
        getPrivateState().addRecord(actionName);
    }

    /**
     * @return action's promise (result)
     */
    public final Promise<R> getResult() {
        return result;
    }

    /**
     * send an action to an other actor
     *
     * @param action
     * 				the action
     * @param actorId
     * 				actor's id
     * @param actorState
     * 				actor's private state (actor's information)
     *
     * @return promise that will hold the result of the sent action
     */
    public Promise<?> sendMessage(Action<?> action, String actorId, PrivateState actorState){
        pool.submit(action, actorId, actorState);
        return action.getResult();
    }

    /**
     * set action's name
     * @param actionName
     */
    public void setActionName(String actionName){
        this.actionName = actionName;
    }

    /**
     * @return action's name
     */
    public String getActionName(){
        return actionName;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorId() {
        return actorId;
    }

    public PrivateState getPrivateState() {
        return privateState;
    }

    public PrivateState getPrivateState(String actorId){
        return pool.getPrivateState(actorId);
    }
}
