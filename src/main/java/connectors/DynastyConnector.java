package connectors;

import connectors.normalizer.TimeRange;
import models.*;

import java.io.FileNotFoundException;

public class DynastyConnector extends GeneralConnector {
    public static void main(String[] args) throws Exception {
        DynastyConnector dynastyConnector = new DynastyConnector();
        dynastyConnector.getData();
        dynastyConnector.connect();
        dynastyConnector.writeToJsonDynasty();
        dynastyConnector.writeToJsonFigure();
        dynastyConnector.writeToJsonEvent();
    }

    public void connect() throws FileNotFoundException {
        for (Dynasty dynasty : dynasties) {
            String dynastyTime = dynasty.getDates();
            dynastyTime = dynastyTime.contains("–") ? dynastyTime.replace("–", "-") : dynastyTime;

            for (Figure figure : figures) {
                if (dynasty.getDescription().contains(figure.getName())) {
                    figure.addDynasty(dynasty);
                    dynasty.addFigure(figure);
                }
                String figureTime = figure.getDates();
                if (figureLivesInDynasty(figureTime, dynastyTime)) {
                    figure.addDynasty(dynasty);
                    dynasty.addFigure(figure);
                }
            }

            for (Event event : events) {
                String eventTime = event.getDates();
                if (eventTookPlaceInDynasty(eventTime, dynastyTime)) {
                    event.addDynasty(dynasty);
                    dynasty.addEvent(event);
                }
            }
        }
    }

    public boolean checkValidTime(String time1, String time2) {
        boolean containsDash = time1.contains("-") && time2.contains("-");
        boolean notContainsTwoQuestionsMark = !(time1.contains("? - ?") || time2.contains("? - ?"));
        return !containsDash || !notContainsTwoQuestionsMark;
    }

    public boolean figureLivesInDynasty(String figureTime, String dynastyTime) {
        if (checkValidTime(figureTime, dynastyTime)) {
            return false;
        }
        TimeRange dynasty = new TimeRange(dynastyTime);
        TimeRange figure = new TimeRange(figureTime);
        return figure.withinRange(dynasty);
    }

    public boolean eventTookPlaceInDynasty(String eventTime, String dynastyTime) {
        if (checkValidTime(eventTime, dynastyTime)) {
            return false;
        }
        TimeRange dynasty = new TimeRange(dynastyTime);
        TimeRange event = new TimeRange(eventTime);
        return event.withinRange(dynasty);
    }

}
