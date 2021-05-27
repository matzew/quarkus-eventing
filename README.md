# quarkus-eventing

Contains three components

* [A Kafka Producer](./kafka-quickstart), that is _hardcoded_ to `my-cluster-kafka-bootstrap.kafka.svc:9092` for Strimzi / Apache Kafka
* [A WebServer](./getting-started), that is a simple webserver dumping the `HttpHeaders` to the console.
* [Kubernetes gluecode](./k8s), which contains a deployment for the _Kafka Producer_ and setup for Knative.



## Running on Knative

1. Install Knative, using [kn-box](https://github.com/matzew/kn-box)
2. Install the deployment:
```
k apply -f 000-kafka-producer.yaml
```
3. Install the Webserver as a Knative Serving Service
```
k apply -f 010-webserver.yaml
```
4. Install the Source, that reads from the topic which is populated by the `PRODUCER` and redirects the native Kafka Records as CloudEvents to the WebServer:
```
k apply -f 020-kafka-source.yml
```


## Inspect some logs

Once all is running, see the logs on the Webserver, like:

```
➜  kafkacat k logs -f -l serving.knative.dev/service=webserver-display -c user-container 
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2021-05-27 06:22:21,672 INFO  [io.quarkus] (main) getting-started 1.0.0-SNAPSHOT native (powered by Quarkus 1.13.5.Final) started in 0.013s. Listening on: http://0.0.0.0:8080
2021-05-27 06:22:21,672 INFO  [io.quarkus] (main) Profile prod activated. 
2021-05-27 06:22:21,672 INFO  [io.quarkus] (main) Installed features: [cdi, resteasy]
Receving some payload....
Accept-Encoding:gzip
Ce-Id:partition:0/offset:38
Ce-Source:/apis/v1/namespaces/default/kafkasources/kafka-source#prices
Ce-Specversion:1.0
Ce-Subject:partition:0#38
Ce-Time:2021-05-27T06:23:27.801Z
Ce-Type:dev.knative.kafka.event
Content-Length:4
Forwarded:for=172.17.0.14;proto=http
Host:webserver-display.default.svc.cluster.local
K-Proxy-Request:activator
Traceparent:00-608a86d521b54ad9b1e3dff00c12d1b5-1d7267382c8d253a-00
User-Agent:Go-http-client/1.1
X-Forwarded-For:172.17.0.14, 172.17.0.7
X-Forwarded-Proto:http
X-Request-Id:ac014ebe-1410-4ca4-8dd6-4345919e3441
```

