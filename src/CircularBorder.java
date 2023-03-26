import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import javax.swing.JTextField;

public class CircularBorder extends AbstractBorder {
    private final Color color;
    private final int arc;

    public CircularBorder(Color color, int arc) {
        this.color = color;
        this.arc = arc;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        Shape shape = new RoundRectangle2D.Double(x, y, width - 1, height - 1, arc, arc);
        ((java.awt.Graphics2D) g).draw(shape);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = 8;
        insets.top = insets.bottom = 4;
        return insets;
    }
}

