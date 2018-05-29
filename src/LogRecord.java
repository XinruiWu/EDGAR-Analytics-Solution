import java.text.SimpleDateFormat;

public class LogRecord {

    private String ip;
    private String startDate; //the date that the session is initialed
    private String startTime; //the time that the session is initialed
    private String endDate; //the last date that the session is active
    private String endTime; //the last time that the session is active
    private long startMilliseconds; //the milliseconds that the session is initialed
    private long endMilliseconds; //the last milliseconds that the session is active
    private long pageCount; //number of pages the session has requested

    public LogRecord (String ip, String date, String time) {
        try{
            this.ip = ip;
            this.startDate = date;
            this.endDate = date;
            this.startTime = time;
            this.endTime = time;
            this.startMilliseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " " + time).getTime();
            this.endMilliseconds = this.startMilliseconds;
            this.pageCount = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newLog (String date, String time) {
        try{
            this.endDate = date;
            this.endTime = time;
            this.endMilliseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " " + time).getTime();
            this.pageCount++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getEndMilliseconds() {
        return endMilliseconds;
    }

    public String getRecord() {
        long duration = (this.endMilliseconds - this.startMilliseconds) / 1000 + 1;
        return this.ip + ',' + this.startDate + ' ' + this.startTime + ',' + this.endDate + ' ' + this.endTime + ',' + duration + ',' + this.pageCount;
    }

    public String getIp() {
        return ip;
    }
}
