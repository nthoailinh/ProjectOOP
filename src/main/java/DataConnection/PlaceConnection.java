package DataConnection;

import VietnameseHistorical.Event;
import VietnameseHistorical.Festival;
import VietnameseHistorical.Place;

import java.util.List;

public class PlaceConnection extends GeneralConnection {
    public void Connection() {
        for (Event event : events) {
            for (int id : event.getPlacesID()) {
                places.get(id).addEvent(event);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        PlaceConnection placeConnection = new PlaceConnection();
        placeConnection.getData();
        placeConnection.Connection();
        placeConnection.writeJsonPlace();
    }
}
