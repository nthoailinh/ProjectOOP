package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

// Class to represent a historical figure of Vietnam
public class HistoricalFigure {
    private String name;
    private String dates;
    private String description;
    private List<Dynasty> dynasties;
    private List<HistoricalEvent> events;

    // Constructor to initialize a new historical figure
    public HistoricalFigure(String name, String dates, String description) {
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.dynasties = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    // Method to add a dynasty to the historical figure
    public void addDynasty(Dynasty dynasty) {
        this.dynasties.add(dynasty);
    }

    // Method to add a historical event to the historical figure
    public void addEvent(HistoricalEvent event) {
        this.events.add(event);
    }

    // Getters for the fields of the historical figure class
    public String getName() {
        return this.name;
    }

    public String getDates() {
        return this.dates;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Dynasty> getDynasties() {
        return this.dynasties;
    }

    public List<HistoricalEvent> getEvents() {
        return this.events;
    }
}