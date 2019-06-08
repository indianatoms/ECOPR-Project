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
import java.util.concurrent.ThreadLocalRandom;


public class Main {

    public static void main(String[] args) throws IOException {

        EntityList entityList = new EntityList();
        int counter = 0;
        int number = 0;
        char a = 'a';
        int b = 0;
        char c = 'c';
        char d = 'd';
        int e = 0;
        char f = 'f';

        //      ArrayList<Entity> arrayList = new ArrayList<>();

        String[] tokens;

        BufferedReader in = new BufferedReader(new FileReader("test.txt"));

        String line;
        while ((line = in.readLine()) != null) {

            System.out.println(line);
            tokens = line.split(",|;");


            if(number == 0)
            {

                int howMany = Integer.parseInt(tokens[0]);
                for(int i = 1; i<howMany + 1; i++)
                {
                    Entity entity = new Entity(tokens[i].charAt(0),false,1);
                    entityList.addEntity(entity);
                }
 //               entityList.printEntities();
            }
            else {
                a = tokens[0].charAt(0);
                b = Integer.parseInt(tokens[1]);
                c = tokens[2].charAt(0);
                d = tokens[3].charAt(0);
                e = Integer.parseInt(tokens[4]);
                f = tokens[5].charAt(0);

                State state = new State(b,c,d,e,f);
                entityList.getEntity(a).addState(state);
            }
            number++;

        }
 //       System.out.println(number);


        while (counter != 4)
        {
            entityList.RandomWalk();
            if(entityList.CheckIfAllQueuesEmpty() && entityList.isBlocked)
            {
                System.out.println("DEADLOCK OCCURED");
                break;
            }
            counter++;
        }
        //entityList.printEntitiyStates('A');
        //entityList.printEntitiyStates('B');
        //entityList.printEntitiyStates('C');
    }


    public static String removeFirst(char ch, String s) {
        int charPos = s.indexOf(ch);
        if (charPos < 0) {
            return s;
        }
        return new StringBuilder(s).deleteCharAt(charPos).toString();
    }




    public static class EntityList{
        private ArrayList<Entity> entityList = new ArrayList<>();
        private boolean isBlocked = false;

        public void addEntity(Entity entity){
            entityList.add(entity);
        }

        public void printEntities() {
            for (int i = 0; i< entityList.size(); i++)
            {
                System.out.println((entityList.get(i).name));
            }
        }

        public Entity getEntity(char name){
            for (int i = 0; i< entityList.size(); i++)
            {
                if(entityList.get(i).name == name)
                {
                    return entityList.get(i);
                }
            }
            return null;
        }

        public boolean CheckIfAllBlocked()
        {
            for (int i = 0; i< entityList.size(); i++)
            {
                if (!entityList.get(i).isBlocked)
                {
                    return false;
                }
            }
            return true;
        }

        public boolean CheckIfAllQueuesEmpty()
        {
            for (int i = 0; i< entityList.size(); i++)
            {
                if (!entityList.get(i).isQueueEmpty())
                {
                    return false;
                }
            }
            return true;
        }

        public  void printAllStates()
        {
            for (int i = 0; i< entityList.size(); i++)
            {
                entityList.get(i).printStates();
            }
        }

        public  void printEntitiyStates(char name)
        {
            for (int i = 0; i< entityList.size(); i++)
            {
                if(entityList.get(i).getName() == name)
                    entityList.get(i).printStates();
            }
        }

        public void printCurrentStates()
        {
            for (int i = 0; i< entityList.size(); i++)
            {
                System.out.println(entityList.get(i).getName() + " is in state " + entityList.get(i).getCurrentState());
            }
        }

        public void RandomWalk()
        {
            ArrayList<PossibleMove> possibleMoves = new ArrayList<>();

            for (int i = 0; i< entityList.size(); i++)
            {
                Entity entity = entityList.get(i);
                for (int j = 0; j< entity.getStateList().size(); j++)
                {
                    State current = entity.getStateList().get(j);
                    if(current.getStateNumber() == entity.currentState)
                    {
                        if(current.getTransition() != '+') {
                            PossibleMove possibleMove = new PossibleMove(current.getStateNumber(), current.getDestination(), current.getReceiver(), entity.getName());
                            possibleMoves.add(possibleMove);
                        }
                        else{
                            for (int it = 0; it< entityList.size(); it++)
                            {
                                if(entityList.get(it).getName() == current.getReceiver()){
                                    if(entity.getQueue() == removeFirst(current.getMessage(),entityList.get(it).getQueue()))
                                    {
                                        //  System.out.println("No Such Element in the Queue");
                                    }
                                    else {
                                        PossibleMove possibleMove = new PossibleMove(current.getStateNumber(), current.getDestination(), current.getReceiver(), entity.getName());
                                        possibleMoves.add(possibleMove);
                                    }
                                }

                            }

                        }
                    }
                }
            }
            if(possibleMoves.size() == 0)
            {
                isBlocked = true;
                return;
            }
            System.out.println("Number rof possible moves " + possibleMoves.size());
            int randomNum = ThreadLocalRandom.current().nextInt(0, possibleMoves.size());
            System.out.println("Number randomly choosen is " + randomNum);
            PossibleMove chosenMove = possibleMoves.get(randomNum);
            makeTransition(chosenMove.getEntity(),chosenMove.getReciever(),chosenMove.getTarget());
        }




