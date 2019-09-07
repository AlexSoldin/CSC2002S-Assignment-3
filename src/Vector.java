import java.text.DecimalFormat;

public class Vector {

    /**
     * Global variables
     */
    private float x;
    private float y;
    private int count;

    /**
     * Parameterised contructor
     * @param x x value
     * @param y y value
     * @param count count value
     */
    public Vector(float x, float y, int count) {
        this.x = x;
        this.y = y;
        this.count = count;
    }

    /**
     * Default constructor
     */
    public Vector(){
        this.x = 0;
        this.y = 0;
        this.count = 0;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Accumulates x and y components in one vector
     * Used for sums and averages
     * @param add vector to add components to current x and y values
     */
    public void add(Vector add){
        x += add.x;
        y += add.y;
    }

    /**
     * Calculates the average of the x and y components
     * @return new vector object
     */
    public Vector getAverage(){
        return new Vector(x/count, y/count, count);
    }

    /**
     * Calculates the magnitude of the x and y components
     * @return magnitude
     */
    public float getMagnitude(){
        double mag = Math.pow(x, 2) + Math.pow(y, 2);
        return (float) Math.sqrt(mag);
    }

    /**
     * Combines the current vector with the vector sent in to form a single vector
     * @param v
     * @return new vector
     */
    public Vector combine(Vector v){
        return new Vector(x + v.getX(),y + v.getY(), count + v.getCount());
    }

    /**
     * Overriding the toString method
     * @return personalised output message
     */
    @Override
    public String toString() {
        return x+" "+y;
    }

    /**
     * Form of toString method returning rounded off values
     * @return rounded of toString method
     */
    public String roundedString(){
        DecimalFormat df = new DecimalFormat("#.###");
        return "["+df.format(x)+", "+df.format(y)+"]";
    }
}
