package bgu.spl.a2.sim;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {

    private HashMap<String, SuspendingMutex> computerMap;

    public Warehouse () {
        computerMap = new HashMap<>();

    }

    private static class WarehouseHolder {
        private static Warehouse instance = new Warehouse();
    }

    public static Warehouse getInstance() {
        return WarehouseHolder.instance;
    }

    public void addComputer(Computer computer) {
        computerMap.put(computer.getComputerType(), new SuspendingMutex(computer));
    }

    public SuspendingMutex getSuspendingMutex(String computerType) {
        return computerMap.get(computerType);
    }

}