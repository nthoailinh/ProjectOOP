package org.VietnameseHistorical;

public class Main {
    public static void main(String[] args) {
        // Create some instances of the Dynasty class
        Dynasty hongBang = new Dynasty("Hong Bang", "2879 BC - 258 AD", "The first known dynasty of Vietnam");
        Dynasty anDuongVuong = new Dynasty("An Duong Vuong", "257 BC - 207 BC", "The second known dynasty of Vietnam");
        Dynasty bacDoiI = new Dynasty("Bac Doi I", "206 BC - 100 BC", "The third known dynasty of Vietnam");

        // Create some instances of the HistoricalFigure class
//    HistoricalFigure anDuongVuong = new HistoricalFigure("An Duong Vuong", "257 BC - 207 BC", "Founder of the An Duong Vuong dynasty");
        HistoricalFigure trieuDa = new HistoricalFigure("Trieu Da", "230 BC - 207 BC", "Son of An Duong Vuong and ruler of the An Duong Vuong dynasty");

        // Create some instances of the HistoricalPlace class
        HistoricalPlace coLoaCitadel = new HistoricalPlace("Co Loa Citadel", "Hanoi, Vietnam", "An ancient citadel that served as the capital of the An Duong Vuong dynasty");
        HistoricalPlace ngocLangTemple = new HistoricalPlace("Ngoc Lang Temple", "Hanoi, Vietnam", "A temple dedicated to An Duong Vuong, the founder of the An Duong Vuong dynasty");

        // Create some instances of the CulturalFestival class
        CulturalFestival tetHoliday = new CulturalFestival("Tet Holiday", "January/February", "A traditional Vietnamese holiday that celebrates the start of the lunar new year");
        CulturalFestival midAutumnFestival = new CulturalFestival("Mid-Autumn Festival", "September/October", "A traditional Vietnamese holiday that celebrates the autumn harvest");

        // Create some instances of the HistoricalEvent class
        HistoricalEvent establishmentOfAnDuongVuongDynasty = new HistoricalEvent("Establishment of the An Duong Vuong Dynasty", "257 BC", "The establishment of the An Duong Vuong dynasty, marking the beginning of a new era in Vietnamese history");
        HistoricalEvent fallOfAnDuongVuongDynasty = new HistoricalEvent("Fall of the An Duong Vuong Dynasty", "207 BC", "The fall of the An Duong Vuong dynasty, marking the end of this era in Vietnamese history");

        // Link the instances together using the methods defined in the classes
//    hongBang.addFigure(anDuongVuong);
        hongBang.addPlace(coLoaCitadel);
        hongBang.addFestival(tetHoliday);
//    hongBang.addEvent(establishmentOfAnDuongVuongDynasty);

//    anDuongVuong.addDynasty(hongBang);
//    anDuongVuong.addHistoricalFigure(trieuDa);
//    anDuongVuong.addHistoricalPlace(coLoaCitadel);
//    anDuongVuong.addHistoricalPlace(ngocLangTemple);
    }
}
