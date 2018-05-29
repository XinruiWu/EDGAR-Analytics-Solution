public class ListNode {

    //use ListNode to construct a double-linked list to output sessions in first request time order
    //each ListNode contain one session's information, the session's timestamp and the bucket number of the bucket it belongs

    private LogRecord logRecord;
    public ListNode pre, nxt;
    private long bucketNum;
    private long timeStamp;

    public ListNode (LogRecord logRecord, long timeStamp) {
        this.logRecord = logRecord;
        this.pre = null;
        this.nxt = null;
        this.timeStamp = timeStamp;
    }

    public void setBucketNum(long bucketNum) {
        this.bucketNum = bucketNum;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public LogRecord getLogRecord() {
        return logRecord;
    }

    public void remove() {
        this.pre.nxt = this.nxt;
        this.nxt.pre = this.pre;
    }

    public void addToEnd(ListNode tail) {
        this.pre = tail.pre;
        this.nxt = tail;
        this.pre.nxt = this;
        this.nxt.pre = this;
    }

    public long getBucketNum() {
        return bucketNum;
    }
}
