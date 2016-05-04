package nl.s5630213023.healthcareproject.HeartRate.bloodpressure;

/**
 * Created by NxEnjoy on 3/10/2016.
 */
public class BloodPressure {
    private int pressure_id;
    private int systolic;
    private int diastolic;
    private String Date;
    private String Time;

    public BloodPressure(int pressure_id, int systolic, int diastolic, String date, String time) {
        this.pressure_id = pressure_id;
        this.systolic = systolic;
        this.diastolic = diastolic;
        Date = date;
        Time = time;
    }

    public String toString(){return this.getSystolic()+"/"+this.getDiastolic()+" mmHg \t"+this.getTime();}

    public int getPressure_id() {return pressure_id;}

    public void setPressure_id(int pressure_id) {this.pressure_id = pressure_id;}

    public int getSystolic() {return systolic;}

    public void setSystolic(int systolic) {this.systolic = systolic;}

    public int getDiastolic() {return diastolic;}

    public void setDiastolic(int diastolic) {this.diastolic = diastolic;}

    public String getDate() {return Date;}

    public void setDate(String date) {Date = date;}

    public String getTime() {return Time;}

    public void setTime(String time) {Time = time;}




}
