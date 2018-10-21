package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;

public class ListMultiset<A> implements Multiset<A> {

    private Map<A,Integer> map = ListMap.empty();

    private ListMultiset (Map<A, Integer> map) {
        this.map = map;
    }

    public static <A> Multiset<A> empty() {
        return new ListMultiset<A>(ListMap.empty());
    }

    @SafeVarargs
    public static <A> Multiset<A> multiset(A... as) {
        Multiset<A> listMultiSet = empty();
        for (A a : as) listMultiSet.insert(a);
        return listMultiSet;
    }

    @SuppressWarnings("static-access")
    @Override
    public int size() {
        return map.entrySet().toList().foldRight(map.entrySet().toList(), 0, x -> y -> x.value + y);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean member(A e) {
        return this.isEmpty() ? false : map.contains(e);
    }

    @Override
    public Multiset<A> remove(A e) {
        return new ListMultiset<A>(map.remove(e));
    }

    @Override
    public Multiset<A> insert(A e) {
        return this.member(e) ? new ListMultiset<A>(map.put(e, (count(e) + 1))) : new ListMultiset<A>(map.put(e, 1));
    }

    @Override
    public int count(A e) {
        return this.isEmpty() ? 0 : this.member(e) ?  map.get(e) : 0;
    }

    @Override
    public int distinct() {
        return this.isEmpty() ? 0 : map.size();
    }

    @Override
    public boolean forAll(Function<A, Boolean> p) {
        return map.forAllKeys(p);
    }

    @Override
    public boolean isSubsetOf(Multiset<A> s) {
        return this.size() > s.size() ? false : this.forAll(x -> s.member(x) && this.count(x) == s.count(x));
    }

    @Override
    public boolean isEqualTo(Multiset<A> s) {
        return this.size() != s.size() ? false : this.isSubsetOf(s) && s.isSubsetOf(this);
    }

    public String toString() {
        if (this.isEmpty()) return "Multiset[]";
        String ret =  map.entrySet().toList().foldRight(map.entrySet().toList(), "Multiset[", x -> y -> y + "(" + x.key + ", " + x.value + "), ") + "]";
        return ret.replaceAll(", ]", "]");
        //return "Map" + map.toString().replaceAll(",", ")").replaceAll("Key: ", "(").replaceAll(", Value: ", ",").substring(3).replaceAll("]", ")]");
    }

    public <K,V>ArrayList<Entry<K, V>> toArrayList() {
        return map.toArrayList();
    }

    public Entry getEntryAtPosition(int position) {
        return map.getEntryAtPosition(position);
    }

}