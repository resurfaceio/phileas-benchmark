# Usage Guide

## Prerequisites

* Java 21
* Maven 3.9.x

## Installation

Build the project using Maven to create the executable JAR:

```bash
mvn clean package
```

This will generate `target/phileas-benchmark-cmd-jar-with-dependencies.jar`.

## Running Benchmarks

The general syntax for running a benchmark is:

```bash
java -server -Xmx512M -jar target/phileas-benchmark-cmd-jar-with-dependencies.jar <document> <redactor> <repetitions> <workload_millis> [output_format]
```

### Recommended JVM Flags

For consistent performance results, especially in production-like environments, it is recommended to use the following JVM flags:

* `-server`: Use the Java HotSpot Server VM.
* `-Xmx512M`: Set the maximum heap size.
* `-XX:+AlwaysPreTouch`: Pre-touch memory pages during JVM startup.
* `-XX:PerBytecodeRecompilationCutoff=10000`: Increase the threshold for bytecode recompilation.
* `-XX:PerMethodRecompilationCutoff=10000`: Increase the threshold for method recompilation.

Example with optimized flags:

```bash
java -server -Xmx512M -XX:+AlwaysPreTouch -XX:PerBytecodeRecompilationCutoff=10000 -XX:PerMethodRecompilationCutoff=10000 -jar target/phileas-benchmark-cmd-jar-with-dependencies.jar all mask_all 1 15000
```

### Parameters

* `document`: The document to use for the benchmark. Use `all` to run against all available documents or specify a specific document name (e.g., `gettysberg_address`).
* `redactor`: The redactor configuration to use (e.g., `mask_all`, `mask_credit_cards`).
* `repetitions`: Number of times to repeat the workload.
* `workload_millis`: Duration of each workload in milliseconds.
* `output_format`: (Optional) Set to `json` for JSON output. Defaults to CSV-like console output.

### Examples

**Run workloads across all documents:**

```bash
java -server -Xmx512M -jar target/phileas-benchmark-cmd-jar-with-dependencies.jar all mask_all 1 15000
```

**Run workloads for a specific document with JSON output:**

```bash
java -server -Xmx512M -jar target/phileas-benchmark-cmd-jar-with-dependencies.jar gettysberg_address mask_credit_cards 1 1000 json
```

## Available Documents

* `hello_world` (11 chars)
* `gettysberg_address` (1474 chars)
* `i_have_a_dream` (7727 chars)

## Available Redactors

### Single Identifiers
* `mask_bank_routing_numbers`
* `mask_bitcoin_addresses`
* `mask_credit_cards`
* `mask_drivers_licenses`
* `mask_email_addresses`
* `mask_iban_codes`
* `mask_ip_addresses`
* `mask_passport_numbers`
* `mask_phone_numbers`
* `mask_ssns`
* `mask_tracking_numbers`
* `mask_vehicle_numbers`

### Multiple Identifiers
* `mask_all`: All identifiers listed above.
* `mask_fastest`: Bank routing numbers, bitcoin addresses, credit cards, email addresses, IBAN codes, phone numbers, SSNs.
* `mask_none`: No redaction.
