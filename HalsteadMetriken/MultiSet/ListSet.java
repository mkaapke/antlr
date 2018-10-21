package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;

import static MultiSet.List.list;

/**
 * Ein ListSet ist eine Menge erstellt mit einer Liste.
 * @author Daniel
 */
public class ListSet<A> implements Set<A> {

    /**
     * Die Liste, die das Set behinhaltet.
     */
    private List<A> list;

    /**
     * Das ist der Konstruktor
     * Laufzeit: O(1)
     */
    private ListSet(List<A> list) {
        this.list = list;
    }

    /**
     * Statische Fabrikmethode f�r ein leeres Set.
     * Laufzeit: O(1)
     * @return
     */
    public static <A> Set<A> set() {
        return new ListSet<A>(list());
    }
    /**
     * Statische Fabrikmethode f�r ein nicht leeres Set.
     * Laufzeit: O(n)
     */
    @SafeVarargs
    public static <A> Set<A> set(A... a) {
        Set<A> n = set();
        for (A e : a) {
            n = n.insert(e);
        }
        return n;
    }

    /**
     * F�gt ein Element in die Menge ein.
     * Laufzeit: O(n)
     */
    public ListSet<A> insert(A a) {
        return list.elem(a) ? new ListSet<A>(list).remove(a).insert(a) : new ListSet<A>(list.cons(a));
    }

    /**
     * Entfernt ein Element aus dem Set.
     * Laufzeit: O(n)
     */
    @Override
    public ListSet<A> remove(A a) {
        return new ListSet<A>(list.filter(x -> !x.equals(a)));
    }

    /**
     * Pr�ft ob ein Element in der Menge ist.
     * Laufzeit: O(n)
     */
    @Override
    public boolean member(A a) {
        return list.isEmpty() ? false : list.elem(a);
    }

    /**
     * 	Gibt die Gr��te des Sets zur�ck, bzw. die Menge der Elemente.
     * Laufzeit: O(n)
     */
    @Override
    public int size() {
        return list.length();
    }

    /**
     * Pr�ft ob das Set leer ist.
     * Laufzeit: O(1)
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Gibt ein Element aus der Menge zur�ck, wenn diese vorhanden ist.
     * Laufzeit: O(n)
     */
    public A get (A a) {
        if (this.member(a)) return a;
        return null;
    }

    /**
     * Pr�ft ob eine Menge eine Teilmenge einer anderen Menge ist.
     * Laufzeit: O(n�)
     */
    public boolean isSubsetOf(Set<A> s) {
        return this.isEmpty() ? true : list.forAll(x -> s.member(x));
    }

    /**
     * Pr�ft ob eine Bedinung f�r alle Elemente des Sets �bereinstimmt.
     * Laufzeit: O(n)
     */
    public boolean forAll (Function<A, Boolean> p) {
        return list.forAll(p);
    }

    /**
     * Pr�ft zwei Mengen auf Gleichheit.
     * Laufzeit: O(n�)
     */
    public boolean isEqualTo (Set<A> s) {
        if (s.size() != this.size()) return false;
        return this.isSubsetOf(s) && s.isSubsetOf(this);
    }

    /**
     * Gibt das Set als String zur�ck.
     */
    public String toString() {
        return this.isEmpty() ? "Set[]" : "Set" + list.toString().substring(0, list.toString().length() - 6) + "]";
    }

    /**
     * Wandelt ein Set in eine List um.
     */
    public List<A> toList() {
        return list;
    }

    public ArrayList<A> toArrayList() {
        return list.toArrayList();
    }

    public A getObjectAtPosition(int position) {
        return list.getObjectAtPosition(position);
    }



}
