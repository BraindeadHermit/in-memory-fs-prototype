package com.mantracoding;

import com.mantracoding.cli.CliColors;
import com.mantracoding.cli.Commands;
import com.mantracoding.cli.Executor;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Executor executor = new Executor();

        System.out.print("\n" + CliColors.ANSI_BG);
        System.out.println(CliColors.ANSI_BLACK + "Mantra" + CliColors.ANSI_RED + "Coding" + CliColors.ANSI_RESET + "\n");
        System.out.println("Benvenuti nel programma per la creazione di un file system in memoria RAM da riga di comando\n" +
                "tramite l'utilizzo della libreria Jimfs di Google.\n" +
                "Il programma è solo un prototipo e permette di eseguire diversi comandi tra i quali:");
        executor.ExecuteHelp();
        System.out.println("per avere accesso alla lista dei comandi ti basta digitare HELP!");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        String cmdLine = "Inserisci un Comando:";

        do{
            System.out.print(cmdLine);
            String command = scanner.nextLine();
            command = command.trim();
            scanner.reset();
            switch (command.toLowerCase().split(" ")[0]){
                case Commands.INIT:
                    String root = executor.ExecuteInit();
                    cmdLine = root + ">";
                break;
                case Commands.MKDIR:
                    String dir = executor.ExecuteCreateDir(command.toLowerCase().split(" ")[1]);
                    System.out.println(dir);
                break;
                case Commands.CD:
                    String changedDir = executor.ExecuteChangeDir(command.split(" ")[1]);
                    if (changedDir != null) {
                        cmdLine = changedDir + ">";
                    } else {
                        System.out.println("Cartella inesistente");
                    }
                break;
                case Commands.LS:
                    ArrayList<String> list = executor.ExecuteListContent();
                    if (list.size() != 0) {
                        list.forEach(item -> {
                            System.out.println("    \\" + item);
                        });
                    } else {
                        System.out.println("La cartella è vuota");
                    }
                break;
                case Commands.HELP:
                    executor.ExecuteHelp();
                break;
                case Commands.EXIT:
                    executor.ExecuteExit();
                break;
                default:
                    System.out.println("Comando Insistente, Riprova");
                break;

            }
        }while (executor.getProgramOn());
    }
}
