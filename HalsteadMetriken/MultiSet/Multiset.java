package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;

public interface Multiset<A> {

    int size();

    boolean isEmpty();

    boolean member(A e);

    Multiset<A> remove(A e);

    Multiset<A> insert(A e);

    int count(A e);

    int distinct();

    boolean forAll(Function<A, Boolean> p);

    boolean isSubsetOf(Multiset<A> s);

    boolean isEqualTo(Multiset<A> s);

    String toString();

    <K, V>ArrayList<Entry<K, V>> toArrayList();

    <K, V>Entry<K, V> getEntryAtPosition(int position);

}