import java.util.ArrayList;
import java.util.List;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        List<ListNode> list = new ArrayList<>();
        ListNode root = head;

        while (head != null) {
            list.add(head);
            head = head.next;
        }

        k = k % list.size();
        if (k == 0) {
            return head;
        }
        ListNode newHead = list.get(list.size() - k);
        list.get(list.size() - k - 1).next = null;
        list.get(list.size() - 1).next = root;

        return newHead;
    }
}