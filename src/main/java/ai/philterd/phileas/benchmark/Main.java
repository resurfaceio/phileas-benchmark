package ai.philterd.phileas.benchmark;

/**
 * Execute benchmarks for Phileas PII engine.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("\n------------------------------------------------------------------------------------------");

        // show usage statement if needed
        if (args.length != 3) {
            System.out.println("Usage: java ai.philterd.phileas.benchmark.Main <document> <redactor> <iterations>");
            throw new IllegalArgumentException("Invalid arguments");
        }

        // read arguments
        String arg_document = args[0];
        String arg_redactor = args[1];
        int arg_iterations = Integer.parseInt(args[2]);
        System.out.println("Using document: " + arg_document);
        System.out.println("Using redactor: " + arg_redactor);
        System.out.println("Using iterations: " + arg_iterations + "\n");

        // run benchmarks against document using redactor
        String document = Documents.get(arg_document);
        Redactor r = new Redactor(arg_redactor);
        for (int i = 0; i < arg_iterations; i++) {
            try {
                System.out.println("\nstring_length,iterations,elapsed_millis,throughput");
                run(r, "", 4000000);
                run(r, document.substring(0, 1), 4000000);
                run(r, document.substring(0, 2), 4000000);
                run(r, document.substring(0, 4), 4000000);
                run(r, document.substring(0, 8), 2000000);
                run(r, document.substring(0, 16), 2000000);
                run(r, document.substring(0, 32), 1000000);
                run(r, document.substring(0, 64), 1000000);
                run(r, document.substring(0, 128), 400000);
                run(r, document.substring(0, 256), 200000);
                run(r, document.substring(0, 512), 100000);
                run(r, document.substring(0, 768), 100000);
                run(r, document.substring(0, 1024), 100000);
                run(r, document.substring(0, 1280), 100000);
                run(r, document.substring(0, 1536), 100000);
                run(r, document.substring(0, 1792), 100000);
                run(r, document.substring(0, 2048), 100000);
            } catch (StringIndexOutOfBoundsException e) {
                // do nothing, ignore
            }
        }
    }

    public static void run(Redactor redactor, String value, long iterations) throws Exception {
        long start = System.currentTimeMillis();
        for (long i = 0; i < iterations; i++) redactor.filter(value);
        long elapsed = System.currentTimeMillis() - start;
        long throughput = iterations / (elapsed + 1) * 1000;
        System.out.println(value.length() + "," + iterations + "," + elapsed + "," + throughput);
    }

}
