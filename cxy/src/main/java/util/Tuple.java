package util;

public class Tuple<T1, T2> {
    public final T1 _1;
    public final T2 _2;

    public Tuple(T1 t1, T2 t2) {
        this._1 = t1;
        this._2 = t2;
    }

    public static <T1, T2> Tuple<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple<>(t1, t2);
    }

    public static void main(String[] args) {
        throw null;
    }
}
