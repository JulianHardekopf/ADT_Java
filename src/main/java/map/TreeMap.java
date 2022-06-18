package map;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import set.Set;
import set.SortedSet;
import set.TreeSet;
import tuple.Tuple;


public class TreeMap<K,V> implements Map<K,V> {
	private final SortedSet<Entry<K,V>> set;

	@SuppressWarnings("unchecked")
	private TreeMap(){
		this (TreeSet.empty());
	}

	private TreeMap(SortedSet<Entry<K,V>> s) {
		set = s;
	}

	public static <K,V> Map<K,V> empty() {
		return new TreeMap<>();
	}

	public static <K,V> Map<K,V> fromList(List<Tuple<K,V>> list) {
		return new TreeMap<>();
	}

	public static Map<String,Integer> wordMap(String s){
		return new TreeMap<>();
	}

	public boolean isEqualTo(Map<K, V> o){
		return false;
	}

	public Map<K,V> insert(K key, V value){
		return new TreeMap<>();
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
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public <K1, V1> Map<K1, V1> fromEntrySet(Set<Entry<K1, V1>> s) {
        return null;
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

}
