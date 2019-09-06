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

    static Resultant FJ(CloudData d){
        return fjPool.invoke(new Cloud(d,0, d.linearAdvection.length));
    }

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {

        getData();
        //displayInput();

        parallelStopWatch();

        sequentialStopWatch();

    }

    /**
     * A stopwatch method to time each parallel run of the program
     */
    static void parallelStopWatch(){

        for (int i = 0; i < 2; i++) {
            System.out.println("Run "+(i+1)+":");
            tick();
            FJPool();
            float time = tock();
            System.out.println((i+1)+" - parallelMethod took "+ time +" seconds\n");
        }

    }

    /**
     * Calculate average wind velocity using Fork-Join framework
     */
    static void FJPool(){
        Resultant ans = FJ(data);
        System.out.println(ans.toString());
    }

    /**
     * A stopwatch method to time each sequential run of the program
     */
    static void sequentialStopWatch(){

        for (int i = 0; i < 2; i++) {
            System.out.println("Run "+(i+1)+":");
            tick();
            sequentialMethod();
            float time = tock();
            System.out.println((i+1)+" - sequentialMethod took "+ time +" seconds\n");
        }

    }

    /**
     * Calculates the local wind average for a specific point in the grid
     * @param t
     * @param x
     * @param y
     * @return vector containing average wind data
     */
    static Vector getLocalAverage(int t, int x, int y){
        Vector[][][] averageWind = new Vector[data.dimt][data.dimx][data.dimy];
        averageWind[t][x][y] = new Vector();
        averageWind[t][x][y].add(data.advection[t][x][y]);
        averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);

//                    int surrounding = 0;
//                    for (int l = Math.max(0, x-1); l < Math.min(data.dimx, x+2); l++) {
//                        for (int m = Math.max(0, y-1); m < Math.min(data.dimy, y+2); m++) {
//                            averageWind[t][x][y].add(data.advection[t][l][m]);
//                            surrounding++;
//                        }
//
//                    }

        if(x!=0){
            averageWind[t][x][y].add(data.advection[t][x-1][y]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(y!=0){
            averageWind[t][x][y].add(data.advection[t][x][y-1]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(x!=0 && y!=0){
            averageWind[t][x][y].add(data.advection[t][x-1][y-1]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(x!=data.dimx-1){
            averageWind[t][x][y].add(data.advection[t][x+1][y]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(y!=data.dimy-1){
            averageWind[t][x][y].add(data.advection[t][x][y+1]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(x!=data.dimx-1 && y!=data.dimy-1){
            averageWind[t][x][y].add(data.advection[t][x+1][y+1]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(x!=0 && y!=data.dimy-1){
            averageWind[t][x][y].add(data.advection[t][x-1][y+1]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        if(y!=0 && x!=data.dimx-1){
            averageWind[t][x][y].add(data.advection[t][x+1][y-1]);
            averageWind[t][x][y].setCount(averageWind[t][x][y].getCount()+1);
        }

        return averageWind[t][x][y].getAverage();
    }

    /**
     * Calculate average wind velocity using sequential methods
     * Classify different clouds and assign them codes
     * 0 - cumulus
     * 1 - striated stratus
     * 2 - amorphous stratus
     */
    static void sequentialMethod(){
        Vector velocity = new Vector();

        String out = "";

        for (int t = 0; t < data.dimt; t++) {
            for (int x = 0; x < data.dimx; x++) {
                for (int y = 0; y < data.dimy; y++) {
                    velocity.add(data.advection[t][x][y]);
                    velocity.setCount(velocity.getCount()+1);

                    Vector averageWind = getLocalAverage(t, x, y);

                    if(Math.abs(data.convection[t][x][y])>averageWind.getMagnitude()){
                        data.classification[t][x][y] = 0;
                        out+= 0+" ";
                    }
                    else if(averageWind.getMagnitude()>0.2 && averageWind.getMagnitude()>=Math.abs(data.convection[t][x][y])){
                        data.classification[t][x][y] = 1;
                        out += 1+" ";
                    }
                    else{
                        data.classification[t][x][y] = 2;
                        out += 2+" ";
                    }

                }
            }
            out += "\n";
        }
        System.out.println(data.dimt+" "+data.dimx+" "+data.dimy);
        System.out.println(velocity.getAverage());
        System.out.println(out);
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
        //data.readData("simplesample_input.txt");
        data.readData("largesample_input.txt");
        //System.out.print("Enter the file name: ");
        //Scanner in = new Scanner(System.in);
        //String file = in.nextLine();
        //data.readData(file);

    }

}
