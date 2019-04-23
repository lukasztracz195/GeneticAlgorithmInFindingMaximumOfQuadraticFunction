public class QuadraticFunction {
    private double a;
    private double b;
    private double c;
    private double delta;

    public QuadraticFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.delta = (b * b) - (4 *a * c);
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
