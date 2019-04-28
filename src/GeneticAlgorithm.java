import AlgorithmGeneticExceptions.Individual;
import AlgorithmGeneticExceptions.InvalidValueProbabilityOfCrossingException;
import AlgorithmGeneticExceptions.InvalidValueProbabilityOfMutation;
import AlgorithmGeneticExceptions.Pair;

import java.util.*;

public class GeneticAlgorithm {
    private int populationSize;
    private double probabilityOfCrossing;
    private double probabilityOfMutation;
    private List<Individual> population;
    private List<Individual> parentsPopulation;
    private QuadraticFunction function;
    private int numberOfCycle = 0;
    private Set<Integer> selectedParentsSet;
    private Random rand;
    private double acurracy;

    public GeneticAlgorithm(int populationSize, double probabilityOfCrossing, double probabilityOfMutation,double acurracy, QuadraticFunction function) throws InvalidValueProbabilityOfCrossingException, InvalidValueProbabilityOfMutation {
        if (probabilityOfCrossing > 1.0 || probabilityOfCrossing < 0.0) {
            throw new InvalidValueProbabilityOfCrossingException();
        }
        if (probabilityOfMutation > 1.0 || probabilityOfMutation < 0.0) {
            throw new InvalidValueProbabilityOfMutation();
        }
        this.function = function;
        this.populationSize = populationSize;
        this.probabilityOfCrossing = probabilityOfCrossing;
        this.probabilityOfMutation = probabilityOfMutation;
        this.acurracy = acurracy;
        parentsPopulation = new ArrayList<>();
        selectedParentsSet = new HashSet<>();
        this.rand = new Random();
        generatePopulation();
        while(repeat(acurracy)){
            numberOfCycle++;
            selection();
            createRouletteWheel();
            randomRouletteWheel();
            crossing();
            mutation();
            for (Individual item : population) {
                System.out.println(item);
            }
            System.out.println("Obieg " + numberOfCycle + " " + population.size());

        }
    }

