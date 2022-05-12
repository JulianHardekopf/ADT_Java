package stack.postfix;

import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;

import static stack.postfix.Postfix.eval;

public class PostfixTest {

    @Data
    Iterable<Tuple.Tuple2<String, Double>> data_Postfix() {
        return Table.of(
                Tuple.of("3 4 +", 7.0),
                Tuple.of("2 6 -", -4.0),
                Tuple.of("10 4 3 + 2 * -", -12.0),
                Tuple.of("90 34 12 33 55 66 + * - + -", 7.0),
                Tuple.of("2 2 3 ^ ^", -4.0),
                Tuple.of("3 ! 4 +", -12.0),
                Tuple.of("4 12 + 4 4 * /", -12.0)
        );

    }
    @Property
    @FromData("data_Postfix")
    boolean postFixTuple(@ForAll String index, @ForAll double result) {
        return eval(index) == result;
    }


}
