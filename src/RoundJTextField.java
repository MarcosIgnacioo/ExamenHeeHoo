import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;

public class RoundJTextField extends JTextField {
    private final Shape shape;

    public RoundJTextField(int size, int arc) {
        super(size);
        setOpaque(false);
        shape = new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }
}
