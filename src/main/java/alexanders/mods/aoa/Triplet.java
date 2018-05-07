package alexanders.mods.aoa;

public class Triplet<A, B, C> {
    public A a;
    public B b;
    public C c;

    public Triplet() {
    }

    public Triplet(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;

        return (a != null ? a.equals(triplet.a) : triplet.a == null) && (b != null ? b.equals(triplet.b) : triplet.b == null) && (c != null ? c.equals(triplet.c) :
                triplet.c == null);
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Triplet{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
    }
}
