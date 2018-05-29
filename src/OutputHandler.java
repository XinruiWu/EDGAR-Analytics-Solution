import java.io.*;

public class OutputHandler {

    private File sessionizationFile;
    private PrintWriter sessionizationWriter;

    public OutputHandler (String sessionizationPath) {
        try {
            this.sessionizationFile = new File(sessionizationPath);
            this.sessionizationWriter = new PrintWriter(this.sessionizationFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeToFile (LogRecord logRecord) {
        this.sessionizationWriter.println(logRecord.getRecord());
    }
    public void close () {
        this.sessionizationWriter.flush();
        this.sessionizationWriter.close();
    }
}
