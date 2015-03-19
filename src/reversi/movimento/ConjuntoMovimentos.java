package reversi.movimento;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by arthur on 19/03/15.
 */
public class ConjuntoMovimentos extends ArrayList<Movimento> {
    private int valor = 0;
    private Point chave = null;

    public ConjuntoMovimentos(Point chave) {
        this.chave = chave;
    }

    @Override
    public boolean add(Movimento movimento) {
        valor += movimento.getValor();
        return super.add(movimento);
    }

    public int getValor() {
        return valor;
    }

    public Point getChave() {
        return chave;
    }
}
