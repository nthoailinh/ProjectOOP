package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private int ID;
    private String name;
    private String location;
    private String description;
    private List<Integer> dynasties;
    private List<Integer> events;

    public Place(int ID, String name, String location, String description) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.dynasties = new ArrayList<>();
        this.events = new ArrayList<>();
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

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getDynasties() {
        return this.dynasties;
    }

    public List<Integer> getEvents() { return this.events; }

    public void addDynasty(Dynasty dynasty) {
        this.dynasties.add(dynasty.getID());
    }

    public void addEvent(Event event) {
        this.events.add(event.getID());
    }
}