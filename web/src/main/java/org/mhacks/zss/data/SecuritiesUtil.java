package org.mhacks.zss.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class SecuritiesUtil {

    public static List<SymbolName> getAllSecurities() throws IOException {
        List<SymbolName> securities = new LinkedList<>();

        for (String filename : new String[]{"/amex.csv", "/nasdaq.csv", "/nyse.csv"}) {
            CSVParser parser = CSVParser.parse(SecuritiesUtil.class.getResource(filename), Charset.defaultCharset(),
                    CSVFormat.RFC4180);

            for (CSVRecord record : parser) {
                securities.add(new SymbolName(record.get(1), record.get(0)));
            }
        }

        return securities;
    }
}
