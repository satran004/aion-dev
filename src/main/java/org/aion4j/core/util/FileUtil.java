package org.aion4j.core.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

    public static String getFileContent(String file) {
        try {
            Path path = Paths.get(FileUtil.class.getClassLoader()
                .getResource(file).toURI());

            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();

            System.out.println(data);
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
