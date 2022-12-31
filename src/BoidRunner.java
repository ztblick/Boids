/**
 * Boids
 *
 * Built by Z. Blick on Nov. 26 2022
 * With guidance from V. Hunter Adams
 * Emulating the classic program by Craig Reynolds.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoidRunner implements ActionListener {

    private ArrayList<Boid> boids;
    private final int NUM_BOIDS = 200;
    private BoidView window;
    private final int SLEEP_TIME = 100;

    public BoidRunner() {
        boids = new ArrayList<>();
        for (int i = 0; i < NUM_BOIDS; i++)
            boids.add(new Boid());
    }

    public void actionPerformed(ActionEvent e) {
        for (Boid boid : boids) {
            boid.fly(boids);
        }
        window.repaint();
    }
    public ArrayList<Boid> getBoids() {
        return boids;
    }

    public static void main(String[] args) {
        BoidRunner b = new BoidRunner();
        b.window = new BoidView(b);
        Timer clock = new Timer(b.SLEEP_TIME, b);
        clock.start();
    }
}
