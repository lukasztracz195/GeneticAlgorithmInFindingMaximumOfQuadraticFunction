import AlgorithmGeneticExceptions.InvalidValueProbabilityOfCrossingException;
import AlgorithmGeneticExceptions.InvalidValueProbabilityOfMutation;

public class Main {

    public static void main(String[] args) {

        QuadraticFunction qf = new QuadraticFunction(-5,7,3);
        System.out.println("Wynik: "+qf.getYpeekFunction());
        System.out.println("X range [ "+qf.getRangeMIN()+" ; "+qf.getRangeMAX()+" ]");
        try {
            GeneticAlgorithm ga = new GeneticAlgorithm(4,0.9,0.1,0.00001,qf);
        } catch (InvalidValueProbabilityOfCrossingException | InvalidValueProbabilityOfMutation e) {
            e.printStackTrace();
        }
    }
}
