package stack.postfix;

import fpinjava.Function;
import list.List;
import stack.ListStack;
import stack.Stack;

public class Postfix {

    final Function<Double, Function<Double, Double>> add = x -> y -> x+y;
    /*
    final Function<Double, Function<Double, Double>> sub =
    final Function<Double, Function<Double, Double>> mul =
    final Function<Double, Function<Double, Double>> div =
    final Function<Double, Function<Double, Double>> pow =
    final Function<Double, Double> fact =


     */



    public double eval(String expr) {



        return 0;
    }

    public double eval_(Stack<Double> s, List<String> expr) {

        if(expr.isEmpty()) {
            return 0;
        }


        return 0;
    }
}
