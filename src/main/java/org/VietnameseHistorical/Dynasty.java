package org.VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

// Class to represent a Vietnamese historical dynasty
public class Dynasty {
    private String name;
    private String dates;
    private String description;
    private List<HistoricalFigure> figures;
    private List<HistoricalPlace> places;
    private List<CulturalFestival> festivals;

    // Constructor to initialize a new dynasty
    public Dynasty(String name, String dates, String description) {
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.figures = new ArrayList<>();
        this.places = new ArrayList<>();
        this.festivals = new ArrayList<>();
    }

    // Method to add a historical figure to the dynasty
    public void addFigure(HistoricalFigure figure) {
        this.figures.add(figure);
    }

    // Method to add a historical place to the dynasty
    public void addPlace(HistoricalPlace place) {
        this.places.add(place);
    }

    // Method to add a cultural festival to the dynasty
    public void addFestival(CulturalFestival festival) {
        this.festivals.add(festival);
    }

    // Getters for the fields of the dynasty class
    public String getName() {
        return this.name;
    }

    public String getDates() {
        return this.dates;
    }

    public String getDescription() {
        return this.description;
    }

    public List<HistoricalFigure> getFigures() {
        return this.figures;
    }

    public List<HistoricalPlace> getPlaces() {
        return this.places;
    }

    public List<CulturalFestival> getFestivals() {
        return this.festivals;
    }
}