package first;

import java.util.Random;

public class SkipList<T extends Comparable<T>> {

    class Node {
        public Node above;
        public Node below;
        public Node next;
        public Node prev;
        public int key;

        public Node(int key) {
            this.key = key;
            this.above= null;
            this.below = null;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;

    private final int NEG_INFINITY = Integer.MIN_VALUE;
    private final int POS_INFINITY = Integer.MAX_VALUE;

    private int heightOfSkipList = 0;

    public Random random = new Random();

    public SkipList() {
        head = new Node(NEG_INFINITY);
        tail = new Node(POS_INFINITY);
        head.next = tail;
        tail.prev = head;
    }

    public Node skipSearch(int key) {
        Node n = head;

        while (n.below != null) {
            n = n.below;

            while (key >= n.next.key) {
                n = n.next;
            }
        }
        return n;
    }

    public Node skipInsert(int key) {
        Node position = skipSearch(key);
        Node q;

        int level = -1;
        int numberOfHeads = -1;

        if (position.key == key)
            return position;

        do {
            numberOfHeads++;
            level++;

            canIncreaseLevel(level);

            q = position;

            while (position.above == null)
                position = position.prev;

            position = position.above;

            q = insertAfterAbove(position, q, key);

        } while (random.nextBoolean() == true);

        return q;
    }

    public Node remove(int key) {
        Node nodeToBeRemoved = skipSearch(key);

        if (nodeToBeRemoved.key != key) {
            System.out.println("There is no such node to remove.");
            return null;
        }

        removeReferencesToNode(nodeToBeRemoved);

        while (nodeToBeRemoved != null) {
            removeReferencesToNode(nodeToBeRemoved);

            if (nodeToBeRemoved.above != null)
                nodeToBeRemoved = nodeToBeRemoved.above;
            else
                break;

        }

        return nodeToBeRemoved;
    }

    private void removeReferencesToNode(Node nodeToBeRemoved) {
        Node afterNodeToBeRemoved = nodeToBeRemoved.next;
        Node beforeNodeToBeRemoved = nodeToBeRemoved.prev;

        beforeNodeToBeRemoved.next = afterNodeToBeRemoved;
        afterNodeToBeRemoved.prev = beforeNodeToBeRemoved;
    }

    private void canIncreaseLevel(int level) {
        if (level >= heightOfSkipList) {
            heightOfSkipList++;
            addEmptyLevel();
        }
    }

    private void addEmptyLevel() {
        Node newHeadNode = new Node(NEG_INFINITY);
        Node newTailNode = new Node(POS_INFINITY);


        newHeadNode.next = newTailNode;
        newHeadNode.below = head;
        newTailNode.prev = newHeadNode;
        newTailNode.below = tail;

        head.above = newHeadNode;
        tail.above = newTailNode;

        head = newHeadNode;
        tail = newTailNode;
    }

    private Node insertAfterAbove(Node position, Node q, int key) {
        Node newNode = new Node(key);
        Node nodeBeforeNewNode = position.below.below;

        setBeforeAndAfterReferences(q, newNode);
        setAboveAndBelowReferences(position, key, newNode, nodeBeforeNewNode);

        return newNode;
    }

    private void setBeforeAndAfterReferences(Node q, Node newNode) {
        newNode.next = q.next;
        newNode.prev = q;
        q.next.prev = newNode;
        q.next = newNode;
    }

    private void setAboveAndBelowReferences(Node position, int key, Node newNode, Node nodeBeforeNewNode) {
        if (nodeBeforeNewNode != null) {
            while (true) {
                if (nodeBeforeNewNode.next.key != key)
                    nodeBeforeNewNode = nodeBeforeNewNode.next;
                else
                    break;

            }

            newNode.below = nodeBeforeNewNode.next;
            nodeBeforeNewNode.next.above = newNode;
        }

        if (position != null) {
            if (position.next.key == key)
                newNode.above = position.next;
        }
    }

    public void printSkipList() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nSkipList starting with top-left most node.\n");

        Node starting = head;

        Node highestLevel = starting;
        int level = heightOfSkipList;

        while (highestLevel != null) {
            sb.append("\nLevel: ").append(level).append("\n");

            while (starting != null) {
                sb.append(starting.key);

                if (starting.next != null)
                    sb.append(" : ");

                starting = starting.next;
            }


            highestLevel = highestLevel.below;
            starting = highestLevel;
            level--;
        }

        System.out.println(sb);
    }
}