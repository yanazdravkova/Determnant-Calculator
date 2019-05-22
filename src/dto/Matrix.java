package dto;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Matrix {
	private short dim;
	double[][] data;
	
	public Matrix(short dim) {
		this.dim = dim;
		this.data = new double[dim][dim];
	}
	
	public static Matrix generateRandomMatrix(short dim) {
		Matrix matrix = new Matrix(dim);
		for(int i = 0; i < dim; ++i) {
			for(int j = 0; j< dim; ++j) {
				matrix.data[i][j] = ThreadLocalRandom.current().nextDouble();
			}
		}
		return matrix;
	}
}
