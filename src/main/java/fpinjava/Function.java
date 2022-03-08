package fpinjava;

public interface Function<T, U> {

  U apply(T arg);

  default <V> Function<V, U> compose(Function<V, T> f) {
    throw new UnsupportedOperationException("Diese Methode muss ich wohl selber noch implementieren");
  }

  default <V> Function<T, V> andThen(Function<U, V> f) {
    return x -> f.apply(apply(x));
  }

  static <T> Function<T, T> id() {
    throw new UnsupportedOperationException("Diese Methode muss ich wohl selber noch implementieren");
  }

  static  Function<Boolean, Boolean> not() {
    throw new UnsupportedOperationException("Diese Methode muss ich wohl selber noch implementieren");
  }
  
  static <T, U, V> Function<V, U> compose(Function<T, U> g, Function<V, T> f) {
    throw new UnsupportedOperationException("Diese Methode muss ich wohl selber noch implementieren");
  }

  static <T, U, V> Function<T, V> andThen(Function<T, U> f, Function<U, V> g) {
    return x -> g.apply(f.apply(x));
  }

  static <T, U, V> Function<T, V> andThen2(Function<T, U> f, Function<U, V> g) {
    return x -> g.compose(f).apply(x);
  }

  static <T, U, V> Function<T, V> andThen3(Function<T, U> f, Function<U, V> g) {
    return compose(g, f);
  }

  static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> compose() {
    return x -> y -> y.compose(x);
  }

  static <T, U, V> Function<Function<T, U>, Function<Function<V, T>, Function<V, U>>> andThen() {
    return x -> y -> y.andThen(x);
  }

  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
    return g -> f -> x -> g.apply(f.apply(x));
  }

  static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen() {
    return f -> g -> z -> g.apply(f.apply(z));
  }

}
