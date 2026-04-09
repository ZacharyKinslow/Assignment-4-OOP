import java.awt.*;

public class TriangleShape extends Shape {

    public TriangleShape(double x, double y, double dx, double dy, int size, Color color) {
        super(color, x, y, dx, dy, size);
    }
    @Override
    public void draw(Graphics2D g2d) {

        // Stop rotation from affecting other shapes
        Graphics2D g = (Graphics2D) g2d.create();


        g.setColor(color);
        int s = size;
        //Rotate Only This Triangle
        g.rotate(Math.toRadians(rotation), x + s / 2.0, y + s / 2.0);

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
