package reversi;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arthur on 12/03/15.
 */
public class Janela extends JFrame {
    public Janela() throws HeadlessException {
        super("Reversi");
        getContentPane().add(new Tabuleiro());
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
