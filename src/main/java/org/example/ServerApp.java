/*
Создать клиент-серверный калькулятор для работы с вещественными числами.
Вычисление должно быть на сервере. Принимаем выражение для вычисления от пользователя на клиенте.
 */
package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(ServerApp.class.getName());
        FileHandler fh = new FileHandler("log.txt");
        logger.addHandler(fh);

        SimpleFormatter sFormat = new SimpleFormatter();
        fh.setFormatter(sFormat);

        logger.log(Level.INFO, "Тестовое логирование");

        {
            try (ServerSocket serverSocket = new ServerSocket(1234)) {
                System.out.println("Сервер запущен, ожидает соединения...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился!");
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                //noinspection InfiniteLoopStatement
                while (true) {
                    double num1 = dataInputStream.readDouble();
                    char operation = dataInputStream.readChar();
                    double num2 = dataInputStream.readDouble();

                    System.out.println("Клиент прислал : " + num1 + " " + " " + operation + " " + num2);
                    double res = Calculator.calc(num1, num2, operation);
                    System.out.println("Ответ направленный клиенту:" + res);

                    dataOutputStream.writeDouble(res);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Calculator{
    public static double calc(double num1, double num2, char operation){
        double result = 0;
        switch (operation) {
            case '+' -> result = num1 + num2;
            case '-' -> result = num1 - num2;
            case '*' -> result = num1 * num2;
            case '/' -> result = num1 / num2;
            default -> System.out.println("Операция не распознана. ");
        }
        return result;
    }
}
