package set;

import fpinjava.Result;

public interface SortedSet<A extends Comparable<A>> extends Set<A>{
    Result<A> lookupMax();
    Result<A> lookupMin();

}
