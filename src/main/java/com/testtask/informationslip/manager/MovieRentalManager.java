package com.testtask.informationslip.manager;

import com.testtask.informationslip.dao.MovieStorage;
import com.testtask.informationslip.dto.Customer;
import com.testtask.informationslip.dto.Movie;
import com.testtask.informationslip.exception.MovieRentalDuplicationException;

import static com.testtask.informationslip.constant.MovieRentalData.REGULAR;
import static com.testtask.informationslip.constant.MovieRentalData.NEW;
import static com.testtask.informationslip.constant.MovieRentalData.CHILDREN;

import java.util.*;


/**
 * Uses lazy caching to persist movies to memory and to database and read it from memory first
 * If movie in memory doesn't exist to read from database if storage is set
 */
public class MovieRentalManager implements MovieStorage {

    private static MovieRentalManager instance = new MovieRentalManager();

    private MovieStorage movieStorage;

    private Map<String, List<Movie>> movieStorageInMemory = new HashMap<>();

    private MovieRentalManager() {
    }

    public static MovieRentalManager getInstance() {
        return instance;
    }

    /**
     * Get movies list that were rented by customer
     * @param customer name of customer
     * @return a list of rented movies
     */
    @Override
    public List<Movie> getMovies(Customer customer) {
        List<Movie> movies = getMoviesFromCache(customer);
        // Try to get data from cache first
        if (movies.isEmpty()) {
            // If cache is empty try to get data from storage
            if (movieStorage != null) {
                // Get rental movies from the storage if it is configured by setter
                List<Movie> moviesFromStorage = movieStorage.getMovies(customer);
                if (!moviesFromStorage.isEmpty()) {
                    addMovieToCache(customer, moviesFromStorage);
                }
                // Returns data from storage or empty collection if data is not exist in the storage
                return moviesFromStorage;
            } else {
                // If storage is not configured returns empty collection
                return movies;
            }
        } else {
            // Returns movies from cache
            return movies;
        }
    }

    /**
     * Add new rented movie to customer
     * @param customer name of customer
     * @param movie rented movie
     */
    @Override
    public void addMovie(Customer customer, Movie movie) {
        // Check if that move has already rented
        List<Movie> currentMovies = getMovies(customer);
        if(currentMovies.stream().anyMatch(e -> e.title().equals(movie.title()))) {
            throw new MovieRentalDuplicationException(String.format("Movie: %s has already rented!",movie.title()));
        }
        // Add movie to the storage
        addMovieToCache(customer, movie);
        if (movieStorage != null) {
            movieStorage.addMovie(customer, movie);
        }
    }

    private void addMovieToCache(Customer customer, Movie movie) {
        List<Movie> movies = movieStorageInMemory.getOrDefault(customer.name(), new ArrayList<>());
        movies.add(movie);
        movieStorageInMemory.put(customer.name(), movies);
    }

    private void addMovieToCache(Customer customer, List<Movie> movies) {
        List<Movie> movieList = movieStorageInMemory.getOrDefault(customer.name(), new ArrayList<>());
        movieList.addAll(movies);
        movieStorageInMemory.put(customer.name(), movieList);
    }

    private List<Movie> getMoviesFromCache(Customer customer) {
        return movieStorageInMemory.getOrDefault(customer.name(), Collections.emptyList());
    }

    /**
     * Set some implementation to persist customer rental information for example ti database
     *
     * @param movieStorage any implementation of interface to persist data
     */
    public void setMovieStorage(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

}
