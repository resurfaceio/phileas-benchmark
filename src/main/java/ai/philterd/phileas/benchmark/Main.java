package ai.philterd.phileas.benchmark;

import java.util.List;

/**
 * Run benchmark workloads for Phileas PII engine.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // show usage statement if needed
        if (args.length != 4) {
            System.out.println("Usage: java ai.philterd.phileas.benchmark.Main <document> <redactor> <repetitions> <workload_millis>");
            throw new IllegalArgumentException("Invalid arguments");
        }

        // read arguments
        String arg_document = args[0];
        String arg_redactor = args[1];
        int repetitions = Integer.parseInt(args[2]);
        int workload_millis = Integer.parseInt(args[3]);

        // create redactor based on Phileas PII engine
        Redactor redactor = new Redactor(arg_redactor);

        // repeatedly redact documents and print results
        List<String> documents = "all".equals(arg_document) ? Documents.keys : List.of(arg_document);
        int[] value_lengths = {0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 768, 1024, 1280, 1536, 1792, 2048, 3072, 4096};
        for (int i = 0; i < repetitions; i++) {
            for (String document : documents) {
                try {
                    System.out.println("\n------------------------------------------------------------------------------------------");
                    System.out.println("Using document: " + document);
                    System.out.println("Using redactor: " + arg_redactor);
                    System.out.println("Using workload_millis: " + workload_millis);
                    System.out.println("\nstring_length,calls_per_sec");
                    for (int value_length : value_lengths) {
                        String value = Documents.get(document).substring(0, value_length);
                        long start = System.currentTimeMillis();
                        long calls = -1;
                        while ((++calls % 100 != 0) || (System.currentTimeMillis() - start < workload_millis)) redactor.filter(value);
                        long calls_per_sec = calls * 1000 / (System.currentTimeMillis() - start);
                        System.out.println(value.length() + "," + calls_per_sec);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // do nothing, ignore
                }
            }
        }
    }

}
