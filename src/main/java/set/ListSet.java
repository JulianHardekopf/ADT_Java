package set;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import static list.List.list;


public class ListSet<A> implements Set<A> {
    private final List<A> set;

	private ListSet(){
        this.set = list();
	}
    private ListSet(List<A> list) {this.set = list;}

    // Laufzeit O(n^2)
    @Override
    public Set<A> insert(A e) {
        return this.member(e) ? new ListSet<>(set.delete(e).cons(e))
                : new ListSet<>(set.cons(e));
    }
    // Laufzeit O(n)
    @Override
    public Set<A> delete(A e) {
        return this.member(e) ? new ListSet<>(set.delete(e)) : new ListSet<>(set);

    }
    // Laufzeit O(n)
    @Override
    public boolean member(A e) {
        return set.elem(e);
    }
    // Laufzeit O(n)
    @Override
    public int size() {
        return set.length();
    }
    // Laufzeit O(1)
    @Override
    public boolean isEmpty() {
        return this.set.isEmpty();
    }
    // Laufzeit O(n) bis O(n^2)
    @Override
    public A findEq(A e) {
        return this.member(e) ? set.filter(e::equals).head() : null;
    }
    // Laufzeit O(n)
    @Override
    public List<A> toList() {
        return set;
    }

    // hier fehlt noch without Nil
    @Override
    public String toString() {
        return this.isEmpty() ? "{}" : "{" +  set.toString() + "}";
    }


    public Set<A> union(Set<A> s) {
        //return fromList(List.append(s.intersection(this).toList(), Set.filter(x -> !s.member(x), this).toList()));
        return foldr(x -> y -> (ListSet<A>) y.insert(x), this, s);
    }

    @Override
    public Set<A> intersection(Set<A> s) {
        return filter(x -> s.member(x), this);
    }

    //
    @Override
    public boolean any(Function<A, Boolean> p) {
        return set.any(p);
    }

    @Override
    public boolean all(Function<A, Boolean> p) {
        return set.all(p);
    }

    public boolean isSubsetOf(Set<A> s) {
        return this.all(x -> s.member(x));
    }

    public boolean disjoint(Set<A> s){

        return !this.any(x -> s.member(x));
    }

    public boolean isEqualTo(Set<A> other){
        return this.isSubsetOf(other) && other.isSubsetOf(this);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Set && isEqualTo((ListSet) o);
  }


    public static <A> Set<A> empty() {
        return new ListSet<>();
    }

    public static <A> Set<A> fromList(List<A> list) {
        return List.foldl(x -> x::insert, empty(), list);
    }

    // idk how
    @SafeVarargs
    public static <A> Set<A> set(A... as) {
        return fromList(List.list(as));
    }
    public static Set<String> wordSet(String s) {
        return fromList(List.words(s));
    }

    public Result<A> lookupEq(A x) {
        return this.isEmpty() ? Result.empty() : Result.success(findEq(x));
    }
    static <A, B> B foldr(Function<A, Function<B, B>> f, B s, Set<A> xs) {
        return xs.isEmpty() ? s
                : f.apply(xs.toList().head()).apply(foldr(f, s, ListSet.fromList(xs.toList().tail())));
    }

    static <A, B> B foldl(Function<B, Function<A, B>> f, B s, Set<A> xs) {
        return xs.isEmpty() ? s
                : foldl(f, f.apply(s).apply(xs.toList().head()), ListSet.fromList(xs.toList().tail()));
    }

    static <A> Set<A> filter(Function<A, Boolean> f, Set<A> xs) {
        return ListSet.fromList(xs.toList().filter(f));
    }

    static <A, B> Set<B> map(Function<A, B> f, Set<A> xs) {
        return  ListSet.fromList(xs.toList().map(f));
    }



    public static void main(String[] args) {
        List list = list(1,2,3,4,5, 6, 7,8);
        List listdup = list(1,2,3,4,5);
        List listnub = list(1,1,1,2,3,4,5,5,6);
        Set set = new ListSet(list);
        Set set2 = new ListSet<>(listdup);
        Set set3 = new ListSet();
        Set set4 = new ListSet();
        System.out.println(set3.insert(1).insert(2).insert(3));
        System.out.println(set4.insert(3).insert(2).insert(1));
        System.out.println("kommutativ test: " + set3.equals(set4));
        System.out.println("toList:     " + set.toList());
        System.out.println("Member operation(souldbeTrue):  " +set.member(4));
        System.out.println("Member operation(souldBeFalse):  " +set.member(6));
        System.out.println("insert operation:   " +set.insert(6).toList());
        System.out.println("insert operation:   " +set.insert(3).toList());
        System.out.println("Delete operation:   " + set.delete(4).toList());
        System.out.println(set.findEq(3));
        System.out.println(set.toList().filter(x -> x.equals(3)));
        System.out.println(set.findEq(3));
        System.out.println(set.findEq(6));
        Set setFromList = fromList(listdup);
        System.out.println(setFromList.toList());
        System.out.println(set.toString());

        Function<Integer, Boolean> funFilter = x -> x%2 == 0;
        Function<Integer, Integer> funMap = x -> x*2;

        System.out.println("Filter Test:" + filter(funFilter, set).toList());

        System.out.println("Map Test:" + map(funMap, set).toList());

        System.out.println(set.intersection(set2).toList());
        System.out.println(set.union(set2));
        System.out.println(set2.union(set));

        System.out.println(wordSet("Elfen helfen Elfen"));
        System.out.println("to set test with List: " + listnub.toSet());
        System.out.println("List nub test:  " + listnub.nub());
        System.out.println(set.equals(set2));
        System.out.println(set.equals(set));

        List list3 = List.list(0);
        Set set5 = fromList(list3);
        Set set6 = fromList(list3);
        System.out.println(set5.insert(0).insert(1));
        System.out.println(set6.insert(1).insert(0));
        System.out.println(set5.equals(set6));
    }

}
