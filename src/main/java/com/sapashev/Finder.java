package com.sapashev;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Finds file in directory and subdirectories.
 * @author Arslan Sapashev
 * @since 30.01.2017
 * @version 1.0
 */
public class Finder {
    public static void main (String[] args) throws IOException {
        new Finder().start(args);
    }

    /**
     * Starts uer request processing.
     * @param args - command-line arguments.
     * @throws IOException
     */
    public void start(String[] args) throws IOException {
        if(isAllKeys(args)){
            if(args[4].equals("-m")){
                args[3] = maskToRegEx(args[3]);
            }
            saveToFile(find(args[1], args[3]), args[6]);
        } else {
            System.out.println("Wrong keys");
            help();
        }
    }

    /**
     * Checks are all keys present.
     * @param args - command line arguments.
     * @return true - all keys are present, false - otherwise.
     * @throws IOException
     */
    public boolean isAllKeys (String[] args) throws IOException {
        String toCheck = String.join(" ", args);
        return toCheck.matches("-d\\s.+\\s(-m|-f|-r)\\s-o\\s.+\\.txt$");
    }

    /**
     * Converts mask to appropriate regular expression.
     * @param mask - mask to convert.
     * @return - mask converted to regex.
     */
    public String maskToRegEx(String mask){
        return mask.replaceAll("\\?",".{1}").replaceAll("\\*", ".+");
    }

    /**
     * Finds all files that matches to specified pattern.
     * @param start - directory to start searching.
     * @param pattern - regex to search.
     * @return - list of paths of found files.
     * @throws IOException
     */
    public List<Path> find(String start, String pattern) throws IOException {
        return Files.find(Paths.get(start),100,
                (p, bfa) -> !Files.isDirectory(p) && p.getFileName().toString().matches(pattern))
                .collect(Collectors.toList());
    }

    /**
     * Saves files to the log file.
     * @param files - found files.
     * @param logFile - file to which saves list of files.
     * @throws IOException
     */
    public void saveToFile (List<Path> files, String logFile) throws IOException {
        String separator = System.getProperty("line.separator");
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(logFile))){
            for(Path p : files){
                out.write((p.toString() + separator).getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * Prints help to console.
     */
    public void help(){
        String[] help = new String[6];
        help[0] = "-d <directory to start search>";
        help[1] = "-n <file name, mask or regex>";
        help[2] = "-m <use mask to search>";
        help[3] = "-f <use full file name to search>";
        help[4] = "-r <use regex to search>";
        help[5] = "-o <file name to save found files>";
        Arrays.stream(help).forEach(System.out::println);
    }
}