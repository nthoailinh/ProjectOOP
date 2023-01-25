package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private int ID;
    private String name;
    private String location;
    private String description;
    private List<Integer> eventsID;

    public Place(int ID, String name, String location, String description) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.description = description;
        this.eventsID = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getEventsID() {
        return eventsID;
    }

    public void addEvent(Event event) {
        if (!eventsID.contains(event.getID())){
            this.eventsID.add(event.getID());
        }
    }
}