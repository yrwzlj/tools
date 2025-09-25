package com.yrw_.retry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @Author: rw.yang
 * @DateTime: 2025/5/18
 **/
class Solution {

     public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }



    public static void main(String[] args) {
        Solution solution = new Solution();

        TreeNode treeNode1 = new TreeNode(4);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(1);

        treeNode1.left = treeNode2;
        treeNode2.left = treeNode3;
        treeNode2.right = treeNode4;

        solution.addOneRow(treeNode1, 1, 3);
    }

    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        Queue<TreeNode> queue = new LinkedList<>();
        int cur = 0;
        TreeNode monitor = new TreeNode(-1);
        monitor.left = root;
        queue.add(monitor);


        while (true) {
            if (cur + 1 == depth) {
                while (!queue.isEmpty()) {
                    TreeNode node = queue.poll();
                    TreeNode left = node.left;
                    TreeNode right = node.right;
                    TreeNode newLeft = new TreeNode(val);
                    TreeNode newRight = new TreeNode(val);
                    node.left = newLeft;
                    node.right = newRight;
                    newLeft.left = left;
                    newRight.right = right;
                }
                break;
            } else {
                Queue<TreeNode> temp = new LinkedList<>();
                while (!queue.isEmpty()) {
                    TreeNode node = queue.poll();
                    if (node.left != null) {
                        temp.add(node.left);
                    }
                    if (node.right != null) {
                        temp.add(node.right);
                    }
                }
                queue = temp;
            }
            cur++;
        }


        return monitor.left;
    }


}