package reversi.peca;

import reversi.Tabuleiro;
import reversi.jogador.Jogador;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arthur on 12/03/15.
 */
public class Peca extends JButton {

    private Point posicao;

    private Jogador dono;
    private boolean hover = false;

    public Peca(int coluna, int linha, Tabuleiro tabuleiro, PecaMouseListener pecaMouseListener) {
        super();
        this.posicao = new Point(coluna, linha);
        setEnabled(false);
        addActionListener(tabuleiro);
        addMouseListener(pecaMouseListener);
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
        return new Dimension(64, 64);
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
        g2D.drawString("[" + getColuna() + "],[" + getLinha() + "]", 10, 35);
        if (hover) {
            g2D.setPaint(Color.BLACK);
            g2D.setStroke(new BasicStroke(10f));
            g2D.draw(new Rectangle.Double(0, 0, getWidth(), getHeight()));
        }
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
        setEnabled(false);
        setHover(false);
    }

    public int getColuna() {
        return posicao.x;
    }

    public int getLinha() {
        return posicao.y;
    }

    public Jogador getDono() {
        return dono;
    }

    @Override
    public String toString() {
        return "Peca{" +
                "coluna=" + getColuna() +
                ", linha=" + getLinha() +
                ", dono=" + dono +
                '}';
    }

    public Point getPosicao() {
        return posicao;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        repaint();
    }
}
