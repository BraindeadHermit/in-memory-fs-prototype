package com.mantracoding.filesystem;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.IOException;
import java.nio.file.FileSystem;

public class FileSystemManager {

    private FileSystem fileSystem = null;

    public FileSystemManager(String name) throws Exception{
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            fileSystem = Jimfs.newFileSystem(name, Configuration.windows());
        } else if (os.toLowerCase().contains("x")) {
            fileSystem = Jimfs.newFileSystem(name, Configuration.osX());
        } else {
            fileSystem = Jimfs.newFileSystem(name, Configuration.unix());
        }
    }

    public FileSystemManager(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            fileSystem = Jimfs.newFileSystem("root",  Configuration.windows());
        } else if (os.toLowerCase().contains("x")) {
            fileSystem = Jimfs.newFileSystem("root", Configuration.osX());
        } else {
            fileSystem = Jimfs.newFileSystem("root", Configuration.unix());
        }
    }

    public void getCurrentDir(){
        if(fileSystem != null){
            fileSystem.getRootDirectories().forEach(root -> {
                System.out.println(root.toString());
            });
        }
    }

    public void closeFileSystem(){
        if (fileSystem.isOpen()) {
            try {
                fileSystem.close();
                if (!fileSystem.isOpen())
                    System.out.println("chiuso");
            } catch (IOException e) {
                System.err.println("Impossibile chiudere il FileSystem");
            }
        }
    }

    public boolean isOpen(){
        return fileSystem.isOpen();
    }
}
