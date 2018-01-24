package com.zopa.interview.loanadviser;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IAdviser {
    /**
     * Calculate a quote based on Lender rate and requested loan.
     *
     * @param loanAmount
     * @param lenders
     * @return quote
     */
    Quote getQuote(Long loanAmount, List<Lender> lenders);

    /**
     * Calculate total amount that borrowers need to pay at end of the load period
     *
     * @param loanAmount requested loan amount
     * @param lenders    list of the leaders and their interest rate.
     * @return a double value as total pay.
     */
    default double calInterestRate(Long loanAmount, List<Lender> lenders) {
        double sumRate = 0.00d;
        int count = 1;
        long borrowed = loanAmount.longValue();
        for (Lender lender : lenders) {
            if (borrowed > lender.getAvailableFund()) {
                borrowed -= lender.getAvailableFund();
                sumRate += lender.getRate();
                count++;
            } else {
                sumRate += lender.getRate();
                break;
            }
        }
        return sumRate / count;
    }

    default double calPeriodicInterestRate(double interestRate) {
        return Math.pow(1 + interestRate, 1 / 12.0) - 1;
    }

    default double calPeriodicRepayment(Long loanAmount, double monthlyRate, int loadDuration) {
        return (loanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -loadDuration));
    }
}
