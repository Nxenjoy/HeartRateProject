package nl.s5630213023.healthcareproject.HeartRate.HeartRate;

/**
 * Created by NxEnjoy on 3/15/2016.
 */
public class Heart {
    private int heartRate_id;
    private int heartrate;
    private String Date;
    private String Time;
    private String Latitude;
    private String Longitude;
    private String Status;

    public Heart(int heartRate_id, int heartrate, String date, String time, String Latitude, String Longitude, String Status) {
        this.heartRate_id = heartRate_id;
        this.heartrate = heartrate;
        Date = date;
        Time = time;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Status = Status;
    }
    public String toString(){return this.getHeartrate()+" mmHg \t"+this.getTime();
    }
    public int getHeartRate_id() { return heartRate_id;}

    public void setHeartRate_id(int heartRate_id) {this.heartRate_id = heartRate_id;}

    public int getHeartrate() {return heartrate;}

    public void setHeartrate(int heartrate) {this.heartrate = heartrate;}

    public String getDate() {return Date;}

    public void setDate(String date) {Date = date;}

    public String getTime() {return Time;}

    public void setTime(String time) {Time = time;}

    public String getLatitude() {return Latitude;}

    public void setLatitude(String latitude) {Latitude = latitude;}

    public String getLongitude() {return Longitude;}

    public void setLongitude(String longitude) {Longitude = longitude;}

    public String getStatus() {return Status;}

    public void setStatus(String status) {Status = status;}


}
