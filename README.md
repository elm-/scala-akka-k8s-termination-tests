# scala-akka-k8s-termination-tests #

Some simple tests that verify how termination works in the combination of scala, akka and Kubernetes.

## Run It ##

The k8s directory contains all the necessary descriptors.


## Shutdown Order ##

On a proper Kubernetes shutdown the following is happening (as expected, see code for details):

```
Shutdown from sys hook
Sleeping for 15000ms as per config
[INFO] [10/16/2017 15:46:26.533] [Thread-0] [CoordinatedShutdown(akka://test)] Starting coordinated shutdown from JVM shutdown hook
Shutdown from actor system
Triggering shutdown of actor system with 10s timeout
Finished clean shut down
```