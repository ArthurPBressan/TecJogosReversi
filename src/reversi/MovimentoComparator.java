package reversi;

import java.util.Comparator;

/**
 * Created by arthur on 18/03/15.
 */
public class MovimentoComparator implements Comparator<Movimento> {
    @Override
    public int compare(Movimento o1, Movimento o2) {
        return o1.valor() - o2.valor();
    }
}
