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
				result += matrix.get(0, i) * Math.pow(-1, 1 + i + 1) * calculateDet(matrix, i, 0);
			}
		}
		
		return result;
	}
	
	public double calculateDet(Matrix matrix, int start_i, int start_j) {
		double result = 0;
		int n = matrix.getDimension();
		//2x2 matrix
		if((n - start_i) == 2) {
			result = matrix.get(start_i, start_j) * matrix.get(start_i + 1, start_j + 1) - 
					 matrix.get(start_i, start_j + 1) * matrix.get(start_i + 1, start_j);
		}
		//NxN
		else {
			for(int i = 0; i < n; ++i) {
				Matrix subMatrix = matrix.generateSubmatrix(start_i, start_j);
				result += matrix.get(i, 0) * Math.pow(-1, 1 + i + 1) * calculateDet(subMatrix, start_i, start_j);
			}
		}
		
		return result;
	}
}