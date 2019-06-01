import dto.Matrix;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.*;

import callable.Silent;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello its me");
		Options options = new Options();
		options.addOption("n", true, "set matrix dimension");
		options.addOption("i", true, "set input file");
		options.addOption("t", true, "set maximum number of threads");
		options.addOption("q", false, "run program in quiet mode");
		options.addOption("o", true, "set output file");

		CommandLineParser parser = new DefaultParser();
		Matrix matrix = null;

		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("n")) {
				int dimension = Integer.parseInt(cmd.getOptionValue("n"));
				matrix = Matrix.generateRandomMatrix(dimension);
			} else if (cmd.hasOption("i")) {
				System.out.println("has i ");
				String fileName = cmd.getOptionValue("i");
				matrix = Matrix.generateMatrixFromFile(fileName);
				System.out.println("matrix read from file ");
				matrix.print();
			}
			if (cmd.hasOption("t")) {
				int numThreads = Integer.parseInt(cmd.getOptionValue("t"));
				long startTime = System.currentTimeMillis();
				if (cmd.hasOption("q")) {
					runDet(true, numThreads, matrix);
				} else {
					runDet(false, numThreads, matrix);
					System.out.println("Threads used in current run: " + numThreads);
				}
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Total execution time for current run (millis): " + totalTime);
				if (cmd.hasOption("o")) {
					String filename = cmd.getOptionValue("o");
					writeResultToFile(filename, totalTime);
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void runDet(boolean isQuiet, int numThreads, Matrix matrix) {
		System.out.println("RUN DET PRINT");
		//matrix.print();
		if (matrix != null) {
			ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

			List<Future<Double>> resultList = new ArrayList<>();
			int dim = matrix.getDimension();
			if (isQuiet) {
				for (int i = 0; i < numThreads; i++) {
					// System.out.println("I: " + i);

					boolean[] firstLine = new boolean[dim];

					for (int j = 0; j < dim; ++j) {
						 System.out.println("i: " + i + " j: " + j + " numThreads " + numThreads + 
								 " j%numThreads " + j%numThreads);
						if (j % numThreads == i) {
							firstLine[j] = true;
						}
					}

					for (int k = 0; k < dim; ++k) {
						 System.out.println("firstline " + k + " " + firstLine[k]);
					}

					Silent worker = new Silent(matrix, firstLine, i);

					Future<Double> result = executor.submit(worker);
					//System.out.println("result " + result);
					resultList.add(result);
				}
				double determinant = 0;
				//System.out.println("resultList size " + resultList.size());
				for (Future<Double> future : resultList) {
					try {
						System.out
								.println("Future result is: " + future.get() + "; And Task done is " + future.isDone());
						determinant += future.get();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("DET: " + determinant);
				// shut down the executor service now
				executor.shutdown();
			}
			executor.shutdown();

			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException е) {
				System.out.println();
			}
		}
	}

	private static void writeResultToFile(String filename, long time) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
			writer.write(String.format("Total execution time for current run(millis): %d", time));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
