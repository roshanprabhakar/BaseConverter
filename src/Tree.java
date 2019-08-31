import java.util.ArrayList;
import java.util.HashMap;

public class Tree {

    private int n;
    private int base;

    private ArrayList<Integer> values; //last row of tree
    private ArrayList<ArrayList<Integer>> tree;
    private ArrayList<ArrayList<Integer>> parsedTree;

    public Tree(int n, int base) {
        this.base = base;
        values = ArrayToList(constructBaseValues(n)); //the nodes at the bottom of the tree
        tree = new ArrayList<>();
        constructTree();
        rConstruct();
        this.n = n;
    }

    private String converted() {
        StringBuilder out = new StringBuilder();
        for (int i = parsedTree.size() - 1; i > 0; i--) {
            out.append(parsedTree.get(i).get(n));
        }
        return out.toString();
    }

    public void read() {
        System.out.println(n + " written in base " + base + ": " + converted());
        System.out.println("Tree: ");
        print(tree);
        System.out.println("Parsed Tree: ");
        print(parsedTree);
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
            firstLine.add(i % base);
        }
        rTree.add(firstLine);
        for (int i = 1; i < tree.size(); i++) {
            rTree.add(elongate(tree.get(i), (int) Math.pow(base, i)));
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

    private ArrayList<Integer> elongate(ArrayList<Integer> array, int factor) {
        ArrayList<Integer> out = new ArrayList<>();
        for (Integer j : array) {
            for (int i = 0; i < factor; i++) {
                out.add(j);
            }
        }
        return out;
    }


    private void constructTree() {
        tree.add(values);
        ArrayList<Integer> next = next(values);
        while (next.size() >= 1) {
            tree.add(next);
            next = next(next);
        }
    }

    private ArrayList<Integer> next(ArrayList<Integer> line) {
        ArrayList<Integer> out = new ArrayList<>();
        for (int i = 0; i < line.size() / base; i++) {
            out.add(i % base);
        }
//        reverse(out);
        return out;
    }

    public void reverse(ArrayList<Integer> line) {
        for (int i = 0; i < line.size(); i++) {
            int temp = line.get(i);
            line.set(i, line.size() - i);
            line.set(line.size() - i, temp);
        }
    }

    private ArrayList<Integer> ArrayToList(int[] list) {
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
    private int valuesNeeded(int c) {

        //edge case
        if (c == 1 || c == 0) return 2;

        int i = c;
        while (!powerOfB(i)) {
            i++;
        }
        return i;
    }

    private boolean powerOfB(int i) {
        double log = Math.log(i) / Math.log(base);
        if (log - (int) log == 0) {
            return true;
        }
        return false;
    }
}
