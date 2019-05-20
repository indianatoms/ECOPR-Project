package com.company;


//zczytanie z inputu +
//chodzenie po stanach +
//wykrywanie blockowan w A i B

//TODO:
//modularnosc nazw
//rozbudowanie do wiekszej ilosci entities
//


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        boolean Ablocked = false;
        boolean Bblocked = false;
        List<Integer> APossibleTransitions = new ArrayList<Integer>();
        List<Integer> BPossibleTransitions = new ArrayList<Integer>();
        
        //warunek petli jest bez sensu
        while (myEntityList.get(Acurrent).getDestiantion() != -1 && myEntityList.get(Bcurrent).getDestiantion() != -1)
        {
            for(int i = 0; i < myEntityList.size(); i++) {
                if (myEntityList.get(i).getEnitityLetter() == 'A' && myEntityList.get(i).getStateNumber() == Acurrent) {
                    APossibleTransitions.add(myEntityList.get(i).getDestiantion());
                }
            }

            if(APossibleTransitions.size() == 0)
            {
                System.out.println("A Has no other trasitions");
                Ablocked = true;
            }else {
                //wychodzi na to ze mozna wylosowac samego siebie i nie ma z tym problemu
                do {
                    RandomWalk = ThreadLocalRandom.current().nextInt(0, APossibleTransitions.size());
                } while (APossibleTransitions.get(RandomWalk) == Acurrent);

                System.out.println("Entity A");
                System.out.println("=====================================================================");
                System.out.println("Transition form " + Acurrent + " to " + APossibleTransitions.get(RandomWalk) + " state");
                for (int i = 0; i < myEntityList.size(); i++) {
                    if (myEntityList.get(i).getEnitityLetter() == 'A' && myEntityList.get(i).getStateNumber() == Acurrent && myEntityList.get(i).getDestiantion() == APossibleTransitions.get(RandomWalk)) {

                        if (myEntityList.get(i).getTransmision() == '-') {
                            System.out.println("Sanding " + myEntityList.get(i).getMessage() + " to Queue");
                            queue = queue + myEntityList.get(i).getMessage();
                        } else if (myEntityList.get(i).getTransmision() == '+') {
                            System.out.println("Receiving " + myEntityList.get(i).getMessage() + " from Queue");
                            queue = removeFirst(myEntityList.get(i).getMessage(), queue);
                        } else {
                            System.out.println("No transtions made");
                        }
                        break;
                    }
                }
                Acurrent = APossibleTransitions.get(RandomWalk);
                System.out.println("Queue looks like this: " + queue);
                APossibleTransitions.clear();
            }
            for(int i = 0; i < myEntityList.size(); i++) {
                if (myEntityList.get(i).getEnitityLetter() == 'B' && myEntityList.get(i).getStateNumber() == Bcurrent) {
                    BPossibleTransitions.add(myEntityList.get(i).getDestiantion());
                }
            }

            if(BPossibleTransitions.size() == 0)
            {
                System.out.println("B Has no other trasitions");
                Bblocked = true;
            }else {
                do {
                    RandomWalk = ThreadLocalRandom.current().nextInt(0, BPossibleTransitions.size());
                } while (BPossibleTransitions.get(RandomWalk) == Bcurrent);

                System.out.println("=====================================================================");
                System.out.println("Entity B");
                System.out.println("Transition form " + Bcurrent + " to " + BPossibleTransitions.get(RandomWalk) + " state");
                for (int i = 0; i < myEntityList.size(); i++) {
                    if (myEntityList.get(i).getEnitityLetter() == 'B' && myEntityList.get(i).getStateNumber() == Bcurrent && myEntityList.get(i).getDestiantion() == BPossibleTransitions.get(RandomWalk)) {

                        if (myEntityList.get(i).getTransmision() == '-') {
                            System.out.println("Sanding " + myEntityList.get(i).getMessage() + " to Queue");
                            queue = queue + myEntityList.get(i).getMessage();
                        } else if (myEntityList.get(i).getTransmision() == '+') {
                            System.out.println("Receiving " + myEntityList.get(i).getMessage() + " from Queue");
                            queue = removeFirst(myEntityList.get(i).getMessage(), queue);
                        } else {
                            System.out.println("No transtions made");
                        }
                        break;
                    }
                }
                Bcurrent = BPossibleTransitions.get(RandomWalk);
                System.out.println("Queue looks like this: " + queue);
                BPossibleTransitions.clear();
            }
            counter++;
            if(counter == 4)
            {
                break;
            }
            if(Ablocked && queue.isEmpty() && Bblocked)
            {
                System.out.println("DeadLock occured");
                break;
            }
        }
    }


    public static String removeFirst(char ch, String s) {
        int charPos = s.indexOf(ch);
        if (charPos < 0) {
            return s;
        }
        return new StringBuilder(s).deleteCharAt(charPos).toString();
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



