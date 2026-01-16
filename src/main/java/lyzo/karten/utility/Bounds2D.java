package lyzo.karten.utility;

public class Bounds2D {

    private double x, y;
    private final double w, h;

    public Bounds2D(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public boolean onScreen(double screenW, double screenH) {
        return false;
    }
}
