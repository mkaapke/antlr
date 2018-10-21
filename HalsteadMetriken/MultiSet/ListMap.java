package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;

import static MultiSet.ListSet.set;

public class ListMap<K, V> implements Map<K, V> {

    /**
     * Unsere ListMap wird mit einem Set gebaut.
     * Laufzeit: o(1)
     */
    private Set<Entry<K, V>> set = set();

    /**
     * Der Konstruktor der ListMap
     * @param set - das Set, mit dem die ListMap erstellt werden soll
     * Laufzeit: o(1)
     */
    private ListMap(Set<Entry<K, V>> set) {
        this.set = set;
    }

    /**
     * Diese Methode gibt eine leere Map zur�ck.
     * Laufzeit: o(1)
     */
    public static <K, V> Map<K, V> empty() {
        return new ListMap<K, V>(set());
    }

    /**
     * Diese Methode f�gt ein Element in die Liste ein.
     * @param key - Der Key f�r die Entry.
     * @param value - Der Value f�r die Entry
     * @return eine neue Map
     * Laufzeit:  o(n)
     */
    @Override
    public Map<K, V> put(K key, V value) {
        return this.contains(key) ? this.remove(key).put(key, value) : new ListMap<K, V>(set.insert(new Entry<K, V>(key, value)));
    }

    /**
     * Diese Methode �berpr�ft die Map auf einen bestimmten Key.
     * @return true wenn vorhanden, false wenn nicht.
     * Laufzeit: o(n)
     */
    @Override
    public boolean contains(K key) {
        return !set.forAll(x -> !x.equals(new Entry<K, V>(key, null)));
    }

    /**
     * Dieses Element entfernt ein Element aus der ListMap.
     * Laufzeit: o(n)
     */
    @Override
    public Map<K, V> remove(K key) {
        return this.contains(key) ? new ListMap<K, V>(set.remove(set.toList().filter(x -> x.key == key || x.key.equals(key)).head())) : this;
    }

    /**
     * Pr�ft die ListMap auf Leere.
     * Laufzeit: o(1);
     */
    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Gibt die Gr��e der ListMap zur�ck.
     * Laufzeit: 0(1)
     */
    @Override
    public int size() {
        return set.size();
    }

    /**
     * Gibt die Map als String aus.
     * Laufzeit: o(1)
     */
    public String toString() {
        return this.isEmpty() ? "Map[]" : "Map" + set.toString().substring(3);
    }

    /**
     * Gibt einen Value zu einem bestimmten Key zur�ck.
     * Laufzeit: o(n)
     */
    @Override
    public V get(K k) {
        return this.contains(k) ?  set.toList().filter(x -> x.key.equals(k)).head().value : null;
    }

    /**
     * Gibt das Set der Map zur�ck
     * Laufzeit: o(1);
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    /**
     * Pr�ft ob eine Bedinung f�r jedes Element der ListMap eintrifft.
     * Laufzeit: 0(n)
     */
    @Override
    public boolean forAll(Function<Entry<K, V>, Boolean> p) {
        return set.forAll(p);
    }

    /**
     * Pr�ft ob eine Bedinung f�r jeden Key der ListMap eintrifft.
     * Laufzeit: 0(n)
     */
    @Override
    public boolean forAllKeys(Function<K, Boolean> p) {
        return set.forAll(x -> p.apply(x.key));
    }

    /**
     * Pr�ft ob zwei Maps identisch ist.
     * Laufzeit: o(n)
     */
    @Override
    public boolean isEqualTo(Map<K, V> o) {
        return this.size() == o.size() ? this.forAllKeys(x -> o.contains(x) && this.get(x) == o.get(x)) : false;
    }

    public ArrayList<Entry<K, V>> toArrayList() {
        return set.toArrayList();
    }

    public Entry<K, V> getEntryAtPosition(int position) {
        return set.getObjectAtPosition(position);
    }




}