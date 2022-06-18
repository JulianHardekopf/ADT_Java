package tree.bst;

import fpinjava.Result;
import list.List;
import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;

import static stack.postfix.Postfix.eval;

public class TreeJqwikTest {


    @Data
    Iterable<Tuple.Tuple3<Integer,Tree<Integer>,  Tree<Integer>>> insert() {
        return Table.of(
                Tuple.of( 1,Tree.tree(List.list(2,1,3)), Tree.tree(List.list(2,1,3)))
                , Tuple.of( 50 ,Tree.tree(List.list(2,1,3)),  Tree.tree(List.list(2,1,3,50))));
    }

    @Property
    @FromData("insert")
    void insertTest(@ForAll Integer x ,@ForAll Tree<Integer> tree , @ForAll Tree<Integer> ergebnis) {
        Assertions.assertEquals(tree.insert(x).toString(), ergebnis.toString());
    }

    @Data
    Iterable<Tuple.Tuple3<Integer,Tree<Integer>,  Boolean>> member() {
        return Table.of(
                Tuple.of(2, Tree.tree(2,3,1), true),
                 Tuple.of( 50 ,Tree.tree(List.list(2,1,3)),  false));
    }

    @Property
    @FromData("member")
    void memberTest(@ForAll Integer x ,@ForAll Tree<Integer> tree , @ForAll boolean ergebnis) {
        Assertions.assertEquals(tree.member(x), ergebnis);
    }


    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> size() {
        return Table.of(
                Tuple.of( Tree.tree(List.list(2,1,3)),3)
                , Tuple.of(Tree.tree(List.list(2,1)),2));
    }
    @Property
    @FromData("size")
    void memberTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.size(), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2< Tree<Integer> ,Integer>> height() {
        return Table.of(
                Tuple.of( Tree.tree(List.list(1,2,3,4,5)), 4)
                , Tuple.of(Tree.tree(List.list(2,1,3)),1));
    }
    @Property
    @FromData("height")
    void heightTest(@ForAll Tree<Integer> tree,@ForAll Integer  ergebnis) {
        Assertions.assertEquals(tree.height(), ergebnis);
    }

    @Data
    Iterable<Tuple.Tuple3<Integer,Tree<Integer>,  Tree<Integer>>> delete() {
        return Table.of(
                Tuple.of( 1,Tree.tree(List.list(2,1,3)), Tree.tree(List.list(2,3)))
                , Tuple.of( 4 ,Tree.tree(List.list(2,1,3)),  Tree.tree(List.list(2,1,3))));
    }

    @Property
    @FromData("delete")
    void deleteTest(@ForAll Integer x ,@ForAll Tree<Integer> tree , @ForAll Tree<Integer> ergebnis) {
        Assertions.assertEquals(tree.delete(x).toString(), ergebnis.toString());
    }
    @Data
    Iterable<Tuple.Tuple3<Integer,Tree<Integer>,  Integer>> findEq() {
        return Table.of(
                Tuple.of(2, Tree.tree(List.list(3,2,1)), 2),
                Tuple.of(1, Tree.tree(List.list(1,2,3)), 1));
    }

    @Property
    @FromData("findEq")
    void findEqTest(@ForAll Integer x ,@ForAll Tree<Integer> tree , @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.findEq(x), ergebnis);
    }

    @Data
    Iterable<Tuple.Tuple3<Integer,Tree<Integer>, Result<Integer>>> lookupEq() {
        return Table.of(
                Tuple.of(2, Tree.tree(List.list(3,2,1)), Result.success(2)),
                Tuple.of(1, Tree.tree(List.list(1,2,3)), Result.success(1)));
    }

    @Property
    @FromData("lookupEq")
    void lookupEqTest(@ForAll Integer x ,@ForAll Tree<Integer> tree , @ForAll Result<Integer> ergebnis) {
        Assertions.assertEquals(tree.lookupEq(x), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> findMin() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),1)
                , Tuple.of(Tree.tree(List.list(2,5,3)),2));
    }
    @Property
    @FromData("findMin")
    void findMinTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.findMin(), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> findMax() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),3)
                , Tuple.of(Tree.tree(List.list(2,5,3)),5));
    }
    @Property
    @FromData("findMax")
    void findMaxTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.findMax(), ergebnis);
    }

    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, List<Integer>>> inorder() {
        return Table.of(
                Tuple.of(Tree.tree(3,2,1), List.list(1,2,3)),
                Tuple.of(Tree.tree(4,3,6), List.list(3,4,6)));

    }
    @Property
    @FromData("inorder")
    void inorderTest(@ForAll Tree<Integer> tree,  @ForAll List<Integer> ergebnis) {
        Assertions.assertEquals(tree.inorder().toString(), ergebnis.toString());
    }

    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> sizeLeaf() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),2)
                , Tuple.of(Tree.tree(List.list(2,1,3,4,5)),2));
    }
    @Property
    @FromData("sizeLeaf")
    void sizeLeafTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.sizeLeaf(), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> sizeInner() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),1)
                , Tuple.of(Tree.tree(List.list(2,1,3,4,5)),3));
    }
    @Property
    @FromData("sizeInner")
    void sizeInnerTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.sizeInner(), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> sizeHalf() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),0)
                , Tuple.of(Tree.tree(List.list(2,1,3,4,5)),2));
    }
    @Property
    @FromData("sizeHalf")
    void sizeHalfTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.sizeHalf(), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> sizeFull() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),1)
                , Tuple.of(Tree.tree(List.list(2,1,3,4,5)),1));
    }
    @Property
    @FromData("sizeFull")
    void sizeFullTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.sizeFull(), ergebnis);
    }
    @Data
    Iterable<Tuple.Tuple2<Tree<Integer>, Integer>> sizeEmpty() {
        return Table.of(
                Tuple.of(Tree.tree(List.list(2,1,3)),4)
                , Tuple.of(Tree.tree(List.list(2,1,3,4,5)),6));
    }
    @Property
    @FromData("sizeEmpty")
    void sizeEmptyTest(@ForAll Tree<Integer> tree,  @ForAll Integer ergebnis) {
        Assertions.assertEquals(tree.sizeEmpty(), ergebnis);
    }
}
