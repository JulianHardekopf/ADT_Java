package list;


import fpinjava.Function;
import fpinjava.Result;
import fpinjava.TailCall;
import set.ListSet;
import set.Set;


import static fpinjava.TailCall.ret;
import static fpinjava.TailCall.sus;

public abstract class List<A> {

    public abstract A head();

    public abstract List<A> tail();

    public abstract boolean isEmpty();

    public abstract List<A> setHead(A h);

    public abstract boolean equals(Object o);

    public abstract boolean isEqualTo(List<A> xs);

    public abstract int length();

    public abstract boolean elem(A x);

    public abstract boolean any(Function<A, Boolean> p);

    public abstract boolean all(Function<A, Boolean> p);

    public abstract <B> List<B> map(Function<A, B> f);

    public abstract List<A> filter(Function<A, Boolean> f);

    public abstract A finde(Function<A, Boolean> f);

    public abstract List<A> init();

    public abstract A last();

    public abstract List<A> take(int n);

    public abstract List<A> drop(int n);

    public abstract List<A> takeWhile(Function<A, Boolean> p);

    public abstract List<A> dropWhile(Function<A, Boolean> p);

    public abstract List<A> delete(A x);

    public abstract List<A> reverse();

    // FoldR Instanzen

    public abstract <B> B foldr(Function<A,Function<B,B>> f, B s);

    public abstract boolean elemR(A x);

    public abstract int lenghtR();

    public abstract boolean anyR(Function<A, Boolean> p);

    public abstract boolean allR(Function<A, Boolean> p);

    public abstract <B> List<B> mapR(Function<A, B> f);

    public abstract List<A> filterR(Function<A, Boolean> f);

    public abstract List<A> takeWhileR(Function<A, Boolean> p);

    public abstract String toStringR();

    // FoldL Instanzen


    public abstract <B> B foldl(Function<B, Function<A, B>> f, B s);

    public abstract Integer lengthL();

    public abstract boolean elemL(A x);

   // public abstract boolean allL(Function<A, Boolean> p);

    public abstract boolean anyL(Function<A, Boolean> p);

    public abstract A lastL();

    public abstract List<A> reverseL();

   // public abstract boolean allMitAnyL(Function<A, Boolean> p);

   // public abstract boolean elemMitAnyL(Function<A, Boolean> p);

    public abstract Result<A> headOption();

    public abstract Result<A> find(Function<A,Boolean> f);

    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    private List() {
    }

    public static Integer sum(List<Integer> list) {
        return list.isEmpty()
                ? 0
                : list.head() + sum(list.tail());
    }

    public static Double prod(List<Double> list) {
        return list.isEmpty()
            ? 1
            : list.head() * prod(list.tail());
    }
    public static <A> List<A> append(List<A> xs, List<A> ys) {
        return xs.isEmpty() ? ys
                : new Cons<>(xs.head(), append(xs.tail(), ys));
    }

    public static <A> List<A> concat(List<List<A>> list) {
        return list.isEmpty() ? list()
                : append(list.head(), concat(list.tail()));
    }
    public static boolean and(List<Boolean> list) {
        return list.isEmpty() || list.head() && and(list.tail());
    }

    public static boolean or(List<Boolean> list) {
        return !list.isEmpty() && (list.head() || or(list.tail()));
    }

    // compareTo value more than 0 if objekct greater(more Chars) than other Object
    public static Integer minimum(List<Integer> list) {
        if(list.isEmpty())  {
            throw new IllegalStateException("empty List");
        } else  {
            return list.length() == 1 ? list.head()
                    :  list.head().compareTo(minimum(list.tail())) < 0
                    ? list.head()
                    : minimum(list.tail());
        }

    }
    // compareTo value more than 0 if objekct greater(more Chars) than other Object
    public static Integer maximum(List<Integer> list) {
        if(list.isEmpty())  {
            throw new IllegalStateException("empty List");
        } else  {
            return list.length() == 1 ? list.head()
                    :  list.head().compareTo(maximum(list.tail())) > 0
                    ? list.head()
                    : maximum(list.tail());
        }

    }
    // A, B Type, s identity, f funktion and represents the operator
    public static <A, B> B foldr(Function<A, Function<B, B>> f, B s, List<A> xs) {
        return xs.isEmpty() ? s
                : f.apply(xs.head()).apply(foldr(f, s, xs.tail()));

    }
    // Stack save version
    public static <A, B> B foldl(Function<B, Function<A, B>> f, B s, List<A> xs) {
        return xs.isEmpty() ? s
                : foldl(f, f.apply(s).apply(xs.head()), xs.tail());
    }
    public <B> List<B> concatMap(Function<A, List<B>> f) {
        return concat(map(f));
    }

