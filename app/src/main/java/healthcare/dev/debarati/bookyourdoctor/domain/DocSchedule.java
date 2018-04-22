package healthcare.dev.debarati.bookyourdoctor.domain;

/**
 * Created by Debarati on 20-04-2018.
 */

public class DocSchedule {

        int drscheduleid;
        String scheduleday;
        int scheduleid;
        int doctorid;
        Doctorde doctorde;
        Schdet schdet;

    public DocSchedule()
    {

    }

    @Override
    public String toString() {
        return "DocSchedule{" +
                "drscheduleid=" + drscheduleid +
                ", scheduleday='" + scheduleday + '\'' +
                ", scheduleid=" + scheduleid +
                ", doctorid=" + doctorid +
                ", doctorde=" + doctorde +
                ", schdet=" + schdet +
                '}';
    }

    public int getDrscheduleid() {
        return drscheduleid;
    }

    public void setDrscheduleid(int drscheduleid) {
        this.drscheduleid = drscheduleid;
    }

    public String getScheduleday() {
        return scheduleday;
    }

    public void setScheduleday(String scheduleday) {
        this.scheduleday = scheduleday;
    }

    public int getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    public Doctorde getDoctorde() {
        return doctorde;
    }

    public void setDoctorde(Doctorde doctorde) {
        this.doctorde = doctorde;
    }

    public Schdet getSchdet() {
        return schdet;
    }

    public void setSchdet(Schdet schdet) {
        this.schdet = schdet;
    }

    public DocSchedule(int drscheduleid, String scheduleday, int scheduleid, int doctorid, Doctorde doctorde, Schdet schdet) {
        this.drscheduleid = drscheduleid;
        this.scheduleday = scheduleday;
        this.scheduleid = scheduleid;
        this.doctorid = doctorid;
        this.doctorde = doctorde;
        this.schdet = schdet;
    }
}
