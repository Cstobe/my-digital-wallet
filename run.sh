#!/usr/bin/env bash

# build java programs
rm src/*.class
javac src/*.java

# execute my programs, with the input directory paymo_input and output the files in the directory paymo_output
java -cp src App ./paymo_input/batch_payment.csv ./paymo_input/stream_payment.csv ./paymo_output


### execute my programs, with the input directory paymo_input and output the files in the directory paymo_output, plus the expand option to grow the graph with new vertices and edges in stream_payment
# java -cp src App ./paymo_input/batch_payment.csv ./paymo_input/stream_payment.csv ./paymo_output True

