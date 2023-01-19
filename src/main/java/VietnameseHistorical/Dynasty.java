package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

// Class to represent a Vietnamese historical dynasty
public class Dynasty {
    private String name;
    private String dates;
    private String description;
    private List<Figure> figures;
    private List<Place> places;
    private List<Festival> festivals;

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
    public void addFigure(Figure figure) {
        this.figures.add(figure);
    }

    // Method to add a historical place to the dynasty
    public void addPlace(Place place) {
        this.places.add(place);
    }

    // Method to add a cultural festival to the dynasty
    public void addFestival(Festival festival) {
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

    public List<Figure> getFigures() {
        return this.figures;
    }

    public List<Place> getPlaces() {
        return this.places;
    }

    public List<Festival> getFestivals() {
        return this.festivals;
    }
}