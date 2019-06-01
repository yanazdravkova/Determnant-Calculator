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
		//System.out.println("calculate PRINT");
		//this.matrix.print();
		int n = matrix.getDimension();
		double result = 0;
		//System.out.println("in calculate before for loop: ");
		for(int i = 0; i < n; ++i) {
			
			if(firstLine[i]) {
				System.out.println("I: " + i);
				Matrix subMatrix = matrix.generateSubmatrix(0, i);
				
				//subMatrix.print();
				double toAdd = calculateDet(subMatrix);
				System.out.println("toAdd " + toAdd);
				System.out.println("matrix.get(0, i)  " + matrix.get(0, i) );
				System.out.println("Math.pow(-1, 1 + i + 1) " + Math.pow(-1, 1 + i + 1));
				result += matrix.get(0, i) * Math.pow(-1, 1 + i + 1) * calculateDet(subMatrix);
			}
		}
		
		return result;
	}
	
	public double calculateDet(Matrix matrix) {
		double result = 0;
		int n = matrix.getDimension();
		//2x2 matrix
		//System.out.println("n: " + n);
		if(n == 1) {
			result = matrix.get(0,0);
		}
		if(n == 2) {
			result = matrix.get(0, 0) * matrix.get(1, 1) - 
					 matrix.get(0, 1) * matrix.get(1, 0);
		}
		//NxN
		else {
			for(int i = 0; i < n; ++i) {
				Matrix subMatrix = matrix.generateSubmatrix(0, i);
				//System.out.println("start_i " + i);
				//System.out.println("dim of submatrix " + subMatrix.getDimension());
				result += matrix.get(i, 0) * Math.pow(-1, 1 + i + 1) * calculateDet(subMatrix);
			}
		}
		
		return result;
	}
}
