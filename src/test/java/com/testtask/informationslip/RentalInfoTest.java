package com.testtask.informationslip;

import com.testtask.informationslip.dto.Customer;
import com.testtask.informationslip.dto.Movie;
import com.testtask.informationslip.dto.MovieRental;
import com.testtask.informationslip.exception.MovieRentalDuplicationException;
import com.testtask.informationslip.manager.MovieRentalManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.testtask.informationslip.constant.MovieRentalData.*;
import static org.junit.Assert.*;

public class RentalInfoTest {

    private RentalInfo rentalInfo;
    private Customer customer;

    @Before
    public void init() {
        rentalInfo = new RentalInfo();
    }

    @Test
    public void getRentalInfoPositive() {
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

        List<MovieRental> movieRentalList = Arrays.asList(
                new MovieRental("F001", 3),
                new MovieRental("F002", 1));
        Customer customer = new Customer("C. U. Stomer", movieRentalList);
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F001", "You've Got Mail", REGULAR));
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F002", "Matrix", REGULAR));

        String result = rentalInfo.statement(customer);

        String errorMessage = "Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result;
        assertEquals(errorMessage, result, expected);
    }

    @Test
    public void getRentalInfoNegative() {
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

        List<MovieRental> movieRentalList = Arrays.asList(
                new MovieRental("F003", 3),
                new MovieRental("F004", 1));
        Customer customer = new Customer("John Doe", movieRentalList);
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F003", "Scary movie", REGULAR));
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F004", "Scary movie 2", NEW));

        String result = rentalInfo.statement(customer);

        assertNotEquals(result, expected);
    }

    @Test
    public void getRentalInfoWithSetupStorage() {
        String expected = "Rental Record for C. U. Stomer\n\tFilm1\t1.5\n\tFilm2\t3.0\nAmount owed is 4.5\nYou earned 2 frequent points\n";

        List<MovieRental> movieRentalList = Arrays.asList(
                new MovieRental("F008", 3),
                new MovieRental("F009", 1));
        Customer customer = new Customer("C. U. Stomer", movieRentalList);
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F008", "Film1", CHILDREN));
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F009", "Film2", NEW));

        String result = rentalInfo.statement(customer);

        String errorMessage = "Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result;
        assertEquals(errorMessage, result, expected);
    }

    @Test(expected = MovieRentalDuplicationException.class)
    public void duplicationsNonAllowed() {
        String expected = "Rental Record for C. U. Stomer\n\tFilm1\t1.5\n\tFilm2\t3.0\nAmount owed is 4.5\nYou earned 2 frequent points\n";

        List<MovieRental> movieRentalList = Arrays.asList(
                new MovieRental("F008", 3),
                new MovieRental("F009", 1));
        Customer customer = new Customer("C. U. Stomer", movieRentalList);
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F006", "Film10", CHILDREN));
        MovieRentalManager.getInstance().addMovie(customer, new Movie("F007", "Film10", CHILDREN));
        fail("We expect to have MovieRentalDuplicationException here");
    }

}
