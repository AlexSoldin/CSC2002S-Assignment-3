import java.util.concurrent.RecursiveTask;

public class Cloud extends RecursiveTask<Vector> {
    int lo;
    int hi;
    float arr[];
    static final int SEQUENTIAL_CUTOFF = 500;

    /**
     * Parameterised constructor
     *
     * @param a vector array containing wind data
     * @param l start value
     * @param h end value
     */
    Cloud(float[] a, int l, int h) {
        this.lo = l;
        this.hi = h;
        this.arr = a;
    }


    /**
     * Compute method that is necessary as this class extends recursive task
     */
    protected Vector compute() {
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            Vector ans = new Vector();
            for(int i=lo; i < hi; i++) {
                ans.setX(ans.getX()+arr[i++]);
                ans.setY(ans.getY()+arr[i++]);
            }

            return ans;
        }
        else {
            Cloud left = new Cloud(arr,lo,(hi+lo)/2);
            Cloud right= new Cloud(arr,(hi+lo)/2,hi);

            left.fork();
            Vector rightAns = right.compute();
            Vector leftAns  = left.join();
            return rightAns.combine(leftAns);
        }
    }

}
