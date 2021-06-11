package constants;

public enum BedType {
    ICU,
    GENERAL,
    COVID,
    VENTILATOR;

    @Override
    public String toString() {
        return this.name();
    }
}
