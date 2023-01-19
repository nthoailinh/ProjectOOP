package DataConnection;

import VietnameseHistorical.Dynasty;
import VietnameseHistorical.Figure;
import VietnameseHistorical.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.List;

public class DynastyConnection {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        List<Dynasty> dynasties = gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType());
        List<Figure> figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
        List<Place> places = gson.fromJson(new FileReader("data/Place.json"), new TypeToken<List<Place>>() {
        }.getType());

        for (Dynasty dyn : dynasties) {
            System.out.println(dyn.getName());
        }
    }
}
