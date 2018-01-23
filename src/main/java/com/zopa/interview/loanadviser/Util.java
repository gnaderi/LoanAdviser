package com.zopa.interview.loanadviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

final public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    private static final String COMMA = ",";

    public static List<Lender> loadLenders(String filePath) {
        logger.info("Lenders data file path:{}", filePath);
        List<Lender> lenders = null;
        try {
            File file  = new File(filePath);
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            logger.info("Load {} of Lenders from the : {} .", lines.size() - 1, filePath);
            // skip(1) the header of the csv
            lenders = lines.stream().skip(1).map(mapToItem).collect(Collectors.toList());
        } catch (IOException ex) {
            logger.debug("Unable to parse the #{}", filePath);
            logger.debug(ex.getMessage());
        }
        return lenders;
    }

    private static Function<String, Lender> mapToItem = (line) -> {
        try {
            String[] fields = line.split(COMMA);// a CSV has comma separated lines
            return new Lender(fields[0], Double.valueOf(fields[1]), Long.valueOf(fields[2]));
        } catch (Exception ex) {
            logger.debug("Unable to parse the line#{}", line);
            logger.debug(ex.getMessage());
        }
        return null;
    };

}
