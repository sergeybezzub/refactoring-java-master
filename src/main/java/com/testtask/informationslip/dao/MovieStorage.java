package com.testtask.informationslip.dao;

import com.testtask.informationslip.dto.Customer;
import com.testtask.informationslip.dto.Movie;

import java.util.List;

public interface MovieStorage {
    List<Movie> getMovies(Customer customer);
    void addMovie(Customer customer, Movie movie);
}
