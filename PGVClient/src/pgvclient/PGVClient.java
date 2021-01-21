package pgvclient;

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

public class PGVClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int PORT = 40080;
        final String HOST = "192.168.43.129";
        
        try {
            Socket socket = new Socket(HOST, PORT);
            
            comunicateWithServer(socket);
        } catch (IOException ex) {
            Logger.getLogger(PGVClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void comunicateWithServer(Socket socket) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            Scanner sc = new Scanner(System.in);
            String line, line2, lineFromServer;
            int ctrl = 1;
            
            do{
                lineFromServer = br.readLine();
                System.out.println(lineFromServer);
                System.out.println("Escriba su respuesta: ");
                line = sc.nextLine();
                bw.write(line);
                bw.newLine();
                bw.flush();
                
                lineFromServer = br.readLine();
                System.out.println("Server said: " + lineFromServer);
                ctrl++;
                
            }while (ctrl <= 6);
            if (ctrl == 6){
                System.exit(0);
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(PGVClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
