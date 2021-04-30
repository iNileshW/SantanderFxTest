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
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;

public class BaseClass {
    PriceFeed priceFeed = new PriceFeed();
    String csvTestData = "src/main/testdata/FxFeed.csv";

    public void readcsv() throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(csvTestData));
        String[] feed;
        List<String[]> a = reader.readAll();
        feed = reader.readNext();
        //while ((feed = reader.readNext())!=null){

        for (int i = 0; i < a.size(); i++) {
            String[] id = a.get(i);
            System.out.println(id);
            String instrument = feed[i + 1];
            System.out.println(instrument);
            float bidPrice = Float.parseFloat(feed[i + 2]);
            System.out.println(bidPrice);
            float askPrice = Float.parseFloat(feed[i + 3]);
            System.out.println(askPrice);
            //Date time = feed[i+4];

            //  }
        }
    }

    static CSVReader reader;

    public PriceFeed latest() throws IOException, CsvValidationException {
        reader = new CSVReader(new FileReader(csvTestData));
        String[] feed;
        feed = reader.readNext();

        for (String feeds : feed) {
            System.out.println(feeds);
        }
        //System.out.println(feed);
        return priceFeed;
    }

    public void CSVIterator(CSVReader csvReader) {
        Iterator<String[]> rows = csvReader.iterator();
        for (Iterator<String[]> it = rows; it.hasNext(); ) {
            String[] r = it.next();
            System.out.println(Arrays.stream(new String[]{r[3]}));
        }

    }

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

            /*do {
                /if (!(priceFeed.getId().equals("…"))) {
                    System.out.println("id : " + priceFeed.getId());
                    System.out.println("Instrument : " + priceFeed.getInstrument());
                    System.out.println("Bid : " + priceFeed.getBid());
                    System.out.println("Ask : " + priceFeed.getAsk());
                    System.out.println("Time : " + priceFeed.getFtime());
                    System.out.println("---------------------------");
                } else {
                    priceFeedIterator.next();
                }
            }while (priceFeedIterator.hasNext());*/
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
            priceFeed = priceFeedIterator.next();
            do {
                if (!(priceFeed.getId().equals("…"))) {
                    boolean idCheck = isInteger(priceFeed.getId());
                    boolean instrumentCheck = isValidString(priceFeed.getId());
                    boolean bidPriceCheck = isValidDouble(priceFeed.getBid());
                    boolean askPriceCheck = isValidDouble(priceFeed.getAsk());
                    boolean timeCheck = isValidString(priceFeed.getFtime());
                    if (!(idCheck | instrumentCheck | bidPriceCheck | askPriceCheck | timeCheck)) {
                        Assert.fail("Sequence Check Test Fail");
                    } else {
                        Assert.assertTrue("Sequence check test Passed", true);
                    }
                } else {
                    priceFeedIterator.next();
                }
            } while (priceFeedIterator.hasNext());
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
        System.out.println(a.length);
        String id = a[0];
        System.out.println(id);
        String instrument = a[1];
        System.out.println(instrument);
        double bidPrice = Double.parseDouble(a[2]);
        System.out.println(bidPrice);
        double askPrice = Double.parseDouble(a[3]);
        System.out.println(askPrice);
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
        //while ((feed = reader.readNext())!=null){
        int validRecord = 1;
        System.out.println(csvSize);
        String[] readRecord = a.get(a.size() - 1);
        String id = readRecord[0];
        System.out.println(id);
        String instrument = readRecord[1];
        System.out.println(instrument);
        double bidPrice = Double.parseDouble(readRecord[2]);
        System.out.println(bidPrice);
        double askPrice = Double.parseDouble(readRecord[3]);
        System.out.println(askPrice);
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


