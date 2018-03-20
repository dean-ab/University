package bgu.spl181.net.api.bidi;

import bgu.spl181.net.srv.Dall;

import java.util.LinkedList;
import java.util.List;


public class MVRProtocol extends BaseProtocol {

    private String broadcast;

    public MVRProtocol (Dall dall) {
        super();
        broadcast = null;
        this.dall = dall;
    }

    @Override
    public void start(int connectionId, Connections connections) {
        super.start(connectionId, connections);
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
            case "SIGNOUT":
                response = signout(message);
                break;
            case "REQUEST":
                response = request(message);
                break;
            default:
                response = "ERROR " + command + " failed - invalid command";
        }
        if (!shouldTerminate()) {
            connections.send(connectionId, response);
        }
        if (broadcast != null) {
            connections.broadcastToUsers(broadcast);
            broadcast = null;
        }
    }

    @Override
    protected String register(String message) {
        if (isLoggedIn)
            return "ERROR registration failed";
        String[] splitMsg = message.split(" ");
        if (splitMsg.length != 4) {
            return "ERROR registration failed";
        }

        splitMsg[3] = splitMsg[3].split("\"")[1];

        //The client performing the register call is already logged in.
        if (!dall.addUser(splitMsg[1], splitMsg[2], splitMsg[3])) {
            return "ERROR registration failed";
        }
        return "ACK registration succeeded";
    }

    @Override
    protected String request(String message) {
        String[] splitMsg = message.split(" ");
        String name = splitMsg[1];
        String output = "";
        String movie;

        switch (name) {
            case "balance" :
                if (splitMsg[2].equals("info")) {
                    output = balanceInfo();
                }
                if (splitMsg[2].equals("add")) {
                    output = addBalance(splitMsg[3]);
                }
                break;
            case "info" :
                if (splitMsg.length > 2) { // Movie was mentioned
                    movie = message.split("\"")[1];
                    output = infoMovie(movie);
                } else {
                    output = infoAllMovies();
                }
                break;
            case "rent" :
                movie = message.split("\"")[1];
                output = rentMovie(movie);
                break;

            case "return" :
                movie = message.split("\"")[1];
                output = returnMovie(movie);
                break;
            case "addmovie" :
                output = addMovie(message);
                break;
            case "remmovie" :
                movie = message.split("\"")[1];
                output = removeMovie(movie);
                break;
            case "changeprice" :
                output = changePrice(message);
                break;

            default:
                output = "ERROR request " + name + "failed - invalid command";

        }
        return output;
    }

    private String rentMovie(String movieName) {
        if (!isLoggedIn) {
            return "ERROR request rent failed";
        }
        if(dall.rentMovie(movieName, name)) {
            broadcast = (dall.getMovieInfo3(movieName));
            return "ACK rent \"" + movieName + "\" success";
        }
        return "ERROR request rent failed";
    }

    private String balanceInfo() {
        if (!isLoggedIn) {
            return "ERROR request balance info failed";
        }
        String balance = dall.getBalance(name);
        if (balance == null) {
            return "ERROR request balance info failed";
        }
        return "ACK balance " + balance;
    }

    private String addBalance(String toAdd) {
        if (!isLoggedIn) {
            return "ERROR request balance add failed";
        }
        int addToBalance = Integer.parseInt(toAdd);
        String newBalance = dall.addToBalance(name, addToBalance);
        if (newBalance == null) {
            return "ERROR request balance add failed";
        }
        return "ACK balance " + newBalance + " added " + addToBalance;
    }

    private String infoMovie(String movieName) {
        if (!isLoggedIn) {
            return "ERROR request info failed";
        }
        String output = dall.getMovieInfo(name, movieName);
        if (output == null) {
            return "ERROR request info failed";
        }
        return output;
    }

    private String infoAllMovies() {
        if (!isLoggedIn) {
            return "ERROR request info failed";
        }
        String list = "";
        for (String mov :
                dall.getMoviesList()) {
            list = list + " " + mov;
        }
        if (list.length() > 0) {
            list = list.substring(1);
        }
        return "ACK info " + list;
    }

    private String returnMovie(String movieName) {
        if (!isLoggedIn)  {
            return "ERROR request return failed";
        }
        if (dall.returnMovie(name, movieName)) {
            broadcast = (dall.getMovieInfo3(movieName));
            return "ACK return \"" + movieName + "\" success";
        }
        return "ERROR request return failed";
    }

    private String addMovie(String message) {
        if (!isLoggedIn) {
            return "ERROR request addmovie failed";
        }
        if (!dall.isAdmin(name)) {
            return "ERROR request addmovie failed";
        }
        String[] splitByQoute = message.split("\"");
        String movieName = splitByQoute[1];
        int amount = Integer.parseInt(splitByQoute[2].split(" ")[1]);
        int price = Integer.parseInt(splitByQoute[2].split(" ")[2]);
        List<String> bannedCountries = new LinkedList<>();
        if (splitByQoute.length > 3) {
            for (int i = 3; i < splitByQoute.length; i = i+2) {
                bannedCountries.add(splitByQoute[i]);
            }
        }
        if (dall.addMovie(movieName, amount, price, bannedCountries)) {
            broadcast = ("BROADCAST movie \"" + movieName + "\" " + amount + " " + price);
            return "ACK addmovie \"" + movieName + "\" success";
        }
        return "ERROR request addmovie failed";
    }

    private String removeMovie(String movie) {
        if (!isLoggedIn) {
            return "ERROR request remmovie failed";
        }
        if (!dall.isAdmin(name)) {
            return "ERROR request remmovie failed";
        }
        if (dall.removeMovie(movie)) {
            broadcast = ("BROADCAST movie \"" + movie + "\" removed");
            return "ACK remmovie \"" + movie + "\" success";
        }
        return "ERROR request remmovie failed";
    }

    private String changePrice(String message) {
        if (!isLoggedIn) {
            return "ERROR request changeprice failed";
        }
        if (!dall.isAdmin(name)) {
            return "ERROR request changeprice failed";
        }
        int price = Integer.parseInt(message.split(" ")[message.split(" ").length-1]);
        String movie = message.split("\"")[1];
        int newPrice = dall.changePrice(movie, price);
        int numOfCopies = dall.getNumOfCopies(movie);
        if (newPrice != -1) {
            broadcast = ("BROADCAST movie \"" + movie + "\" " + numOfCopies + " " + price);
            return "ACK changeprice \"" + movie + "\" success";
        }
        return "ERROR request changeprice failed";
    }

}
