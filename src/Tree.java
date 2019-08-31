import java.util.ArrayList;
import java.util.HashMap;

public class Tree {

    private int n;

    private ArrayList<Integer> values; //last row of tree
    private ArrayList<ArrayList<Integer>> tree;
    private ArrayList<ArrayList<Integer>> parsedTree;

    public Tree(int n) {
        values = ArrayToList(constructBaseValues(n)); //the nodes at the bottom of the tree
        tree = new ArrayList<>();
        constructTree();
        rConstruct();
        this.n = n;
    }

    public void read() {
        StringBuilder out = new StringBuilder();
        for (int i = parsedTree.size() - 1; i > 0; i--) {
            out.append(parsedTree.get(i).get(n));
        }
        System.out.println(out.toString());
    }

    /**
     * Example:
     * 0 0 0 0 0 0 0 0
     * 0 0 0 0 1 1 1 1
     * 0 0 1 1 0 0 1 1
     * 0 1 0 1 0 1 0 1
     * 0 1 2 3 4 5 6 7
     */
    private void rConstruct() {
        ArrayList<ArrayList<Integer>> rTree = new ArrayList<>();
        rTree.add(tree.get(0));
        ArrayList<Integer> firstLine = new ArrayList<>();
        for (int i = 0; i < tree.get(0).size(); i++) {
            firstLine.add(i % 2);
        }
        rTree.add(firstLine);
        for (int i = 1; i < tree.size(); i++) {
            rTree.add(elongate(tree.get(i), (int) Math.pow(2, i)));
        }
        parsedTree = rTree;
    }

    //Util
    private void print(ArrayList<ArrayList<Integer>> array) {
        for (int r = 0; r < array.size(); r++) {
            for (int c = 0; c < array.get(r).size(); c++) {
                System.out.print(array.get(r).get(c) + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<Integer> elongate(ArrayList<Integer> array, int factor) {
        ArrayList<Integer> out = new ArrayList<>();
        for (Integer j : array) {
            for (int i = 0; i < factor; i++) {
                out.add(j);
            }
        }
        return out;
    }


    public void constructTree() {
        tree.add(values);
        ArrayList<Integer> next = next(values);
        while (next.size() >= 1) {
            tree.add(next);
            next = next(next);
        }
    }

    public static ArrayList<Integer> next(ArrayList<Integer> line) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i = 0; i < line.size() / 2; i++) {
            out.add(i % 2);
        }
//        reverse(out);
        return out;
    }

    public static void reverse(ArrayList<Integer> line) {
        for (int i = 0; i < line.size(); i++) {
            int temp = line.get(i);
            line.set(i, line.size() - i);
            line.set(line.size() - i, temp);
        }
    }

    public static ArrayList<Integer> ArrayToList(int[] list) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i : list) {
            out.add(i);
        }
        return out;
    }

    /**
     * @param n
     * @return bottom row of tree
     * ranges from 0 - (2^x - 1) X |E R
     */
    private int[] constructBaseValues(int n) {
        int[] out = new int[valuesNeeded(n)];
        for (int i = 0; i < out.length; i++) {
            out[i] = i;
        }
        return out;
    }

    /**
     * @param c the value that needs to be converted
     * @return number of nodes needed to create a tree containing c
     * value always equal to next power of two after c
     */
    public static int valuesNeeded(int c) {

        //edge case
        if (c == 1 || c == 0) return 2;

        int i = c;
        while (!powerOfTwo(i)) {
            i++;
        }
        return i;
    }

    public static boolean powerOfTwo(int i) {
        double log = Math.log(i) / Math.log(2);
        if (log - (int) log == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param tree
     * @return boolean whether or not tree can be used
     * A tree can only be used if all values can be turned into a node, hence divisible by two till node 1
     */
    public static boolean checkTree(Tree tree) {
        double i = tree.getValueCount();
        while ((i / 2 - i == 0) || (i == 1)) {
            i /= 2;
        }
        if (i == 1) return true;
        return false;
    }


    public int getValueCount() {
        return values.size();
    }
}
