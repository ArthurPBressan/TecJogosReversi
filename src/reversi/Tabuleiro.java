package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by arthur on 12/03/15.
 */
public class Tabuleiro extends JPanel implements ActionListener {
    private Peca[][] tabuleiro;
    private Jogador vez;

    private Jogador[] jogadores;

    private int jogada = 0;
    private int nCol = 8;
    private int nLin = 8;

    public Tabuleiro() {
        setLayout(new GridLayout(8, 8));

        jogadores = new Jogador[] {new Jogador(this, Color.RED), new Jogador(this, Color.BLUE)};

        tabuleiro = new Peca[8][8];
        for (int l = 0; l < nLin; l++) {
            for (int c = 0; c < nCol; c++) {
                Peca peca = new Peca(c, l, this);
                tabuleiro[c][l] = peca;
                add(peca);
            }
        }

        jogadores[0].addPeca(tabuleiro[3][3]);
        jogadores[0].addPeca(tabuleiro[4][4]);
        jogadores[1].addPeca(tabuleiro[3][4]);
        jogadores[1].addPeca(tabuleiro[4][3]);

        vez = jogadores[0];
        vez.calcularMovimentos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fazerJogada((Peca) e.getSource());
        postJogada();
    }

    private void fazerJogada(Peca peca) {
        Movimento movimento = vez.getMovimentoNaPosicao(peca.getPosicao());
        for (Peca pecaCapturada : movimento.getPecasCapturadas()) {
            vez.addPeca(pecaCapturada);
        }
        vez.addPeca(peca);
    }

    private void postJogada() {
        limparPecas();
        vez = getNextJogador();
        vez.calcularMovimentos();
    }

    private void limparPecas() {
        for (int l = 0; l < nLin; l++) {
            for (int c = 0; c < nCol; c++) {
                tabuleiro[c][l].setEnabled(false);
            }
        }
    }

    private Jogador getNextJogador() {
        jogada++;
        int i = jogada % jogadores.length;
        return jogadores[i];
    }

    public Peca getPeca(int coluna, int linha) {
        return tabuleiro[coluna][linha];
    }
}
