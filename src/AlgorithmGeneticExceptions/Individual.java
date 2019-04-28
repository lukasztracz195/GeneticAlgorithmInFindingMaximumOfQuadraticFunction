package AlgorithmGeneticExceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Individual {
    private double fenotyp;
    private double adaptation;
    private double probOfSelect;
    private double start;
    private double stop;

    public Individual(Individual item) {
        this.fenotyp = item.getFenotyp();
        this.adaptation = item.getAdaptation();
        this.probOfSelect = item.getProbOfSelect();
        this.start = item.getStart();
        this.stop = item.getStop();
    }

    public boolean isSelected(double valueFromRouletteWheel){
        return valueFromRouletteWheel >= start && valueFromRouletteWheel <= stop;
    }

    public Individual(double fenotyp, double adaptation) {
        this.fenotyp = fenotyp;
        this.adaptation = adaptation;
    }
}
