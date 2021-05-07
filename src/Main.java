
public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String[] states = {"1", "2", "3", "4", "5"};
    String[] finalStates = {"4"};
    String startState = "1";
    
    String[][] transitions = {{"1", "", "2"}, {"1", "b", "3"}, {"2", "a", "4"}, {"2", "", "5"}, {"3", "b", "5"},{"4", "a", "3"}, {"4", "b", "5"},{"5", "", "4"}};
    NFA nfa = new NFA(startState, states, finalStates, transitions);
//    System.out.println(nfa.numberOfPathsWithString("abbabbbaab"));

    nfa.printTransitions();
    nfa.killLambdaMoves();
    nfa.printTransitions();
  }

}
