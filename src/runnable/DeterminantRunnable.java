package runnable;

import com.sun.security.auth.NTDomainPrincipal;

import dto.Matrix;
public abstract class DeterminantRunnable {
	protected final Matrix matrix;
	protected final Boolean[] firstLine;
	
	public DeterminantRunnable(Matrix matrix, Boolean[] firstLine) {
		this.matrix = matrix;
		this.firstLine = firstLine;
	}
	
	public double calculate() {
		int n = matrix.getDimension();
		double result = 0;
		for(int i = 0; i < n; ++i) {
			if(firstLine[i]) {
				result += matrix.get(0, i) * Math.pow(-1, i + 1) * calculateDet(matrix);
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
