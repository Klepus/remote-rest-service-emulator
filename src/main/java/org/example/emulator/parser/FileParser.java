package org.example.emulator.parser;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Slf4j
public class FileParser {

    private final Map<String, String> requestMap;

    public FileParser(String path) {
        requestMap = fillRequestMap(path);
        log.info("Emulation for requests: {}", requestMap.keySet());
    }

    private Map<String, String> fillRequestMap(String pathToFolder) {
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

    private void parseFile(Path file, Map<String, String> requestMap) {
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