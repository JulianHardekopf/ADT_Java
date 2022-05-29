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


}
