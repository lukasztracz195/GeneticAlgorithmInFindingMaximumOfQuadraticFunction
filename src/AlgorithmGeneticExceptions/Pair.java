package AlgorithmGeneticExceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pair {

    private Individual one;
    private Individual two;
    private double probability;

    public Individual getOne() {
        return one;
    }

    public void setOne(Individual one) {
        this.one = one;
    }

    public Individual getTwo() {
        return two;
    }

    public void setTwo(Individual two) {
        this.two = two;
    }


    public Pair(Individual one, Individual two, double probability) {
        this.one = one;
        this.two = two;
        this.probability = probability;
    }

    public void crossing(double probabilityOfCrossing) {
        if (probability <= probabilityOfCrossing) {
            double parentValue1 = one.getFenotyp();
            double parentValue2 = two.getFenotyp();
            one.setFenotyp(getFloor(parentValue1) + getFractionalPart(parentValue2));
            two.setFenotyp(getFloor(parentValue2) + (getFractionalPart(parentValue1)));
        }
    }

    private double getFractionalPart(double number) {
        int floor = (int)number;
        double value = number;
        if (floor < 0) {
            value = number + Math.abs(floor);
        } else {
            value = number - floor;
        }
        return value;
    }

    private double getFloor(double number) {
        int floor = (int) number;
        return (double) floor;
    }
}
