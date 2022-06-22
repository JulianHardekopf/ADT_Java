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
        return new TreeMap<>(TreeSet.<Entry<K1,V1>>fromSet(s));
    }
    public static <K1, V1> Map<K1, V1> fromEntrySet1(Set<Entry<K1, V1>> s) {
        return new TreeMap<>(TreeSet.<Entry<K1,V1>>fromSet(s));
    }
    public static <K extends Comparable<K>,V> Map<K,V> fromList(List<Tuple<K,V>> l) {
        return new TreeMap<>(TreeSet.<Entry<K,V>>fromSet(Set.map(x -> mapEntry(x.fst, x.snd), l.toSet())));

    }


	public static Map<String,Integer> wordMap(String s){
        return List.words(s).foldr(w -> m -> m.insertWith(x -> y -> x+y, w, 1), ListMap.empty());
	}

	public boolean isEqualTo(Map<K, V> o){
        return this.isSubmapOf(o) && o.isSubmapOf(this);
	}
    @Override
    public boolean equals(Object o) {
        return o instanceof Map && isEqualTo((Map<K, V>) o);
    }

	public Map<K,V> insert(K key, V value){
		return new TreeMap<>(TreeSet.fromSet(set.insert(mapEntry(key,value))));
        //return new TreeMap<>(TreeSet.fromSet(set).insert(mapEntry(key, value)));
	}

    @Override
    public boolean member(K key) {
        return set.member(mapEntry(key));
    }

    @Override
    public Map<K, V> delete(K key) {
        return fromEntrySet(set.delete(mapEntry(key, null)));
    }


    @Override
    public V get(K key) {
        return member(key) ? ListSet.filter(x -> key.equals(x.getKey()),set).toList().head().getValue() : null;
    }

    public Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value) {
		//return new TreeMap<>(fromEntrySet(set).insertWith(f, key, value));
        return !this.member(key) ? this.insert(key, value)
                : this.insert(key, f.apply(value).apply(this.get(key)));
	}


    @Override
    public List<Tuple<K, V>> toList() {
       return ListSet.map(x -> new Tuple<>(x.getKey(),x.getValue()),set).toList();
    }

    @Override
    public List<K> keys() {
        return ListSet.map(Entry::getKey, set).toList();
    }

    @Override
    public Set<K> keysSet() {
        return ListSet.map(Entry::getKey, set).toList().toSet();
    }

    @Override
    public List<V> elems() {
        return ListSet.map(Entry::getValue, set).toList();
    }

    @Override
    public boolean all(Function<K, Function<V, Boolean>> p) {
        return set.all(x -> p.apply(x.getKey()).apply(x.getValue()));
    }

    @Override
    public boolean allKeys(Function<K, Boolean> p) {
        return set.all(x -> p.apply(x.getKey()));
    }

    @Override
    public boolean isSubmapOf(Map<K, V> m) {
        return this.allKeys(x -> m.member(x) && this.get(x).equals(m.get(x)));
    }

    @Override
    public <V2> V2 foldr(Function<V, Function<V2, V2>> f, V2 s) {
        return this.elems().foldr(f, s);
    }

    @Override
    public <V2> V2 foldl(Function<V2, Function<V, V2>> f, V2 s) {
        return this.elems().foldl(f,s);
    }

    @Override
    public Map<K, V> filter(Function<V, Boolean> p) {
        return fromEntrySet(ListSet.filter(x -> p.apply(x.getValue()),set));
    }

    @Override
    public <V2> Map<K, V2> map(Function<V, V2> f) {
        return fromEntrySet(ListSet.map(x -> mapEntry(x.getKey(), f.apply(x.getValue())),set));
    }

    @Override
    public Map<K, V> union(Map<K, V> m) {
        return fromEntrySet(m.entrySet().union(this.set));
    }

    @Override
    public Map<K, V> intersection(Map<K, V> m) {
        return fromEntrySet(set.intersection(m.entrySet()));
    }

    @Override
    public Result<V> lookup(K k) {
        return Result.of(get(k));
    }

    public boolean isEmpty() {
        return this.set.isEmpty();
	}

    @Override
    public int size() {
        return set.size();
    }

    public String toString() {
        return set.isEmpty() ? "{}" : set.toString();
    }

    public static void main(String[] args) {
        TreeMap<Integer, String > map = new TreeMap<>();
        List list = List.list(3,52,2,1,7);
        List<Tuple<Integer, String>> list2 = List.list(Tuple.tuple(3, "test fromList"), Tuple.tuple(2, "zz"), Tuple.tuple(1, "aa"), Tuple.tuple(15, "bb"), Tuple.tuple(6, "ccc"));
        Map<Integer, String> lm = ListMap.fromList(list2);
        System.out.println("Map fromList: " + ListMap.fromList(list2));
        System.out.println("from entry set TreeMap: " + fromList(list2));
        System.out.println(map.insert(3, "hallo").insert(2, "test").insert(4, "hu").toList());
        System.out.println("insert+ delete test: " + map.insert(2, "test").insert(3, "refw").delete(2));
        System.out.println("member test: " + map.insert(2, "t3wefde").member(2));
        //System.out.println(map.fromEntrySet(list2);
        System.out.println("get test = 2 : " + map.insert(2, "hier 2").insert(3, "3").get(2));
        System.out.println(map.insert(1, "weffwe").size());
        System.out.println(map.insert(1, "weffwe").lookup(1));

    }
}
