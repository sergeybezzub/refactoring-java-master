package com.testtask.informationslip.dto;

import java.util.List;

public record Customer(String name, List<MovieRental> rentals) {}
