import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main (String[] args) {
        String logPath = "../input/log.csv";
        String periodPath = "../input/inactivity_period.txt";
        String sessionizationPath = "../output/sessionization.txt";
        // Use InputHandler Instance to read from files
        InputHandler inputHandler = new InputHandler(logPath, periodPath);
        // Use OutputHandler Instance to write to file
        OutputHandler outputHandler = new OutputHandler(sessionizationPath);
        Map<String, ListNode> aliveConn = new HashMap<>();
        //use buckets to store logs according to the time that each log's most recent activity happens
        TreeMap<Long, TreeSet<ListNode>> buckets = new TreeMap<>();
        //build a double-linked List in order to record logs to output file in first request order
        ListNode head = new ListNode(null, Long.MIN_VALUE);
        ListNode tail = new ListNode(null, Long.MIN_VALUE);
        head.nxt = tail;
        tail.pre = head;
        //assign a timestamp to each log in order to sort logs by first request time
        long timeStamp = Long.MIN_VALUE;
        int period = inputHandler.getPeriod();
        String logLine;
        //read each line of logs
        while ((logLine = inputHandler.getNewLog()) != null) {
            String[] logData = logLine.trim().split(",+");
            String ip = logData[0];
            String date = logData[1];
            String time = logData[2];
            try{
                long currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " " + time).getTime();
                //write expired sessions to output file
                while (!buckets.isEmpty() && currentTime - buckets.firstKey() > period * 1000) {
                    TreeSet<ListNode> expiredSet = buckets.remove(buckets.firstKey());
                    while (!expiredSet.isEmpty()) {
                        ListNode expiredNode = expiredSet.pollFirst();
                        outputHandler.writeToFile(expiredNode.getLogRecord());
                        aliveConn.remove(expiredNode.getLogRecord().getIp());
                        expiredNode.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //if the session is ongoing
            if (aliveConn.containsKey(ip)) {
                //update session's information
                ListNode currentConn = aliveConn.get(ip);
                currentConn.getLogRecord().newLog(date, time);
                long bucketNumber = currentConn.getBucketNum();
                long currentMilliseconds = currentConn.getLogRecord().getEndMilliseconds();
                //update session to corresponding bucket
                if (currentMilliseconds != currentConn.getBucketNum()) {
                    buckets.get(bucketNumber).remove(currentConn);
                    if (!buckets.containsKey(currentMilliseconds)){
                        buckets.put(currentMilliseconds, new TreeSet<>(Comparator.comparing(ListNode::getTimeStamp)));
                    }
                    buckets.get(currentMilliseconds).add(currentConn);
                    currentConn.setBucketNum(currentMilliseconds);
                }
            } else { // if the session is new
                //construct a new object to save the session's information
                LogRecord newRecord = new LogRecord(ip, date, time);
                ListNode newListNode = new ListNode(newRecord, timeStamp++);
                //put the object into the hashMap and the right bucket
                newListNode.setBucketNum(newListNode.getLogRecord().getEndMilliseconds());
                aliveConn.put(ip, newListNode);
                long bucketNumber = newListNode.getBucketNum();
                if (!buckets.containsKey(bucketNumber)){
                    buckets.put(bucketNumber, new TreeSet<>(Comparator.comparing(ListNode::getTimeStamp)));
                }
                buckets.get(bucketNumber).add(newListNode);
                newListNode.addToEnd(tail);
            }
        }
        //when reaches the end of the file, write all remain sessions into output file in first request order
        ListNode current = head.nxt;
        while (current != null && current.getLogRecord() != null){
            outputHandler.writeToFile(current.getLogRecord());
            current = current.nxt;
        }
        inputHandler.close();
        outputHandler.close();
    }
}
