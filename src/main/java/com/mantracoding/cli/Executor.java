package com.mantracoding.cli;

import com.mantracoding.filesystem.FileSystemManager;

public class Executor {

    private FileSystemManager fileSystemManager;
    private boolean ProgramOn = true;

    public boolean getProgramOn() {
        return ProgramOn;
    }

    public void ExecuteHelp(){
        System.out.println("   - " + CliColors.ANSI_CYAN + Commands.INIT + CliColors.ANSI_RESET + ": per la creazione di un nuovo file system,\n" +
                "       questo comanda andrà a creare un file sistem con un nome di defalult, per assegnare tu un nome al tuo file system bisognerà eseguire:\n" +
                "           - " + CliColors.ANSI_CYAN + Commands.INIT + " -name yourName" + CliColors.ANSI_RESET + "\n" +
                "       ATTENZIONE! l'esecuzione multipla di questo comando non andrà a generare più file system, ma a sovreasrcivere quello esistente,\n" +
                "       quindi se si hanno file salvati verranno automaticamente persi\n" +
                "   - " + CliColors.ANSI_CYAN + "COMANDO 2" + CliColors.ANSI_RESET + ": per la ....\n" +
                "   - " + CliColors.ANSI_CYAN + Commands.HELP + CliColors.ANSI_RESET + ": per stampare a video la lista dei comandi\n" +
                "   - " + CliColors.ANSI_CYAN + Commands.EXIT + CliColors.ANSI_RESET + ": per uscire dal programma\n");
    }

    public void ExecuteExit(){
        fileSystemManager.closeFileSystem();
        ProgramOn = false;
    }

    public void ExecuteInit(){
        try {
            if (fileSystemManager == null) {
                fileSystemManager = new FileSystemManager();
            }
        } catch (Exception e){
            System.err.println("Impossibilie creare un file system in questo momento, errore sconosciuto");
            ExecuteExit();
        }
    }

    public void ExecuteInit(String name){
        try {
            if(fileSystemManager == null) {
                fileSystemManager = new FileSystemManager(name);
            }
        } catch (Exception e){
            System.err.println("Impossibilie creare un file system in questo momento, errore sconosciuto");
            ExecuteExit();
        }
    }
}
