import java.util.concurrent.RecursiveTask;

public class Cloud extends RecursiveTask<Vector> {
    int start;
    int end;
    Vector arr[][][];
    static final int SEQUENTIAL_CUTOFF = 500;

    CloudData data = new CloudData();

    /**
     * Parameterised constructor
     *
     * @param a vector array containing wind data
     * @param s start value
     * @param e end value
     */
    Cloud(Vector[][][] a, int s, int e) {
        this.start = s;
        this.end = e;
        this.arr = a;
    }


    /**
     * Compute method that is necessary as this class extends recursive task
     */

    protected Vector compute() {
        Vector ans = new Vector();
        /*
        if ((end - start) < SEQUENTIAL_CUTOFF) {

            for (int t = 0; t < data.dimt; t++) {
                for (int x = 0; x < data.dimx; x++) {
                    for (int y = 0; y < data.dimy; y++) {
                        ans += (float) arr[t][x][y].get(0);
                        //ans[1] += (float) arr[t][x][y].get(1);
                    }
                }
            }
            return ans;
        } else {
            Cloud left = new Cloud(arr, start, (end + start) / 2);
            Cloud right = new Cloud(arr, (end + start) / 2, end);
            left.fork();

            int rightAns = right.compute();
            int leftAns = left.join();
            return leftAns + rightAns;
        }
        */
         return ans;
    }

}
