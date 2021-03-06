package reversi;

import reversi.jogador.Jogador;
import reversi.jogador.JogadorComparator;
import reversi.movimento.Movimento;
import reversi.peca.Peca;
import reversi.peca.PecaMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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
    private boolean debug;

    public boolean isDebug() {
        return debug;
    }

    public Tabuleiro(boolean debug) {
        this.debug = debug;
        setLayout(new GridLayout(8, 8));

        jogadores = new Jogador[] {
                new Jogador(this, Color.WHITE, false, "Branco"),
                new Jogador(this, Color.BLACK, false, "Preto")
        };
        PecaMouseListener pecaMouseListener = new PecaMouseListener(this);
        tabuleiro = new Peca[8][8];
        for (int l = 0; l < nLin; l++) {
            for (int c = 0; c < nCol; c++) {
                Peca peca = new Peca(c, l, this, pecaMouseListener, debug);
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
        if (vez.isIA()) {
            vez.jogar();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fazerJogada((Peca) e.getSource());
    }

    public void fazerJogada(Peca peca) {
        ArrayList<Movimento> movimentos = vez.getMovimentosNaPosicao(peca.getPosicao());
        if (movimentos == null) {
            return;
        }
        for (Movimento movimento : movimentos) {
            for (Peca pecaCapturada : movimento.getPecasCapturadas()) {
                vez.addPeca(pecaCapturada);
            }
        }
        vez.addPeca(peca);
        postJogada();
    }

    private void postJogada() {
        limparPecas();
        trocaJogador(jogadores.length - 1);
        if (debug) {
            for (Jogador jogador : jogadores) {
                System.out.print(jogador.toString() + " ");
            }
            System.out.println();
        }
        if (vez.isIA()) {
            vez.jogar();
        }
    }

    private void limparPecas() {
        if (!debug) {
            return;
        }
        for (int l = 0; l < nLin; l++) {
            for (int c = 0; c < nCol; c++) {
                tabuleiro[c][l].setEnabled(false);
            }
        }
    }

    private void trocaJogador(int n) {
        vez = getNextJogador();
        int quantidadeMovimentos = vez.calcularMovimentos();
        if (quantidadeMovimentos == 0) {
            JOptionPane.showMessageDialog(this, vez.getNome() + " não tem movimentos! Pulando a vez..");
            if (n > 0) {
                trocaJogador(n - 1);
            } else {
                finalizaJogo();
            }
        }
    }

    private void finalizaJogo() {
        Arrays.sort(jogadores, new JogadorComparator());
        if (jogadores[0].getNumeroPecas() == jogadores[1].getNumeroPecas()) {
            JOptionPane.showMessageDialog(this, "Empate!");
        } else {
            Jogador vencedor = jogadores[0];
            JOptionPane.showMessageDialog(this, "Vencedor: " + vencedor.getNome() + " com " + vencedor.getNumeroPecas() + " peças.");
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

    public Jogador getJogadorAtual() {
        return vez;
    }

    public Peca getPeca(Point pontoMelhorMovimento) {
        return getPeca(pontoMelhorMovimento.x, pontoMelhorMovimento.y);
    }
}
