package InputOutput;

import java.io.PrintWriter;

public class Output {
     PrintWriter writer = new PrintWriter(System.out);
    public  void output(String output){
        writer.write(output+"\n");
    }
}