    public int euler1() {
        return sum(range(1, 999).filter(x -> x % 3 == 0 || x % 5 == 0));
    }
    //Rechtsfaltung Klassenmethoden

    public static Integer sumR(List<Integer> xs) {
        return foldr(x -> y -> x + y, 0, xs);
    }

    public static Double prodR(List<Double> xs) {
        return foldr(x -> y -> x * y, 1.0, xs);
    }

    public static boolean andR(List<Boolean> list) {
        return foldr(x -> y -> x && y, true, list);
    }

    public static boolean orR(List<Boolean> list) {
        return foldr(x -> y -> x || y, false, list);
    }

    public static <A> List<A> appendR(List<A> xs, List<A> ys) {
        return foldr(x -> l -> new Cons<>(x, l), ys, xs);
    }

    public static <A> List<A> concatR(List<List<A>> list) {
        return foldr(x -> y -> append(x, y), list(), list);
    }


    //Linksfaltung Klassenmethoden
    public static Integer sumL(List<Integer> xs) {
        return foldl(x -> y -> x + y, 0, xs);
    }

    public static Double prodL(List<Double> xs) {
        return foldl(x -> y -> x * y, 1.0, xs);
    }

    public static boolean andL(List<Boolean> list) {
        return foldl(x -> y -> x && y, true, list);
    }

    public static boolean orL(List<Boolean> list) {
        return foldl(x -> y -> x || y, false, list);
    }

    public static List<Integer> range(int start, int end) {
        return start > end ? list() : new Cons<>(start, range(start + 1, end));
    }
    public static List<String> words(String s) {
        return s.isEmpty() ? list() : list(s.split("[\\s]+"));
    }


    // test, weil Pipeline failed
    public boolean allL(Function<A, Boolean> p) {
        if(list().isEmpty()) {
            return false;
        } else {
            return foldl(y -> x -> p.apply(x) && y, true, tail());
        }
    }

    public boolean allMitAnyL(Function<A, Boolean> p) {
        if(list().isEmpty()) {
            return true;
        } else {
            return anyL(p) && tail().allMitAnyL(p);
        }
    }

    public boolean elemMitAnyL(Function<A, Boolean> p) {
        if(list().isEmpty()) {
            return false;
        } else {
            return anyL(p);
        }
    }
    public Set<A> toSet() {
        return ListSet.fromList(this);
    }
    public List<A> nub() {
        return toSet().toList();
    }



    // --------------------------------------------------------
    // new class
    private static class Nil<A> extends List<A> {

        private Nil() {
        }

        public List<A> cons(A a) {
            return new Cons<>(a, this);
        }

        public A head() {
            throw new IllegalStateException("head called en empty list");
        }

        public List<A> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHead(A h) {
            throw new IllegalStateException("setHead called on empty list");
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Nil;
        }

