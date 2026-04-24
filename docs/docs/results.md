# Benchmark Results

Phileas Benchmark can output results in two formats: a human-readable console output (CSV-like) and JSON.

## Default Console Output

The default output format is designed for quick reading and can be easily copied into CSV-compatible tools.

Example output:
```text
Using document: gettysberg_address
Using redactor: mask_all
Using workload_millis: 1000

string_length,calls_per_sec
0,25300
1,25100
2,24800
...
```

The `string_length` indicates the length of the string being redacted, and `calls_per_sec` provides the throughput for that specific string size.

## JSON Output

By adding `json` as the final argument, the benchmark will output a single JSON object per document processed.

Example JSON output:
```json
{
  "phileas_version": "2.13.0-SNAPSHOT",
  "document": "gettysberg_address",
  "redactor": "mask_all",
  "workload_millis": 1000,
  "calls_per_sec": {
    "0": 25300,
    "1": 25100,
    "2": 24800
  },
  "run_id": "550e8400-e29b-41d4-a716-446655440000",
  "timestamp": 1713961200000
}
```

The JSON output includes additional metadata:

* `phileas_version`: The version of the Phileas engine used.
* `run_id`: A unique UUID for the benchmark run.
* `timestamp`: Epoch milliseconds when the benchmark was executed.

## Indexing Results

The benchmark results can be indexed into OpenSearch or Elasticsearch for visualization and long-term tracking.

### Using OpenSearch

A `docker-compose.yaml` is provided to spin up an OpenSearch and OpenSearch Dashboards instance:

```bash
docker-compose up -d
```

### Automated Run and Index

The `run-and-index.sh` script automates the process of running benchmarks across all documents and indexing the resulting JSON into a local OpenSearch instance.

```bash
./run-and-index.sh
```

This script will:
1. Run the benchmark for all documents with `mask_all` and 100ms workload.
2. Create (or recreate) the `phileas_benchmarks` index with appropriate mappings.
3. Bulk upload the results to OpenSearch.

Once indexed, you can access OpenSearch Dashboards at `http://localhost:5601` to visualize the performance data.
