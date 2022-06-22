package set;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import tree.bst.Tree;

import static list.List.list;


public class TreeSet<A extends Comparable<A>> implements SortedSet<A> {
	private final Tree<A> TREE;


    private TreeSet() {
        TREE = Tree.tree();
    }

	private TreeSet(Tree<A> tree){
        this.TREE = tree;
	}


    public static <A extends Comparable<A>> SortedSet<A> empty() {
		return new TreeSet<>();

	}

  public static <A extends Comparable<A>> SortedSet<A> fromList(List<A> list) {
		return new TreeSet<>(Tree.tree(list));
  }
  public static <A extends Comparable<A>> SortedSet<A> fromSet(Set<A> s) {
        return new TreeSet<>(Tree.tree(s.toList()));
   }
  @SafeVarargs
  public static <A extends Comparable<A>> SortedSet<A> set(A... as) {
		return new TreeSet<>(Tree.tree(as));
  }
  public static SortedSet<String> wordSet(String s) {
        return new TreeSet<>(Tree.tree(List.words(s)));
  }

	public boolean isEqualTo(Set<A> other){
        return this.isSubsetOf(other) && other.isSubsetOf(this);
	}
    public boolean equals(Object o) {
        return o instanceof SortedSet && isEqualTo((SortedSet) o);
    }
    @Override
    public Set<A> insert(A e) {
        return new TreeSet<>(TREE.insert(e));
    }

    @Override
    public Set<A> delete(A e) {
        return new TreeSet<>(TREE.delete(e));
    }

    @Override
    public boolean member(A e) {
        return TREE.member(e);
    }

    @Override
    public int size() {
        return TREE.size();
    }

    @Override
    public boolean isEmpty() {
        return TREE.isEmpty();
    }

    @Override
    public A findEq(A e) {
        return TREE.findEq(e);
    }

    @Override
    public List<A> toList() {
        return TREE.inorder();
    }

    @Override
    public Set<A> union(Set<A> s) {
        return s.union(TREE.inorder().toSet());
    }

    @Override
    public Set<A> intersection(Set<A> s) {
        return s.intersection(TREE.inorder().toSet());
    }

    @Override
    public boolean any(Function<A, Boolean> p) {
        return TREE.inorder().toSet().any(p);
    }

    @Override
    public boolean all(Function<A, Boolean> p) {
        return TREE.inorder().toSet().all(p);
    }

    @Override
    public boolean isSubsetOf(Set<A> s) {
        return s.isSubsetOf(TREE.inorder().toSet());
    }

    @Override
    public boolean disjoint(Set<A> s) {
        return s.disjoint(TREE.inorder().toSet());
    }

    @Override
    public Result<A> lookupEq(A e) {
        return TREE.inorder().toSet().lookupEq(e);
    }
    @Override
    public Result<A> lookupMax() {
        return TREE.lookupMax();
    }

    @Override
    public Result<A> lookupMin() {
        return TREE.lookupMin();
    }

    @Override
    public A findMax() {
        return TREE.findMax();
    }

    @Override
    public A findMin() {
        return TREE.findMin();
    }
    @Override
    public String toString() {
        return TREE.inorder().toString();
    }

    public static <A> Set<A> filter(Function<A, Boolean> f, Set<A> xs) {
        return ListSet.filter(f,xs);
    }
    // Laufzeit O(n)
    public static <A, B> Set<B> map(Function<A, B> f, Set<A> xs) {
        return ListSet.map(f,xs);
    }
    // Laufzeit O(n)
    public static <A, B> B foldr(Function<A, Function<B, B>> f, B s, Set<A> xs) {
        return ListSet.foldr(f,s,xs);
    }

    // Laufzeit O(n)
    public static <A, B> B foldl(Function<B, Function<A, B>> f, B s, Set<A> xs) {
        return ListSet.foldl(f,s,xs);
    }


    public static void main(String[] args) {

        List l = List.list(3,4,52,2,1);
        /*
        SortedSet ts1 = fromList(l);
        SortedSet ts2 = new TreeSet();
        ts2.insert(2);
        System.out.println(ts2.toList());
        System.out.println(ts1);
        System.out.println(ts1.insert(9));

         */
        List list = list(5,321,43,422,5453,2,45);
        SortedSet<Integer>  set = fromList(list);
        SortedSet<Integer> treeset = fromList(l);
        System.out.println("toList: " + treeset.toList());
        System.out.println("Insert Test: " + treeset.insert(0).toList());
        System.out.println("delel Test: " + treeset.delete(0).toList());
        System.out.println("Size test: " + treeset.size());
        System.out.println("member Test: " + treeset.member(3));
        System.out.println("UnionTest: " + treeset.union(set).toList());
        Function<Integer, Boolean> fun = x -> x%2 == 0;
        System.out.println("Filter test coming: ");
        System.out.println(treeset.toList());
        System.out.println(set.toList());
        System.out.println("Filter test with default: " + filter(fun, treeset));
        System.out.println("fromSet test: " + fromSet(set).toList());
        String test = "Elfen helfen Elfeen test test AA";
        System.out.println(wordSet(test).toList());
        //Tree tree
        SortedSet<Integer> ts1 = fromList(list);
        System.out.println(ts1.member(2));
        System.out.println(ts1.lookupMin());
        System.out.println(ts1.findMax());
        System.out.println(ts1.findMin());
        System.out.println(ts1);
        List list2 = list(3,2,1,15,6);
        System.out.println(fromList(list2));
    }


}

