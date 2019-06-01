package callable;

import dto.Matrix;
public abstract class DeterminantCallable {
	protected final Matrix matrix;
	protected final boolean[] firstLine;
	protected final int thread_id;
	
	public DeterminantCallable(Matrix matrix, boolean[] firstLine, int thread_id) {
		this.matrix = matrix;
		this.firstLine = firstLine;
		this.thread_id = thread_id;
	}
	
	public double calculate() {
		int n = matrix.getDimension();
		double result = 0;
		
		for(int i = 0; i < n; ++i) {			
			if(firstLine[i]) {
				Matrix subMatrix = matrix.generateSubmatrix(0, i);
				result += matrix.get(0, i) * Math.pow(-1, 1 + i + 1) * calculateDet(subMatrix);
			}
		}
		
		return result;
	}
	
	public double calculateDet(Matrix matrix) {
		double result = 0;
		int n = matrix.getDimension();
		
		if(n == 1) {
			result = matrix.get(0,0);
		}
		else if(n == 2) {
			result = matrix.get(0, 0) * matrix.get(1, 1) - 
					 matrix.get(0, 1) * matrix.get(1, 0);
		}
		//NxN
		else {
			for(int i = 0; i < n; ++i) {
				Matrix subMatrix = matrix.generateSubmatrix(0, i);
				result += matrix.get(i, 0) * Math.pow(-1, 1 + i + 1) * calculateDet(subMatrix);
			}
		}
		
		return result;
	}
}
