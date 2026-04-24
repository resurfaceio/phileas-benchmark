# Infrastructure

Phileas Benchmark includes integrated support for performance data visualization using OpenSearch and OpenSearch Dashboards.

## OpenSearch Stack

The provided `docker-compose.yaml` defines a local environment for storing and analyzing benchmark results.

### Services

* **OpenSearch**: A distributed search and analytics engine where benchmark results are stored.
    * Port: `9200` (API), `9600` (Performance Analyzer)
    * Authentication: Disabled for local testing (default admin password is `SuperSecretPassword_123`).
* **OpenSearch Dashboards**: A visualization tool to create charts and dashboards from the data in OpenSearch.
    * Port: `5601`

### Running the Stack

To start the infrastructure:

```bash
docker-compose up -d
```

To stop the services:

```bash
docker-compose down
```

## Data Lifecycle

1. **Generation**: Run the benchmark with the `json` output format.
2. **Indexing**: Use `curl` or the provided `run-and-index.sh` to send the JSON data to the OpenSearch Bulk API.
3. **Visualization**: Create an index pattern for `phileas_benchmarks` in OpenSearch Dashboards to begin building visualizations.

## Mappings

The `phileas_benchmarks` index uses specific mappings for optimized querying:

* `timestamp`: Date (epoch_millis)
* `run_id`: Keyword
* `phileas_version`: Keyword
* `document`: Keyword
* `redactor`: Keyword
* `workload_millis`: Long
* `calls_per_sec`: Nested object containing throughput for various string lengths.
