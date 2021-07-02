package org.example.emulator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.emulator.parser.FileParser;
import org.springframework.http.MediaType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RestServiceEmulator extends HttpServlet {

    private final String pathToFolder;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(request.getMethod())
               .append(" ")
               .append(request.getRequestURI())
               .append("?")
               .append(request.getQueryString());
        String requestString = URLDecoder.decode(requestBuilder.toString(), StandardCharsets.UTF_8);
        log.info("Request: {}", requestString);
        sendResponse(requestString, response);
    }

    private void sendResponse(String requestString, HttpServletResponse response) throws IOException {
        String result = handleResponse(requestString);
        int status;
        if (StringUtils.isEmpty(result)) {
            status = HttpServletResponse.SC_NOT_FOUND;
            result = "Not found";
        } else {
            status = HttpServletResponse.SC_OK;
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        writer.println(result);
    }

    private String handleResponse(String requestString) {
        Map<String, String> requestMap = FileParser.getRequestMap(pathToFolder);
        return requestMap.get(requestString);
    }

}