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

public class FestivalConnection {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        List<Festival> festivals = gson.fromJson(new FileReader("data/Festival.json"), new TypeToken<List<Festival>>() {
        }.getType());
        List<Figure> figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
        List<Event> events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType());

        for (Festival festival : festivals) {
            for (Figure figure : figures) {
                if (festival.getDescription().contains(figure.getName())) {
                    festival.addFigure(figure);
                }
            }
        }

        String json = gson.toJson(festivals);
        FileWriter writer = new FileWriter("data/Festival.json");
        writer.write(json);
        writer.close();

//        json = gson.toJson(events);
//        writer = new FileWriter("data/Event.json");
//        writer.write(json);
//        writer.close();

        int line = 0;
        for (Festival festival : festivals) {
            List<Integer> figuresID = festival.getFiguresID();
            for (int id : figuresID) {
                System.out.println(festival.getName() + " -- " + figures.get(id).getName());
                line++;
            }
        }
        System.out.println(line);
    }
}
