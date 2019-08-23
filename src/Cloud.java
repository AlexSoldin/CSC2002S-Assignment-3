import java.util.concurrent.RecursiveTask;

public class Cloud extends RecursiveTask<Integer> {
    int lo;
    int hi;
    int[] arr;
    static final int SEQUENTIAL_CUTOFF = 500;

    int ans = 0;


    /**
     * Parameterised constructor
     */
    Cloud(int[] a, int l, int h) {
        lo=l; hi=h; arr=a;
    }


    /**
     * Compute method that is necessary as this class extends recursive task
     */
    protected Integer compute(){
        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            int ans = 0;
            for(int i=lo; i < hi; i++)
                ans += arr[i];
            return ans;
        }
        else {
            Cloud left = new Cloud(arr,lo,(hi+lo)/2);
            Cloud right= new Cloud(arr,(hi+lo)/2,hi);

            // order of next 4 lines
            // essential – why?
            left.fork();
            int rightAns = right.compute();
            int leftAns  = left.join();
            return leftAns + rightAns;
        }
    }

}
