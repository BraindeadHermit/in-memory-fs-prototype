package com.mantracoding;

import com.mantracoding.cli.CliColors;
import com.mantracoding.cli.Commands;
import com.mantracoding.cli.Executor;

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

        do{
            System.out.println("Inserisci un Comando:");
            String command = scanner.nextLine();
            scanner.reset();
            switch (command.toLowerCase().split(" ")[0]){
                case Commands.INIT:
                    if (command.split(" ").length > 1) {
                        String name = command.split(" ")[2];
                        executor.ExecuteInit(name);
                    } else {
                        executor.ExecuteInit();
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
