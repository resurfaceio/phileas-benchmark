# phileas-benchmark
Benchmark tests for Phileas PII engine

This command-line program runs a series of benchmarks using [Phileas](https://github.com/philterd/phileas)
to redact PII tokens in strings of varying sizes.

## Dependencies

* Java 17+
* [philterd/phileas](https://github.com/philterd/phileas) 

## Running Locally

```
mvn clean test package

# run standard series
bash benchmarks.sh

# run specific test
java -server -Xmx512M -XX:+AlwaysPreTouch -XX:PerBytecodeRecompilationCutoff=10000 -XX:PerMethodRecompilationCutoff=10000 -jar target/main-jar-with-dependencies.jar gettysberg_address mask_credit_cards 1

# run with minimal dependencies
java -server -Xmx512M -cp "lib/*:target/phileas-benchmark-0.0.1.jar" ai.philterd.phileas.benchmark.Main <document> <redactor> <iterations>
```

### Available documents

* hello_world (11 chars)
* gettysberg_address (1474 chars)
* i_have_a_dream (7727 chars)

### Available redactors

* mask_all
* mask_credit_cards
* mask_email_addresses
* skip_all
