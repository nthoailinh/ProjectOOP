package DataConnection;

import VietnameseHistorical.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;

public class DynastyConnection {
    public static boolean checkValidTime(String time1, String time2){
        boolean containsDash = time1.contains("-") && time2.contains("-");
        boolean notContainsTwoQuestionsMark = !(time1.contains("? - ?") || time2.contains("? - ?"));
        return containsDash && notContainsTwoQuestionsMark;
    }
    public static boolean figureLivesInDynasty(String figureTime, String dynastyTime) {
        if (!checkValidTime(figureTime, dynastyTime)){
            return false;
        }
        // Split the strings by "-" to separate the start and end years
        String[] dynastyRange = dynastyTime.split("-");
        String[] figureRange = figureTime.split(" - ");
        
        int dynastyStart, figureStart, dynastyEnd, figureEnd;

        dynastyRange[0] = dynastyRange[0].contains("?") ? dynastyRange[1] : dynastyRange[0];
        dynastyRange[1] = dynastyRange[1].contains("?") ? dynastyRange[0] : dynastyRange[1];

        figureRange[0] = figureRange[0].contains("?") ? figureRange[1] : figureRange[0];
        figureRange[1] = figureRange[1].contains("?") ? figureRange[0] : figureRange[1];

        // Parse the start and end years for both the dynasty and the figure
        if (dynastyRange[0].contains("TCN")) {
            dynastyRange[0] = dynastyRange[0].split(" ")[0];
            dynastyStart = -Integer.parseInt(dynastyRange[0].trim());
//            System.out.print( dynastyStart + " ");
        } else {
            try {
                dynastyStart = Integer.parseInt(dynastyRange[0].trim());
            } catch (NumberFormatException e) {
                return false;
            }
//            System.out.print( dynastyStart + " ");
        }

        if (dynastyRange[1].contains("nay")) {
            dynastyEnd = Integer.MAX_VALUE;
//            System.out.print( dynastyEnd + " ");
        } else if (dynastyRange[1].contains("TCN") || dynastyRange[1].contains("SCN")) {
            dynastyRange[1] = dynastyRange[1].split(" ")[0];
            dynastyEnd = -Integer.parseInt(dynastyRange[1].trim());
//            System.out.print( dynastyEnd + " ");
        } else {
            dynastyEnd = Integer.MIN_VALUE;
            try {
                dynastyEnd = Integer.parseInt(dynastyRange[1].trim());
            } catch (NumberFormatException e) {
                if (dynastyRange[1].contains("hoặc")) {
                    dynastyEnd = Integer.parseInt(dynastyRange[1].split(" ")[0]);
                }
            }
//            System.out.print( dynastyEnd + " ");
        }

        if (figureRange[0].contains("TCN")) {
            figureRange[0] = figureRange[0].split(" ")[0];
            figureStart = -Integer.parseInt(figureRange[0].trim());
//            System.out.print( figureStart + " ");
        } else {
            figureStart = Integer.parseInt(figureRange[0].trim());
//            System.out.print( figureStart + " ");
        }

        if (figureRange[1].contains("nay")) {
            figureEnd = Integer.MAX_VALUE;
//            System.out.print( figureEnd + " ");
        } else if (figureRange[1].contains("TCN")) {
            figureRange[1] = figureRange[1].split(" ")[0];
            figureEnd = -Integer.parseInt(figureRange[1].trim());
//            System.out.print( figureEnd + " ");
        } else {
            figureEnd = Integer.parseInt(figureRange[1].trim());
//            System.out.print( figureEnd + " ");
        }

        // Check if the figure's time range is within the dynasty's time range
        return (figureStart >= dynastyStart && figureStart <= dynastyEnd) || (figureEnd >= dynastyStart && figureEnd <= dynastyEnd);
    }

    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        List<Dynasty> dynasties = gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType());
        List<Figure> figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
//        List<Figure> events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
//        }.getType());

        for (Dynasty dynasty : dynasties) {
            for (Figure figure : figures) {
                if (dynasty.getDescription().contains(figure.getName())) {
                    figure.addDynasty(dynasty);
                    dynasty.addFigure(figure);
                }
                String dynastyTime = dynasty.getDates();
                dynastyTime = dynastyTime.contains("–") ? dynastyTime.replace("–", "-") : dynastyTime;
                String figureTime = figure.getDates();
//                System.out.println(figureTime + " + " + dynastyTime);
                if (figureLivesInDynasty(figureTime, dynastyTime)) {
                    figure.addDynasty(dynasty);
                    dynasty.addFigure(figure);
                }
            }
        }

        String json = gson.toJson(dynasties);
        FileWriter writer = new FileWriter("data/Dynasty.json");
        writer.write(json);
        writer.close();

        json = gson.toJson(figures);
        writer = new FileWriter("data/Figure.json");
        writer.write(json);
        writer.close();

//        int line = 0;
//        for (Dynasty dynasty : dynasties) {
//            List<Integer> figuresID = dynasty.getFiguresID();
//            for (int id : figuresID) {
//                System.out.println(dynasty.getName() + " " + figures.get(id).getName());
//                line++;
//            }
//        }
//        System.out.println(line);
//        System.out.println(checkValidTime("1924 - 2015", "1955–1975"));
    }
}
