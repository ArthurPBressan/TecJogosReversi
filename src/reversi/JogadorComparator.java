package reversi;

import java.util.Comparator;

/**
 * Created by arthur on 19/03/15.
 */
public class JogadorComparator implements Comparator<Jogador> {
    @Override
    public int compare(Jogador o1, Jogador o2) {
        // Ranqueia em ordem decresente de pontos
        return o2.getNumeroPecas() - o1.getNumeroPecas();
    }
}