        @Override
        public boolean isEqualTo(List<A> xs) {
            return xs.isEmpty();
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public boolean elem(A x) {
            return false;
        }

        @Override
        public boolean any(Function<A, Boolean> p) {
            return false;
        }

        @Override
        public boolean all(Function<A, Boolean> p) {
            return true;
        }

        @Override
        public <B> List<B> map(Function<A, B> f) {
            return list();
        }

        @Override
        public List<A> filter(Function<A, Boolean> f) {
            return list();
        }

        @Override
        public A finde(Function<A, Boolean> f) {
            return null;
        }

        @Override
        public List<A> init() {
            throw new Error("empty list");
        }

        @Override
        public A last() {
            throw new Error("empty list");
        }

        @Override
        public List<A> take(int n) {
            return list();
        }

        @Override
        public List<A> drop(int n) {
            return list();
        }

        @Override
        public List<A> takeWhile(Function<A, Boolean> p) {
            return list();
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> p) {
            return list();
        }

        @Override
        public List<A> delete(A x) {
            return list();
        }

        @Override
        public List<A> reverse() {
            return this;
        }

        @Override
        public <B> B foldr(Function<A, Function<B, B>> f, B s) {
            return s;
        }

        @Override
        public boolean elemR(A x) {
            return false;
        }

        @Override
        public int lenghtR() {
            return 0;
        }

        @Override
        public boolean anyR(Function<A, Boolean> p) {
            return false;
        }

        @Override
        public boolean allR(Function<A, Boolean> p) {
            return true;
        }

        @Override
        public <B> List<B> mapR(Function<A, B> f) {
            return list();
        }

        @Override
        public List<A> filterR(Function<A, Boolean> f) {
            return list();
        }

        @Override
        public List<A> takeWhileR(Function<A, Boolean> p) {
            return list();
        }

        @Override
        public String toStringR() {
            return "";
        }

        @Override
        public <B> B foldl(Function<B, Function<A, B>> f, B s) {
            return s;
        }

        @Override
        public Integer lengthL() {
            return 0;
        }

        @Override
        public boolean elemL(A x) {
            return false;
        }

        /*
        @Override
        public boolean allL(Function<A, Boolean> p) {
            return true;
        }


         */
        @Override
        public boolean anyL(Function<A, Boolean> p) {
            return false;
        }

        @Override
        public A lastL() {
            return null;
        }

        @Override
        public List<A> reverseL() {
            return list();
        }
        /*
        @Override
        public boolean allMitAnyL(Function<A, Boolean> p) {
            return true;
        }

        @Override
        public boolean elemMitAnyL(Function<A, Boolean> p) {
            return false;
        }
        */
        // Damit Java keine Exception wirft - Result provided eine default value

        @Override
        public Result<A> headOption() {
            return Result.empty();
        }

        @Override
        public Result<A> find(Function<A, Boolean> f) {
            return Result.empty();
        }

        public String toString() {
            return "[NIL]";
        }
    }



    // --------------------------------------------------------
    // new class

    private static class Cons<A> extends List<A> {

        private final A head;
        private final List<A> tail;

        private Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public List<A> tail() {
            return tail;
        }

        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<A> setHead(A h) {
            return new Cons<>(h, tail());
        }

        // instanceOf determin whether the result is a success
        @Override
        public boolean equals(Object o) {
            return o instanceof Cons && isEqualTo((List<A>) o);
        }



        @Override
        public boolean isEqualTo(List<A> xs) {
            return !xs.isEmpty() && xs.head().equals(this.head()) && xs.tail().isEqualTo(this.tail());
        }

        @Override
        public int length() {
            return 1 + tail().length();
        }

        @Override
        public boolean elem(A x) {
            return x.equals(head()) || tail().elem(x);
        }

        @Override
        public boolean any(Function<A, Boolean> p) {
            return p.apply(head()) || tail().any(p);
        }

        @Override
        public boolean all(Function<A, Boolean> p) {
            return p.apply(head()) && tail().all(p);
        }

        @Override
        public <B> List<B> map(Function<A, B> f) {
            return new Cons<>(f.apply(head()), tail().map(f));
        }

        @Override
        public List<A> filter(Function<A, Boolean> f) {
            return f.apply(head()) ?
                    new Cons<>(head(), tail().filter(f))
                    : tail().filter(f);
        }

        @Override
        public A finde(Function<A, Boolean> f) {
            return filter(f).equals(list()) ? null : f.apply(head()) ? head() : tail.finde(f);
        }

        @Override
        public List<A> init() {
            return length() == 1 ? list()
                    : new Cons<>(head(), tail().init());
        }

