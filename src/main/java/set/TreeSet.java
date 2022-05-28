package set;

import fpinjava.Function;
import list.List;


public class TreeSet<A extends Comparable<A>> implements SortedSet<A> {
	

	private TreeSet(){
	}

	public static <A extends Comparable<A>> SortedSet<A> empty() {
		return new TreeSet<>();
	}

  public static <A extends Comparable<A>> SortedSet<A> fromList(List<A> list) {
		return new TreeSet<>();
  }

  @SafeVarargs
  public static <A extends Comparable<A>> SortedSet<A> set(A... as) {
		return new TreeSet<>();
  }

	public boolean isEqualTo(Set<A> other){
		return false;
	}

    @Override
    public Set<A> insert(A e) {
        return null;
    }

    @Override
    public Set<A> delete(A e) {
        return null;
    }

    @Override
    public boolean member(A e) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public A findEq(A e) {
        return null;
    }

    @Override
    public List<A> toList() {
        return null;
    }

    @Override
    public Set<A> union(Set<A> s) {
        return null;
    }

    @Override
    public Set<A> intersection(Set<A> s) {
        return null;
    }

    @Override
    public boolean any(Function<A, Boolean> p) {
        return false;
    }

    @Override
    public boolean all(Function<A, Boolean> p) {
        return false;
    }

    @Override
    public boolean isSubsetOf(Set<A> s) {
        return false;
    }

    @Override
    public boolean disjoint(Set<A> s) {
        return false;
    }

}
