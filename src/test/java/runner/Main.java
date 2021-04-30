package runner;

import base.BaseClass;
import base.PriceFeed;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Iterator;

public class Main extends BaseClass {
    private static String csvTestData = "src/main/testdata/FxFeed.csv";
    public static void main(String[] args ) throws IOException, CsvException {
        BaseClass b = new BaseClass();
        PriceFeed p = b.latest();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvTestData));
        ) {
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(PriceFeed.class);
            String[] memberFieldsToBindTo = {"id", "instrument", "bid", "ask", "ftime"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<PriceFeed> csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<PriceFeed> priceFeedIterator = csvToBean.iterator();
            PriceFeed priceFeed = priceFeedIterator.next();
            do {
                if (!(priceFeed.getId().equals("…"))) {
                    System.out.println("id : " + priceFeed.getId());
                    System.out.println("Instrument : " + priceFeed.getInstrument());
                    System.out.println("Bid : " + priceFeed.getBid());
                    System.out.println("Ask : " + priceFeed.getAsk());
                    System.out.println("Time : " + priceFeed.getFtime());
                    System.out.println("---------------------------");
                } else {
                    priceFeedIterator.next();
                }
            }while (priceFeedIterator.hasNext());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    }


