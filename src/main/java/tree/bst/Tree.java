package tree.bst;

import fpinjava.Result;
import list.List;

import static list.List.list;

public abstract class Tree <A extends Comparable<A>>  {

    @SuppressWarnings("rawtypes")
    private final static Tree EMPTY = new Empty();
    public abstract A value();
    abstract Tree<A> left();
    abstract Tree<A> right();
    public abstract Tree<A> insert(A a);
    public abstract boolean member(A a);
    public abstract int size();
    public abstract int height();
    public abstract Tree<A> delete(A a);
    protected abstract Tree<A> removeMerge(Tree<A> ta);
    public abstract boolean isEmpty();
    public abstract A findEq(A x);
    public abstract Result<A> lookupEq(A x);
    public abstract A findMin();
    public abstract A findMax();
    public abstract List<A> inorder();
    public abstract int sizeLeaf();
    public abstract int sizeInner();
    public abstract int sizeHalf();
    public abstract int sizeFull();
    public abstract int sizeEmpty();
    public abstract Result<A> lookupMax();
    public abstract Result<A> lookupMin();


    private static class Empty<A extends Comparable<A>> extends Tree<A> {
        @Override
        public A value() {
            throw new IllegalStateException("value() called on empty");
        }
        @Override
        Tree<A> left() {
            throw new IllegalStateException("left() called on empty");
        }
        @Override
        Tree<A> right() {
            throw new IllegalStateException("right() called on empty");
        }

        @Override
        public Tree<A> insert(A a) {
            return new T<>(empty(), a, empty());
        }

        @Override
        public boolean member(A a) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }
        // -1 damit height != size
        @Override
        public int height() {
            return -1;
        }

        @Override
        public Tree<A> delete(A a) {
            return this;
        }

        @Override
        protected Tree<A> removeMerge(Tree<A> ta) {
            return ta;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public A findEq(A x) {
            return null;
        }

        @Override
        public Result<A> lookupEq(A x) {
            return Result.empty();
        }

        @Override
        public A findMin() {
            throw new IllegalStateException("empty Tree");
        }

        @Override
        public A findMax() {
            throw new IllegalStateException("empty Tree");
        }

        @Override
        public List<A> inorder() {
            return List.list();
        }

        @Override
        public int sizeLeaf() {
            return 0;
        }

        @Override
        public int sizeInner() {
            return 0;
        }

        @Override
        public int sizeHalf() {
            return 0;
        }

        @Override
        public int sizeFull() {
            return 0;
        }

        @Override
        public int sizeEmpty() {
            return 0;
        }

        @Override
        public Result<A> lookupMax() {
            return Result.empty();
        }

        @Override
        public Result<A> lookupMin() {
            return Result.empty();
        }

        @Override
        public String toString() {
            return "E";
        }
    }
    private static class T<A extends Comparable<A>> extends Tree<A> {
        private final Tree<A> left;
        private final Tree<A> right;
        private final A value;
        private T(Tree<A> left, A value, Tree<A> right) {
            this.left = left;
            this.right = right;
            this.value = value;
        }
        @Override
        public A value() {
            return value;
        }
        @Override
        Tree<A> left() {
            return left;
        }
        @Override
        Tree<A> right() {
            return right;
        }
        // The method returns 0 if the string is equal to the other string.
        // A value less than 0 is returned if the string is less than the other string (less characters)
        // and a value greater than 0 if the string is greater than the other string (more characters).
        @Override
        public Tree<A> insert(A a) {
            return a.compareTo(this.value) < 0
                    ? new T<>(left.insert(a), this.value, right)
                    : a.compareTo(this.value) > 0
                    ? new T<>(left, this.value, right.insert(a))
                    : new T<>(this.left, a, this.right);
        }

        @Override
        public boolean member(A a) {
            return a.compareTo(this.value) < 0
                    ? left.member(a)
                    : a.compareTo(this.value) == 0 || right.member(a);
        }

        @Override
        public int size() {
            return 1 + left().size() + right().size();
        }

        @Override
        public int height() {
            return 1 + Math.max(left.height(), right.height());
        }

        @Override
        public Tree<A> delete(A a) {
            if (a.compareTo(this.value) < 0) {
                return new T<>(left.delete(a), value, right);
            } else if (a.compareTo(this.value) > 0) {
                return new T<>(left, value, right.delete(a));
            } else {
                return left.removeMerge (right);
            }
        }

