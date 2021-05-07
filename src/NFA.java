import java.util.Set;

public class NFA extends FiniteStateMachine {
  public NFA(Node initialState, Set<Node> finalStates) {
    super(initialState, finalStates);
  }

  public NFA(String initialState, String[] states, String[] finalStates, String[][] transitions) {
    super(initialState, states, finalStates, transitions);
  }
  
  public void killLambdaMoves() {
    for(Node node1: states) {
      for(Node node2: states) {
        addMissingTransitions(node1, node2);
      }
    }
    removeTransitionsWithLetter("");
  }
  private void removeTransitionsWithLetter(String letter) {
    for(Node state: states) {
      state.removeTransition(letter);
    }
    
    for(int i = 0; i < transitions.size(); i++) {
      Transition t = transitions.get(i);
      if(t.letter.equals(letter)) {
        transitions.remove(t);
      }
    }
  }
  
  private void addMissingTransitions(Node start, Node finish) {
    addMissTransitions("", start, start, finish);
  }
  
  private void addMissTransitions(String string, Node startNode, Node currentNode, Node finalNode) {
    if(string.length() > 1) {
      return;
    }
    
    if(string.length() == 1 && currentNode == finalNode) {
      if(!startNode.hasTransition(string, finalNode)) {
//        Transition newTransition = new Transition(finalNode, string);
        Transition t = new Transition(startNode, string, finalNode);
        startNode.addTransition(t);
        transitions.add(t);
      }
    }
    
    for(int i = 0; i < currentNode.transitions.size(); i++) {
      Transition t = currentNode.transitions.get(i);
      //lambda transition
      addMissTransitions(string + t.letter, startNode, t.destination, finalNode);
    }
  }
}
