package com.mantracoding.filesystem;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean CreateFile(String fileName) throws IOException {
        if (Files.notExists(fileSystem.getPath(currentPath, fileName))) {
            Files.createFile(fileSystem.getPath(currentPath, fileName));
            return true;
        }

        return false;
    }

    public boolean Remove(String fileName) throws IOException {
        if (fileSystem != null){
            if (Files.exists(fileSystem.getPath(currentPath, fileName))){
                if (Files.isDirectory(fileSystem.getPath(currentPath, fileName))){
                    Stream<Path> toDel = Files.walk(fileSystem.getPath(currentPath, fileName));
                    List<Path> l = toDel.collect(Collectors.toList());
                    Collections.reverse(l);
                    l.forEach(path -> {
                        try {
                            Files.delete(fileSystem.getPath(path.toString()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Files.delete(fileSystem.getPath(currentPath, fileName));
                }
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> listContent() throws IOException {
        ArrayList<String> folderContent = new ArrayList<>();

        if (fileSystem != null) {
            Files.list(fileSystem.getPath(currentPath)).forEach(path -> {
                if (currentPath.equals("")) {
                    folderContent.add(path.toString());
                } else {
                    int length = path.toString().split("\\\\").length;
                    folderContent.add(path.toString().split("\\\\")[length - 1]);
                }
            });
        }

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
}