        @Override
        protected Tree<A> removeMerge(Tree<A> ta) {
            if (ta.isEmpty()) {
                return this;
            }
            if (ta.value().compareTo(value) < 0) {
                return new T<>(left.removeMerge(ta), value, right);
            } else if (ta.value().compareTo(value) > 0) {
                return new T<>(left, value, right.removeMerge(ta));
            }
            throw new IllegalStateException("We shouldn't be here");
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public A findEq(A x) {
            return x.compareTo(this.value) < 0
                    ? left.findEq(x)
                    : x.compareTo(this.value) > 0
                    ? right.findEq(x)
                    : this.value;

        }

        @Override
        public Result<A> lookupEq(A x) {
            return x.compareTo(this.value) < 0
                    ? left.lookupEq(x)
                    : x.compareTo(this.value) > 0
                    ? right.lookupEq(x)
                    : Result.success(this.value);
        }

        @Override
        public A findMin() {
            return left.isEmpty() ? this.value : this.left.findMin();
        }

        @Override
        public A findMax() {
            return right.isEmpty() ? this.value : this.right.findMax();
        }

        // Laufzeit: 0(n^2)
        @Override
        public List<A> inorder() {
            List<A> list = List.append(this.left().inorder(),List.list(this.value()));
            return List.append(list, this.right().inorder());
        }

        //O(n)
        @Override
        public int sizeLeaf() {
            if(left.isEmpty() && right.isEmpty()) {
                return 1;
            }
            return right.sizeLeaf() + left.sizeLeaf();
        }
        //O(n)
        @Override
        public int sizeInner() {
            return left.isEmpty() && right.isEmpty() ? 0
                    : left.sizeInner() + right.sizeInner() + 1;
        }
        //O(n)
        @Override
        public int sizeHalf() {
            return (left.isEmpty() && !right.isEmpty() || right.isEmpty() && !left.isEmpty()
                    ?  1 + left.sizeHalf() + right.sizeHalf() : left.sizeHalf() + right.sizeHalf());
        }
        //O(n)
        @Override
        public int sizeFull() {
            return (!right.isEmpty() && !left.isEmpty() ? 1 + right.sizeFull() + left.sizeFull()
                    : right.sizeFull() + left.sizeFull()) ;
        }
        //O(n)
        @Override
        public int sizeEmpty() {
            if(left.isEmpty() && !right.isEmpty())  {
                return 1 + right.sizeEmpty();
            }
            if(!left.isEmpty() && right.isEmpty())  {
                return 1 + left.sizeEmpty();
            }
            if(left.isEmpty() && right.isEmpty())  {
                return 2 ;
            }

            return  right.sizeEmpty() + left.sizeEmpty();

        }

        @Override
        public Result<A> lookupMax() {
            return right.lookupMax().orElse(() -> Result.success(value));
        }

        @Override
        public Result<A> lookupMin() {
            return left.lookupMin().orElse(() -> Result.success(value));
        }

        @Override
        public String toString() {
            return String.format("(T %s %s %s)", left, value, right);
        }
    }
    @SuppressWarnings("unchecked")
    public static <A extends Comparable<A>> Tree<A> empty() {
        return EMPTY;
    }

    public static <A extends Comparable<A>> Tree<A> tree(List<A> list) {
        return List.foldl(x -> x::insert, empty(), list);
    }
    @SafeVarargs
    public static <A extends Comparable<A>> Tree<A> tree(A... as) {
        return tree(list(as));
    }

    public static void main(String[] args) {
        List list = list(2,1,6,4,2);
        List list2 = list(5,6,3,44,22,3,1,342,4,2);
        Tree tree = tree(list);
        System.out.println(tree);
        System.out.println();
        System.out.println(tree.size());
        System.out.println("height: " + tree.height());
        System.out.println("findEQflase: " + tree.findEq(7));
        System.out.println(tree.lookupEq(2));
        System.out.println(tree.lookupEq(4));
        //System.out.println(tree.findMin());
        Tree tree2 = tree.insert(2).insert(0).insert(3);
        Tree tree3 = tree(list2);
        System.out.println(tree2);
        System.out.println("tree left (empty): " + tree2.left());
        System.out.println("tree right: " + tree2.right());
        System.out.println("tree right value: " + tree2.right().value());
        System.out.println(tree2.left().value());
        System.out.println(tree.left());
        System.out.println(tree2.findMin());
        System.out.println(tree3.findMin());
        System.out.println(tree3.findMax());
        System.out.println(tree3.inorder());
        System.out.println(tree3.left());
        Tree tree4 = tree(2,1,3,4,5);
        System.out.println("findmIN: " + tree4.findMin());
        System.out.println(tree4.sizeLeaf());
        System.out.println(tree4.sizeInner());
        System.out.println(tree4.sizeHalf());
        System.out.println(tree4.sizeFull());
        System.out.println(tree4.sizeEmpty());
        Tree tree5 = new Empty();
        System.out.println(tree5);
        Tree tree6 = tree(7,1,2,3);
        System.out.println(tree6.lookupMax());
        System.out.println(tree6.lookupMin());
    }
}


