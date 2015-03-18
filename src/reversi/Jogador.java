package reversi;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by arthur on 13/03/15.
 */
public class Jogador {
    private Tabuleiro tabuleiro;
    private Color cor;

    private ArrayList<Peca> pecas;
    private ArrayList<Movimento> movimentos;

    public Jogador(Tabuleiro tabuleiro, Color cor) {
        this.tabuleiro = tabuleiro;
        this.cor = cor;
        pecas = new ArrayList<Peca>();
    }

    public Color getCor() {
        return cor;
    }

    public void addPeca(Peca peca) {
        peca.setDono(this);
        pecas.add(peca);
    }

    public void calcularMovimentos() {
        for (Peca peca: pecas) {
            gerarMovimentosNaDirecao(peca, DirecaoHorizontal.DIREITA, DirecaoVertical.NENHUMA);
            gerarMovimentosNaDirecao(peca, DirecaoHorizontal.ESQUERDA, DirecaoVertical.NENHUMA);
        }
    }

    private enum DirecaoVertical {
        BAIXO(-1),
        NENHUMA(0),
        CIMA(1);

        private int valor;

        DirecaoVertical(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }
    }

    private enum DirecaoHorizontal {
        ESQUERDA(-1),
        NENHUMA(0),
        DIREITA(1);

        private int valor;

        DirecaoHorizontal(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }
    }

    private void gerarMovimentosNaDirecao(Peca pecaInicial, DirecaoHorizontal direcaoHorizontal, DirecaoVertical direcaoVertical ) {
        System.out.println("Começando da peça:" + pecaInicial.toString());
        int linha = pecaInicial.getLinha();
        boolean candidato = false;
        for (int coluna = pecaInicial.getColuna(); ; coluna = coluna + direcaoHorizontal.getValor()) {
            Peca peca;
            try {
                System.out.println("Trying to get c=" + coluna + "; l=" + linha);
                peca = tabuleiro.getPeca(coluna, linha);
                System.out.println(peca.toString());
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            if (peca == pecaInicial) {
                continue;
            }
            if (peca.getDono() != this && peca.getDono() != null) {
                candidato = true;
                continue;
            }
            if (peca.getDono() == null && candidato) {
                peca.setEnabled(true);
                break;
            }
            if (peca.getDono() == this) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "cor=" + cor +
                '}';
    }
}
