package callable;
import java.util.concurrent.Callable;

import dto.Matrix;

public class Silent extends DeterminantCallable implements Callable<Double>  {
    public Silent(Matrix matrix, boolean[] firstLine, int thread_id) {
        super(matrix, firstLine, thread_id);
    }
    
    @Override
    public Double call() {
    	double result = calculate();
        return result;
    }
}
