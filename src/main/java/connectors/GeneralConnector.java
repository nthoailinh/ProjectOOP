package connectors;

import models.*;
import services.GsonHandler;
import services.IJsonHandler;

import java.io.FileWriter;
import java.util.List;

public class GeneralConnector {
    public List<Festival> festivals;
    public List<Figure> figures;
    public List<Event> events;
    public List<Dynasty> dynasties;
    public List<Place> places;
    IJsonHandler gson = new GsonHandler();

    public GeneralConnector() {

    }

    public void getData() throws Exception {
        festivals = gson.fromJson("data/Festival.json");
        figures = gson.fromJson("data/Figure.json");
        events = gson.fromJson("data/Event.json");
        dynasties = gson.fromJson("data/Dynasty.json");
        places = gson.fromJson("data/Place.json");
    }

    public void writeToJsonDynasty() throws Exception {
        String json = gson.toJson(dynasties);
        FileWriter writer = new FileWriter("data/Dynasty.json");
        writer.write(json);
        writer.close();
    }

    public void writeToJsonFigure() throws Exception {
        String json = gson.toJson(figures);
        FileWriter writer = new FileWriter("data/Figure.json");
        writer.write(json);
        writer.close();
    }

    public void writeToJsonEvent() throws Exception {
        String json = gson.toJson(events);
        FileWriter writer = new FileWriter("data/Event.json");
        writer.write(json);
        writer.close();
    }

    public void writeToJsonPlace() throws Exception {
        String json = gson.toJson(places);
        FileWriter writer = new FileWriter("data/Place.json");
        writer.write(json);
        writer.close();
    }

    public void writeToJsonFestival() throws Exception {
        String json = gson.toJson(festivals);
        FileWriter writer = new FileWriter("data/Festival.json");
        writer.write(json);
        writer.close();
    }

}
