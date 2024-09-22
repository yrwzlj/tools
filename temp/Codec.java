import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    static public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    static public class ReverNode {
        TreeNode parent;
        int flag;
        TreeNode node;

        public ReverNode(TreeNode node) {
            this.node = node;
        }

        public ReverNode(TreeNode parent, int flag, TreeNode node) {
            this.parent = parent;
            this.flag = flag;
            this.node = node;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        treeNode.left = left;
        TreeNode right = new TreeNode(3);
        treeNode.right = right;
        TreeNode rl = new TreeNode(4);
        right.left = rl;
        TreeNode rr = new TreeNode(5);
        right.right = rr;
        Codec codec = new Codec();
        String serialize = codec.serialize(treeNode);
        System.out.println(serialize);

        TreeNode deserialize = codec.deserialize(serialize);
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        String seri = "";
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        while (!deque.isEmpty()) {
            TreeNode head = deque.poll();
            if (head != null) {
                seri = seri + head.val + " ";
                deque.add(head.left);
                deque.add(head.right);
            } else {
                seri = seri + "null ";
            }
        }

        return seri;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] split = data.split(" ");

        if ("null".equals(split[0])) {
            return null;
        }

        Deque<ReverNode> deque = new LinkedList<>();
        TreeNode root = new TreeNode(0);
        ReverNode reverNode = new ReverNode(root);
        deque.add(reverNode);

        for (int i = 0; i < split.length; i++) {
            ReverNode head = deque.poll();

            if ("null".equals(split[i])) {
                TreeNode parent = head.parent;
                if (head.flag == 0) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            } else {
                TreeNode node = head.node;
                node.val = Integer.parseInt(split[i]);

                TreeNode left = new TreeNode(0);
                node.left = left;
                deque.add(new ReverNode(node, 0, left));

                TreeNode right = new TreeNode(0);
                node.right = right;
                deque.add(new ReverNode(node, 1, right));
            }
        }

        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));