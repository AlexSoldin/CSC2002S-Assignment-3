public class Resultant {

    /**
     * Global variables
     */
    public CloudData data;
    public Vector velocity;

    /**
     * Parameterised contructor
     * @param data cloud data
     * @param velocity average wind velocity
     */
    public Resultant(CloudData data, Vector velocity) {
        this.data = data;
        this.velocity = velocity;
    }

    /**
     * Accessor and Mutator methods
     */
    public CloudData getData() {
        return data;
    }

    public void setData(CloudData data) {
        this.data = data;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    /**
     * Overriding the toString method
     * @return personalised output message
     */
    public String toString(){
        String out = data.dimt+" "+data.dimx+" "+data.dimy+"\n";
        out+= velocity.getAverage()+"\n";
        for (int i = 0; i < data.dimt; i++) {
            for (int j = 0; j < data.dimx*data.dimy; j++) {
                out+=data.linearClassification[i*data.dimx*data.dimy+j]+" ";
            }
            out+="\n";
        }
        return out;
    }
}
