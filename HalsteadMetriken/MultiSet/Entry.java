package MultiSet;

public class Entry<K, V> {

    public K key;
    public V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setValue (V v) {
        value = v;
    }

    public boolean equals(Entry<K, V> another) {
        return this.key == another.key || this.key.equals(another.key);
    }

    @Override
    public String toString() {
        return "Key: " + key + ", Value: "+ value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }
}
