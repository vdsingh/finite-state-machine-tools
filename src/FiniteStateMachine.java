import java.util.*;

class FiniteStateMachine{
  Node initialState;
  Set<Node> finalStates;
  Set<Node> states;
  ArrayList<Transition> transitions;
  
  public FiniteStateMachine(Node initialState, Set<Node> finalStates) {
    this.initialState = initialState;
    this.finalStates = finalStates;
    
    addStatesAndTransitions(initialState);
  }
  
  public void addStatesAndTransitions(Node initialState) {
    states = new HashSet<Node>();
    Queue<Node> q = new LinkedList<Node>();
    
    q.add(initialState);
    while(!q.isEmpty()) {
      Node node = q.remove();
      states.add(node);
      for(Transition transition: node.transitions) {
        transitions.add(transition);
        q.add(transition.destination);
      }
    }
  }
  
  public FiniteStateMachine(String initialState, String[] states, String[] finalStates, String[][] transitions) {
    //create a hashmap that maps nodes by their label
    Map<String, Node> nodes = new HashMap<String, Node>();
    
    //create the initial state
    this.initialState = new Node(initialState);
    nodes.put(initialState, this.initialState);
    
    //initialize set of states and add the initial state
    this.states = new HashSet<Node>();
    this.states.add(this.initialState);
    
    //initialize set of transitions
    this.transitions = new ArrayList<Transition>();
    
    //initialize set of final states
    this.finalStates = new HashSet<Node>();
    
    //creating the states, adding them to the map by label, and adding them to the states set (and finalStates if they're final)
    for(String state: states) {
      if (!state.equals(initialState)){
        Node node = new Node(state);
        nodes.put(state, node);
        this.states.add(node);
        if(isFinal(finalStates, state)) {
          this.finalStates.add(node);
        }
      }
    }
    
    //adding the transitions for each state to each state and to the transitions set (in case we want to print out the transitions)
    for(String[] transition: transitions) {
      String node1label = transition[0];
      String letter = transition[1];
      String node2label = transition[2];
      
      Node node1 = nodes.get(node1label);
      Node node2 = nodes.get(node2label);
      Transition t = new Transition(node1, letter, node2);
      node1.addTransition(t);
      this.transitions.add(t);
    }
  }
  
  //returns the number of paths with the specified string, starting from the initial state
  public int numberOfPathsWithString(String finalString) {
    return numberOfPathsWithString("", finalString, 0, initialState);
  }
  
  //starts at the initial state and finds the number of paths such that a string can be created.
  private int numberOfPathsWithString(String string, String finalString, int index, Node currentNode) {
    if(!finalString.substring(0, index).equals(string) || string.length() > finalString.length()) {
      return 0;
    }
    
    //we have gotten all the way through the string
    if(string.equals(finalString)) {
      //we can only count it if it is a final state
      if(finalStates.contains(currentNode)) {
        return 1;
      }
      else return 0;
    }
   
    int total = 0;
    for(Transition transition: currentNode.transitions) {
      total += numberOfPathsWithString(string + transition.letter, finalString, index + 1, transition.destination);
    }
    return total;
  }
  
  public boolean isFinal(String[] finalStates, String state) {
    for(String s: finalStates) {
      if(state.equals(s)) {
        return true;
      }
    }
    return false;
  }
  
  public void printTransitions() {
    for(Transition transition: transitions) {
      if(transition.letter.equals(""))
        System.out.print("("+transition.source+",λ,"+ transition.destination + "),");
      else
        System.out.print("("+transition.source+"," + transition.letter +","+ transition.destination + "),");
    }
    System.out.println();
  }
}

class Node{
  ArrayList<Transition> transitions;
  String label;
  
  public Node(String label) {
    transitions = new ArrayList<Transition>();
    this.label = label;
  }
  
  public void addTransition(Transition t) {
    transitions.add(t);
  }
  
  public void addTransition(Node destination, String letter) {
    transitions.add(new Transition(this, letter, destination));
  }
  
  public void removeTransition(String letter) {
    for(int i = 0; i < transitions.size(); i++) {
      if(transitions.get(i).letter.equals(letter)) {
        transitions.remove(i);
        i--;
      }
    }
  }
  
  public boolean hasTransition(String letter, Node destination) {
    for(Transition transition: transitions) {
      if(transition.letter.equals(letter) && destination == transition.destination) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public String toString() {
    return label;
  }
}

class Transition{
  Node source;
  Node destination;
  String letter;
  
  public Transition(Node source, String letter, Node destination) {
    this.source = source;
    this.destination = destination;
    this.letter = letter;
  }
}
