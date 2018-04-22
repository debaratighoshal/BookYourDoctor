package healthcare.dev.debarati.bookyourdoctor.domain;

/**
 * Created by Debarati on 20-04-2018.
 */
import java.util.List;
import java.util.ArrayList;

public class GetDocDetails
{
    String DepartmentDetails;
    List<DocSchedule> DoctorSchedule;
    String userDetails;
    String timeslotdetails;
    String Message;
    String d;
    String Sucess;
    List<Availableschedule> availableschedule;

    public String getDepartmentDetails() {
        return DepartmentDetails;
    }

    public void setDepartmentDetails(String departmentDetails) {
        this.DepartmentDetails = departmentDetails;
    }

    public List<DocSchedule> getDoctorSchedule() {
        return DoctorSchedule;
    }

    public void setDoctorSchedule(List<DocSchedule> doctorSchedule) {
        this.DoctorSchedule = doctorSchedule;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getTimeslotdetails() {
        return timeslotdetails;
    }

    public void setTimeslotdetails(String timeslotdetails) {
        this.timeslotdetails = timeslotdetails;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getSucess() {
        return Sucess;
    }

    public void setSucess(String sucess) {
        Sucess = sucess;
    }

    public List<Availableschedule> getAvailableschedule() {
        return availableschedule;
    }

    public void setAvailableschedule(List<Availableschedule> availableschedule) {
        this.availableschedule = availableschedule;
    }

    public GetDocDetails(String departmentDetails, List<DocSchedule> doctorSchedule, String userDetails, String timeslotdetails, String message, String d, String sucess, List<Availableschedule> availableschedule) {
        this.DepartmentDetails = departmentDetails;
        this.DoctorSchedule = doctorSchedule;
        this.userDetails = userDetails;
        this.timeslotdetails = timeslotdetails;
        Message = message;
        this.d = d;
        Sucess = sucess;
        this.availableschedule = availableschedule;
    }
    public GetDocDetails()
    {

    }

    @Override
    public String toString() {
        return "GetDocDetails{" +
                "departmentDetails='" + DepartmentDetails + '\'' +
                ", doctorSchedule=" + DoctorSchedule +
                ", userDetails='" + userDetails + '\'' +
                ", timeslotdetails='" + timeslotdetails + '\'' +
                ", Message='" + Message + '\'' +
                ", d='" + d + '\'' +
                ", Sucess='" + Sucess + '\'' +
                ", availableschedule='" + availableschedule + '\'' +
                '}';
    }
}
