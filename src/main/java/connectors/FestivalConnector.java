package connectors;

import models.Event;
import models.Festival;
import models.Figure;

public class FestivalConnector extends GeneralConnector {
    public static void main(String[] args) throws Exception {
        FestivalConnector festivalConnector = new FestivalConnector();
        festivalConnector.getData();
        festivalConnector.connect();
        festivalConnector.writeToJsonFestival();
    }

    public void connect() {
        for (Festival festival : festivals) {
            for (Figure figure : figures) {
                if (festival.getDescription().contains(figure.getName())) {
                    festival.addFigure(figure);
                }
            }
        }

        for (Event event : events) {
            for (int id : event.getFestivalsID()) {
                festivals.get(id).addEvent(event);
            }
        }
    }
}
