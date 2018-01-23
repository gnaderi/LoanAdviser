package com.zopa.interview.loanadviser;

import java.text.DecimalFormat;

public class Quote {
    private long loanAmount;
    private double quote;
    private double monthlyRepayment;
    private double totalRepayment;

    public Quote(long loanAmount, double quote, double monthlyRepayment, double totalRepayment) {
        this.loanAmount = loanAmount;
        this.quote = quote;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getQuote() {
        return quote;
    }

    public void setQuote(double quote) {
        this.quote = quote;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(double monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }

    public void setTotalRepayment(double totalRepayment) {
        this.totalRepayment = totalRepayment;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "loanAmount=" + loanAmount +
                ", quote=" + quote +
                ", monthlyRepayment=" + monthlyRepayment +
                ", totalRepayment=" + totalRepayment +
                '}';
    }
}
