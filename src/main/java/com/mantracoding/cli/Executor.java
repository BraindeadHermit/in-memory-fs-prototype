package com.mantracoding.cli;

import com.mantracoding.filesystem.FileSystemManager;

import java.io.IOException;
import java.util.ArrayList;

public class Executor {

    private FileSystemManager fileSystemManager;
    private boolean ProgramOn = true;

    public boolean getProgramOn() {
        return ProgramOn;
    }

    public void ExecuteHelp(){
        System.out.println("   - " + CliColors.ANSI_CYAN + Commands.INIT + CliColors.ANSI_RESET + ": per la creazione di un nuovo file system,\n" +
                "       questo comanda andrà a creare un file sistem con un nome di defalult\n" +
                "   - " + CliColors.ANSI_CYAN + Commands.MKDIR + " yourFolderName" + CliColors.ANSI_RESET + ": per la creazione di una cartella\n" +
                "   - " + CliColors.ANSI_CYAN + Commands.HELP + CliColors.ANSI_RESET + ": per stampare a video la lista dei comandi\n" +
                "   - " + CliColors.ANSI_CYAN + Commands.EXIT + CliColors.ANSI_RESET + ": per uscire dal programma\n");
    }

    public void ExecuteExit(){
        fileSystemManager.closeFileSystem();
        ProgramOn = false;
    }

    public String ExecuteInit(){
        try {
            if (fileSystemManager == null) {
                fileSystemManager = new FileSystemManager();
            }

            return fileSystemManager.getRoot();
        } catch (Exception e){
            System.err.println("Impossibilie creare un file system in questo momento, errore sconosciuto");
            ExecuteExit();
        }

        return "";
    }

    public String ExecuteCreateDir(String path){
        try{
            if (fileSystemManager.createDir(path)){
                return "Cartella <" + path + "> creata con successo!";
            } else {
                return "Cartella già esistente";
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public String ExecuteChangeDir(String path){
        if (fileSystemManager != null){
            if (fileSystemManager.SwitchDir(path))
                return fileSystemManager.getCurrentPath();
            else
                return null;
        }

        return null;
    }

    public ArrayList<String> ExecuteListContent(){
        if (fileSystemManager != null){
            try {
                return fileSystemManager.listContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String ExecuteTouch(String fileName){
        if (fileSystemManager != null) {
            try {
                if(fileSystemManager.CreateFile(fileName))
                    return "File <" + fileName + "> creato con successo";
                else
                    return "Il file esiste già";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String ExecuteRemove(String fileName){
        if (fileSystemManager != null) {
            try {
                if(fileSystemManager.Remove(fileName))
                    return "File/Cartella <" + fileName + "> eliminato con successo";
                else
                    return "Il è inesistente";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
