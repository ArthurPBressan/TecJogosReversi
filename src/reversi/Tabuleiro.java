package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        PecaMouseListener pecaMouseListener = new PecaMouseListener(this);
        tabuleiro = new Peca[8][8];
        for (int l = 0; l < nLin; l++) {
            for (int c = 0; c < nCol; c++) {
                Peca peca = new Peca(c, l, this, pecaMouseListener);
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
        ArrayList<Movimento> movimentos = vez.getMovimentosNaPosicao(peca.getPosicao());
        for (Movimento movimento : movimentos) {
            for (Peca pecaCapturada : movimento.getPecasCapturadas()) {
                vez.addPeca(pecaCapturada);
            }
        }
        vez.addPeca(peca);
    }

    private void postJogada() {
        limparPecas();
        trocaJogador();
    }

    private void limparPecas() {
        for (int l = 0; l < nLin; l++) {
            for (int c = 0; c < nCol; c++) {
                tabuleiro[c][l].setEnabled(false);
            }
        }
    }

    private void trocaJogador() {
        vez = getNextJogador();
        int quantidadeMovimentos = vez.calcularMovimentos();
        if (quantidadeMovimentos == 0) {
            System.out.println(vez.toString() + " não tem movimentos! Pulando a vez..");
            try {
                trocaJogador();
            } catch (StackOverflowError stackOverflowError) {
                finalizaJogo();
            }
        }
    }

    private void finalizaJogo() {

    }

    private Jogador getNextJogador() {
        jogada++;
        int i = jogada % jogadores.length;
        return jogadores[i];
    }

    public Peca getPeca(int coluna, int linha) {
        return tabuleiro[coluna][linha];
    }

    public Jogador getJogadorAtual() {
        return vez;
    }
}
