# phileas-benchmark
Benchmark tests for Phileas PII engine

This command-line utility runs a series of single-threaded workloads using [Phileas](https://github.com/philterd/phileas)
to redact PII tokens in strings of varying sizes. Workloads can be run multiple times to warm up the JVM or test long-term use.
Workloads run for a fixed amount of time rather than a fixed number of iterations.

[![CodeFactor](https://www.codefactor.io/repository/github/resurfaceio/phileas-benchmark/badge)](https://www.codefactor.io/repository/github/resurfaceio/phileas-benchmark)

## Dependencies

* Java 17+
* [philterd/phileas](https://github.com/philterd/phileas) 

## Running Locally

```
mvn clean test package

# run workloads across all documents
java -server -Xmx512M -XX:+AlwaysPreTouch -XX:PerBytecodeRecompilationCutoff=10000 -XX:PerMethodRecompilationCutoff=10000 -jar target/main-jar-with-dependencies.jar all mask_all 1 15000

# run workloads for specific document
java -server -Xmx512M -XX:+AlwaysPreTouch -XX:PerBytecodeRecompilationCutoff=10000 -XX:PerMethodRecompilationCutoff=10000 -jar target/main-jar-with-dependencies.jar gettysberg_address mask_credit_cards 1 1000

# run workloads with minimal or alternate dependencies
java -server -Xmx512M -XX:+AlwaysPreTouch -XX:PerBytecodeRecompilationCutoff=10000 -XX:PerMethodRecompilationCutoff=10000 -cp "lib/*:target/phileas-benchmark-0.0.1.jar" ai.philterd.phileas.benchmark.Main <document> <redactor> <repetitions> <workload_millis>
```

### Available documents

* hello_world (11 chars)
* gettysberg_address (1474 chars)
* i_have_a_dream (7727 chars)

### Available redactors

For testing single identifiers:
* mask_bank_routing_numbers
* mask_bitcoin_addresses
* mask_credit_cards
* mask_drivers_licenses
* mask_email_addresses
* mask_iban_codes
* mask_passport_numbers
* mask_phone_numbers
* mask_ssns

For testing multiple identifiers:
* mask_all (the identifiers listed above 👆)
* mask_default (mask_fastest + email addresses)
* mask_fastest (bank routing numbers, bitcoin addresses, credit cards, IBAN codes, phone numbers, ssns)
* mask_none (no identifiers masked)

Copyright 2024 Philterd, LLC @ https://www.philterd.ai