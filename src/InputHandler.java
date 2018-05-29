import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class InputHandler {

    private File logFile;
    private File periodFile;
    private FileInputStream logFileStream;
    private FileInputStream periodFileStream;
    private InputStreamReader logFileStreamReader;
    private InputStreamReader periodFileStreamReader;
    private BufferedReader logFileBufferedReader;
    private BufferedReader periodFileBufferedReader;
    private String logLine;
    private String period;

    public InputHandler (String logPath, String periodPath) {
        try {
            this.logFile = new File(logPath);
            this.periodFile = new File(periodPath);
            this.logFileStream = new FileInputStream(this.logFile);
            this.periodFileStream = new FileInputStream(this.periodFile);
            this.logFileStreamReader = new InputStreamReader(this.logFileStream);
            this.periodFileStreamReader = new InputStreamReader(this.periodFileStream);
            this.logFileBufferedReader = new BufferedReader(this.logFileStreamReader);
            this.periodFileBufferedReader = new BufferedReader(this.periodFileStreamReader);
            this.logLine = logFileBufferedReader.readLine();
            this.period = periodFileBufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNewLog() {
        if (this.logLine == null) {
            return null;
        } else {
            try {
                return this.logLine = this.logFileBufferedReader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public int getPeriod() {
        return Integer.parseInt(period.trim());
    }

    public void close() {
        try {
            this.periodFileBufferedReader.close();
            this.periodFileStreamReader.close();
            this.periodFileStream.close();
            this.logFileBufferedReader.close();
            this.logFileStreamReader.close();
            this.logFileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
