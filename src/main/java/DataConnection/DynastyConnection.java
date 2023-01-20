package DataConnection;

import VietnameseHistorical.Dynasty;
import VietnameseHistorical.Figure;
import VietnameseHistorical.Place;
import VietnameseHistorical.Festival;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
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
//        List<Festival> festivals = gson.fromJson(new FileReader("data/Festival.json"), new TypeToken<List<Festival>>() {
//        }.getType());
//        for (int i=0; i<figures.size(); ++i) {
//            for (int j=0; j<dynasties.size(); ++j){
//                if(dynasties.get(j).getDescription().contains((figures.get(i).getName()))){
//                    figures.get(i).addDynasty(dynasties.get(j));
//                    dynasties.get(j).addFigure((figures.get(i)));
//                }
//
//            }
//        }
        for(Dynasty dynasty:dynasties){
            for(Figure figure:figures){
                if(dynasty.getDescription().contains(figure.getName())){
                    figure.addDynasty(dynasty);
                    dynasty.addFigure(figure);
                }

            }
        }
        for(Dynasty dynasty:dynasties){
            List<Integer> figuresID = dynasty.getFigures();
            for(int id:figuresID){
                System.out.println(dynasty.getName() + " " + figures.get(id).getName());
            }
        }
    }
}
