package statistic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.*;
import services.GsonHandler;
import services.IJsonHandler;

import java.io.FileNotFoundException;
import java.util.List;

public class statistic {
    private static List<Dynasty> dynasties;
    private static List<Event> events;
    private static List<Festival> festivals;
    private static List<Figure> figures;
    private static List<Place> places;
    private static final IJsonHandler gson = new GsonHandler();


    public static void main(String[] args) throws FileNotFoundException {
        places = gson.fromJson("data/Place.json", new TypeToken<List<Place>>() {
        }.getType());
        dynasties = gson.fromJson("data/Dynasty.json", new TypeToken<List<Dynasty>>() {
        }.getType());
        events = gson.fromJson("data/Event.json", new TypeToken<List<Event>>() {
        }.getType());
        figures = gson.fromJson("data/Figure.json", new TypeToken<List<Figure>>() {
        }.getType());
        festivals = gson.fromJson("data/Festival.json", new TypeToken<List<Festival>>() {
        }.getType());
        printCounts(dynasties, events, festivals, figures, places);

    }
    private static void printCounts(List<Dynasty> dynasties, List<Event> events, List<Festival> festivals, List<Figure> figures, List<Place> places) {
        int dF = 0;
        int dE = 0;
        for (Dynasty dynasty : dynasties) {
            dF += dynasty.getFiguresID().size();
            dE += dynasty.getEventsID().size();
        }
        System.out.println("Dynasty");
        System.out.println("Number of Figures related: " + dF);
        System.out.println("Number of Events related: " + dE);

        System.out.println("Event");
        int eF = 0;
        int eP = 0;
        int eD = 0;
        int eFes = 0;
        for (Event event : events) {
            eF += event.getFiguresID().size();
            eP += event.getPlacesID().size();
            eD += event.getDynastiesID().size();
            eFes += event.getFestivalsID().size();
        }
        System.out.println("Number of Figures related: " + eF);
        System.out.println("Number of Places related: " + eP);
        System.out.println("Number of Dynasties related: " + eD);
        System.out.println("Number of Festivals related: " + eFes);

        int fF = 0;
        int fE = 0;
        for (Festival festival : festivals) {
            fF += festival.getFiguresID().size();
            fE += festival.getEventsID().size();
        }
        System.out.println("Festival");
        System.out.println("Number of Figures related: " + fF);
        System.out.println("Number of Events related: " + fE);

        int figureE = 0;
        int figureD = 0;
        for (Figure figure : figures) {
            figureE += figure.getEventsID().size();
            figureD += figure.getDynastiesID().size();
        }
        System.out.println("Figure");
        System.out.println("Figure's events: " + figureE);
        System.out.println("Figure's dynasties: " + figureD);

        int pE = 0;
        for (Place place : places) {
            pE += place.getEventsID().size();
        }
        System.out.println("Place");
        System.out.println("Place's events: " + pE);
    }
}
