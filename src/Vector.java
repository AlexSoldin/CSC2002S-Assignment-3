public class Vector {

    /**
     * Global variables
     */
    private float x;
    private float y;

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
     *
     * @param add
     */
    public void add(Vector add){
        x += add.x;
        y += add.y;
    }

    /**
     * Overriding the toString method
     * @return personalised message
     */
    @Override
    public String toString() {
        return "["+x+", "+y+"]";
    }
}
