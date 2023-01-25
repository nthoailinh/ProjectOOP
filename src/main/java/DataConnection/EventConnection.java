package DataConnection;

import VietnameseHistorical.Dynasty;
import VietnameseHistorical.Event;
import VietnameseHistorical.Festival;
import VietnameseHistorical.Figure;
import VietnameseHistorical.Place;
public class EventConnection extends GeneralConnection {
    public void Connection() {

        for (Event event : events) {
            for(Figure figure : figures) {
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
                    if(festival.getDescription().contains(figures.get(id).getName())) {
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
    public static  void main(String[] args) throws  Exception{
        EventConnection eventConnection = new EventConnection();
        eventConnection.getData();
        eventConnection.Connection();
        eventConnection.writeJsonEvent();
    }
}
