import dto.Matrix;

public class Main {
	public static void main(String[] args) {
		Options options = new Options();
        options.addOption("n", true, "set number of graph vertices");
        options.addOption("i", true, "set file containing graph");
        options.addOption("t", true, "set maximum number of threads");
        options.addOption("q", false, "run program in quiet mode");
        options.addOption("o", true, "set output file");

        CommandLineParser parser = new DefaultParser();
        Matrix matrix = null;

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("n")) {
                int vertices = Integer.parseInt(cmd.getOptionValue("n"));
                graph = Graph.generateRandomGraph(vertices);
            } else if (cmd.hasOption("i")) {
                String file = cmd.getOptionValue("i");
                graph = Graph.createGraphFromFile(file);
            }
            if (cmd.hasOption("t")) {
                int numThreads = Integer.parseInt(cmd.getOptionValue("t"));
                long startTime = System.currentTimeMillis();
                if (cmd.hasOption("q")) {
                    runBFS(true, numThreads, graph);
                } else {
                    runBFS(false, numThreads, graph);
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
}
