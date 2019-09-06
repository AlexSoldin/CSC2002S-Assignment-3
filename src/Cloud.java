import java.util.concurrent.RecursiveTask;

public class Cloud extends RecursiveTask<Resultant> {
    int lo;
    int hi;
    CloudData data;
    int middle;
    static final int SEQUENTIAL_CUTOFF = 500;

    /**
     * Parameterised constructor
     *
     * @param d vector array containing wind data
     * @param l start value
     * @param h end value
     */
    Cloud(CloudData d, int l, int h) {
        this.lo = l;
        this.hi = h;
        this.data = d;
        this.middle = (hi+lo)/2;
    }


    /**
     * Compute method that is necessary as this class extends recursive task
     */
    protected Resultant compute() {

        if((hi-lo) < SEQUENTIAL_CUTOFF) {
            Vector windData = new Vector();
            Vector velocity = new Vector();
            Vector average;

            int xComponent;
            int yComponent;
            int uComponent;

            boolean topRight;
            boolean bottomRight;
            boolean topLeft;
            boolean bottomLeft;

            int current = lo/3;
            current = current % (data.dim()/data.dimt);
            int count = lo%3;

            for(int i=lo; i < hi; i+=3) {
                xComponent = i;
                yComponent = i+1;
                uComponent = i+2;

                topRight = false;
                bottomRight = false;
                topLeft = false;
                bottomLeft = false;

                windData.setX(windData.getX() + data.linearAdvection[xComponent]);
                windData.setY(windData.getY() + data.linearAdvection[yComponent]);
                windData.setCount(windData.getCount()+1);

                velocity.setX(windData.getX() + data.linearAdvection[xComponent]);
                velocity.setY(windData.getY() + data.linearAdvection[yComponent]);
                velocity.setCount(windData.getCount()+1);

                if (current % data.dimx != 0){
                    windData.setX(windData.getX() + data.linearAdvection[xComponent-3]);
                    windData.setY(windData.getY() + data.linearAdvection[yComponent-3]);
                    windData.setCount(windData.getCount()+1);
                    if (current >= data.dimx) {
                        //add above left
                        windData.setX(windData.getX() + data.linearAdvection[xComponent - 3 - data.dimx*3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent - 3 - data.dimx*3]);
                        windData.setCount(windData.getCount()+1);
                        topLeft = true;
                    }
                    if ((current + data.dimx) < data.dimx * data.dimy) {
                        //add bottom left
                        windData.setX(windData.getX() + data.linearAdvection[xComponent - 3 + data.dimx * 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent - 3 + data.dimx * 3]);
                        windData.setCount(windData.getCount()+1);
                        bottomLeft = true;
                    }
                }
                if (current >= data.dimx) {
                    //add above
                    windData.setX(windData.getX() + data.linearAdvection[xComponent - data.dimx*3]);
                    windData.setY(windData.getY() + data.linearAdvection[yComponent - data.dimx*3]);
                    windData.setCount(windData.getCount()+1);
                    if (current % data.dimx != 0 && topLeft == false) {
                        //add above left
                        windData.setX(windData.getX() + data.linearAdvection[xComponent - data.dimx*3 - 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent - data.dimx*3 - 3]);
                        windData.setCount(windData.getCount()+1);
                    }
                    if ((current + 1) % data.dimx != 0) {
                        //add above right
                        windData.setX(windData.getX() + data.linearAdvection[xComponent - data.dimx*3 + 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent - data.dimx*3 + 3]);
                        windData.setCount(windData.getCount()+1);
                        topRight = true;
                    }
                }
                if ((current + 1) % data.dimx != 0) {
                    //add right
                    windData.setX(windData.getX() + data.linearAdvection[xComponent + 3]);
                    windData.setY(windData.getY() + data.linearAdvection[yComponent + 3]);
                    windData.setCount(windData.getCount()+1);
                    if ((current + data.dimx) < data.dimx * data.dimy) {
                        //add bottom right
                        windData.setX(windData.getX() + data.linearAdvection[xComponent + 3 + data.dimx * 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent + 3 + data.dimx * 3]);
                        windData.setCount(windData.getCount()+1);
                        bottomRight = true;
                    }
                    if (current >= data.dimx && topRight == false) {
                        //add above right
                        windData.setX(windData.getX() + data.linearAdvection[xComponent - data.dimx*3 + 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent - data.dimx*3 + 3]);
                        windData.setCount(windData.getCount()+1);
                    }
                }
                if ((current + data.dimx) < data.dimx * data.dimy) {
                    //add bottom
                    windData.setX(windData.getX() + data.linearAdvection[xComponent + data.dimx * 3]);
                    windData.setY(windData.getY() + data.linearAdvection[yComponent + data.dimx * 3]);
                    windData.setCount(windData.getCount()+1);
                    if (current % data.dimx != 0 && bottomLeft == false) {
                        //add bottom left
                        windData.setX(windData.getX() + data.linearAdvection[xComponent + data.dimx * 3 - 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent + data.dimx * 3 - 3]);
                        windData.setCount(windData.getCount()+1);
                    }
                    if ((current + 1) % data.dimx != 0 && bottomRight == false) {
                        //add bottom right
                        windData.setX(windData.getX() + data.linearAdvection[xComponent + data.dimx * 3 + 3]);
                        windData.setY(windData.getY() + data.linearAdvection[yComponent + data.dimx * 3 + 3]);
                        windData.setCount(windData.getCount()+1);
                    }
                }
                current += 1;
                if (current == data.dim()/data.dimt) {
                    current = 0;
                }

                average = windData.getAverage();

                if (Math.abs(data.linearAdvection[uComponent]) > average.getMagnitude()) {
                    data.linearClassification[count] = 0;
                } else if (average.getMagnitude() > 0.2) {
                    data.linearClassification[count] = 1;
                } else {
                    data.linearClassification[count] = 2;
                }
                count++;

                windData.setX(0);
                windData.setY(0);
                windData.setCount(0);

            }
            return new Resultant(data, velocity);
        }
        else {
            while (middle % 3 != 0) {
                middle++;
            }
            Cloud left = new Cloud(data,lo,middle);
            Cloud right= new Cloud(data,middle,hi);

            left.fork();
            Resultant rightAns = right.compute();
            Resultant leftAns  = left.join();
            Vector windData = leftAns.velocity.combine(rightAns.velocity);
            int begin = middle / 3;
            for (int i = middle; i < hi; i += 3) {
                leftAns.data.linearClassification[begin] = rightAns.data.linearClassification[begin];
                begin++;
            }
            leftAns.velocity = windData;
            return leftAns;
        }
    }

}
