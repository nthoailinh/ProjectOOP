package DataConnection;


import VietnameseHistorical.*;

import java.io.FileWriter;

public class FigureConnection extends GeneralConnection {
    public void Connection() {
        for (Event event : events) {
            for (int id : event.getFiguresID()) {
                figures.get(id).addEvent(event);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        FigureConnection figureConnection = new FigureConnection();
        figureConnection.getData();
        figureConnection.Connection();
        figureConnection.writeJsonFigure();
    }

}
