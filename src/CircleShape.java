import java.awt.*;

public class CircleShape extends Shape {

    public CircleShape(double x, double y, double dx, double dy, int size, Color color) {
        super(color, x, y, dx, dy, size);
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        int s = size;

        // Visual states of size variation
        if (state == 1) s = (int)(size * 1.3);
        else if (state == 2) s = (int)(size * 0.7);

        g.fillOval((int)x, (int)y, s, s);
    }

    @Override
    public Rectangle getBounds() {
        int s = size;
        if (state == 1) s = (int)(size * 1.3);
        else if (state == 2) s = (int)(size * 0.7);
        return new Rectangle((int)x, (int)y, s, s);
    }
}
