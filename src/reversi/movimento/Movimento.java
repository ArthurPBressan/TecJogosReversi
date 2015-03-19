package reversi.movimento;

import reversi.peca.Peca;

import java.util.ArrayList;

/**
 * Created by arthur on 12/03/15.
 */
public class Movimento {
    private Peca pecaInicial;
    private Peca pecaFinal;

    private ArrayList<Peca> pecasCapturadas;

    public Movimento(Peca pecaInicial) {
        this.pecaInicial = pecaInicial;
        this.pecasCapturadas = new ArrayList<Peca>();
    }

    public void addPecaCapturada(Peca peca) {
        pecasCapturadas.add(peca);
    }

    public void setPecaFinal(Peca pecaFinal) {
        this.pecaFinal = pecaFinal;
    }

    public Peca getPecaFinal() {
        return pecaFinal;
    }

    public ArrayList<Peca> getPecasCapturadas() {
        return pecasCapturadas;
    }

    @Override
    public String toString() {
        return "Movimento{" +
                "pecaInicial=" + pecaInicial +
                ", pecaFinal=" + pecaFinal +
                ", pecasCapturadas=" + pecasCapturadas +
                ", valor=" + valor() +
                '}';
    }

    public int valor() {
        return pecasCapturadas.size();
    }
}
