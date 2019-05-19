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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Main {

    public static void main(String[] args) throws IOException {

        List<MyEntity> myEntityList = new ArrayList<MyEntity>();
        int Anumber = 0;
        int Bnumber = 0;
        int number = 0;
        int counter = 0;
        String queue = "";
        char a = 'a';
        int b = 0;
        char c = 'c';
        char d = 'd';
        int e = 0;


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
            if(myEntityList.get(i).getEnitityLetter() == 'A')
            {
                Anumber++;
            }
            if(myEntityList.get(i).getEnitityLetter() == 'B')
            {
                Bnumber++;
            }
        }
        System.out.println(Anumber);
        System.out.println(Bnumber);

        int Acurrent = 0,Bcurrent = Anumber, RandomWalk;
        List<Integer> APossibleTransitions = new ArrayList<Integer>();
        while (myEntityList.get(Acurrent).getDestiantion() != -1 && myEntityList.get(Bcurrent).getDestiantion() != -1)
        {
            for(int i = 0; i < myEntityList.size(); i++) {
                if (myEntityList.get(i).getEnitityLetter() == 'A' && myEntityList.get(i).getStateNumber() == Acurrent) {
                    APossibleTransitions.add(myEntityList.get(i).getDestiantion());
                }
            }
            do {
                RandomWalk = ThreadLocalRandom.current().nextInt(0, APossibleTransitions.size() + 1);
            }while (RandomWalk == Acurrent);
            System.out.println("Entity A");
            System.out.println("Transition form " + Acurrent + " to " + RandomWalk + " state");
            for(int i = 0; i < myEntityList.size(); i++) {
                if (myEntityList.get(i).getEnitityLetter() == 'A' && myEntityList.get(i).getStateNumber() == Acurrent && myEntityList.get(i).getDestiantion() == RandomWalk)
                {

                    if(myEntityList.get(i).getTransmision() == '-')
                    {
                        System.out.println("Sanding " + myEntityList.get(i).getMessage() + " to Queue");
                        queue = queue + myEntityList.get(i).getMessage();
                    }
                    else if (myEntityList.get(i).getTransmision() == '+')
                    {
                        System.out.println("Receiving " + myEntityList.get(i).getMessage() + "from Queue");
                        queue = queue + "-" + myEntityList.get(i).getMessage();
                    }
                    else
                    {
                        System.out.println("No transtions made");
                    }
                    break;
                }
            }
            Acurrent = RandomWalk;
            System.out.println("Queue looks like this: " + queue);
            APossibleTransitions.clear();
            counter++;
            if(counter == 10)
            {
                break;
            }
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
            System.out.println("Next Element");
            System.out.println(enitityLetter);
            System.out.println(stateNumber);
            System.out.println(transmision);
            System.out.println(message);
            System.out.println(destiantion);
        }

    }
}



