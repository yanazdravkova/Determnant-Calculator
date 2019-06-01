package dto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Matrix {
	private int dim;
	double[][] data;
	
	public Matrix(int dim) {
		this.dim = dim;
		this.data = new double[dim][dim];
	}
	
	public Matrix(int dim, double[][] data) {
		if(data.length == dim) {
			this.dim = dim;
			this.data = data;			
		}
		else {
			throw new ExceptionInInitializerError("Unable to construct matrix with different size and dimension.");
		}
	}
	
	public int getDimension() {
		return dim;
	}
	
	public double get(int i, int j) {
		return data[i][j];
	}
	
	public Matrix generateSubmatrix(int start_i, int start_j) {
		int submatrixDim = this.dim - 1;
		double m[][] = new double[submatrixDim][submatrixDim];
		
		for(int i = 1; i < dim; i++) {
			int k = 0;
			for(int j = 0; j < dim; j++)
			{
				if(j == start_j) {
					continue;
				}
				else {
					m[i-1][k] = this.data[i][j];
					k++;
				}
			}
		}
		
		for(int i = 0; i < dim-1; i++ ) {
			for (int j = 0; j < dim-1; j++) {
				System.out.print(" m[" + i + "][" + j + "] " + m[i][j] );
			}
			System.out.println("*");
		}
		return new Matrix(submatrixDim, m);
	}
	
	public void print() {
		int n = this.dim;
		System.out.println("***************************");
		for(int i = 0; i < n; i++ ) {
			for (int j = 0; j < n; j++) {
				System.out.print(this.get(i, j) + " ");
			}
			System.out.println();
		}
		System.out.println("***************************");
	}
	public static Matrix generateRandomMatrix(int dim) {
		Matrix matrix = new Matrix(dim);
		for(int i = 0; i < dim; ++i) {
			for(int j = 0; j< dim; ++j) {
				matrix.data[i][j] = ThreadLocalRandom.current().nextDouble();
			}
		}
		return matrix;
	}
	
	public static Matrix generateMatrixFromFile(String fileName) {
		Matrix matrix = null;
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			int dim = Integer.parseInt(reader.readLine());
			double [][] data = new double[dim][dim];
			String line = null;
			
			for(int i = 0; i < dim; ++i) {
				if((line = reader.readLine()) != null) {
					String[] values = line.split(" ");
					for(int j = 0; j < dim; ++j) {
						data[i][j] = Double.parseDouble(values[j]);
					}
				}
			}
			
			matrix = new Matrix(dim);
			matrix.data = data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
	}
}
