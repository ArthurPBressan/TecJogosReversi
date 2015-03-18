package reversi;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by arthur on 13/03/15.
 */
public class Jogador {
    private Tabuleiro tabuleiro;
    private Color cor;

    private ArrayList<Peca> pecas;
    private HashMap<Point, Movimento> movimentos;

    public Jogador(Tabuleiro tabuleiro, Color cor) {
        this.tabuleiro = tabuleiro;
        this.cor = cor;
        this.movimentos = new HashMap<Point, Movimento>();
        this.pecas = new ArrayList<Peca>();
    }

    public Color getCor() {
        return cor;
    }

    public void addPeca(Peca peca) {
        Jogador dono = peca.getDono();
        if (dono != null) {
            dono.removerPeca(peca);
        }
        peca.setDono(this);
        pecas.add(peca);
    }

    private void removerPeca(Peca peca) {
        pecas.remove(peca);
    }

    public void calcularMovimentos() {
        movimentos.clear();
        for (Peca peca: pecas) {
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.NENHUMA, DirecaoVertical.CIMA);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.DIREITA, DirecaoVertical.CIMA);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.DIREITA, DirecaoVertical.NENHUMA);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.DIREITA, DirecaoVertical.BAIXO);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.NENHUMA, DirecaoVertical.BAIXO);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.ESQUERDA, DirecaoVertical.BAIXO);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.ESQUERDA, DirecaoVertical.NENHUMA);
            gerarMovimentoNaDirecao(peca, DirecaoHorizontal.ESQUERDA, DirecaoVertical.CIMA);
        }
        for (Movimento movimento: movimentos.values()) {
            movimento.getPecaFinal().setEnabled(true);
        }
    }

    public Movimento getMovimentoNaPosicao(Point posicao) {
        return movimentos.get(posicao);
    }

    private enum DirecaoVertical {
        CIMA(-1),
        NENHUMA(0),
        BAIXO(1);

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

    private void gerarMovimentoNaDirecao(Peca pecaInicial, DirecaoHorizontal direcaoHorizontal, DirecaoVertical direcaoVertical) {
        boolean candidato = false;
        Movimento movimento = new Movimento(pecaInicial);
        for (int coluna = pecaInicial.getColuna(),
                 linha = pecaInicial.getLinha();;
                coluna = coluna + direcaoHorizontal.getValor(),
                linha = linha + direcaoVertical.getValor()) {
            Peca peca;
            try {
                peca = tabuleiro.getPeca(coluna, linha);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
            if (peca == pecaInicial) {
                continue;
            }
            if (peca.getDono() != this && peca.getDono() != null) {
                movimento.addPecaCapturada(peca);
                candidato = true;
                continue;
            }
            if (peca.getDono() == null && candidato) {
                movimento.setPecaFinal(peca);
                movimentos.put(peca.getPosicao(), movimento);
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
