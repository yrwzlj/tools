class RangeModule {

    int max = 1_00;

    class Node {
        int val;
        int add = -1;
        Node l,r;
    }

    Node root = new Node();

    public RangeModule() {

    }

    public void addRange(int left, int right) {
        add(root, 0, max, left, right - 1);
    }

    public void removeRange(int left, int right) {
        remove(root, 0, max, left, right - 1);
    }

    public boolean queryRange(int left, int right) {
        if (query(root, 0, max, left, right - 1) == 1) {
            return true;
        }

        return false;
    }

    private void add(Node node, int begin, int end, int left, int right) {
        if (begin >= left && end <= right) {
            node.val = 1;
            node.add = 1;
            return;
        }

        pushDown(node);

        int mid = (begin + end) / 2;
        if (mid >= left) {
            add(node.l, begin, mid, left, right);
        }
        if (mid + 1 <= right) {
            add(node.r, mid + 1, end, left, right);
        }

        pushUp(node);
    }

    private void pushUp(Node node) {
        node.val = Math.min(node.l.val, node.r.val);
    }

    private void pushDown(Node node) {
        if (node.l == null) {
            node.l = new Node();
        }
        if (node.r == null) {
            node.r = new Node();
        }

        if (node.add == -1) {
            return;
        }

        node.l.add = node.add;
        node.l.val = node.add;

        node.r.add = node.add;
        node.r.val = node.add;

        node.add = -1;
    }


    private int query(Node node, int begin, int end, int left, int right) {
        if (begin >= left && end <= right) {
            return node.val;
        }

        pushDown(node);

        int mid = (begin + end) / 2;
        int ans = 1;
        if (mid >= left) {
            ans = query(node.l, begin, mid, left, right);
        }
        if (mid + 1 <= right) {
            ans = Math.min(ans, query(node.r, mid + 1, end, left, right));
        }

        return ans;
    }

    private void remove(Node node, int begin, int end, int left, int right) {
        if (begin >= left && end <= right) {
            node.val = 0;
            node.add = 0;
            return;
        }

        pushDown(node);

        int mid = (begin + end) / 2;
        if (mid >= left) {
            remove(node.l, begin, mid, left, right);
        }
        if (mid + 1 <= right) {
            remove(node.r, mid + 1, end, left, right);
        }

        pushUp(node);
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */