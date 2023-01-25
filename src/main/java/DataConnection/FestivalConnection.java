package DataConnection;

import VietnameseHistorical.Dynasty;
import VietnameseHistorical.Event;
import VietnameseHistorical.Festival;
import VietnameseHistorical.Figure;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class FestivalConnection extends GeneralConnection {
    public void Connection() {
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
    public static void main (String[]args) throws Exception {
        FestivalConnection festivalConnection = new FestivalConnection();
        festivalConnection.getData();
        festivalConnection.Connection();
        festivalConnection.writeJsonFestival();
    }
}
