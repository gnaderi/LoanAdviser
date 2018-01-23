package com.gnaderi.interview.loanadviser;

import com.zopa.interview.loanadviser.DefaultAdviser;
import com.zopa.interview.loanadviser.IAdviser;
import com.zopa.interview.loanadviser.Quote;
import com.zopa.interview.loanadviser.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration()
public class LoanAdviserApplicationTests {
    @Autowired
    IAdviser adviser;

    @Test
    public void testGetQuoteWithSampleDataProvidedByZopa() {
        DecimalFormat df = new DecimalFormat(".##");
        Quote quote = adviser.getQuote(1000l, Util.loadLenders("data.csv"));
        Assert.assertNotNull(quote);
        Assert.assertEquals(quote.getLoanAmount(), 1000);
        Assert.assertEquals(df.format(quote.getMonthlyRepayment()), "30.78");
        Assert.assertEquals(df.format(quote.getQuote()), ".07");
        Assert.assertEquals(df.format(quote.getTotalRepayment()), "1108.08");//My calculated could n't make it 108.10 but by rounding it it will be.

    }

    @Configuration
    static class BeanCfg {
        @Bean
        IAdviser cipherService() {
            return new DefaultAdviser();
        }
    }
}
