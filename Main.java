package com.company;


//Jestem na etapie zczytywania z txt inputu do programu teraz bede to przetwarzac

import org.w3c.dom.Node;

import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException {

        List<MyEntity> myEntityList = new ArrayList<MyEntity>();

        int number = 0;
        char a = 'a';
        int b = 0;
        char c = 'c';
        char d = 'd';
        int e = 0;

        //      ArrayList<Entity> arrayList = new ArrayList<>();

        String[] tokens = new String[5];

        BufferedReader in = new BufferedReader(new FileReader("test.txt"));

        String line;
        while ((line = in.readLine()) != null) {
            number++;
            System.out.println(line);
            tokens = line.split(",|;");
            a = tokens[0].charAt(0);
            b = Integer.parseInt(tokens[1]);
            c = tokens[2].charAt(0);
            d = tokens[3].charAt(0);
            e = Integer.parseInt(tokens[4]);

            myEntityList.add(new MyEntity(a,b,c,d,e));
        }
        System.out.println(number);

        for(int i = 0; i < myEntityList.size(); i++) {
            myEntityList.get(i).PrintElements();
        }

    }







    public static class MyEntity {
        private char enitityLetter;
        private int stateNumber;
        private char transmision;
        private char message;
        private int destiantion;

        public char getEnitityLetter() {
            return enitityLetter;
        }

        public int getStateNumber() {
            return stateNumber;
        }

        public char getTransmision() {
            return transmision;
        }

        public char getMessage() {
            return message;
        }

        public int getDestiantion() {
            return destiantion;
        }

        public MyEntity(char enitityLetter, int stateNumber, char transmision, char message, int destiantion) {
            this.enitityLetter = enitityLetter;
            this.stateNumber = stateNumber;
            this.transmision = transmision;
            this.message = message;
            this.destiantion = destiantion;
        }

        public void PrintElements()
        {
            System.out.println("here");
            System.out.println(enitityLetter);
            System.out.println(stateNumber);
            System.out.println(transmision);
            System.out.println(message);
            System.out.println(destiantion);
        }
    }
}



