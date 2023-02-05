package models;

public abstract class HistoricalObject {
    private int ID;
    private String name;
    private String dates;
    private String description;

    public HistoricalObject(int ID, String name, String dates, String description) {
        this.ID = ID;
        this.name = name;
        this.dates = dates;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDates() {
        return dates;
    }

    public String getDescription() {
        return description;
    }
}
