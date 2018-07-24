# Anomaly Detection Module

This model contains the whole implementation of the framework used within the [sampleApplication](../sampleApplication) module.

## Overview

The whole framework consists of an easily extensible and highly efficient pipeline that shall be open for future 
improvements and adaptions by third parties if needed. Below each of the components making up the core foundation 
of the project are described in further detail.

![Log Analysis Pipeline](https://github.com/LGrege/LogAnalysis/blob/master/documentation/images/overview.png?raw=true "Title")

### 1. Input Parsing

In the first step of the pipeline new events need to be read in as they occur. To do so this framework makes use of 
Apaches Log4J utilities LogFilePatternReceiver, which parses new lines within the log file.

### 2. Numerical Representation

After a new logging event has been read, the next step in making it usable for the following steps is to extract a 
numerical representation (feature vector) out of the log event. This step heavily influences the overall quality of the 
detection algorithm as all following computations are based on the produced vector.

The following two approaches are used to cooperatively create the representation:

- The most important, most distinct elements of a log line are hashed and used as feature values within the resulting 
feature vector. These elements are the log level, the thread name as well as the associated logger.
- The actual log message is transformed using the TF/IDF transformation (Term frequency / inverse document frequency) 
using a sliding window data structure to speed up the calculations.

The result of this step is a numerical vector per logging event which can be used to cluster and further refactor the 
results before using it during the anomaly detection step.

### 3. Clustering

To further simplify the data provided to the anomaly detection algorithm, the log events now represented by a numerical 
vector need to be clustered. The resulting clusters are then used to find outliers within them. 

As log events occur during runtime, the used clustering algorithm needed to be online as well, allowing the insertion 
of new points into the cluster on the go. The clustering algorithm chosen is [SOStream](https://dl.acm.org/citation.cfm?id=2358881),
which is based on self organizing clusters which adapt as new logging events are inserted into them.

### 4. Anomaly Detection

Outliers are detected per cluster by calculating the local- as well as global-outlier score for each logging 
event within the cluster. The global outlier score is defined by the inverse probability that new points get 
assigned to the current cluster, while the local outlier score is the distance of a point to the clusters centre.