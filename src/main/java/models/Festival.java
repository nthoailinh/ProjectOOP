package models;

import java.util.ArrayList;
import java.util.List;

public class Festival extends HistoryObject {
    private List<Integer> figuresID;
    private List<Integer> eventsID;

    public Festival(int ID, String name, String dates, String description) {
        super(ID, name, dates, description);
        this.figuresID = new ArrayList<>();
        this.eventsID = new ArrayList<>();
    }

    public List<Integer> getFiguresID() {
        return figuresID;
    }

    public List<Integer> getEventsID() {
        return eventsID;
    }

    public void addFigure(Figure figure) {
        if (!figuresID.contains(figure.getID())){
            this.figuresID.add(figure.getID());
        }
    }

    public void addEvent(Event event) {
        if (!eventsID.contains(event.getID())){
            this.eventsID.add(event.getID());
        }
    }
}