    private void generatePopulation() {
        Double start = function.getRangeMIN();
        Double stop = function.getRangeMAX();
        double value = 0.0;
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            value = start + (stop - start) * rand.nextDouble();
            if (Double.isInfinite(value)) {
                value = start + (stop - start) * rand.nextDouble();
            } else {
                population.add(new Individual(value, function.evaluate(value)));
            }
            //System.out.println(population.get(i));
        }
        //System.out.println(population);
    }

    public GeneticAlgorithm(int populationSize, double probabilityOfCrossing, double probabilityOfMutation, int numberOfIteration, QuadraticFunction function) throws InvalidValueProbabilityOfCrossingException, InvalidValueProbabilityOfMutation {
        if (probabilityOfCrossing > 1.0 || probabilityOfCrossing < 0.0) {
            throw new InvalidValueProbabilityOfCrossingException();
        }
        if (probabilityOfMutation > 1.0 || probabilityOfMutation < 0.0) {
            throw new InvalidValueProbabilityOfMutation();
        }
        this.function = function;
        this.populationSize = populationSize;
        this.probabilityOfCrossing = probabilityOfCrossing;
        this.probabilityOfMutation = probabilityOfMutation;
        parentsPopulation = new ArrayList<>();
        selectedParentsSet = new HashSet<>();
        this.rand = new Random();
        generatePopulation();
        for(numberOfCycle = 1 ;numberOfCycle<= numberOfIteration;numberOfCycle++){
            selection();
            createRouletteWheel();
            randomRouletteWheel();
            crossing();
            mutation();
            System.out.println("Obieg " + numberOfCycle + " " + population.size());
            for (Individual item : population) {
                System.out.println(item);
            }
        }
    }

    private void selection() {
      //  System.out.println("Before selection " + population.size());
        double sum = 0;
        for (Individual item : population) {
            sum += item.getAdaptation();
        }
        for (Individual item : population) {
            item.setProbOfSelect(item.getAdaptation() / sum);
           // System.out.println(item);
        }
       // System.out.println("After selection " + population.size());
    }

    private void createRouletteWheel() {
     //   System.out.println("Before createRouletteWheel " + population.size());
        double start = 0.0;
        for (Individual item : population) {
            item.setStart(start);
            item.setStop(start + item.getProbOfSelect());
            start = start + item.getProbOfSelect();
       //     System.out.println(item);
        }
      //  System.out.println("After createRouletteWheel " + population.size());
    }

    private void randomRouletteWheel() {
      //  System.out.println("Before randomRouletteWheel " + population.size());
        double value = 0.0;
        parentsPopulation.clear();
        for (int i = 0; i < populationSize; i++) {
            value = rand.nextDouble();
            for (Individual item : population) {

                if (item.isSelected(value)) {
                    Individual indi = new Individual(item);
             //       System.out.println(indi);
                    parentsPopulation.add(indi);
                    break;
                }
            }
        }
        //System.out.println(parentsPopulation);
      //  System.out.println("After randomRouletteWheel parents " + parentsPopulation.size());
    }

    private void crossing() {
       // System.out.println("Before crossing " + population.size());
     //   System.out.println("Before crossing parentsPopulation:" + parentsPopulation.size());
        population.clear();
        selectedParentsSet.clear();
        int selectedIndexParent1 = 0;
        int selectedIndexParent2 = 0;
       // System.out.println(parentsPopulation.size());
        int i = 0;
        while (selectedParentsSet.size() < parentsPopulation.size()) {
       //     System.out.println("Croossing obieg: " + i);
            i++;
            while (selectedParentsSet.contains(selectedIndexParent1)) {
                selectedIndexParent1 = rand.nextInt(parentsPopulation.size());
            }
            selectedParentsSet.add(selectedIndexParent1);
            while (selectedParentsSet.contains(selectedIndexParent2)) {
                selectedIndexParent2 = rand.nextInt(parentsPopulation.size());
            }
            selectedParentsSet.add(selectedIndexParent2);
            //System.out.println(selectedIndexParent1 + " " + selectedIndexParent2);


          // System.out.println(selectedIndexParent1 + " " + selectedIndexParent2 + " sizeSet: " + selectedParentsSet.size());
            Pair pair = new Pair(parentsPopulation.get(selectedIndexParent1), parentsPopulation.get(selectedIndexParent2), rand.nextDouble());
      //      System.out.println("Before crossing in Crossing");
        //    System.out.println(pair.getOne());
         //   System.out.println(pair.getTwo());
            pair.crossing(probabilityOfCrossing);
       //     System.out.println("After crossing in Crossing");
          //  System.out.println(pair.getOne());
          //  System.out.println(pair.getTwo());
            population.add(pair.getOne());
            population.add(pair.getTwo());
        }
        setAdaptationValue(population);
     //   System.out.println("All items after crossing");
      //  System.out.println("After crossing " + population.size());
    }

    private void mutation() {
      //  System.out.println("Before mutation " + population.size());
        double mutationProbably = 0.0;
        double operation = 0.0;
        double valueForOperation = 0.0;
        for (Individual item : population) {
            mutationProbably = rand.nextDouble();
            if (mutationProbably <= probabilityOfMutation) {
                operation = rand.nextDouble();
                valueForOperation = rand.nextDouble();
                if (operation <= 0.5) {
                    item.setFenotyp(item.getFenotyp() - valueForOperation);
                } else {
                    item.setFenotyp(item.getFenotyp() + valueForOperation);
                }
            }
        }
        setAdaptationValue(population);
        //population = parentsPopulation;
        for (Individual item : population) {
            System.out.println(item);
        }
       // System.out.println("After mutation " + population.size());
    }

    private void setAdaptationValue(List<Individual> list) {
        for (Individual item : list) {
            item.setAdaptation(function.evaluate(item.getFenotyp()));
        }
    }

    private boolean repeat(double accuracy) {
        for (Individual item : population) {
            if (item.getAdaptation() < function.getYpeekFunction() + accuracy && item.getAdaptation() > function.getYpeekFunction() - accuracy ) {
                return false;
            }
        }
        return true;
    }
}
