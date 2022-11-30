/**
 * Boids
 *
 * Built by Z. Blick on Nov. 26 2022
 * With some inspiration from V. Hunter Adams
 */
import java.util.ArrayList;

public class BoidRunner {

    private ArrayList<Boid> boids;
    private boolean runOK;
    private final int NUM_BOIDS = 200;
    private BoidView window;
    private int sleepTime = 100;

    public BoidRunner() {
        boids = new ArrayList<>();
        for (int i = 0; i < NUM_BOIDS; i++)
            boids.add(new Boid());
        runOK = true;
        window = new BoidView(this);
    }

    public void run() {
        while (runOK) {
            for (Boid boid : boids) {
                boid.fly(boids);
            }
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            window.repaint();
        }
    }

    public ArrayList<Boid> getBoids() {
        return boids;
    }

    public void end() {
        runOK = false;
    }

    public static void main(String[] args) {
        BoidRunner b = new BoidRunner();
        b.run();
    }
}
