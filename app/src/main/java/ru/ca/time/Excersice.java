package ru.ca.time;


public class Excersice {

    String name,desc;
    int time;

    public Excersice(String name, int time, String desc){
        this.name = name;
        this.time = time;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getTime() {
        return time;
    }

    public String getStringTime() {
        String result = "";
        if (String.valueOf(time % 60).length() == 1){
            result = String.valueOf((time/60)%24) + ":0" + String.valueOf(time % 60);
        }
        else {
            result = String.valueOf((time/60)%24) + ":" + String.valueOf(time % 60);
        }
        return String.valueOf(result);
    }
}
