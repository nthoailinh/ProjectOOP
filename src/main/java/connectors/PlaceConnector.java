package connectors;

import models.Event;

public class PlaceConnector extends GeneralConnector {
    public static void main(String[] args) throws Exception {
        PlaceConnector placeConnection = new PlaceConnector();
        placeConnection.getData();
        placeConnection .connect();
        placeConnection.writeToJsonPlace();
    }

    public void connect() {
        for (Event event : events) {
            for (int id : event.getPlacesID()) {
                places.get(id).addEvent(event);
            }
        }
    }

}
