import java.awt.*;
import java.util.ArrayList;

public class Boid {
    private double x, y, dx, dy, speed, biasval = 0.001;
    private int bias = 0;
    private static final int BOID_LENGTH = 12,
                            PROTECTED_RANGE = 20,
                            VISUAL_RANGE = 100,
                            MARGIN = 100,
                            WINDOW_WIDTH = 1000,
                            WINDOW_HEIGHT = 800;
    private final double TURN_FACTOR = 3,
                        AVOID_FACTOR = 1,
                        CENTERING_FACTOR = 0.01,
                        MATCHING_FACTOR = 0.5,
                        MIN_SPEED = 10,
                        MAX_SPEED = 16,
                        BIAS_LIKELIHOOD = 0.12,
                        MAX_BIAS = 0.01,
                        BIAS_INCREMENT = 0.00004;

    // Generate a Boid in a random position with a random velocity.
    public Boid() {
        x = MARGIN + (Math.random()*(WINDOW_WIDTH-MARGIN*2));
        y = MARGIN + (Math.random()*(WINDOW_HEIGHT-MARGIN*2));
        double angle = Math.random()*2*Math.PI;
        dx = (MAX_SPEED + MIN_SPEED) / 2 * Math.cos(angle);
        dy = (MAX_SPEED + MIN_SPEED) / 2  * Math.sin(angle);
        double b = Math.random();
        if (b < BIAS_LIKELIHOOD)
            bias = -1;
        else if (b > 1 - BIAS_LIKELIHOOD)
            bias = -1;
    }

    public void fly(ArrayList<Boid> boids) {
        flock(boids);
        screenEdges();
        bias();
        speedLimits();
        x += dx;
        y += dy;
    }

    public double getDistance(Boid b) {
        return Math.sqrt((b.x - this.x)*(b.x - this.x) + (b.y - this.y)*(b.y - this.y));
    }

    public void flock(ArrayList<Boid> boids) {
        double close_dx = 0, close_dy = 0, x_avg = 0, y_avg = 0, dx_avg = 0, dy_avg = 0, neighboring_boids = 0;
        for(Boid b : boids) {
            // Separation
            if(this.getDistance(b) < PROTECTED_RANGE && b != this) {
                close_dx += this.x - b.x;
                close_dy += this.y - b.y;
            }
            // Cohesion & Alignment
            else if (this.getDistance(b) < VISUAL_RANGE && b != this) {
                x_avg += b.x;
                dx_avg += b.dx;
                y_avg += b.y;
                dy_avg += b.dy;
                neighboring_boids++;
            }
        }
        if (neighboring_boids > 0) {
            x_avg /= neighboring_boids;
            y_avg /= neighboring_boids;
            dx_avg /= neighboring_boids;
            dy_avg /= neighboring_boids;
        }
        this.dx += close_dx*AVOID_FACTOR;
        this.dy += close_dy*AVOID_FACTOR;
        this.dx += (x_avg - this.x)*CENTERING_FACTOR;
        this.dx += (dx_avg - this.dx)*MATCHING_FACTOR;
        this.dy += (y_avg - this.y)*CENTERING_FACTOR;
        this.dy += (dy_avg - this.dy)*MATCHING_FACTOR;
    }
    public void screenEdges() {
        if (x < MARGIN)
            dx += TURN_FACTOR;
        if (x > WINDOW_WIDTH - MARGIN)
            dx -= TURN_FACTOR;
        if (y < MARGIN)
            dy += TURN_FACTOR;
        if (y > WINDOW_HEIGHT - MARGIN)
            dy -= TURN_FACTOR;
    }
    public void bias() {
        if (this.bias != 0) {
            if (this.dx * bias > 0)
                this.biasval = Math.min(MAX_BIAS, this.biasval + BIAS_INCREMENT);
            else
                this.biasval = Math.max(BIAS_INCREMENT, this.biasval - BIAS_INCREMENT);
            this.dx = (1 - this.biasval) * this.dx + (this.biasval * bias);
        }
    }
    public void speedLimits(){
        speed = Math.sqrt(this.dx*this.dx + this.dy*this.dy);

        // Enforce min and max speeds
        if (speed < MIN_SPEED) {
            this.dx = (this.dx / speed) * MIN_SPEED;
            this.dy = (this.dy / speed) * MIN_SPEED;
        }
        if (speed > MAX_SPEED) {
            this.dx = (this.dx / speed) * MAX_SPEED;
            this.dy = (this.dy / speed) * MAX_SPEED;
        }
    }



    public void drawBoid(Graphics g) {

        // Get angle
        double theta = Math.atan2(dy, dx);
        double l = BOID_LENGTH/2;

        // Create new points
        double front_x = x + l*Math.cos(theta);
        double front_y = y + l*Math.sin(theta);
        double left_x = x - l*Math.cos(theta) + l/2*Math.sin(theta);
        double left_y = y - l*Math.sin(theta) - l/2*Math.cos(theta);
        double right_x = x - l*Math.cos(theta) - l/2*Math.sin(theta);;
        double right_y = y - l*Math.sin(theta) + l/2*Math.cos(theta);;


        g.setColor(Color.BLACK);
        g.fillPolygon(new int[] {(int)front_x, (int)left_x, (int)right_x},
                        new int[] {(int)front_y, (int)left_y, (int)right_y}, 3);
    }
}
