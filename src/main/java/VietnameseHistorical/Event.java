package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int ID;
    private String name;
    private String dates;
    private String description;
    private List<Integer> dynastiesID;
    private List<Integer> figuresID;

    public Event(int ID, String name, String dates, String description) {
        this.ID = ID;
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.dynastiesID = new ArrayList<>();
        this.figuresID = new ArrayList<>();
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

    public List<Integer> getFigures() {
        return this.figuresID;
    }

    public void addDynasty(Dynasty dynasty) {
        this.dynastiesID.add(dynasty.getID());
    }

    public void addFigure(Figure figure) {
        this.figuresID.add(figure.getID());
    }
}