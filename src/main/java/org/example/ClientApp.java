package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientApp {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 1234)) {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            while (true){
                double num1;
                char operation;
                double num2;
                System.out.println("""
                        Добро пожаловать в калькулятор. Выберите нужный пункт меню:
                        1 - Зайти в калькулятор
                        2 - Выход""");
                Scanner scanner = new Scanner(System.in);
                int input = scanner.nextInt();
                if(input == 1) {
                    num1 = GetData.getDouble1();
                    operation = GetData.getOperation();
                    num2 = GetData.getDouble2();
                }
                else{
                    break;
                }
                dataOutputStream.writeDouble(num1);
                dataOutputStream.writeChar(operation);
                dataOutputStream.writeDouble(num2);
                System.out.println("Ответ от сервера: " + dataInputStream.readDouble());

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class GetData{
    static Scanner scanner = new Scanner(System.in);
    public static double getDouble1(){
        System.out.println("Введите число: ...");
        double num;
        if(scanner.hasNextDouble()){
            num = scanner.nextDouble();
        } else {
            System.out.println("Вы допустили ошибку при вводе числа. Попробуйте еще раз.");
            scanner.next();//рекурсия
            num = getDouble1();
        }
        return num;
    }

    public static char getOperation(){
        System.out.println("Введите операцию:");
        char operation;
        if(scanner.hasNext()){
            operation = scanner.next().charAt(0);
        } else {
            System.out.println("Вы допустили ошибку при вводе операции. Попробуйте еще раз.");
            scanner.next();//рекурсия
            operation = getOperation();
        }
        return operation;
    }
    public static double getDouble2(){
        System.out.println("Введите число:");
        double num;
        if(scanner.hasNextDouble()){
            num = scanner.nextDouble();
        } else {
            System.out.println("Вы допустили ошибку при вводе числа. Попробуйте еще раз.");
            scanner.next();//рекурсия
            num = getDouble2();
        }
        return num;
    }

}