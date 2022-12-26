package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

// Class to represent a Vietnamese historical place or relic
public class HistoricalPlace {
    private String name;
    private String location;
    private String description;
    private List<Dynasty> dynasties;
    private List<HistoricalEvent> events;

    // Constructor
    public HistoricalPlace(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.dynasties = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    // Getters and setters for the fields
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

    // Methods to add and remove dynasties and events from the lists
    public void addDynasty(Dynasty dynasty) {
        this.dynasties.add(dynasty);
    }

    public void removeDynasty(Dynasty dynasty) {
        this.dynasties.remove(dynasty);
    }

    public void addHistoricalEvent(HistoricalEvent event) {
        this.events.add(event);
    }

    public void removeHistoricalEvent(HistoricalEvent event) {
        this.events.remove(event);
    }
}