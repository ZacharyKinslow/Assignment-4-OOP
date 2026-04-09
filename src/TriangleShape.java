import java.awt.*;

public class TriangleShape extends Shape {

    public TriangleShape(double x, double y, double dx, double dy, int size, Color color) {
        super(x, y, dx, dy, size, color);
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        int s = size;

        int[] xs = {(int)x, (int)(x + s / 2.0), (int)(x + s)};
        int[] ys = {(int)(y + s), (int)(y),  (int)(y + s)};

        if (state == 1) {
            //Flipped Upside down
            ys = new int[]{(int)y, (int)(y + s), (int)y};
        }  else if (state == 2) {
            //Skew slightly
            xs = new int[]{(int)x, (int)(x + s * 0.8), (int)(x + s)};
        }
        g.fillPolygon(xs, ys, 3);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }
}
