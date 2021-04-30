package base;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;

public class BaseClass {
    PriceFeed priceFeed = new PriceFeed();
    String csvTestData = "src/main/testdata/FxFeed.csv";
    static CSVReader reader;

    public PriceFeed pickUpPrice() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceFeed;
    }

    public void sequenceCheck(PriceFeed priceFeed) {
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

            while (priceFeedIterator.hasNext()) {
                PriceFeed priceFeed1 = priceFeedIterator.next();
                if (!(priceFeed1.getId().equals("…"))) {
                    boolean idCheck = isInteger(priceFeed1.getId());
                    boolean instrumentCheck = isValidString(priceFeed1.getId());
                    boolean bidPriceCheck = isValidDouble(priceFeed1.getBid());
                    boolean askPriceCheck = isValidDouble(priceFeed1.getAsk());
                    boolean timeCheck = isValidString(priceFeed1.getFtime());
                    if (!(idCheck | instrumentCheck | bidPriceCheck | askPriceCheck | timeCheck)) {
                        Assert.fail("Sequence Check Test Fail");
                    } else {
                        Assert.assertTrue("Sequence check test Passed", true);
                    }
                } else {
                    priceFeedIterator.next();
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDouble(double ask) {
        if (ask == 0.0) {
            return false;
        }
        try {
            double price = ask;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean isValidString(String str) {
        if (str == null) {
            return false;
        }
        try {
            String.format("%s", str);
        } catch (IllegalFormatException nfe) {
            return false;
        }
        return true;
    }

    private static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void misingFieldCheck(PriceFeed priceFeed) throws IOException, CsvException {
        Reader reader = Files.newBufferedReader(Paths.get(csvTestData));
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> records = csvReader.readAll();
        for (String[] record : records) {
            if (!(record[0].equals("…"))) {
                if ((record[0].isEmpty() | record[1].isEmpty() | record[2].isEmpty() | record[3].isEmpty() | record[4].isEmpty())) {
                    Assert.assertNull("Empty field in ID: " + record[0]);
                }
            }
        }
    }

    public void bidaskPriceCheck(PriceFeed priceFeed) {
        if ((priceFeed.getBid() > priceFeed.getAsk() | (priceFeed.getBid() == priceFeed.getAsk()))) {
            Assert.assertFalse("Test Failed : Bid Price greater than ask ", false);
        } else
            Assert.assertTrue("Test passed : Bid Price less than ask ", true);
    }

    public PriceFeed pickValidPrice(PriceFeed priceFeed) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader(csvTestData));
        String[] feed;
        String[] a = reader.readNext();
        a = reader.readNext();
        //while ((feed = reader.readNext())!=null){
        int validRecord = 1;
        String id = a[0];
        String instrument = a[1];
        double bidPrice = Double.parseDouble(a[2]);
        double askPrice = Double.parseDouble(a[3]);
        String ftime = a[4];
        PriceFeed p = new PriceFeed(id, instrument, bidPrice, askPrice, ftime);
        return p;
    }

    public String convertDoubleToString(Double price) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(price);
    }

    public PriceFeed pickLatestPrice(PriceFeed priceFeed) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(csvTestData));
        String[] feed;
        List<String[]> a = reader.readAll();
        int csvSize = a.size();
        int validRecord = 1;
        String[] readRecord = a.get(a.size() - 1);
        String id = readRecord[0];
        String instrument = readRecord[1];
        double bidPrice = Double.parseDouble(readRecord[2]);
        double askPrice = Double.parseDouble(readRecord[3]);
        String ftime = readRecord[4];
        PriceFeed latestFeed = new PriceFeed(id, instrument, bidPrice, askPrice, ftime);
        return latestFeed;
    }

    public PriceFeed pickPenUltimatePrice(PriceFeed priceFeed) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(csvTestData));
        String[] feed;
        List<String[]> a = reader.readAll();
        int csvSize = a.size();
        String[] readRecord = a.get(a.size() - 1);
        String id = readRecord[0];
        String instrument = readRecord[1];
        double bidPrice = Double.parseDouble(readRecord[2]);
        double askPrice = Double.parseDouble(readRecord[3]);
        String ftime = readRecord[4];
        PriceFeed oldPriceFeed = new PriceFeed(id, instrument, bidPrice, askPrice, ftime);
        for (int priceIterator = (csvSize-2); priceIterator >= 0; priceIterator--) {
            String[] currentRecord = a.get(priceIterator);
            if (!(currentRecord[0].equals("…"))) {
                String currentInstrument = currentRecord[1];
                if (currentInstrument.equals(instrument)) {
                    String previousId = currentRecord[0];
                    String previousInstrument = currentRecord[1];
                    double previousBidPrice = Double.parseDouble(currentRecord[2]);
                    double previousAskPrice = Double.parseDouble(currentRecord[3]);
                    String previousFtime = currentRecord[4];
                    oldPriceFeed = new PriceFeed(previousId, previousInstrument, previousBidPrice, previousAskPrice, previousFtime);
                    return oldPriceFeed;
                }
            }
        }
        return oldPriceFeed;
    }
}