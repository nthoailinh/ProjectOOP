package connectors;

import models.Event;

public class FigureConnector extends GeneralConnector {
    public static void main(String[] args) throws Exception {
        FigureConnector figureConnector = new FigureConnector();
        figureConnector.getData();
        figureConnector.connect();
        figureConnector.writeToJsonFigure();
    }

    public void connect() {
        for (Event event : events) {
            for (int id : event.getFiguresID()) {
                figures.get(id).addEvent(event);
            }
        }
    }

}
