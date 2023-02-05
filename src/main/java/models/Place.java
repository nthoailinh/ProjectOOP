package models;

import java.util.ArrayList;
import java.util.List;

public class Place extends HistoryObject {
    private String location;
    private List<Integer> eventsID;

    public Place(int ID, String name, String location, String description) {
        super(ID, name, "", description);
        this.location = location;
        this.eventsID = new ArrayList<>();
    }

    public String getLocation() {
        return location;
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