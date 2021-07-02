package org.example.emulator.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileParser {

    public static Map<String, String> getRequestMap(String pathToFolder) {
        Map<String, String> requestMap = new HashMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(pathToFolder))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> parseFile(file , requestMap));
        } catch (IOException e) {
            e.printStackTrace();
            return requestMap;
        }
        return requestMap;
    }

    private static void parseFile(Path file, Map<String, String> requestMap) {
        List<String> fileLines;
        try (Stream<String> lines = Files.lines(file)) {
            fileLines = lines.collect(Collectors.toList());
            if (!fileLines.isEmpty()) {
                String request = fileLines.get(0);
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < fileLines.size(); i++) {
                    builder.append(fileLines.get(i));
                    builder.append("\n");
                }
                String response = builder.toString().trim();
                requestMap.put(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}