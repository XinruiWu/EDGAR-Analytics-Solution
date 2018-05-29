
# EDGAR-Analytics Challenge Solution

##  How to run:
Please go to the top-most directory of the repo and run `run.sh` and find the output file in the `output` folder

## Approach
### Data Structure
This solution uses one HashMap to keep track alive sessions, one TreeMap to divide alive sessions into several buckets for expiring purpose, and one double-linked list to expire sessions in first request time order when reaches the end of input file.
### Algorithm
Each log is stored in an instance of Node class. when a new line of log is read, the code will firstly check the buckets to expire sessions that did not send request during the inactive-period, then it will try to distinguish if the log is alive currently. If it is, it will be wrapped into a new instance of Node and insert into the three data structures; otherwise, the code will get the instance from the HashMap and update its information then put it into corresponding bucket. When the end of input file is meet, the code will write alive sessions in the same order as how they present in the double-linked list, from head to tail.