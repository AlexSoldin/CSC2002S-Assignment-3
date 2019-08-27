import java.text.DecimalFormat;

public class Vector {

    /**
     * Global variables
     */
    private float x;
    private float y;
    private DecimalFormat df = new DecimalFormat("#.###");

    /**
     * Parameterised contructor
     * @param x x value
     * @param y y value
     */

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Default constructor
     */
    public Vector(){
        this.x = 0;
        this.y = 0;
    }

    /**
     * Accessor and Mutator methods
     */
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * Accumulates x and y components in one vector
     * Used for sums and averages
     * @param add
     */
    public void add(Vector add){
        x += add.x;
        y += add.y;
    }

    public float getMagnitude(){
        double mag = Math.pow(x, 2) + Math.pow(y, 2);
        return (float) Math.sqrt(mag);
    }

    /**
     * Overriding the toString method
     * @return personalised message
     */
    @Override
    public String toString() {
        return "["+x+", "+y+"]";
    }

    public String roundedString(){
        return "["+df.format(x)+", "+df.format(y)+"]";
    }
}
