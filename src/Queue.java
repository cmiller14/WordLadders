public class Queue <E> {

    private class ListNode<E> {
        public E value;
        public ListNode<E> next;
        public ListNode() {
        }
        public ListNode(E value) {
            this.value = value;
        }
    }

    private ListNode<E> head = new ListNode<>();
    private ListNode<E> tail;
    private int size;

    public Queue() {
        this.size = 0;
        tail = head;
    }

    public boolean isEmpty() {
        boolean isEmpty = false;
        if (head.next == null) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public void enqueue(E value) {
        ListNode<E> node = new ListNode<>(value);
        ListNode<E> current = head.next;

        node.next = current; // set the node.next to the first node in queue
        head.next = node; // the head will now refer to the enqueued node

        this.size++; // increment the size of the queue

    }

    public E dequeue() {

        if (isEmpty()) {
            return null;
        } else {
            // need to traverse the queue
            ListNode<E> current = head.next;
            ListNode<E> previous = head;


            while (current.next != null) { // traversal of the queue
                previous = current;
                current = current.next;
            }

            previous.next = null; // refer the second to last node to null, removing the last node


            return current.value; // return the dequeued value
        }

    }











    
}