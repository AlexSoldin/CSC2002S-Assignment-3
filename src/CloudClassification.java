import java.util.concurrent.ForkJoinPool;

public class CloudClassification {

    static long startTime = 0;
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static float averageWindVector[][];

    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    } //time in seconds

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
        tick();
        //int sumArr = sum(arr);
        float time = tock();
        System.out.println("Run took "+ time +" seconds");

        System.out.println("Sum is:");
       // System.out.println(sumArr);
        tick();
       // sumArr = sum(arr);
        time = tock();
        System.out.println("Second run took "+ time +" seconds");

        System.out.println("Sum is:");
        //System.out.println(sumArr);

    }

}
