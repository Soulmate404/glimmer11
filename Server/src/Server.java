import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<PrintWriter>clients=new ArrayList<>();
    public static void main(String[] args)  {
        try {


            ServerSocket serverSocket = new ServerSocket(40000);
            System.out.println("等待连接");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("有用户加入");
                new serverClient(socket).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class serverClient extends Thread{
        private Socket socket;
        private PrintWriter printWriter;
        private BufferedReader bufferedReader;

        public serverClient(Socket socket)throws IOException{
            this.socket=socket;
           printWriter =new PrintWriter(socket.getOutputStream(),true);
           bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
           clients.add(printWriter);
        }
        @Override
        public void run(){

            String msg=null;
            try {
                while((msg= bufferedReader.readLine())!=null){
                    System.out.println("MESSAGE:"+msg);
                    for(PrintWriter printWriter1:clients){
                        printWriter1.println(msg);
                        printWriter1.flush();
                    }

                }


            } catch (Exception e) {
                System.out.println("有用户退出");

            }finally {
                try {
                    socket.close();
                    if(bufferedReader!=null){
                        clients.remove(printWriter);
                        System.out.println("有用户退出");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}