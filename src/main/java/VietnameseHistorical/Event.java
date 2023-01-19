package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

// Class to represent a Vietnamese historical event
public class Event {
    private String name;
    private String dates;
    private String description;
    private List<Dynasty> dynasties;
    private List<Figure> figures;

    // Constructor
    public Event(String name, String dates, String description) {
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.dynasties = new ArrayList<>();
        this.figures = new ArrayList<>();
    }

    // Getters and setters for the fields
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

    // Methods to add and remove dynasties and figures from the lists
    public void addDynasty(Dynasty dynasty) {
        this.dynasties.add(dynasty);
    }

    public void removeDynasty(Dynasty dynasty) {
        this.dynasties.remove(dynasty);
    }

    public void addHistoricalFigure(Figure figure) {
        this.figures.add(figure);
    }

    public void removeHistoricalFigure(Figure figure) {
        this.figures.remove(figure);
    }
}