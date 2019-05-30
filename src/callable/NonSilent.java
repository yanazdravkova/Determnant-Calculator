package callable;
import java.util.concurrent.Callable;

import dto.Matrix;

public class NonSilent extends DeterminantCallable implements Callable<Double> {
    public NonSilent(Matrix matrix, boolean[] firstLine, int thread_id) {
        super(matrix, firstLine, thread_id);
    }

    @Override
    public Double call() {
        System.out.println("Thread-" + this.thread_id + " started.");
        
        long start = System.currentTimeMillis();
    	double result = calculate();
        long end = System.currentTimeMillis();
        long time = end - start;
        
        System.out.println("Thread-" + this.thread_id + " stopped.");
        System.out.println("Thread-" + this.thread_id + " execution time was (millis):" + time);
        
        return result;
    }
}
