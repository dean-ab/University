package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.Dall;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class BaseProtocol implements BidiMessagingProtocol<String> {


    protected int connectionId;
    protected ConnectionsServer connections;
    protected boolean shouldTerminate;
    protected String name;
    protected Dall dall;
    protected boolean isLoggedIn;

    public BaseProtocol () {
        this.shouldTerminate = false;
    }

    @Override
    public void start(int connectionId, Connections connections) {
        this.connectionId = connectionId;
        this.connections = (ConnectionsServer) connections;
        isLoggedIn = false;
    }

    @Override
    public void process(String message) {
        String response;
        String command = message.split(" ")[0];
        switch (command) {
            case "REGISTER":
                response = register(message);
                break;
            case "LOGIN":
                response = login(message);
                break;
            case "SIGNOUT" :
                response = signout(message);
                break;
//            case "REQUEST" :
//                response = request(message);
//                break;
            default:
                response = "ERROR " + command + " failed - invalid command";
        }
        if (!shouldTerminate()) {
            connections.send(connectionId, response);
        }
    }



    @Override
    public boolean shouldTerminate(){
        return shouldTerminate;
    }


    protected String register(String message) {
        if (isLoggedIn)
            return "ERROR registration failed";
        String[] splitMsg = message.split(" ");
        if (splitMsg.length != 3) {
            return "ERROR registration failed";
        }
        //The client performing the register call is already logged in.
        if (!dall.addBaseUser(splitMsg[1], splitMsg[2])) {
            return "ERROR registration failed";
        }
        return "ACK registration succeeded";
    }

    protected String login(String message) {
        if (isLoggedIn) {
            return "ERROR login failed";
        }
        String[] splitMsg = message.split(" ");

        if (splitMsg.length != 3) {
            return "ERROR login failed";
        }

        if (!dall.validLogin(splitMsg[1], splitMsg[2], connections, connectionId)){
            return "ERROR login failed";
        }

        name = splitMsg[1];
        isLoggedIn = true;

        return "ACK login succeeded";
    }

    protected String signout(String message) {
        if (!dall.isLoggedIn(name)) {
            return "ERROR signout failed";
        }
        shouldTerminate = true;
        dall.signout(name, connections, connectionId);
        return "ACK signout succeeded";
    }

    protected abstract String request(String message);

}
