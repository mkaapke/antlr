package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;

public interface Set<A> {

    Set<A> insert(A e);

    Set<A> remove(A e);

    boolean member(A e);

    int size();

    A get(A a);

    boolean isSubsetOf(Set<A> s);

    boolean forAll(Function<A, Boolean> p);

    public boolean isEqualTo(Set<A> s);

    boolean isEmpty();

    String toString();

    List<A> toList();

    ArrayList<A> toArrayList();

    A getObjectAtPosition(int position);

}