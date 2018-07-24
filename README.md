# LogAnalysis

Java based anomaly detection framework finding outliers within log files 

## Abstract
Today's software produces huge amounts of real time data logged in files containing highly 
valuable information when monitoring a systems health or tracing the causes of faults and bugs. 
In order to have an early warning system in place which helps in the prevention of system failures, critical events in the 
continuous stream of log messages need to be detected the moment they occur. 

This project is about creating a framework to enable easy setup and maintenance of such detection systems, 
based on the log4j as wells as slf4j logging frameworks. The proposed solution makes use of different machine 
learning techniques and can automatically cluster and analyse log files at runtime as new logging events occur.

## Acknowledgements

The whole framework was heavily inspired by the bachelor thesis of Tim Zwietasch on [Detecting Anomalies in System Log Files using Machine Learning Techniques](https://elib.uni-stuttgart.de/handle/11682/3471).

The clustering algorithm implemented within this framework is based on the paper 
[SOStream: self organizing density-based clustering over data stream](https://dl.acm.org/citation.cfm?id=2358881)

## Overview

The whole framework consists of an easily extensible and highly efficient pipeline that shall be open for future 
improvements and adaptions by third parties if needed. Below each of the components making up the core foundation 
of the project are described in further detail.

![Log Analysis Pipeline](https://github.com/LGrege/LogAnalysis/blob/master/documentation/images/overview.png?raw=true "Title")

This repository contains two independent modules listed below:

### [Anomaly Detector](anomalyDetector)

This model contains the whole implementation of the framework used within the sampleApplication module.


### [Sample Application](sampleApplication)

Demo model showing how to use the anomalyDetector framework