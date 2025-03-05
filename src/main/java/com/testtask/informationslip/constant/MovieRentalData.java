package com.testtask.informationslip.constant;

/**
 * Constant value for movie code, period for discount, price for period with discount and price afte discount period
 */
public enum MovieRentalData {
    REGULAR(2,2.0,1.5),
    NEW(1,3.0,3.0),
    CHILDREN(3, 1.5, 1.5);

    private final Double amountForPeriod;
    private final Double amountAfterPeriod;

    private final Integer period;

    MovieRentalData(Integer period, Double amountForPeriod, Double amountAfterPeriod ) {
        this.period = period;
        this.amountForPeriod = amountForPeriod;
        this.amountAfterPeriod = amountAfterPeriod;
    }

    public Integer getPeriod() {
        return period;
    }

    public Double getAmountForPeriod() {
        return amountForPeriod;
    }

    public Double getAmountAfterPeriod() {
        return amountAfterPeriod;
    }
}
