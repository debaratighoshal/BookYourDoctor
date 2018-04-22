package healthcare.dev.debarati.bookyourdoctor.domain;

/**
 * Created by Debarati on 20-04-2018.
 */

public class Availableschedule {
    int timeslotid;
    String status;
    String slotfrom;
    String slotto;

    public Availableschedule(int timeslotid, String status, String slotfrom, String slotto) {
        this.timeslotid = timeslotid;
        this.status = status;
        this.slotfrom = slotfrom;
        this.slotto = slotto;
    }
    public Availableschedule()
    {

    }

    @Override
    public String toString() {
        return "Availableschedule{" +
                "timeslotid=" + timeslotid +
                ", status='" + status + '\'' +
                ", slotfrom='" + slotfrom + '\'' +
                ", slotto='" + slotto + '\'' +
                '}';
    }

    public int getTimeslotid() {
        return timeslotid;
    }

    public void setTimeslotid(int timeslotid) {
        this.timeslotid = timeslotid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlotfrom() {
        return slotfrom;
    }

    public void setSlotfrom(String slotfrom) {
        this.slotfrom = slotfrom;
    }

    public String getSlotto() {
        return slotto;
    }

    public void setSlotto(String slotto) {
        this.slotto = slotto;
    }
}
