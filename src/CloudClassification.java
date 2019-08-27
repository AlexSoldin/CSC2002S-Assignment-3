import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class CloudClassification {

    static CloudData data = new CloudData();
    static float time;

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

        getData();
        displayInput();

        sequentialStopWatch();

        sequentialCloudClassification();

        tick();
        //int sumArr = sum(arr);
        time = tock();
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
     * A stopwatch method to time each sequential run of the program
     */
    static void sequentialStopWatch(){
        tick();
        sequentialWindVelocity();
        float time = tock();
        System.out.println("1 - Sequential averageWindVector took "+ time +" seconds\n");

        tick();
        sequentialWindVelocity();
        time = tock();
        System.out.println("2 - Sequential averageWindVector took "+ time +" seconds\n");

        tick();
        sequentialWindVelocity();
        time = tock();
        System.out.println("3 - Sequential averageWindVector took "+ time +" seconds\n");
    }

    static void sequentialCloudClassification(){

        for (int t = 0; t < data.dimt; t++) {
            for (int x = 0; x < data.dimx; x++) {
                for (int y = 0; y < data.dimy; y++) {

                    if(Math.abs(data.convection[t][x][y])>data.advection[t][x][y].getMagnitude()){
                        data.classification[t][x][y] = 0;
                        System.out.print(0+" ");
                    }
                    else if(Math.abs(data.convection[t][x][y])>0.2){
                        data.classification[t][x][y] = 1;
                        System.out.print(1+" ");
                    }
                    else{
                        data.classification[t][x][y] = 2;
                        System.out.print(2+" ");
                    }

                }
            }
            System.out.println();
        }
    }


    /**
     * Calculate average wind velocity using sequential methods
     */
    static void sequentialWindVelocity() {
        Vector velocity = new Vector();
        float count = 0;

        for (int t = 0; t < data.dimt; t++) {
            for (int x = 0; x < data.dimx; x++) {
                for (int y = 0; y < data.dimy; y++) {
                    velocity.add(data.advection[t][x][y]);
                    count++;
                }
            }
        }
        velocity.setX((velocity.getX()/count));
        velocity.setY((velocity.getY()/count));
        System.out.println("WindAverageSequential = "+velocity.roundedString()+"\n");

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

    /**
     * Takes in the name of the input file and reads in the data
     */
    public static void getData(){
        data.readData("simplesample_input.txt");
        //Scanner in = new Scanner(System.in);
        //String file = in.nextLine();
        //data.readData(file);

    }

}
