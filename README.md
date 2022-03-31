# usl-modeler

This little repository contains the Java code we put together in order to use [usl4j](https://github.com/codahale/usl4j) during the first Web Applications II laboratory.

## Usage

The program accepts the input filename and the output filename as arguments.

Using the {concurrency, throughput} pairs in the input file, it models a model that obeys to the [Universal Scalability Law](http://www.perfdynamics.com/Manifesto/USLscalability.html), and writes in the output file the predicted throughput at increasing concurrency levels.

A command line example is:

```bash
java -jar usl-modeler.jar <input-file> <output-file>
```
