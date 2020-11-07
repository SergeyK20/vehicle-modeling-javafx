package main.java.model;

public enum Distribution {
    NORM("Нормальное"),
    UNIFORM("Равномерное"),
    EXPONENTIAL("Экспоненциал"),
    DETERMINISTIC("Детерминированное");

    private final String nameDistribution;

    Distribution(String nameDistribution) {
        this.nameDistribution = nameDistribution;
    }

    public String getNameDistribution() {
        return nameDistribution;
    }

    public Distribution getEnumsDistribution(String nameDistribution){
        for(Distribution element: Distribution.values()){
            if(element.getNameDistribution().equals(nameDistribution)){
                return element;
            }
        }
        return null;
    }


}
