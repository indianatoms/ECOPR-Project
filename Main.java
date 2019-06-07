package com.company;




//TODO:
//


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {

        EntityList entityList = new EntityList();
        int number = 0;
        int counter = 0;
        char a = 'a';
        int b = 0;
        char c = 'c';
        char d = 'd';
        int e = 0;


        String[] tokens = new String[5];

        BufferedReader in = new BufferedReader(new FileReader("test.txt"));

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            if(number == 0)
            {
                tokens = line.split(",|;");
                int howMany = Integer.parseInt(tokens[0]);
                for(int i = 1; i<howMany + 1; i++)
                {
                    Entity entity = new Entity(tokens[i].charAt(0),false,0);
                    entityList.addEntity(entity);
                }
                entityList.printEntities();
            }
            else {
                tokens = line.split(",|;");
                a = tokens[0].charAt(0);
                b = Integer.parseInt(tokens[1]);
                c = tokens[2].charAt(0);
                d = tokens[3].charAt(0);
                e = Integer.parseInt(tokens[4]);
                State state = new State(b,c,d,e);
                entityList.getEntity(a).addState(state);
            }
            number++;

        }
        System.out.println(number);
        //entityList.printAllStates();
        //entityList.printEntitiyStates('A');
        //entityList.printEntitiyStates('B');
        //entityList.printEntitiyStates('C');

        //Make Transitions

        while (counter != 50)
        {

            if(entityList.CheckIfAllBlocked() && entityList.CheckIfAllQueuesEmpty())
            {
                System.out.println("DEADLOCK OCCURED");

            }
            counter++;
        }
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

    }

    public static class Entity {
        private char name;
        private ArrayList<State> stateList = new ArrayList<>();
        private String queue;
        private int currentState;
        private boolean isBlocked;

        public Entity(char name, boolean isBlocked,int currentState) {
            this.name = name;
            this.isBlocked = isBlocked;
            this.queue = "";
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
    }


    public static class State {
        private int stateNumber;
        private char transition;
        private char message;
        private int destination;


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

        public State(int stateNumber, char transition, char message, int destination) {
            this.stateNumber = stateNumber;
            this.transition = transition;
            this.message = message;
            this.destination = destination;
        }

        public void PrintState()
        {
            System.out.println("Next Element");
            System.out.println(stateNumber);
            System.out.println(transition);
            System.out.println(message);
            System.out.println(destination);
        }

    }
}