        @Override
        public A last() {
            return length() == 1 ? head()
                    : tail().last();
        }

        @Override
        public List<A> take(int n) {
            return n <= 0 ? list()
                    : new Cons<>(head(), tail().take(n - 1));
        }

        @Override
        public List<A> drop(int n) {
            return n > 0 ? tail().drop(n - 1)
                    : this;
        }

        @Override
        public List<A> takeWhile(Function<A, Boolean> p) {
            return p.apply(head()) ? new Cons<>(head(), tail().takeWhile(p))
                    : list();
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> p) {
            return p.apply(head()) ? tail().dropWhile(p)
                    : new Cons<>(head(), tail());
        }

        @Override
        public List<A> delete(A x) {
            return head().equals(x) ? tail()
                    : new Cons<>(head(), tail().delete(x));
        }

        @Override
        public List<A> reverse() {
            return append(tail().reverse(), list(head()));
        }

        @Override
        public <B> B foldr(Function<A, Function<B, B>> f, B s) {
            return f.apply(head()).apply(tail().foldr(f, s));
        }

        @Override
        public boolean elemR(A x) {
            return foldr(y -> z -> y == x || z, false, tail());
        }

        @Override
        public int lenghtR() {
            return foldr(x -> n -> n + 1, 0, tail());
        }

        @Override
        public boolean anyR(Function<A, Boolean> p) {
            return foldr(x -> y -> p.apply(x) || y, false, tail());
        }

        @Override
        public boolean allR(Function<A, Boolean> p) {
            return foldr(x -> y -> p.apply(x) && y, true, tail());
        }

        @Override
        public <B> List<B> mapR(Function<A, B> f) {
            return foldr(x -> xs -> xs.cons(f.apply(x)), list(), tail());
        }

        @Override
        public List<A> filterR(Function<A, Boolean> f) {
            return foldr(x -> xs -> f.apply(x) ? xs.cons(x) : xs, list(), tail());
        }

        @Override
        public List<A> takeWhileR(Function<A, Boolean> p) {
            return foldr(x -> y -> p.apply(x) ? new Cons<>(x, y) : list(), list(), tail());
        }

        @Override
        public String toStringR() {
            return null;
        }


        @Override
        public <B> B foldl(Function<B, Function<A, B>> f, B s) {
            return tail().foldl(f, f.apply(s).apply(head()));
        }


        @Override
        public Integer lengthL() {
            return foldl(n -> x -> n + 1, 0, tail());
        }


         @Override
        public boolean elemL(A x) {
            return foldl(z -> y -> x == y || z, false, tail());
        }
        /*
        @Override
        public boolean allL(Function<A, Boolean> p) {
            return foldl(y -> x -> p.apply(x) && y, true, tail());
        }
         */

        @Override
        public boolean anyL(Function<A, Boolean> p) {
            return foldl(y -> x -> p.apply(x) || y, false, tail());
        }

        @Override
        public A lastL() {
            return foldl(y -> x -> x, head(), tail());
        }

        @Override
        public List<A> reverseL() {
            return foldl(xs -> xs::cons, list(), tail());
        }


        /*
        @Override
        public boolean allMitAnyL(Function<A, Boolean> p) {
            return anyL(p) && tail().allMitAnyL(p);
        }

        @Override
        public boolean elemMitAnyL(Function<A, Boolean> p) {
            return anyL(p);
        }
        */


        // Damit Java keine Exception wirft - Result provided eine default value
        @Override
        public Result<A> headOption() {
            return Result.success(head);
        }

        @Override
        public Result<A> find(Function<A, Boolean> f) {
            if(head().equals(list())) {
                return Result.empty();
            } else {
               return f.apply(head()) ? Result.success(head) : tail().find(f);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> List<A> list(A... a) {
        List<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Cons<>(a[i], n);
        }
        return n;
    }

    public String toString() {
        return String.format("[%sNIL]",
                toString(new StringBuilder(), this).eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
        return list.isEmpty()
                ? ret(acc)
                : sus(() -> toString(acc.append(list.head()).append(", "),
                list.tail()));
    }


}