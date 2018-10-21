package MultiSet;

import java.util.ArrayList;
import java.util.function.Function;


public abstract class List<A> {

    /**
     * Kopf einer Liste. Behinhaltet ein Element beliegem Datentypes.
     */
    public abstract A head();

    /**
     * Ende einer Liste. Verwei�t auf eine andere Liste.
     */
    public abstract List<A> tail();

    /**
     * Pr�ft ob die Liste leer ist.
     */
    public abstract boolean isEmpty();

    /**
     * �nder den Kopf einer Liste
     * @param h das neue Element f�r den Kopf.
     */
    public abstract List<A> setHead(A h);

    /**
     * Gibt die Liste als String zur�ck
     */
    public abstract String toString();

    /**
     * Speichert die L�nge einer Liste.
     * @return
     */
    public abstract int lengthMemoized();

    /**
     * Die leere Liste.
     */
    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    /**
     * Der Konstruktor
     */
    private List() {
    }

    /**
     * Die leere Liste.
     */
    private static class Nil<A> extends List<A> {

        /**
         * Der Konstruktor
         */
        private Nil() {
        }

        /**
         * Der Kopf. Auf einen Kopf in der leeren Liste kann nicht zugegriffen werden, deshalb wird eine Fehlermeldung geworfen.
         */
        public A head() {
            throw new IllegalStateException("head called en empty list");
        }

        public List<A> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        /**
         * Pr�ft ob die leere Liste leer ist. Da sie immer leer ist, gibt diese Methode immer true zur�ck.
         */
        public boolean isEmpty() {
            return true;
        }

        /**
         * Setzt den Kopf einer Liste. Auf einen Kopf in der leeren Liste kann nicht zugegriffen werden, deshalb wird eine Fehlermeldung geworfen.
         */
        @Override
        public List<A> setHead(A h) {
            throw new IllegalStateException("setHead called on empty list");
        }

        /**
         * Gibt die leere Liste als String zur�ck.
         */
        @Override
        public String toString() {
            return "[NIL]";
        }

        /**
         * Die L�nge einer leeren Liste. Da die leere Liste immer leer ist, ist die L�nge immer 0.
         */
        @Override
        public int lengthMemoized() {
            return 0;
        }

    }

    /**
     * Die "nicht leere" Liste.
     */
    private static class Cons<A> extends List<A> {

        /**
         * Der Kopf der leeren Liste.
         */
        private final A head;

        /**
         * Das Ende der Liste.
         */
        private final List<A> tail;

        /**
         * Die L�nge der Liste.
         */
        private final Integer length;

        /**
         * Der Konstruktor
         */
        private Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
            this.length = tail.length() + 1;
        }

        /**
         * Gibt den Kopf zur�ck.
         */
        public A head() {
            return head;
        }

        /**
         * Gibt das Ende der Liste zur�ck.
         */
        public List<A> tail() {
            return tail;
        }

        /**
         * Pr�ft ob die Liste leer ist. Da eine "nicht leere" Liste niemals leer ist, gibt diese Methode immer false zur�ck.
         */
        public boolean isEmpty() {
            return false;
        }

        /**
         * �ndert den Kopf einer Liste.
         */
        public List<A> setHead(A a) {
            return new Cons<>(a, this.tail());
        }

        /**
         * Gibt die Liste als String zur�ck.
         */
        @Override
        public String toString() {
            return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
        }

