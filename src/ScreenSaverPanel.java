import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class ScreenSaverPanel extends JPanel {

    private final ArrayList<Shape> shapes = new ArrayList<>();
    private final Timer timer;
    private final Random rand  = new Random();

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
    private void updateShapes() {
        int w =  getWidth();
        int h = getHeight();

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

        //Antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape s : shapes) {
            s.draw(g2);
        }
        g2.dispose();
    }
}
