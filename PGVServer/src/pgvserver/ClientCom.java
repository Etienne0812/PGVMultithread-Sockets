package pgvserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random; 

public class ClientCom extends Thread {
    Socket socket;

    public ClientCom(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        serveClient(socket);
    }
    
    private static void serveClient(Socket socket) {
        BufferedReader br = null;
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            Scanner sc = new Scanner(System.in);
            
            String line;
            String lineToSendToClient;
            int ctrl = 1;
            int pts = 0;
            
            
            do{
                Random rand = new Random();
            int rand_int1 = rand.nextInt(101); 
            int rand_int2 = rand.nextInt(101) + 100; 
            int rand_int3 = rand.nextInt(rand_int2-rand_int1) + rand_int1;
            
            lineToSendToClient = "Adivine el número entre " + rand_int1 + " y " + rand_int2;
            bw.write(lineToSendToClient);
            bw.newLine();
            bw.flush();
            
                line = br.readLine();
                String comp = String.valueOf(rand_int3);
 
                if(line.equals(comp)){
                    bw.write("Correcto!");
                    bw.newLine();
                    bw.flush();
                    ctrl++;
                    pts++;
                } else if(line.equals("")) {
                        
                } else {
                    bw.write("Incorrecto!");
                    bw.newLine();
                    bw.flush();
                    ctrl++;
                }
            } while (ctrl<=5);
            bw.write("Has acertado " + pts + " veces!");
            bw.newLine();
            bw.flush();
                
    
        } catch (IOException ex) {
            Logger.getLogger(PGVServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(br != null) try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(PGVServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
