package stack.postfix;

import fpinjava.Function;
import list.List;
import stack.ListStack;
import stack.Stack;

public class Postfix {

    private static double fact(double x) {
        return x < 1 ? 1 : x * fact(x-1);
    }

    static final Function<Double, Function<Double, Double>> add = x -> y -> x+y;
    static final Function<Double, Function<Double, Double>> sub = x -> y -> y-x;
    static final Function<Double, Function<Double, Double>> mul = x -> y -> x*y;
    static final Function<Double, Function<Double, Double>> div = x -> y -> y/x;
    static final Function<Double, Function<Double, Double>> pow = x -> y -> Math.pow(x, y);
    static final Function<Double, Double> fact = Postfix::fact;



    public static double eval(String expr) {
        return eval_(ListStack.empty(), List.words(expr));
    }

    public static double eval_(Stack<Double> s, List<String> expr) {
        //System.out.println(s.popTop().fst);wefweef
        if(expr.isEmpty()) {
            return s.top();
        }
            else if(expr.head().equals("+")) {
                return eval_(s.push(add.apply(s.top()).apply(s.pop().popTop().fst)), expr.tail());
        }
        else if(expr.head().equals("-")) {
            return eval_(s.push(add.apply(s.popTop().fst).apply(s.pop().popTop().fst)), expr.tail());
        }

            s = s.push(Double.valueOf(expr.head()));
            System.out.println("popTop auf liste: " + s.popTop().fst);
            System.out.println("printListe : " + s.toList());
            return eval_(s, expr.tail());
    }

    public static void main(String[] args) {
        System.out.println(add.apply(5.0).apply(3.0));
        System.out.println(sub.apply(3.0).apply(5.0));
        System.out.println(mul.apply(5.0).apply(3.0));
        System.out.println(div.apply(2.0).apply(4.0));
        System.out.println(pow.apply(2.0).apply(4.0));
        System.out.println(fact.apply(5.0));


        System.out.println(eval("5 6 + 3 - "));

    }
}
