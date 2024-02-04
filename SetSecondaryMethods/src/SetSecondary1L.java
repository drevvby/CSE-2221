import components.set.Set;
import components.set.Set1L;

/**
 * Layered implementations of secondary methods {@code add} and {@code remove}
 * for {@code Set}.
 *
 * @param <T>
 *            type of {@code Set} elements
 */
public final class SetSecondary1L<T> extends Set1L<T> {

    /**
     * No-argument constructor.
     */
    public SetSecondary1L() {
        super();
    }

    @Override
    public Set<T> remove(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        Set<T> temp = s.newInstance();

        int count = s.size();

        while (count > 0) {

            T x = s.removeAny();

            if (this.contains(x)) {
                this.remove(x);
                temp.add(x);
            }
            s.add(x);
            count--;
        }
        return temp;

    }

    @Override
    public void add(Set<T> s) {
        assert s != null : "Violation of: s is not null";
        assert s != this : "Violation of: s is not this";

        int count = s.size();

        while (count > 0) {

            T x = s.removeAny();

            if (this.contains(x)) {
                s.add(x);
            } else {
                this.add(x);
            }
            count--;
        }

    }

}