        /**
         * Gibt die Liste als String zur�ck mit Hilfe von TailCall.
         */
        private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
            return list.isEmpty() ? TailCall.ret(acc)
                    : TailCall.sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
        }

        /**
         * Die L�nge einer Methode.
         */
        @Override
        public int lengthMemoized() {
            return length;
        }

    }

    /**
     * Konstruktor f�r eine leere Liste.
     */
    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    /**
     * Konstruktor f�r eine "nicht leere" Liste.
     */
    @SafeVarargs
    public static <A> List<A> list(A... a) {
        List<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Cons<>(a[i], n);
        }
        return n;
    }

    /**
     * F�gt ein Element einer Liste.
     */
    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    /**
     * Verbindet zwei Listen.
     */
    public static <A> List<A> concat(List<A> list1, List<A> list2) {
        return foldRight(list1, list2, x -> y -> new Cons<>(x, y));
    }

    /**
     * Summiert eine Integer Liste.
     */
    public static Integer sum(List<Integer> ints) {
        return (ints.isEmpty() ? 0 : ints.head() + List.sum(ints.tail()));
    }

    /**
     * Multipliziert eine Double Liste.
     */
    public static Double product(List<Double> doubles) {
        return (doubles.isEmpty() ? 1.0 : doubles.head() == 0.0 ? 0.0 : doubles.head() * List.product(doubles.tail()));
    }

    /**
     * Geht eine Liste von Rechts nach Links durch und macht mit jedem Element der Liste etwas, was in einer Function definiert wird.
     */
    public static <A, B> B foldRight(List<A> list, B n, Function<A, Function<B, B>> f) {
        return list.isEmpty() ? n : f.apply(list.head()).apply(foldRight(list.tail(), n, f));
    }

    public ArrayList<A> toArrayList() {
        ArrayList<A> aList = new ArrayList<A>();
        if (this.isEmpty()) {
            return aList;
        }
        aList.add(head());
        aList.addAll(this.tail().toArrayList());
        return aList;
    }

    public static <A, B> B foldLeft(List<A> ts, B identity,
                                    Function<B, Function<A, B>> f) {
        return ts.isEmpty()
                ? identity
                : foldLeft(ts.tail(), f.apply(identity).apply(ts.head()), f);
    }

    /**
     * Berechnet die L�nge einer Liste.
     * @return
     */
    public Integer length() {
        return foldRight(this, 0, x -> y -> y + 1);
    }

    /**
     * Multipliziert jedes Element einer Liste mit 3.
     */
    public static List<Integer> multThree(List<Integer> list) {
        return List.foldRight(list, List.<Integer>list(), x -> y -> y.cons(x * 3));
    }

    /**
     * Verwandelt eine Double Liste in eine String Liste.
     */
    public static List<String> doubleToString(List<Double> list) {
        return List.foldRight(list, List.<String>list(), x -> y -> y.cons(x.toString()));
    }

    /**
     * Pr�ft, ob mindestens ein Element existiert, welche die Bedinung, die in der Function definiert wird, erf�llt.
     */
    public boolean exist(Function<A, Boolean> p) {
        return this.isEmpty() ? false : p.apply(head()) || tail().exist(p);
    }

    /**
     * Pr�ft, ob alle Element die Bedinung, die in der Function definiert wird, erf�llen.
     */
    public boolean forAll(Function<A, Boolean> p) {
        return !exist(x -> !p.apply(x));
    }

    /**
     * Pr�ft, ob ein bestimmtes Element in der Liste enthalten ist.
     * @param a
     * @return
     */
    public boolean elem(A a) {
        return this.isEmpty() ? false : a == this.head() ? true : this.tail().elem(a);
    }

    /**
     * Filtert die Liste nach einer Bedinung, die in der Function definiert wird, erf�llen. Sie werden entfernt, wenn Sie diese nicht erf�llen.
     */
    public List<A> filter(Function<A, Boolean> f) {
        return foldRight(this, list(), x -> y -> f.apply(x) ? new Cons<>(x,y) : y);
    }

    @SuppressWarnings("static-access")
    public List<A> distinct() {
        return this.isEmpty() ? this : this.foldRight(this, ListSet.<A>set(), x -> y -> y.insert(x)).toList();
    }

    public <B> Map<B, List<A>> groupBy(Function<A, B> f) {
        return foldRight(this, ListMap.empty(), t -> mt -> {
            final B k = f.apply(t);
            return mt.put(k, mt.contains(k) ? mt.get(k).cons(t) : list(t));
        });
    }

    public A getObjectAtPosition(int position) {
        return isEmpty() ? null : this.length() == position ? head() : tail().getObjectAtPosition(position);
    }

	/*@SuppressWarnings("static-access")
	public <B> Map<B, List<A>> groupBy(Function<A, B> f) {
		return this.foldRight(this, ListMap.em	pty(), x -> y -> {
			B k = f.apply(x);
			List<A> list =  list();
			list = y.get(k) != null ? y.get(k) : x.;
			return y.put(k, list);
		});
	}*/

}
