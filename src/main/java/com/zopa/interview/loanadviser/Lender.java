package com.zopa.interview.loanadviser;

public class Lender implements Comparable<Lender> {
    private String lenderName;
    private Double rate;
    private Long availableFund;

    public Lender(String lenderName, Double rate, Long availableFund) {
        this.lenderName = lenderName;
        this.rate = rate;
        this.availableFund = availableFund;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(Long availableFund) {
        this.availableFund = availableFund;
    }

    @Override
    public int compareTo(Lender l) {
        return this.getRate().compareTo(l.getRate());
    }
}
