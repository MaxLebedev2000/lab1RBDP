package com.example.springboot.Repository;

import com.example.springboot.Entity.Users;

import java.beans.XMLDecoder;
import java.io.BufferedOutputStream;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class XMLRepository {
    private final String filePath = "users.xml";

    public void write (List<Users> allUsers) {
        try {
            final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
                    new FileOutputStream(filePath)));
            for (Users user : allUsers) encoder.writeObject(user);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        }
    }

    public void read () {
        try {
            final XMLDecoder decoder = new XMLDecoder(new FileInputStream(filePath));
            List<Users> listFromFile = (List<Users>) decoder.readObject();
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        }
    }
}
