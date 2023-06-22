package com.mantracoding.filesystem;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSystemManager {

    private String currentPath = "";

    private FileSystem fileSystem = null;

    public FileSystemManager() throws IOException {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            fileSystem = Jimfs.newFileSystem(Configuration.windows());
        } else if (os.toLowerCase().contains("x")) {
            fileSystem = Jimfs.newFileSystem(Configuration.osX());
        } else {
            fileSystem = Jimfs.newFileSystem(Configuration.unix());
        }
    }

    public String getRoot(){
        if(fileSystem != null){
            return fileSystem.getPath("").toAbsolutePath().toString();
        }

        return null;
    }


    public String getCurrentPath() {
        return fileSystem.getPath(currentPath).toAbsolutePath().toString();
    }

    public boolean createDir(String path) throws IOException {
        if (!Files.isDirectory(fileSystem.getPath(currentPath, path))) {
            Path virtualPath = fileSystem.getPath(currentPath, path);
            Files.createDirectory(virtualPath);
            return true;
        }

        return false;
    }

    public boolean SwitchDir(String to){
        if (!to.equals("..")) {
            if (Files.isDirectory(fileSystem.getPath(currentPath, to))) {
                currentPath += currentPath.equals("") ? fileSystem.getPath(to).toString() : "\\" + fileSystem.getPath(to).toString();
                return true;
            }
        } else {
            if (currentPath.contains("\\")){
                currentPath = fileSystem.getPath(currentPath).getParent().toString();
                return true;
            } else {
                currentPath = "";
            }

            return true;
        }

        return false;
    }

    public String getAbsolutePath(String path){
        if(fileSystem != null){
            return fileSystem.getPath(path).toAbsolutePath().toString();
        }

        return null;
    }

    public ArrayList<String> listContent() throws IOException {
        ArrayList<String> folderContent = new ArrayList<>();

        Files.list(fileSystem.getPath(currentPath)).forEach(path -> {
            folderContent.add(path.toString());
        });

        return folderContent;
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
