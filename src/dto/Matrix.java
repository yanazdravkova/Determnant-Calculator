package dto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoublePredicate;

public class Matrix {
	private int dim;
	double[][] data;
	
	public Matrix(int dim) {
		this.dim = dim;
		this.data = new double[dim][dim];
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
