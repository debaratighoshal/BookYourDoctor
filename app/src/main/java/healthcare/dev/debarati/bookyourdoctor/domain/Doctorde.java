package healthcare.dev.debarati.bookyourdoctor.domain;

/**
 * Created by Debarati on 20-04-2018.
 */

public class Doctorde {
    int doctorid;
     String doctorname;
     int departmentid;

    public Doctorde(int doctorid, String doctorname, int departmentid) {
        this.doctorid = doctorid;
        this.doctorname = doctorname;
        this.departmentid = departmentid;

    }

    @Override
    public String toString() {
        return "Doctorde{" +
                "doctorid=" + doctorid +
                ", doctorname='" + doctorname + '\'' +
                ", departmentid=" + departmentid +
                '}';
    }

    public Doctorde() {


    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }
}
