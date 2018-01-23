package com.zopa.interview.loanadviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultAdviser implements ApplicationRunner, IAdviser {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @Value("${loan.min}")
    private int loadMin;
    @Value("${loan.max}")
    private int loanMax;
    @Value("${loan.block.size}")
    private int loadBlockSize;
    @Value("${loan.duration}")
    private Integer loadDuration;

    private List<Lender> lenders;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        try {
            this.lenders = Util.loadLenders(applicationArguments.getNonOptionArgs().get(0)).stream().sorted().collect(Collectors.toList());
            if (lenders.size() <= 0) {
                logger.info("Lenders data file is empty or unable to read it");
                return;
            }
            long loanAmount = Long.parseLong(applicationArguments.getNonOptionArgs().get(1));
            if (!validateRequest(loanAmount)) {
                return;
            }
            Quote quote = this.getQuote(loanAmount, lenders);
            logger.info("~~~~~~~~~~~~~~~~~~~~~~Quote Details~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
            if (quote != null) {
                logger.info("Requested amount:{}", quote.getLoanAmount());
                logger.info("Rate:{}%", String.format("%.1f", quote.getQuote() * 100));
                logger.info("Monthly repayment:{}", String.format("%.2f", quote.getMonthlyRepayment()));
                logger.info("Total repayment:{}", String.format("%.2f", quote.getTotalRepayment()));
            } else {
                logger.info("Unable to get a quote for loan# {}", loanAmount);
            }
            logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");

        } catch (Exception ex) {
            logger.info("Invalid arguments:{}", applicationArguments.getNonOptionArgs());
            logger.info(ex.getMessage());
        }
    }

    private boolean validateRequest(long amount) {
        if (amount < loadMin || amount > loanMax || amount % loadBlockSize != 0) {
            logger.info("The loan amount must be between {} and {} and a number as {}x .", loadMin, loanMax, loadBlockSize);
            return false;
        }
        return true;

    }

    @Override
    public Quote getQuote(Long loanAmount, List<Lender> lenders) {
        //check to see if lenders afford to pay the loan.
        if (lenders.stream().mapToLong(Lender::getAvailableFund).sum() < loanAmount) {
            return null;
        }
        double interestRate = this.calInterestRate(loanAmount, lenders);
        double monthlyRate = this.calPeriodicInterestRate(Double.parseDouble(df2.format(interestRate)));
        double monthlyRepaymentInstallment = calPeriodicRepayment(loanAmount, monthlyRate, loadDuration);
        double totalRepayment = Double.parseDouble(df2.format(monthlyRepaymentInstallment)) * loadDuration;
        return new Quote(loanAmount, interestRate, monthlyRepaymentInstallment, totalRepayment);
    }


}
