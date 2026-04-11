import java.awt.*;

public abstract class Shape {
    protected Color color;
    protected double x, y;
    protected double dx, dy;
    protected int size;
    protected int state;
    protected double rotation = 0;
    protected double rotationSpeed = 1; // degrees per frame\
    protected int colorShift = 0;
    protected int collisionCooldown = 0;

    public Shape(Color color, double x, double y, double dx, double dy, int size) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.state = 0;
    }

    // Shape rendering
    public abstract void draw(Graphics2D g);

    //Get shape outline
    public abstract Rectangle getBounds();

    //Move shape
    public void move(int panelWidth, int panelHeight) {
        x += dx;
        y += dy;
        rotation += rotationSpeed;
        if (collisionCooldown > 0)
            collisionCooldown--;


        colorShift = (colorShift + 1) % 360;
        color = Color.getHSBColor(colorShift / 360f, 1f, 1f);

        Rectangle bounds = getBounds();

        //Stay inside program window
        if (bounds.x < 0) {
            x -= bounds.x;
            dx = -dx;
        } else if (bounds.x + bounds.width > panelWidth) {
            x -= (bounds.x + bounds.width - panelWidth);
            dx = -dx;
        }
        if (bounds.y < 0) {
            y -= bounds.y;
            dy = -dy;
        }  else if (bounds.y + bounds.height > panelHeight) {
            y -= (bounds.y + bounds.height - panelHeight);
            dy = -dy;
        }
    }

    //Check if shape hits another shape
    public boolean collidesWith(Shape other) {
        if (this.collisionCooldown > 0 || other.collisionCooldown > 0)
            return false;

        return this.getBounds().intersects(other.getBounds());
    }

    //Collision Cooldown (Stop visual glitching)
    protected boolean justCollided = false;

    //Change shape visuals on collision
    public void onShapeCollision() {
        dx = -dx;
        dy = -dy;

        // reduce spin intensity on collision
        rotationSpeed *= 0.8;

        state = (state + 1) % 3;

        // pick two random colors for a gradient theme
        Color c1 = Color.getHSBColor((float)Math.random(), 1f, 1f);
        Color c2 = Color.getHSBColor((float)Math.random(), 1f, 1f);

        // blend them based on state
        float t = state / 2f; // 0 → 0.0, 1 → 0.5, 2 → 1.0

        int r = (int)(c1.getRed()   * (1 - t) + c2.getRed()   * t);
        int g = (int)(c1.getGreen() * (1 - t) + c2.getGreen() * t);
        int b = (int)(c1.getBlue()  * (1 - t) + c2.getBlue()  * t);

        color = new Color(r, g, b);

        collisionCooldown = 10;
    }

}
