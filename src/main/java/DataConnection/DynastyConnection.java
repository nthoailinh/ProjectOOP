package DataConnection;

import VietnameseHistorical.Dynasty;
import VietnameseHistorical.Figure;
import VietnameseHistorical.Place;
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
//        List<Figure> figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
//        }.getType());
//        for (int i=0; i<figures.size(); ++i) {
//            for (int j=0; j<dynasties.size(); ++j){
//                if(dynasties.get(j).getDescription().contains((figures.get(i).getName()))){
//                    figures.get(i).addDynasty(dynasties.get(j));
//                    dynasties.get(j).addFigure((figures.get(i)));
//                }
//            }
//        }
//        for(int i=0; i<dynasties.size(); ++i){
//            List<Figure> fs = dynasties.get(i).getFigures();
//            for(Figure f : fs){
//                System.out.println(dynasties.get(i).getName() + " " + f.getName());
//            }
//        }

        for(Dynasty dyn:dynasties){
            System.out.println(dyn.getID());
        }
    }
}
