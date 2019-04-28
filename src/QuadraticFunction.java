public class QuadraticFunction {
    private double a;
    private double b;
    private double c;
    private double delta;
    private double x1;
    private double x2;
    private Double rangeMIN;
    private Double rangeMAX;

    public QuadraticFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.delta = (b * b) - (4 *a * c);
            x1 = (-b - Math.sqrt(delta))/(2 *a);
            x2 = (-b + Math.sqrt(delta))/(2 *a);

        if(a < 0){
            rangeMAX = this.getXpeekFunction();
            if(x1 < rangeMAX){
                rangeMIN = x1;
            }else{
                rangeMIN = x2;
            }
        }else{
            rangeMIN = this.getXpeekFunction();
            if(x1 < rangeMIN){
                rangeMAX = x1;
            }else{
                rangeMAX = x2;
            }
        }
    }

    public Double getRangeMIN() {
        return rangeMIN;
    }

    public Double getRangeMAX() {
        return rangeMAX;
    }

    public double evaluate(double x){
        return a * ( x * x ) + ( b *x ) + c ;
    }

    public double getXpeekFunction(){
        return -( b / (2 * a));
    }

    public double getYpeekFunction(){
        return - (delta / (4 * a));
    }



}
