import java.util.concurrent.ForkJoinPool;

public class CloudClassification {

    static CloudData data = new CloudData();
    static float averageWindVector[][];

    static long startTime = 0;
    static final ForkJoinPool fjPool = new ForkJoinPool();

    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    } //time in seconds

    //static int sum(float[][][] arr){
        //return fjPool.invoke(new Cloud(data.advection,0, data.dimt));
    //}

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {

        data.readData("simplesample_input.txt");
        displayInput();

        Cloud cloud = new Cloud(data.advection, 0, data.dimt);
        Vector averageSequential = cloud.sequential();
        System.out.println("WindAverage = ("+averageSequential.getX()+", "+averageSequential.getY()+")\n");

        tick();
        //int sumArr = sum(arr);
        float time = tock();
        System.out.println("Run took "+ time +" seconds");

        System.out.println("Sum is:");
        //System.out.println(sumArr);
        tick();
        //sumArr = sum(arr);
        time = tock();
        System.out.println("Second run took "+ time +" seconds");

        System.out.println("Sum is:");
        //System.out.println(sumArr);

    }


    /**
     * Displaying the input data to ensure it is being read in correctly
     * Each value of 't' is separated by a space, starting from t=0
     */
    public static void displayInput(){

        System.out.println("Length of advection: "+data.advection.length);
        System.out.println("Length of convection: "+data.convection.length+"\n");

        for(int t = 0; t < data.dimt; t++) {
            for (int x = 0; x < data.dimx; x++) {
                for (int y = 0; y < data.dimy; y++) {
                    System.out.println(data.advection[t][x][y].toString());
                    System.out.println(data.convection[t][x][y]);
                }
            }
            System.out.println();
        }
    }

}