        public boolean makeTransition (char entitiy, char targetEntity, int destination)
        {
            for (int i = 0; i< entityList.size(); i++)
            {
                if(entityList.get(i).getName() == entitiy)
                {
                    Entity entity =  entityList.get(i);
                    for (int j = 0; j< entity.getStateList().size(); j++)
                    {
                        if(entity.getStateList().get(j).getStateNumber() == entity.getCurrentState())
                        {
                            if(entity.getStateList().get(j).getDestination() == destination && entity.getStateList().get(j).getReceiver() == targetEntity)
                            {
                                State state = entity.getStateList().get(j);
                                state.PrintState();
                                if(state.getTransition() == '-')
                                {
                                    entity.AddElementToQueue(state.getMessage());
                                    System.out.println("Message Send");
                                    entity.setCurrentState(state.getDestination());
                                    return true;
                                }
                                else if (state.getTransition() == '+')
                                {
                                    for (int it = 0; i< entityList.size(); it++)
                                    {
                                        if(entityList.get(it).getName() == state.getReceiver())
                                        {
                                            Entity traget = entityList.get(it);
                                            if(traget.getQueue() == removeFirst(state.getMessage(),entity.getQueue()))
                                            {
                                                System.out.println("No Such Element in the Queue");
                                                return false;
                                            }
                                            else {
                                                traget.RemoveElementFromQueue(state.getMessage());
                                                System.out.println("Message succesfully recieved");
                                                return true;
                                            }
                                        }
                                    }

                                }
                                else if (state.getTransition() == 't')
                                {
                                    System.out.println("Empty Transitions Occured");
                                    entity.setCurrentState(state.getDestination());
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }

    }

    public static class Entity {
        private char name;
        private ArrayList<State> stateList = new ArrayList<>();
        private String queue;
        private int currentState;
        private boolean isBlocked;

        public String getQueue() {
            return queue;
        }

        public Entity(char name, boolean isBlocked, int currentState) {
            this.name = name;
            this.isBlocked = isBlocked;
            this.queue = "";
            this.currentState = currentState;
        }

        public void AddElementToQueue(char add)
        {
            queue += add;
        }

        public void RemoveElementFromQueue(char sign)
        {
            queue = removeFirst(sign,queue);
        }

        public void setCurrentState(int currentState) {
            this.currentState = currentState;
        }

        public void addState(State state){
            stateList.add(state);
        }

        public void printStates(){
            for (int i = 0; i< stateList.size(); i++)
            {
                stateList.get(i).PrintState();
            }
        }

        public char getName() {
            return name;
        }

        public int getCurrentState() {
            return currentState;
        }

        public boolean isQueueEmpty()  {return this.queue.isEmpty();}

        public ArrayList<State> getStateList() {
            return stateList;
        }


    }


    public static class State {
        private int stateNumber;
        private char transition;
        private char message;
        private char receiver;
        private int destination;


        public char getReceiver() {
            return receiver;
        }

        public int getStateNumber() {
            return stateNumber;
        }

        public char getTransition() {
            return transition;
        }

        public char getMessage() {
            return message;
        }

        public int getDestination() {
            return destination;
        }

        public State(int stateNumber, char transition, char message, int destination, char receiver) {
            this.stateNumber = stateNumber;
            this.transition = transition;
            this.message = message;
            this.destination = destination;
            this.receiver = receiver;
        }

        public void PrintState()
        {
            System.out.println("Next Element");
            System.out.println(stateNumber);
            System.out.println(transition);
            System.out.println(message);
            System.out.println(destination);
            System.out.println(receiver);
        }

    }

    public static class PossibleMove
    {
        int current;
        int target;
        char reciever;
        char entity;

        public PossibleMove(int current, int target, char reciever,char entity) {
            this.current = current;
            this.target = target;
            this.reciever = reciever;
            this.entity = entity;
        }

        public int getCurrent() {
            return current;
        }

        public int getTarget() {
            return target;
        }

        public char getReciever() {
            return reciever;
        }

        public char getEntity() {
            return entity;
        }
    }


}

//tests
//NO DEADLOCKS - GOES IN INFINITY
//2,A,B;
//        A,1,-,a,2,B;
//        A,2,+,a,1,B;
//        B,1,t,b,2,A;
//        B,2,t,b,2,A;

//DEADLOCK BY THIRD MOVE
//2,A,B;
//        A,1,t,-,2,B;
//        B,1,t,-,2,A;
