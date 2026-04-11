import java.awt.*;

public class CircleShape extends Shape {

    public CircleShape(double x, double y, double dx, double dy, int size, Color color) {
        super(color, x, y, dx, dy, size);
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)x, (int)y, size, size);
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
