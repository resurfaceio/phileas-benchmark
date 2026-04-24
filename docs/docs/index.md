# Phileas Benchmark

Benchmark tests for [Phileas](https://github.com/philterd/phileas) PII engine.

This command-line utility runs a series of single-threaded workloads using Phileas to redact PII tokens in strings of varying sizes. Workloads can be run multiple times to warm up the JVM or test long-term use.

Workloads run for a fixed amount of time rather than a fixed number of iterations, allowing for consistent performance measurement across different environments.

## Key Features

* Benchmark various PII redaction scenarios.
* Support for multiple document types and sizes.
* Configurable workload duration and repetitions.
* Output results in CSV or JSON format.
