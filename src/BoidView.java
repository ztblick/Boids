import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
public class BoidView extends JFrame {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final int MARGIN = 100;
    private BoidRunner b;

    public BoidView(BoidRunner b) {
        this.b = b;
        this.setTitle("Blick's Boids");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.createBufferStrategy(2);
    }

    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try
        {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        }
        finally
        {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void myPaint(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(MARGIN, MARGIN,WINDOW_WIDTH-2*MARGIN, WINDOW_HEIGHT-2*MARGIN);
        for (Boid boid : b.getBoids())
            boid.drawBoid(g);
    }
}
