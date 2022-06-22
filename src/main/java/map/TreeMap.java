package map;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import set.ListSet;
import set.Set;
import set.SortedSet;
import set.TreeSet;
import tree.bst.Tree;
import tuple.Tuple;

import static map.Entry.mapEntry;


public class TreeMap<K,V> implements Map<K,V> {
	private final SortedSet<Entry<K,V>> set;

	@SuppressWarnings("unchecked")
	private TreeMap(){
		this(TreeSet.empty());
	}

	private TreeMap(SortedSet<Entry<K,V>> s) {
		set = s;
	}
    public static <K extends Comparable<K>,V> Map<K,V> empty() {
        return new TreeMap<>();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.set;
    }
    @Override
    public <K1, V1> Map<K1, V1> fromEntrySet(Set<Entry<K1, V1>> s) {
        return new TreeMap<>(TreeSet.fromSet(s));
    }
    public static <K1, V1> Map<K1, V1> fromEntrySet1(Set<Entry<K1, V1>> s) {
        return new TreeMap<>(TreeSet.fromSet(s));
    }
    public static <K extends Comparable<K>,V> Map<K,V> fromList(List<Tuple<K,V>> l) {
        return new TreeMap<>(TreeSet.fromSet(Set.map(x ->  mapEntry(x.fst, x.snd), l.toSet())));
        //return new TreeMap<>();
    }


	public static Map<String,Integer> wordMap(String s){
		return new TreeMap<>();
	}

	public boolean isEqualTo(Map<K, V> o){
		return false;
	}

	public Map<K,V> insert(K key, V value){
		return new TreeMap<>(TreeSet.fromSet(set.insert(mapEntry(key,value))));
        //return new TreeMap<>(TreeSet.fromSet(set).insert(mapEntry(key, value)));
	}

    @Override
    public boolean member(K key) {
        return false;
    }

    @Override
    public Map<K, V> delete(K key) {
        return null;
    }

    @Override
    public V get(K key) {
        return null;
    }

    public Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value) {
		return new TreeMap<>();
	}





    @Override
    public List<Tuple<K, V>> toList() {
        return null;
    }

    @Override
    public List<K> keys() {
        return null;
    }

    @Override
    public Set<K> keysSet() {
        return null;
    }

    @Override
    public List<V> elems() {
        return null;
    }

    @Override
    public boolean all(Function<K, Function<V, Boolean>> p) {
        return false;
    }

    @Override
    public boolean allKeys(Function<K, Boolean> p) {
        return false;
    }

    @Override
    public boolean isSubmapOf(Map<K, V> m) {
        return false;
    }

    @Override
    public <V2> V2 foldr(Function<V, Function<V2, V2>> f, V2 s) {
        return null;
    }

    @Override
    public <V2> V2 foldl(Function<V2, Function<V, V2>> f, V2 s) {
        return null;
    }

    @Override
    public Map<K, V> filter(Function<V, Boolean> p) {
        return null;
    }

    @Override
    public <V2> Map<K, V2> map(Function<V, V2> f) {
        return null;
    }

    @Override
    public Map<K, V> union(Map<K, V> m) {
        return null;
    }

    @Override
    public Map<K, V> intersection(Map<K, V> m) {
        return null;
    }

    @Override
    public Result<V> lookup(K k) {
        return null;
    }

    public boolean isEmpty() {
		return false;
	}

    @Override
    public int size() {
        return 0;
    }

    public String toString() {
        return set.isEmpty() ? "{}" : set.toString();
    }

    public static void main(String[] args) {
        TreeMap<Integer, String > map = new TreeMap<>();
        List list = List.list(3,52,2,1,7);
        List<Tuple<Integer, String>> list2 = List.list(Tuple.tuple(3, "test fromList"), Tuple.tuple(2, "test"), Tuple.tuple(1, "test"));
        Map<Integer, String> lm = ListMap.fromList(list2);
        System.out.println("Map fromList: " + ListMap.fromList(list2));
        System.out.println("from entry set: " + fromList(list2));
        System.out.println(map.insert(3, "hallo").insert(2, "test").insert(4, "hu").entrySet());
        System.out.println(map.insert(3, "test"));

    }
}
