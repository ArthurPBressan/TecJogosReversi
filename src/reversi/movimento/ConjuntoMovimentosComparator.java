package reversi.movimento;

import reversi.movimento.Movimento;

import java.util.Comparator;

/**
 * Created by arthur on 18/03/15.
 */
public class ConjuntoMovimentosComparator implements Comparator<ConjuntoMovimentos> {
    @Override
    public int compare(ConjuntoMovimentos o1, ConjuntoMovimentos o2) {
        return o2.getValor() - o1.getValor();
    }
}
