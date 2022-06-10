package set;

import fpinjava.Function;
import fpinjava.Result;
import list.List;


public interface Set<A>  {
    boolean isEqualTo(Set<A> other);
    Set<A> insert(A e);
    Set<A> delete(A e);
    boolean member(A e);
    int size();
    boolean isEmpty();
    A findEq(A e);
    List<A> toList();
    boolean equals(Object o);
    public String toString();
    Set<A> union(Set<A> s);
    Set<A> intersection(Set<A> s);
    boolean any(Function<A, Boolean> p);
    boolean all(Function<A, Boolean> p);
    boolean isSubsetOf(Set<A> s);
    boolean disjoint(Set<A> s);
    Result<A> lookupEq(A e);

    // Laufzeit O(n)
    default <A> Set<A> filter(Function<A, Boolean> f, Set<A> xs) {
        return ListSet.fromList(xs.toList().filter(f));
    }
    // Laufzeit O(n)
    default  <A, B> Set<B> map(Function<A, B> f, Set<A> xs) {
        return ListSet.fromList(xs.toList().map(f));
    }
    // Laufzeit O(n)
    default  <A, B> B foldr(Function<A, Function<B, B>> f, B s, Set<A> xs) {
        return xs.isEmpty() ? s
                : f.apply(xs.toList().head()).apply(foldr(f, s, ListSet.fromList(xs.toList().tail())));
    }

    // Laufzeit O(n)
    default  <A, B> B foldl(Function<B, Function<A, B>> f, B s, Set<A> xs) {
        return xs.isEmpty() ? s
                : foldl(f, f.apply(s).apply(xs.toList().head()), ListSet.fromList(xs.toList().tail()));
    }
}
