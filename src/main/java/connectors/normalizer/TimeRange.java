package connectors.normalizer;

public class TimeRange {
    private int start;
    private int end;

    public TimeRange(String time) {
        String[] range = time.split("-|–");
        range[0] = range[0].contains("?") ? range[1] : range[0];
        range[1] = range[1].contains("?") ? range[0] : range[1];

        if (range[0].contains("TCN")) {
            range[0] = range[0].split(" ")[0];
            start = -Integer.parseInt(range[0].trim());
        } else {
            try{
                start = Integer.parseInt(range[0].trim());
            } catch (NumberFormatException ignored){

            }
        }

        if (range[1].contains("nay")) {
            end = Integer.MAX_VALUE;
        } else if (range[1].contains("TCN") || range[1].contains("SCN")) {
            range[1] = range[1].split(" ")[0];
            end = -Integer.parseInt(range[1].trim());
        } else {
            end = Integer.MIN_VALUE;
            try {
                end = Integer.parseInt(range[1].trim());
            } catch (NumberFormatException e) {
                if (range[1].contains("hoặc")) {
                    end = Integer.parseInt(range[1].split(" ")[0]);
                }
            }
        }
    }

    public boolean withinRange(TimeRange other) {
        return (start >= other.start && start <= other.end) || (end >= other.start && end <= other.end);
    }

    public static void main(String[] args){
        TimeRange t = new TimeRange("257 SCN –179 SCN");
        System.out.println(t.start + " " + t.end);
    }
}