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
    static final Function<Double, Function<Double, Double>> pow = x -> y -> Math.pow(y, x);
    static final Function<Double, Double> fact = Postfix::fact;



    public static double eval(String expr) {
        return eval_(ListStack.empty(), List.words(expr));
    }

    public static double eval_(Stack<Double> s, List<String> expr) {
        //System.out.println(s.popTop().fst);
        if(expr.isEmpty()) {
            return s.top();
        }
            else if(expr.head().equals("+")) {
                return eval_(s.pop().pop().push(add.apply(s.top()).apply(s.pop().top())), expr.tail());
        }
        else if(expr.head().equals("-")) {
            return eval_(s.pop().pop().push(sub.apply(s.top()).apply(s.pop().top())), expr.tail());
        }
        else if(expr.head().equals("*")) {
            return eval_(s.pop().pop().push(mul.apply(s.top()).apply(s.pop().top())), expr.tail());
        }
        else if(expr.head().equals("/")) {
            return eval_(s.pop().pop().push(div.apply(s.top()).apply(s.pop().top())), expr.tail());
        }
        else if(expr.head().equals("^")) {
            return eval_(s.pop().pop().push(pow.apply(s.top()).apply(s.pop().top())), expr.tail());
        }
        else if(expr.head().equals("!")) {
            return eval_(s.pop().push(fact.apply(s.top())), expr.tail());
        }

            s = s.push(Double.valueOf(expr.head()));
            // System.out.println("popTop auf liste: " + s.popTop().fst);
            // System.out.println("printListe : " + s.toList());
            return eval_(s, expr.tail());
    }
    /*
    public static double eval2(String expr) {
        return eval_(ListStack.empty(), List.words(expr));
    }

    public static double eval2_(Stack<Double> s, List<String> expr) {
        //System.out.println(s.popTop().fst);
        if(expr.isEmpty()) {
            return s.top();
        }
        else if(expr.head().equals("+")) {
            Double fst = s.top();
            s = s.pop();
            Double snd = s.top();
            s = s.pop();
            s = s.push(add.apply(fst).apply(snd));
            return eval2_(s, expr.tail());
        }
        else if(expr.head().equals("-")) {
            Double fst = s.top();
            s = s.pop();
            Double snd = s.top();
            s = s.pop();
            s = s.push(sub.apply(fst).apply(snd));
            return eval2_(s, expr.tail());
        }
        else if(expr.head().equals("*")) {
            Double fst = s.top();
            s = s.pop();
            Double snd = s.top();
            s = s.pop();
            s = s.push(mul.apply(fst).apply(snd));
            return eval2_(s, expr.tail());
        }
        else if(expr.head().equals("/")) {
            Double fst = s.top();
            s = s.pop();
            Double snd = s.top();
            s = s.pop();
            s = s.push(div.apply(fst).apply(snd));
            return eval2_(s, expr.tail());
        }
        else if(expr.head().equals("^")) {
            Double fst = s.top();
            s = s.pop();
            Double snd = s.top();
            s = s.pop();
            s = s.push(pow.apply(fst).apply(snd));
            return eval2_(s, expr.tail());
        }
        else if(expr.head().equals("!")) {
            Double fst = s.top();
            s = s.pop();
            s = s.push(fact.apply(fst));
            return eval2_(s, expr.tail());
        }

        s = s.push(Double.valueOf(expr.head()));
        // System.out.println("popTop auf liste: " + s.popTop().fst);
        // System.out.println("printListe : " + s.toList());
        return eval2_(s, expr.tail());
    }
*/
    public static void main(String[] args) {
        /*
        System.out.println(add.apply(5.0).apply(3.0));
        System.out.println(sub.apply(3.0).apply(5.0));
        System.out.println(mul.apply(5.0).apply(3.0));
        System.out.println(div.apply(2.0).apply(4.0));
        System.out.println(pow.apply(2.0).apply(4.0));
        System.out.println(fact.apply(5.0));



        System.out.println(eval("2 3 +" ) + "   exp: 5");
        System.out.println(eval("2 6 -") + "    exp: -4");
        System.out.println(eval("5 6 + 8 * ") + "   exp: 88");
        System.out.println(eval("10 4 3 + 2 * -") + "   exp: 26");
        System.out.println(eval("90 34 12 33 55 66 + * - + -") + "  exp: 1444");
        System.out.println(eval("2 2 3 ^ ^") + "    exp: 256");
        System.out.println(eval("3 ! 4 +") +  "    exp: 10");
        System.out.println(eval("4 12 + 4 4 * /") + "   exp: 16");
        */


        System.out.println("-----------------------------------------");

        System.out.println(eval("2 3 +" ) + "   exp: 5");
        System.out.println(eval("2 6 -") + "    exp: -4");
        System.out.println(eval("2 6 *") + "    exp: -4");
        System.out.println(eval("2 6 /") + "    exp: -4");
        System.out.println(eval("2 2 ^") + "    exp: -4");
        System.out.println(eval("10 4 3 + 2 * -") + "   exp: -4");
        System.out.println(eval("90 34 12 33 55 66 + * - + -") + "  exp: 4037");
        System.out.println(eval("2 2 3 ^ ^") + "    exp: 256");
        System.out.println(eval("3 ! 4 +") +  "    exp: 10");
        System.out.println(eval("4 12 + 4 4 * /") + "   exp: 1");
        System.out.println(eval("2 6 ^") + "    exp: 36");

    }
}
