package com.hak.haklist.service.util;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ResourceLoader {

    public byte[] getFile(String fileName) throws IOException {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        byte[] buffer=new byte[(int) file.length()];
        FileInputStream fileInputStream=new FileInputStream(file);
        fileInputStream.read(buffer);
        return buffer;

    }

}
