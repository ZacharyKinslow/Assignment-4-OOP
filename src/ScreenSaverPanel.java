import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class ScreenSaverPanel extends JPanel {

    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final Timer timer;
    private final Random rand  = new Random();
    private int textColorShift = 0;

    public ScreenSaverPanel() {
        setBackground(Color.BLACK);

        //add shape on mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addRandomShape(e.getX(), e.getY());
            }
        });

        //Timer : 60fps
        timer = new Timer(16, e -> {
            updateShapes();
            repaint();
        });
        timer.start();
    }

    private void addRandomShape(int x, int y) {
        int size = 32 + rand.nextInt(40);
        double dx = -3 + rand.nextDouble() * 6;
        double dy = -3 + rand.nextDouble() * 6;
        if (dx == 0 && dy == 0) dx = 1;

        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        int type = rand.nextInt(4);
        Shape s;
        switch (type) {
            case 0: s = new CircleShape(x, y, dx, dy, size, color);
            break;
            case 1: s = new SquareShape(x, y, dx, dy, size, color);
            break;
            case 2: s = new TriangleShape(x, y, dx, dy, size, color);
            break;
            default: s = new StarShape(x, y, dx, dy, size, color);
            break;

        }

        shapes.add(s);
    }

    //Fix collision bug when overlapping shapes spawn
    private void resolveOverlap(Shape a, Shape b) {
        Rectangle r1 = a.getBounds();
        Rectangle r2 = b.getBounds();

        if (r1.intersects(r2)) {
            double overlapX = (r1.width + r2.width) / 2.0 - Math.abs(a.x - b.x);
            double overlapY = (r1.height + r2.height) / 2.0 - Math.abs(a.y - b.y);

            if (overlapX < overlapY) {
                if (a.x < b.x) { a.x -= overlapX / 2; b.x += overlapX / 2; }
                else           { a.x += overlapX / 2; b.x -= overlapX / 2; }
            } else {
                if (a.y < b.y) { a.y -= overlapY / 2; b.y += overlapY / 2; }
                else           { a.y += overlapY / 2; b.y -= overlapY / 2; }
            }
        }
    }

    private void updateShapes() {
        int w =  getWidth();
        int h = getHeight();
        textColorShift = (textColorShift + 1) % 360;

        //Move shapes and handle wall collisions
        for (Shape s : shapes) {
            s.move(w, h);
        }

        //Inter shape collisions
        for (int i = 0; i < shapes.size(); i++) {
            for (int j = i + 1; j < shapes.size(); j++) {
                Shape a = shapes.get(i);
                Shape b = shapes.get(j);
                if (a.collidesWith(b)) {
                    resolveOverlap(a, b);
                    a.onShapeCollision();
                    b.onShapeCollision();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 🌈 Rainbow text color
        Color textColor = Color.getHSBColor(textColorShift / 360f, 1f, 1f);

        String message = "Thanks Yousef for a great class!\n Have a great summer!";
        Font font = new Font("Arial", Font.BOLD, 28);
        g2.setFont(font);

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(message)) / 2;
        int y = getHeight() / 2;

        //Glow effect
        g2.setColor(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 80));
        g2.drawString(message, x + 2, y + 2);

        // 🎯 Main text
        g2.setColor(textColor);
        g2.drawString(message, x, y);

        // 🟣 Draw shapes on top
        for (Shape s : shapes) {
            s.draw(g2);
        }

        g2.dispose();
    }
}
