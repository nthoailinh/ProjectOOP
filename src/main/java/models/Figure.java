package models;

import java.util.ArrayList;
import java.util.List;

public class Figure extends HistoryObject {
    private List<Integer> dynastiesID;
    private List<Integer> eventsID;

    public Figure(int ID, String name, String dates, String description) {
        super(ID, name, dates, description);
        this.dynastiesID = new ArrayList<>();
        this.eventsID = new ArrayList<>();
    }

    public List<Integer> getDynastiesID() {
        return dynastiesID;
    }

    public List<Integer> getEventsID() {
        return eventsID;
    }

    public void addDynasty(Dynasty dynasty) {
        if (!dynastiesID.contains(dynasty.getID())){
            this.dynastiesID.add(dynasty.getID());
        }
    }

    public void addEvent(Event event) {
        if (!eventsID.contains(event.getID())){
            this.eventsID.add(event.getID());
        }
    }
}