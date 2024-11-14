import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MultiUser {

    private List<PrintWriter> writerList = new ArrayList<PrintWriter>();

    public void addWriter(PrintWriter pw) {
        writerList.add(pw);
    }

    public void removeWriter(PrintWriter pw) {
        writerList.remove(pw);
    }

    public void print(String message) {
        for (PrintWriter pw : writerList) {
            pw.println(message);
        }
    }

}