package bgu.spl181.net.srv;

import bgu.spl181.net.api.bidi.Connections;
import bgu.spl181.net.api.bidi.ConnectionsServer;
import bgu.spl181.net.json.Movie;
import bgu.spl181.net.json.Movies;
import bgu.spl181.net.json.User;
import bgu.spl181.net.json.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Dall {

    private Users users;
    private Movies movies;
    private ReentrantReadWriteLock usersWRLock;
    private ReentrantReadWriteLock moviesWRLock;
    private Integer movieCounterId;
    private HashSet<String> logginUsers;

    public Dall() {
        users = parseUJson("Database/Users.json");
        movies = parseMJson("Database/Movies.json");
        usersWRLock = new ReentrantReadWriteLock();
        moviesWRLock = new ReentrantReadWriteLock();
        movieCounterId = movies.getMovies().size() + 1;
        logginUsers = new HashSet<>();
    }

    public List<String> getMoviesList() {
        moviesWRLock.readLock().lock();
        List<String> moviesNamesList = new LinkedList();
        for (Movie m :
             movies.getMovies()) {
            moviesNamesList.add("\"" + m.getName() + "\"");
        }
        moviesWRLock.readLock().unlock();
        return moviesNamesList;
    }

    public Movies parseMJson(String filePath) {

        Gson gson = new Gson();

        // Get the file and parse it into classes
        try (FileReader reader = new FileReader(new File(filePath))) {

            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(reader);

            Movies parsedJson = gson.fromJson(tree, Movies.class);

            return parsedJson;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users parseUJson(String filePath) {

        Gson gson = new Gson();

        // Get the file and parse it into classes
        try (FileReader reader = new FileReader(new File(filePath))) {

            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(reader);

            Users parsedJson = gson.fromJson(tree, Users.class);

            return parsedJson;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUsers() {
        try (Writer writer = new FileWriter("Database/Users.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMovies() {
        try (Writer writer = new FileWriter("Database/Movies.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(movies, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getUser(String userName) {
        for (User us: users.getUsers()
                ) {
            if (us.getUsername().equalsIgnoreCase(userName)) {
                return us;
            }
        }
        return null;
    }

    private Movie getMovie(String movieName) {
        for (Movie movie: movies.getMovies()
                ) {
            if (movie.getName().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }

    public boolean addBaseUser(String user, String pass) {
        usersWRLock.writeLock().lock();
        if (isUserExist(user)) {
            return false;
        }
        User us = new User();
        us.setUsername(user);
        us.setPassword(pass);
        users.addUser(us);
        updateUsers();
        usersWRLock.writeLock().unlock();
        return true;
    }

    public boolean addUser(String user, String pass, String country) {
        usersWRLock.writeLock().lock();
        if (isUserExist(user)) {
            usersWRLock.writeLock().unlock();
            return false;
        }
        User  us = new User();
        us.setUsername(user);
        us.setPassword(pass);
        us.setCountry(country);
        us.setType("normal");
        us.setBalance("0");
        us.setMovies(new LinkedList<>());
        users.addUser(us);
        updateUsers();
        usersWRLock.writeLock().unlock();
        return true;
    }

    public boolean isUserExist (String userName) {
        usersWRLock.readLock().lock();
        if (getUser(userName) != null) {
            usersWRLock.readLock().unlock();
            return true;
        }
        usersWRLock.readLock().unlock();
        return false;
    }

    public boolean validLogin(String userName, String password, ConnectionsServer connections, Integer connId) {
        User us = getUser(userName);
        if (us == null) {
            return false;
        }
        boolean output = true;
        synchronized (us) {
            if(logginUsers.contains(userName) || !us.getPassword().equals(password)) {
                output = false;
            }
            connections.logInUser(connId);
            logginUsers.add(userName);
        }
        return output;
    }

    public void signout(String userName, Connections connections, int connectionId) {
        connections.disconnect(connectionId);
        logginUsers.remove(userName);
    }

    public boolean isLoggedIn(String username) {
        usersWRLock.readLock().lock();
        boolean output = logginUsers.contains(username);
        usersWRLock.readLock().unlock();
        return output;
    }

    public String getBalance(String username) {
        usersWRLock.readLock().lock();
        User us = getUser(username);
        String output = us.getBalance();
        usersWRLock.readLock().unlock();
        return output;
    }

    public String addToBalance(String username, int toAdd) {
        usersWRLock.writeLock().lock();
        User us = getUser(username);
        Integer currBalance = Integer.parseInt(us.getBalance());
        if (toAdd < 1) {
            usersWRLock.writeLock().unlock();
            return null;
        }
        Integer newBalance = (currBalance + toAdd);
        us.setBalance(newBalance.toString());
        updateUsers();
        usersWRLock.writeLock().unlock();
        return newBalance.toString();
    }

    public String getMovieInfo(String username, String movieName) {
        moviesWRLock.readLock().lock();
        Movie movie = getMovie(movieName);
        User user = getUser(username);
        if ((movie == null) || (user == null)) {
            return null;
        }
        String list = "";
        for (String country :
                movie.getBannedCountries()) {
            list = list + " \"" + country + "\"";
        }

        String output = "ACK info \"" + movie.getName() + "\" " + movie.getAvailableAmount() + " " + movie.getPrice() + list;
        moviesWRLock.readLock().unlock();
        return output;
    }

    public boolean returnMovie(String userName, String movieName) {
        moviesWRLock.writeLock().lock();
        usersWRLock.writeLock().lock();
        Movie movie = getMovie(movieName);
        if (movie == null) {
            moviesWRLock.writeLock().unlock();
            usersWRLock.writeLock().unlock();
            return false;
        }
        User user = getUser(userName);
        for (Movie mov: user.getMovies()
                ) {
            if (mov.getName().equalsIgnoreCase(movieName)) {
                Integer newAmount = Integer.parseInt(movie.getAvailableAmount()) + 1;
                movie.setAvailableAmount(newAmount.toString());
                user.getMovies().remove(mov);
                updateMovies();
                updateUsers();
                moviesWRLock.writeLock().unlock();
                usersWRLock.writeLock().unlock();
                return true;
            }
        }
        moviesWRLock.writeLock().unlock();
        usersWRLock.writeLock().unlock();
        return false;
    }

    public boolean rentMovie(String movieName, String userName) {
        moviesWRLock.writeLock().lock();
        usersWRLock.writeLock().lock();
        Movie movie = getMovie(movieName);
        if (movie == null) {
            moviesWRLock.writeLock().unlock();
            usersWRLock.writeLock().unlock();
            return false;
        }
        User user = getUser(userName);
        if (Integer.parseInt(user.getBalance()) < Integer.parseInt(movie.getPrice())) {
            moviesWRLock.writeLock().unlock();
            usersWRLock.writeLock().unlock();
            return false;
        }
        if (Integer.parseInt(movie.getAvailableAmount()) < 1) {
            moviesWRLock.writeLock().unlock();
            usersWRLock.writeLock().unlock();
            return false;
        }
        for (String country: movie.getBannedCountries()
                ) {
            if (country.equalsIgnoreCase(user.getCountry())) {
                moviesWRLock.writeLock().unlock();
                usersWRLock.writeLock().unlock();
                return false;
            }
        }
        for (Movie mov: user.getMovies()
                ) {
            if (mov.getName().equalsIgnoreCase(movieName)) {
                moviesWRLock.writeLock().unlock();
                usersWRLock.writeLock().unlock();
                return false;
            }
        }
        Movie mv = new Movie();
        mv.setId(movie.getId());
        mv.setName(movie.getName());
        user.getMovies().add(mv);
        Integer newBalance = Integer.parseInt(user.getBalance()) - Integer.parseInt(movie.getPrice());
        user.setBalance(newBalance.toString());
        Integer newAmount = Integer.parseInt(movie.getAvailableAmount()) - 1;
        movie.setAvailableAmount(newAmount.toString());
        updateUsers();
        updateMovies();
        moviesWRLock.writeLock().unlock();
        usersWRLock.writeLock().unlock();
        return true;
    }

    public String getMovieInfo3(String movieName) {
        moviesWRLock.readLock().lock();
        for (Movie movie: movies.getMovies()
                ) {
            if (movie.getName().equalsIgnoreCase(movieName)) {
                String output = "BROADCAST movie \"" + movie.getName() + "\" " + movie.getAvailableAmount() + " " + movie.getPrice();
                moviesWRLock.readLock().unlock();
                return output;
            }
        }
        moviesWRLock.readLock().unlock();
        return null;
    }

    public boolean isAdmin(String username) {
        usersWRLock.readLock().lock();
        boolean output = getUser(username).getType().equals("admin");
        usersWRLock.readLock().unlock();
        return output;
    }

    public boolean addMovie(String movieName, Integer amount, Integer price, List<String> bannedCountries) {
        if ((amount <= 0) | (price <= 0)) {
            return false;
        }
        moviesWRLock.writeLock().lock();
        if (getMovie(movieName) == null) {
            Movie newMovie = new Movie();
            newMovie.setId(movieCounterId.toString());
            movieCounterId++;
            newMovie.setTotalAmount(amount.toString());
            newMovie.setAvailableAmount(amount.toString());
            newMovie.setBannedCountries(bannedCountries);
            newMovie.setName(movieName);
            newMovie.setPrice(price.toString());
            movies.getMovies().add(newMovie);
            updateMovies();
            moviesWRLock.writeLock().unlock();
            return true;
        }
        moviesWRLock.writeLock().unlock();
        return false;
    }

    public boolean removeMovie(String movieName) {
        moviesWRLock.writeLock().lock();
        Movie movie = getMovie(movieName);
        if (movie == null) {
            moviesWRLock.writeLock().unlock();
            return false;
        }
        if (Integer.parseInt(movie.getTotalAmount()) - Integer.parseInt(movie.getAvailableAmount()) != 0) {
            moviesWRLock.writeLock().unlock();
            return false;
        }
        for (Movie mv:
        movies.getMovies()) {
            if (mv.getName().equalsIgnoreCase(movieName)) {
                movies.getMovies().remove(mv);
                updateMovies();
                moviesWRLock.writeLock().unlock();
                return true;
            }
        }
        moviesWRLock.writeLock().unlock();
        return false;
    }

    public int changePrice(String movieName, Integer newPrice) {
        if (newPrice < 1) {
            return -1;
        }
        moviesWRLock.writeLock().lock();
        Movie movie = getMovie(movieName);
        if (movie == null) {
            moviesWRLock.writeLock().unlock();
            return -1;
        }
        movie.setPrice(newPrice.toString());
        updateMovies();
        moviesWRLock.writeLock().unlock();
        return newPrice;
    }

    public int getNumOfCopies(String movieName) {
        moviesWRLock.readLock().lock();
        Movie movie = getMovie(movieName);
        if (movie == null) {
            moviesWRLock.readLock().unlock();
            return -1;
        }
        int output = Integer.parseInt(movie.getAvailableAmount());
        moviesWRLock.readLock().unlock();
        return output;
    }
}
