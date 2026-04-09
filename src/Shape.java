import java.awt.*;

public abstract class Shape {
    protected Color color;
    protected double x, y;
    protected double dx, dy;
    protected int size;
    protected int state;
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
        return this.getBounds().intersects(other.getBounds());
    }

    //Change shape visuals on collision
    public void onShapeCollision() {
        //Switch direction
        dx = -dx;
        dy = -dy;

        //Cycle visual state
        state = (state + 1) % 3;

        //Change color based on state
        switch (state) {
            case 0: color = Color.MAGENTA;
            case 1: color = Color.GREEN;
            case 2: color = Color.RED;
        }
    }

}
