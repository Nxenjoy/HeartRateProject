package nl.s5630213023.healthcareproject.HeartRate.exercise;

/**
 * Created by NxEnjoy on 3/15/2016.
 */
public class Exer {
    private int exercise_id;
    private String Date;
    private String Time;
    private int Timer;
    private String Type;

    public Exer(int exercise_id, String date, String time, int Timer, String Type) {
        this.exercise_id = exercise_id;
        Date = date;
        Time = time;
        this.Timer = Timer;
        this.Type = Type;
    }

    public String toString(){return this.getType()+" \t"+this.getTimer() +" minute";
    }

    public int getExercise_id() {return exercise_id;}

    public void setExercise_id(int exercise_id) {this.exercise_id = exercise_id;}

    public String getDate() {return Date;}

    public void setDate(String date) {Date = date;}

    public String getTime() {return Time;}

    public void setTime(String time) {Time = time;}

    public int getTimer() {return Timer;}

    public void setTimer(int timer) {Timer = timer;}

    public String getType() {return Type;}

    public void setType(String type) {Type = type;}
}
