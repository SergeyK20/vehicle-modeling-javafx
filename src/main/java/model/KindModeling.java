package main.java.model;

/**
 * Класс с видами генерации
 */
public enum KindModeling {
    DETERMINISTIC("Детерминированное"), ACCIDENTAL("Случайное");

    private String nameKindModeling;

    KindModeling(String nameKindModeling) {
        this.nameKindModeling = nameKindModeling;
    }

    public String getNameKindModeling() {
        return nameKindModeling;
    }

    public KindModeling getEnumsKindModeling(String nameKindModeling) {
        for (KindModeling element : KindModeling.values()) {
            if (element.getNameKindModeling().equals(nameKindModeling)) {
                return element;
            }
        }
        return null;
    }
}
