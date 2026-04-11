import java.awt.*;

public class StarShape extends Shape {

    public StarShape(double x, double y, double dx, double dy, int size, Color color) {
        super(color, x, y, dx, dy, size);
    }

    public void onSpinChange() {
        rotationSpeed *= 0.8;
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Stop rotation from affecting other shapes
        Graphics2D g = (Graphics2D) g2d.create();

        g.setColor(color);
        int s = size;

        // Rotate ONLY this shape around its center
        g.rotate(Math.toRadians(rotation), x + s / 2.0, y + s / 2.0);

        int cx = (int)(x + s / 2.0);
        int cy = (int)(y + s / 2.0);

        int points = 10;
        int[] xs = new int[points];
        int[] ys = new int[points];

        double outer = s * 0.45;
        double inner = s * 0.25;

        // visual state: rotate a bit
        double baseAngle = switch (state) {
            case 1 -> Math.toRadians(18);
            case 2 -> Math.toRadians(36);
            default -> 0;
        };

        for (int i = 0; i < points; i++) {
            double angle = baseAngle + i * Math.PI / 5.0;
            double radius = (i % 2 == 0) ? outer : inner;
            xs[i] = (int)(cx + radius * Math.cos(angle));
            ys[i] = (int)(cy + radius * Math.sin(angle));
        }

        g.fillPolygon(xs, ys, points);

        // Restore original transform so other shapes aren't affected
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