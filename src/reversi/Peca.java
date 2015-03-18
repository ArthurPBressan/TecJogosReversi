package reversi;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arthur on 12/03/15.
 */
public class Peca extends JButton {
    private static int tamanho = 64;

    private int coluna;
    private int linha;

    private Jogador dono;

    public Peca(int coluna, int linha, Tabuleiro tabuleiro) {
        super();
        this.coluna = coluna;
        this.linha = linha;
        setEnabled(false);
        addActionListener(tabuleiro);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(tamanho, tamanho);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        if (dono != null) {
            g2D.setColor(dono.getCor());
            g2D.fillOval(6, 6, getWidth() - 12, getHeight() - 12);
        }

        g2D.setColor(Color.GRAY);
        g2D.drawOval(6, 6, getWidth() - 12, getHeight() - 12);
        g2D.drawString("[" + coluna + "],[" + linha + "]", 10, 35);
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
        setEnabled(false);
    }

    public int getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    public Jogador getDono() {
        return dono;
    }

    @Override
    public String toString() {
        return "Peca{" +
                "coluna=" + coluna +
                ", linha=" + linha +
                ", dono=" + dono +
                '}';
    }
}
