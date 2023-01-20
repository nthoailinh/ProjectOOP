package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

public class Festival {
    private int ID;
    private String name;
    private String dates;
    private String description;
    private List<Integer> dynastiesID;
    private List<Integer> eventsID;

    public Festival(int ID, String name, String dates, String description) {
        this.ID = ID;
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.dynastiesID = new ArrayList<>();
        this.eventsID = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDates() {
        return this.dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getDynasties() {
        return this.dynastiesID;
    }

    public List<Integer> getEvents() {
        return this.eventsID;
    }

    public void addDynasty(Dynasty dynasty) {
        this.dynastiesID.add(dynasty.getID());
    }

    public void addEvent(Event event) {
        this.eventsID.add(event.getID());
    }
}