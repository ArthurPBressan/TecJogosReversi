package reversi.peca;

import reversi.movimento.Movimento;
import reversi.Tabuleiro;
import reversi.jogador.Jogador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by arthur on 18/03/15.
 */
public class PecaMouseListener implements MouseListener {
    private Tabuleiro tabuleiro;

    public PecaMouseListener(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Peca peca = (Peca) e.getSource();
        setPecasCapturadasHoverState(peca, true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Peca peca = (Peca) e.getSource();
        setPecasCapturadasHoverState(peca, false);
    }

    private void setPecasCapturadasHoverState(Peca peca, boolean state) {
        Jogador jogador = tabuleiro.getJogadorAtual();
        ArrayList<Movimento> movimentos = jogador.getMovimentosNaPosicao(peca.getPosicao());
        if (movimentos == null) {
            return;
        }
        for (Movimento movimento : movimentos) {
            for (Peca pecaCapturada : movimento.getPecasCapturadas()) {
                pecaCapturada.setHover(state);
            }
        }
    }
}
