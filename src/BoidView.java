import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
public class BoidView extends JFrame {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final int MARGIN = 100;
    private BoidRunner b;

    public BoidView(BoidRunner b) {
        this.b = b;
        setTitle("Blick's Boids");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);
        createBufferStrategy(2);

        // Terminate the program when the user closes the window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // End the program in BoidRunner
                b.end();
                System.exit(0);
            }
        });
    }

    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try
        {
            g2 = bf.getDrawGraphics();
            // myPaint does the actual drawing
            myPaint(g2);
        }
        finally
        {
            // It is best to dispose() a Graphics object when done with it.
            g2.dispose();
        }
        // Shows the contents of the backbuffer on the screen.
        bf.show();
        //Tell the System to do the Drawing now, otherwise it can take a few extra ms until
        //Drawing is done which looks very jerky
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
