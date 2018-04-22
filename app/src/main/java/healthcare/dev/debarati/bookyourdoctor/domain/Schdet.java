package healthcare.dev.debarati.bookyourdoctor.domain;

/**
 * Created by Debarati on 20-04-2018.
 */

public class Schdet {
     int scheduleid;
     String starttime;
     String endtime;

    @Override
    public String toString() {
        return "Schdet{" +
                "scheduleid=" + scheduleid +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }

    public Schdet() {


    }

    public Schdet(int scheduleid, String starttime, String endtime) {
        this.scheduleid = scheduleid;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
