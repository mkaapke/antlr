package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;

public interface Map<K, V> {

    public Map<K, V> put(K key, V value);

    public boolean contains(K key);

    public Map<K, V> remove(K key);

    public boolean isEmpty();

    public int size();

    V get(K k);

    Set<Entry<K,V>> entrySet();

    boolean forAll(Function<Entry<K, V>, Boolean> p);

    boolean forAllKeys(Function<K, Boolean> p);

    boolean isEqualTo(Map<K, V> o);

    <A>ArrayList<A> toArrayList();

    Entry<K, V> getEntryAtPosition(int position);

}
