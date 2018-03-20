
package bgu.spl181.net.json;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movies implements Serializable
{

    @SerializedName("movies")
    @Expose
    private List<Movie> movies = null;
    private final static long serialVersionUID = 6088479842310280739L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Movies() {
    }

    /**
     * 
     * @param movies
     */
    public Movies(List<Movie> movies) {
        super();
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie (Movie movie) {
        movies.add(movie);
    }

}
