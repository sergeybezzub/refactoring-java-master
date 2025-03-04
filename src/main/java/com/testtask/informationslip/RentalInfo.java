package com.testtask.informationslip;

import com.testtask.informationslip.constant.MovieRentalData;
import com.testtask.informationslip.dto.Customer;
import com.testtask.informationslip.dto.Movie;
import com.testtask.informationslip.dto.MovieRental;
import com.testtask.informationslip.manager.MovieRentalManager;

import static com.testtask.informationslip.constant.MovieRentalData.NEW;

import java.util.HashMap;
import java.util.List;

/**
 * Provides rental information of customer
 */
public class RentalInfo {

  private final static int BONUS_FOR_NEW_RELEASE_RENTAL_DAYS = 2;
  public static final String RENTAL_RECORD_FOR_ = "Rental Record for ";
  public static final String AMOUNT_OWED_IS_ = "Amount owed is ";
  public static final String YOU_EARNED_ = "You earned ";
  public static final String FREQUENT_POINTS = " frequent points\n";
  public static final String LINE_BREAK = "\n";
  public static final String TAB = "\t";

  public String statement(Customer customer) {
    HashMap<String, Movie> movies = new HashMap<>();

    List<Movie> moviesList = MovieRentalManager.getInstance().getMovies(customer);

    for(Movie movie : moviesList) {
      movies.put(movie.id(), movie);
    }

    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder(RENTAL_RECORD_FOR_);
    result.append(customer.name()).append(LINE_BREAK);

    for (MovieRental movieRental : customer.rentals()) {
      double thisAmount = 0;

      // determine amount for each movie
      for(MovieRentalData movieRentalData : MovieRentalData.values()) {
        if (movieRentalData == movies.get(movieRental.movieId()).code()) {
          thisAmount = movieRentalData.getAmountForPeriod();
          if (movieRental.days() > movieRentalData.getPeriod()) {
            thisAmount = ((movieRental.days() - movieRentalData.getPeriod()) * movieRentalData.getAmountAfterPeriod()) + thisAmount;
          }
        }
      }
      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movies.get(movieRental.movieId()).code() == NEW && movieRental.days() >  BONUS_FOR_NEW_RELEASE_RENTAL_DAYS)
        frequentEnterPoints++;

      //print figures for this rental
      result.append(TAB).append(movies.get(movieRental.movieId()).title()).append(TAB).append(thisAmount).append(LINE_BREAK);
      totalAmount = totalAmount + thisAmount;
    }
    // add footer lines
    result.append(AMOUNT_OWED_IS_).append(totalAmount).append(LINE_BREAK);
    result.append(YOU_EARNED_).append(frequentEnterPoints).append(FREQUENT_POINTS);

    return result.toString();
  }
}
