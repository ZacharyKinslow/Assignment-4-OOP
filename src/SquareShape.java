import java.awt.*;

public class SquareShape extends Shape{
    public SquareShape(double x, double y, double dx, double dy, int size, Color color) {
        super(color, x, y, dx, dy, size);
    }
    @Override
    public void draw(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d.create();


        int s = size;
        g.setColor(color);

        //Rotate only this shape
        g.rotate(Math.toRadians(rotation), x + s / 2.0, y + s / 2.0);

        if (state == 0) {
            g.fillRect((int)x, (int)y, s, s);
        } else if (state == 1) {
            g.drawRect((int)x, (int)y, s, s);
        } else {
            //Gradient effect
            GradientPaint gp = new GradientPaint((int)x, (int)y, color, (int)x + s, (int)y + s, color.WHITE);
            Paint old =  g.getPaint();
            g.setPaint(gp);
            g.fillRect((int)x, (int)y, s, s);
            g.setPaint(old);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }
}
