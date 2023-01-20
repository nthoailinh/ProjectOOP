package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
    private int ID;
    private String name;
    private String dates;
    private String description;
    private List<Integer> figuresID;
    private List<Integer> eventsID;

    public Dynasty(int ID, String name, String dates, String description) {
        this.ID = ID;
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.figuresID = new ArrayList<>();
        this.eventsID = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDates() {
        return dates;
    }

    public String getDescription() {
        return description;
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