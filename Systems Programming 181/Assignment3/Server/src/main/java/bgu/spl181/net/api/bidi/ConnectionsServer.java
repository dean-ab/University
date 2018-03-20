package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.ConnectionHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsServer<T> implements Connections<T> {

    private Map<Integer, ConnectionHandler<T>> connectionsMap;
    private Map<Integer, Boolean> loggedInMap;
    private Integer countId;

    public ConnectionsServer() {
        this.connectionsMap = new ConcurrentHashMap<>();
        this.loggedInMap = new ConcurrentHashMap<>();
        this.countId = 0;
    }

    @Override
    public boolean send(int connectionId, T msg) {
        ConnectionHandler client = connectionsMap.get(connectionId);
        if (client == null) {
            return false;
        }
        client.send(msg);
        return true;
    }

    // Broadcast message only to logged in clients.
    public void broadcastToUsers(T msg) {
        // Running over all the clients connected to the server
        for (Map.Entry<Integer, ConnectionHandler<T>> entry:
                connectionsMap.entrySet() ) {
            // Checks if the user has logged in
            if (loggedInMap.get(entry.getKey())) {
                // if yes, then send him the broadcast message.
                entry.getValue().send(msg);
            }
        }
    }

    @Override
    public void broadcast(T msg) {
        for (Map.Entry<Integer, ConnectionHandler<T>> entry :
             connectionsMap.entrySet()) {
            entry.getValue().send(msg);
        }
    }

    @Override
    public void disconnect(int connectionId) {
        ConnectionHandler client = connectionsMap.get(connectionId);
        if (client == null) {
            return;
        }
        connectionsMap.remove(connectionId);
        loggedInMap.remove(connectionId);
        client.send("ACK signout succeeded");
    }

    public Integer addUser(ConnectionHandler<T> connectionHandler) {
        Integer userId = countId++;
        connectionsMap.put(userId, connectionHandler);
        loggedInMap.put(userId, false);
        return userId;
    }

    public void logInUser(int connectionId) {
        if (!loggedInMap.containsKey(connectionId)) {
            return;
        }
        loggedInMap.put(connectionId, true);
    }

}
