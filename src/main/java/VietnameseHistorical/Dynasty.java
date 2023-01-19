package VietnameseHistorical;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
    
    private int ID;
    private String name;
    private String dates;
    private String description;
    private List<Integer> figuresID;
    private List<Integer> placesID;
    private List<Integer> festivalsID;

    public Dynasty(int ID, String name, String dates, String description) {
        this.ID = ID;
        this.name = name;
        this.dates = dates;
        this.description = description;
        this.figuresID = new ArrayList<>();
        this.placesID = new ArrayList<>();
        this.festivalsID = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return this.name;
    }

    public String getDates() {
        return this.dates;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Integer> getFigures() {
        return this.figuresID;
    }

    public List<Integer> getPlaces() {
        return this.placesID;
    }

    public List<Integer> getFestivals() {
        return this.festivalsID;
    }

    public void addFigure(Figure figure) {
        this.figuresID.add(figure.getID());
    }

    public void addPlace(Place place) {
        this.placesID.add(place.getID());
    }

    public void addFestival(Festival festival) {
        this.festivalsID.add(festival.getID());
    }
}