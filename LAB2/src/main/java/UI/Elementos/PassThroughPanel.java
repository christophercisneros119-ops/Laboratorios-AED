package UI.Elementos;

import java.awt.Component;
import java.awt.Point;
import javax.swing.JPanel;

public class PassThroughPanel extends JPanel {

    public PassThroughPanel() {
        setOpaque(false);
        setLayout(null);
    }

    @Override
    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    @Override
    public boolean contains(int x, int y) {
        for (Component c : getComponents()) {
            if (c.isVisible() && c.contains(x - c.getX(), y - c.getY())) {
                return true;
            }
        }
        return false;
    }
}