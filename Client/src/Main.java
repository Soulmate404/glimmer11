import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException{

        Socket socket=new Socket("127.0.0.1",40000);
        System.out.println("已连接到服务器");
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner=new Scanner(System.in);
        new Thread(()->{
           String msg=null;
            while (true) {
                try {
                    if (Objects.equals(msg = in.readLine(), "T")) break;
                } catch (IOException e) {
                    System.out.println("客户端关闭");
                    break;
                }

                System.out.println("收到了："+msg);
            }

        }).start();
        String string;
        while(!Objects.equals(string = scanner.nextLine(), "T")){
            out.println(string);
            out.flush();
        }
        scanner.close();
        out.close();
        in.close();
        socket.close();
    }
}



