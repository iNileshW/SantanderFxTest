package base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PriceFeed {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public String getFtime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public PriceFeed(String id, String instrument, double bid, double ask, String ftime) {
        this.id = id;
        this.instrument = instrument;
        this.bid = bid;
        this.ask = ask;
        this.ftime = ftime;
    }

    private String id;
    private String instrument;
    private double bid;
    private double ask;
    private String ftime;

    public PriceFeed(){}

}
