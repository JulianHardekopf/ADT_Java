package map;

import fpinjava.Function;
import fpinjava.Result;
import list.List;
import set.ListSet;
import set.Set;
import tuple.Tuple;

import static map.Entry.mapEntry;


public class ListMap<K,V> implements Map<K,V> {
	private final Set<Entry<K, V>> set;

	private ListMap() {
        this(ListSet.empty());
	}

	private ListMap(Set<Entry<K, V>> s) {
		set = s;
	}


    public static <K, V> Map<K, V> empty() {
		return new ListMap<>();
	}

	public static <K, V> Map<K, V> fromList(List<Tuple<K, V>> list) {
        return new ListMap<>(Set.map(x -> mapEntry(x.fst, x.snd), list.toSet()));
    }

	public static Map<String, Integer> wordMap(String s) {
		return List.words(s).foldr(w -> m -> m.insertWith(x -> y -> x+y, w, 1), ListMap.empty());
	}
    //Der Aufruf insertWith(f, key, value) fügt das Paar (key,value) in die Map ein,
    //wenn key noch nicht in der Map existiert.
    //Wenn key schon existiert,
    //wird das Paar (key, f.apply(value).apply(old_value)) eingefügt:
    public Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value) {
        return !this.member(key) ? this.insert(key, value) : this.insert(key, f.apply(value).apply(this.get(key)));
        //return new ListMap<>();
    }
    // entrySet liefert alle Einträge der Map als Set:
    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.set;
    }
    // fromEntrySet erzeugt aus dem Entry-Set s eine ListMap:
    @Override
    public <K1, V1> Map<K1, V1> fromEntrySet(Set<Entry<K1, V1>> s) {
        return new ListMap<>(s);
    }

    @Override
    public List<Tuple<K, V>> toList() {
        return ListSet.map(x -> new Tuple<>(x.getKey(),x.getValue()),set).toList();
    }
    //keys liefert alle Schlüssel der Map als Liste:
    @Override
    public List<K> keys() {
        return ListSet.map(Entry::getKey, set).toList();
    }
    // keysSet liefert alle Schlüssel der Map als Set:
    @Override
    public Set<K> keysSet() {
        return ListSet.map(Entry::getKey, set).toList().toSet();
    }
    // elems liefert alle Werte (Values) der Map als Liste:
    @Override
    public List<V> elems() {
        return ListSet.map(Entry::getValue, set).toList();
    }
    // all liefert true,
    //wenn alle (Schlüssel, Value)-Einträge der Map das Prädikat p erfüllen:
    @Override
    public boolean all(Function<K, Function<V, Boolean>> p) {
        return set.all(x -> p.apply(x.getKey()).apply(x.getValue()));
    }
    // allKeys liefert true,
    //wenn alle Schlüssel der Map das Prädikat p erfüllen:
    @Override
    public boolean allKeys(Function<K, Boolean> p) {
        return set.all(x -> p.apply(x.getKey()));
    }
    //isSubmapOf prüft,
    //ob alle (Schlüssel, Value)-Einträge dieser Map (this)
    //auch in der Map m enthalten sind:
    @Override
    public boolean isSubmapOf(Map<K, V> m) {
        return this.allKeys(x -> m.member(x) && this.get(x).equals(m.get(x)));
    }
    public boolean isEqualTo(Map<K, V> o) {
        return this.isSubmapOf(o) && o.isSubmapOf(this);
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Map && isEqualTo((Map<K, V>) o);
    }

    @Override
    public <V2> V2 foldr(Function<V, Function<V2, V2>> f, V2 s) {
        return this.elems().foldr(f, s);
    }

    @Override
    public <V2> V2 foldl(Function<V2, Function<V, V2>> f, V2 s) {
        return this.elems().foldl(f,s);
    }
    // filter filtert die Values der Map.
    //Die neue Map enthält nur noch die Einträge,
    //für deren Values die Funktion p true liefert:
    @Override
    public Map<K, V> filter(Function<V, Boolean> p) {
        return fromEntrySet(ListSet.filter(x -> p.apply(x.getValue()),set));
    }
    // map wendet die Funktion f auf alle Values der Map an:
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
       // return member(k) ? Result.success(set.findEq(mapEntry(k)).value) : Result.empty();
      //  return Result.of(get(k));
       // return Result.of(set.findEq(mapEntry(k)).value);
        //return member(k) ? set.lookupEq(get(k)) : Result.empty();
        return Result.of(get(k));
    }


	public Map<K, V> insert(K key, V value) {
		return new ListMap<>(set.insert(mapEntry(key,value)));
	}

    @Override
    public boolean member(K key) {
        return set.member(mapEntry(key));
    }

    @Override
    public Map<K, V> delete(K key) {
        return new ListMap<>(set.delete(mapEntry(key, null)));
    }

    @Override
    public V get(K key) {
        return member(key) ? ListSet.filter(x -> key.equals(x.getKey()),set).toList().head().getValue() : null;
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


        ListMap<Integer, String > map = new ListMap<>();
        ListMap<Integer, String> map2 = new ListMap<>();
        map2.insert(1, "3").insert(2, "2").insert(3, "3").insert(4, "4").insert(5, "5");
        System.out.println("Insert: " + map.insert(1, "etwrf").get(1));
        System.out.println("size test 1: " + map.insert(2, "hallo").size());
        System.out.println("memberTest: " + map.insert(3, "test").member(3));
        System.out.println("empty now: " + map.insert(2 ,"test").delete(2));
        List<Tuple<Integer, String>> list = List.list(Tuple.tuple(1, "test fromList"));
        System.out.println(fromList(list));
        System.out.println(map.get(2));
        System.out.println("Return Map as Set:" + map.insert(1, "3").insert(2, "2").insert(3, "3").entrySet());
        System.out.println("Return Keys as Set:" + map.insert(1, "3").insert(2, "2").insert(3, "3").keysSet());
        System.out.println("Return MapValuesElem as List:" + map.insert(1, "3").insert(2, "2").insert(3, "3").elems());
        System.out.println("Submaptest wahr" + map.isSubmapOf(map2));
        System.out.println(map2.insert(3, "§").get(3));

        System.out.println(map2.insert(1, "k1").insert(1, "k2").get(1));

        // defOfUnion
        ListMap<String, Integer > map3 = new ListMap<>();
        map3.insert("aaa", 20);
        ListMap<String, Integer> map4 = new ListMap<>();
        System.out.println("map3: " + map3.insert("aaa", 20).union(map4.insert("aaa", 21)).lookup("aaa"));
        System.out.println(map3.insert("aaa", 20));
    }
}

