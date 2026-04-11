import java.awt.*;

public class TriangleShape extends Shape {

    public TriangleShape(double x, double y, double dx, double dy, int size, Color color) {
        super(color, x, y, dx, dy, size);
    }
    @Override
    public void draw(Graphics2D g2d) {

        Graphics2D g = (Graphics2D) g2d.create();
        g.setColor(color);

        int s = size;

        // Center of the triangle
        double cx = x + s / 2.0;
        double cy = y + s / 2.0;

        // Rotate around center
        g.rotate(Math.toRadians(rotation), cx, cy);

        // Triangle points relative to center
        int[] xs = {
                (int)(cx - s/2),   // left
                (int)(cx),         // top
                (int)(cx + s/2)    // right
        };

        int[] ys = {
                (int)(cy + s/2),   // bottom left
                (int)(cy - s/2),   // top
                (int)(cy + s/2)    // bottom right
        };

        // Apply skew when state == 1
        if (state == 1) {
            xs[1] = (int)(cx + s * 0.2); // shift top point slightly right
        }

        g.fillPolygon(xs, ys, 3);
        g.dispose();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }

    @Override
    public void onShapeCollision() {
        dx = -dx;
        dy = -dy;

        state = (state + 1) % 3;

        collisionCooldown = 10;
    }
}

