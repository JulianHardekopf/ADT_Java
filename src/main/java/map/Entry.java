package map;

import javax.swing.border.EtchedBorder;

public class Entry<K,V> implements Comparable<Entry<K,V>> {

    public final K key;
    public final V value;


    private Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }


    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
	public int compareTo(Entry<K,V> other) {
		return 0;
	}
    @Override
    public String toString() {
        return String.format("Entry(%s, %s)", key, value);
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Entry && this.key.equals(((Entry<?, ?>) o).key);
    }

    public static <V, K> Entry<K,V> mapEntry(K key, V value) {
        return new Entry<>(key, value);

    }
    public static <V, K> Entry<K,V> mapEntry(K key)  {
        return new Entry<>(key, null);

    }
}