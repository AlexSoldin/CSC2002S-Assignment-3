import java.util.concurrent.ForkJoinPool;

public class CloudClassification {

    static long startTime = 0;
    static final ForkJoinPool fjPool = new ForkJoinPool();

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

    }

}
