package reversi.jogador;

import reversi.movimento.ConjuntoMovimentosComparator;
import reversi.movimento.ConjuntoMovimentos;
import reversi.movimento.Movimento;
import reversi.peca.Peca;
import reversi.Tabuleiro;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by arthur on 13/03/15.
 */
public class Jogador {
    private Tabuleiro tabuleiro;
    private Color cor;
    private ArrayList<Peca> pecas;
    private HashMap<Point, ConjuntoMovimentos> movimentosMap;
    private Point pontoMelhorMovimento;
    private boolean IA;
    private String nome;

    public String getNome() {
        return nome;
    }

    public Jogador(Tabuleiro tabuleiro, Color cor, boolean IA, String nome) {
        this.tabuleiro = tabuleiro;
        this.cor = cor;
        this.IA = IA;
        this.nome = nome;

        this.movimentosMap = new HashMap<Point, ConjuntoMovimentos>();
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

    public int calcularMovimentos() {
        movimentosMap.clear();
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

        ArrayList<ConjuntoMovimentos> movimentosValues = new ArrayList<ConjuntoMovimentos>(movimentosMap.values());
        Collections.sort(movimentosValues, new ConjuntoMovimentosComparator());
        if (movimentosValues.size() > 0) {
            pontoMelhorMovimento = movimentosValues.get(0).getChave();
        } else {
            pontoMelhorMovimento = null;
        }
        if (tabuleiro.isDebug()) {
            System.out.println("Melhor movimento: " + pontoMelhorMovimento);
            for (ConjuntoMovimentos movimentos : movimentosValues) {
                for(Movimento movimento : movimentos) {
                    movimento.getPecaFinal().setEnabled(true);
                }
            }
        }
        return movimentosValues.size();
    }

    public ArrayList<Movimento> getMovimentosNaPosicao(Point posicao) {
        return movimentosMap.get(posicao);
    }

    public int getNumeroPecas() {
        return pecas.size();
    }

    public void jogar() {
        if (pontoMelhorMovimento != null) {
            tabuleiro.fazerJogada(tabuleiro.getPeca(pontoMelhorMovimento));
        }
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
            if (peca.getDono() == null && !candidato) {
                break;
            }
            if (peca.getDono() != this && peca.getDono() != null) {
                movimento.addPecaCapturada(peca);
                candidato = true;
                continue;
            }
            if (peca.getDono() == null && candidato) {
                movimento.setPecaFinal(peca);
                Point posicao = peca.getPosicao();
                ConjuntoMovimentos movimentos = movimentosMap.getOrDefault(posicao, new ConjuntoMovimentos(posicao));
                movimentos.add(movimento);
                movimentosMap.put(peca.getPosicao(), movimentos);
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
                "num pecas= " + getNumeroPecas() +
                '}';
    }

    public boolean isIA() {
        return IA;
    }
}
