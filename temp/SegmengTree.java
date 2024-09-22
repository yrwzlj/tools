public class SegmengTree {

    static class TreeNode{
        public int right;
        public int left;
        public Long val;
        public Long flag;
        public TreeNode(int right, int left, Long val, Long flag) {
            this.right = right;
            this.left = left;
            this.val = val;
            this.flag = flag;
        }
    }

    static TreeNode[] tree;

    private static void add(int l, int r, Long k, int cur) {
        if (l <= tree[cur].right && r >= tree[cur].left) {
            tree[cur].val += k * (tree[cur].left - tree[cur].right + 1);
            tree[cur].flag += k;
            return;
        }
        spread(cur);
        int mid = tree[cur].right + tree[cur].left >> 1;
        if (mid >= l) {
            add(l,r,k,cur * 2);
        }
        if (mid < r) {
            add(l, r, k,cur * 2 + 1);
        }
        tree[cur].val = tree[cur * 2].val + tree[cur * 2 + 1].val;
    }

    private static Long sum(int l, int r, int cur) {
        if (l <= tree[cur].right && r >= tree[cur].left) {
            return tree[cur].val;
        }
        spread(cur);
        int mid = tree[cur].right + tree[cur].left >> 1;
        Long left = 0L;
        Long right = 0L;
        if (mid >= l) {
            left = sum(l, r, cur * 2);
        }
        if (mid < r) {
            right = sum(l, r, cur * 2 + 1);
        }
        return left + right;
    }

    private static void build(int l, int r, Long[] num, int cur) {
        tree[cur].right = l;
        tree[cur].left = r;
        if (l == r) {
            tree[cur].val = num[l];
            return;
        }
        int mid = l + r >> 1;
        build(l,mid,num,cur * 2);
        build(mid + 1,r,num,cur * 2 + 1);
        tree[cur].val = tree[cur * 2].val + tree[cur * 2 + 1].val;
    }

    private static void spread(int cur) {
        if (tree[cur].flag != 0L) {
            tree[cur * 2].val += tree[cur].flag * (tree[cur * 2].left - tree[cur * 2].right + 1);
            tree[cur * 2 + 1].val += tree[cur].flag * (tree[cur * 2 + 1].left - tree[cur * 2 + 1].right + 1);
            tree[cur * 2].flag += tree[cur].flag;
            tree[cur * 2 + 1].flag += tree[cur].flag;
            tree[cur].flag = 0L;
        }
    }
}