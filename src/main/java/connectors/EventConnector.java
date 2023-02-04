package connectors;

import models.Event;
import models.Festival;
import models.Figure;
import models.Place;

public class EventConnector extends GeneralConnector {
    public static void main(String[] args) throws Exception {
        EventConnector eventConnector = new EventConnector();
        eventConnector.getData();
        eventConnector.connect();
        eventConnector.writeToJsonEvent();
    }

    public void connect() {

        for (Event event : events) {
            for (Figure figure : figures) {
                if (event.getDescription().contains(figure.getName())) {
                    event.addFigure(figure);
                }
            }

            for (Place place : places) {
                if (event.getDescription().contains(place.getName())) {
                    event.addPlace(place);
                }
            }
        }

        for (Event event : events) {
            for (Festival festival : festivals) {
                for (int id : event.getFiguresID()) {
                    if (festival.getDescription().contains(figures.get(id).getName())) {
                        event.addFestival(festival);
                    }
                }

                for (int id : event.getPlacesID()) {
                    if (festival.getDescription().contains(places.get(id).getName())) {
                        event.addFestival(festival);
                    }
                }
            }
        }
    }
}
