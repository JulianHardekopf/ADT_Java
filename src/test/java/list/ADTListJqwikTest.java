package list;

import fpinjava.Function;
import fpinjava.Option;
import fpinjava.Result;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import tuple.Tuple;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import static list.JqwikUtils.diffInts;
import static list.JqwikUtils.unEqualIntLists;
import static list.List.*;
import static org.assertj.core.api.Assumptions.assumeThat;

public class ADTListJqwikTest {

  final int maxLen = 10;

  @Provide
  <A> Arbitrary<A> as(){
    return (Arbitrary<A>) ints();
  }

  private Arbitrary<Integer> ints() {
    return Arbitraries.integers().between(0,100);
  }

  private Arbitrary<Boolean> bools() {
    return Arbitraries.of(true,false);
  }
  
  private Arbitrary<String> strings() {
    return Arbitraries.strings().withCharRange('a','z').ofMinLength(2).ofMaxLength(5);
  }

  @Provide
  <A> Arbitrary<List<A>> lists(){
    return JqwikUtils.lists(as(),maxLen);
  }

  @Provide
  Arbitrary<List<Integer>> intLists(){
    return JqwikUtils.lists(ints(),maxLen);
  }

  @Provide
  Arbitrary<List<Boolean>> boolLists(){
    return JqwikUtils.lists(bools(),maxLen);
  }

  @Provide
  <A extends Serializable> Arbitrary<Tuple<List<A>,List<A>>> equalLists(){
    return JqwikUtils.equalLists(as(),maxLen);
  }

  @Provide
  Arbitrary<Tuple<List<Integer>,List<Integer>>> unEqualLists() {
    final int minInt = 0;
    final int maxInt = 4;
    final int minLen = 1;
    final int maxLen = 4;
    return unEqualIntLists(minInt,maxInt, minLen, maxLen);
  }

  @Property  // nicht aendern!
  <A> boolean equalListsAreEqual(@ForAll("equalLists") Tuple<List<A>,List<A>> t){
    return t.fst.equals(t.snd);
  }

  @Property  // nicht aendern!
  boolean listsFromUnEqualArraysAreUnEqual(@ForAll Integer[] a1, @ForAll Integer[] a2){
    Assume.that(!Arrays.equals(a1,a2));
    return !list(a1).equals(list(a2));
  }

  @Property // nicht aendern!
  <A> boolean listsFromUnEqualJavaListsAreUnEqual(@ForAll java.util.List<A> a1, @ForAll java.util.List<A> a2){
    Assume.that(!a1.equals(a2));
    return !list(a1).equals(list(a2));
  }


  @Property // nicht aendern!
  <A> boolean unEqualListsAreUnEqual(@ForAll("unEqualLists") Tuple<List<A>,List<A>> t){
    return !t.fst.equals(t.snd);
  }

  // isEmpty((empty) = true
  @Example
  boolean isEmpty_empty() {
    return list().isEmpty();
  }

  // length(empty) = 0
  @Example
  boolean length_empty() {
    return list().length() == 0;
  }

  //  ∀x:A, ∀l:List<A> : isEmpty(cons(x,l)) = false
  @Property
  <A> boolean isEmpty_cons(@ForAll("as") A x, @ForAll("lists") List<A> l){
    return !l.cons(x).isEmpty();
  }


  // ∀x:A : elem(x,empty)	= false
  @Property
  <A> boolean elem_empty(@ForAll("as") A x){
    return !x.equals(list());
  }

  //  ∀x:A, ∀l:List<A> : elem(x,cons(x,l)) = true
  @Property
  <A> boolean elem_cons(@ForAll("as") A x, @ForAll("lists") List<A> l){
    return l.cons(x).elem(x);
  }

  // ∀x:A, ∀l:List<A> : head(cons(x,l)) = x
  @Property
  <A> boolean head_cons(@ForAll("as") A x, @ForAll("lists") List<A> l){
    return l.cons(x).head() == x;
  }

  // ∀x:A, ∀l:List<A> :	tail(cons(x,l)) = l
  @Property
  <A> boolean tail_cons(@ForAll("as") A x, @ForAll("lists") List<A> l){
    return l.cons(x).tail() == l;
  }

  // ∀x:A, ∀l:List<A> : length(cons(x,l)) = length(l)+1
  @Property
  <A> boolean length_cons(@ForAll("as") A x, @ForAll("lists") List<A> l){
    return l.cons(x).length() == l.length() + 1;
  }

  // ∀l:List<A> : length(tail(l)) = length(l) - 1, falls l nicht leer
  @Property
  <A> boolean length_tail(@ForAll("lists") List<A> l) {
      Assume.that(!l.isEmpty());
      return l.tail().length() == l.length() - 1;
  }

  // or ([]) 		= false
  @Example
  boolean or_empty(){
    return !or(list());
  }

  // and ([]) 		= true
  @Example
  boolean and_empty(){
    return and(list());
  }

  // ∀f: A → Boolean
  // any(f,[])	= False
  @Property
  <A> boolean any_empty(@ForAll Function<A,Boolean> f){
    return !List.<A>list().any(f);
  }

  // ∀f: A → Boolean
  // all(f,[])	= True
  @Property
  <A> boolean all_empty(@ForAll Function<A,Boolean> f){
      return List.<A>list().all(f);
  }

  // ∀l: List<A> :  any(x -> x==minimum(l),l)   = true, falls l nicht leer
  @Property
  boolean minimum_any(@ForAll("intLists") List<Integer> l){
      Assume.that(!l.isEmpty());
      return l.any(x -> minumum(l).equals(x));
  }

  //  ∀l: List<A> : all(x -> x>=minimum(l),l)   = true, falls l nicht leer
  @Property
  boolean minimum_all(@ForAll("intLists") List<Integer> l){
      Assume.that(!l.isEmpty());
      return l.all(x -> minumum(l) <= x);
  }

  // Formulieren Sie ein Axiom analog zu minimum_any!
  @Property
  boolean maximum_any(@ForAll("intLists") List<Integer> l){
      Assume.that(!l.isEmpty());
      return l.any(x -> maximum(l).equals(x));
  }

  //  Formulieren Sie ein Axiom analog zu minimum_all!
  @Property
  boolean maximum_all(@ForAll("intLists") List<Integer> l){
      Assume.that(!l.isEmpty());
      return l.any(x -> maximum(l) >= x);
  }

  // ∀ l: List, ∀ start,end: Integer :	minimum([start..end])  = start, falls end >= start
  @Property // Definieren Sie einen geeigneten IntRange für die Parameter!
  boolean minimum_range(@ForAll @IntRange(min = 1, max = 10)  int start, @ForAll @IntRange(min = 10, max = 20) int end) {
    return minumum(range(start, end)) == start;
  }

  //  Formulieren Sie ein Axiom analog zu minimum_range!
  @Property
  boolean maximum_range(@ForAll @IntRange(min = 1, max = 10) int start, @ForAll @IntRange(min = 10, max = 20) int end) {
    return maximum(range(start, end)) == end;
  }

  // ∀l: List<A>, ∀f: A → Boolean
  // any(f,xs)	= or (map(f, xs))
  @Property
  <A> boolean any_or(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
    return l.any(f) == or(l.map(f));
  }

  // ∀l: List<A>, ∀f: A → Boolean
  // all(f,xs)   = and(map(f, xs))
  @Property
  <A> boolean all_and(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
    return l.all(f) == and(l.map(f));
  }

  // ∀l: List<Boolean>
  // not(or(l)) = and(map(not,l)) -- Gesetz von De Morgan
  @Property
  boolean or_and(@ForAll("boolLists") List<Boolean> l){
    return !or(l) == and(l.map(Function.not()));
  }

  // ∀l: List<Boolean>
  // not(and(l))= or(map(not,l))   -- Gesetz von De Morgan
  @Property
  boolean and_or(@ForAll("boolLists") List<Boolean> l){
    return !and(l) == or(l.map(Function.not()));
  }

  // ∀l: List<A>, ∀f: A → Boolean
  // not(any(f,l))	= all (not.f, l) -- Gesetz von De Morgan
  @Property
  <A> boolean any_all(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
    return !l.any(f) == l.all(Function.not().compose(f));
  }

  // ∀l: List<A>, ∀f: A → Boolean
  // not(all(f,l)) = any(not.f, l) -- Gesetz von De Morgan
  @Property
  <A> boolean all_any(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
    return !l.all(f) == l.any(Function.not().compose(f));
  }

  // ∀l: List<A>, ∀n: Integer
  // take(n,l) ++ drop(n,l) = l
  @Property
  <A> boolean take_drop(@ForAll("lists") List<A> l, @ForAll int n){
    return List.append(l.take(n), l.drop(n)).equals(l);
  }

  // ∀l: List<A>, ∀f: A → Boolean
  // takeWhile(f,l) ++ dropWhile(f,l) = l
  @Property @Report(Reporting.GENERATED)
  <A> boolean takeWhile_dropWhile(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
    return List.append(l.takeWhile(f), l.dropWhile(f)).equals(l);
  }

  // ∀l: List<A>, ∀f: A → Boolean
  // finde(f,l) = any(f,l) ? head(filter(f,l)) : null
  // null-Pointer-sicher vergleichen!

  @Property @Report(Reporting.GENERATED)
  <A> boolean finde(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
      return l.finde(f) == (l.any(f) ? l.filter(f).head() : null);
  }

    // ∀l : List<A>, ∀x : A
    //headOption(cons(x,l)) =  Result.success(x)
    //headOption(empty)     =  Result.empty()
    @Property
    <A> boolean headOptionCons(@ForAll("as") A x, @ForAll("lists") List<A> l) {
      return l.cons(x).headOption().equals(Result.success(x));
    }
    @Property
    <A> boolean headOptionEmpty(@ForAll("as") A x, @ForAll("lists") List<A> l) {
        return list().headOption().equals(Result.empty());
    }

    //axioms ∀l : List<A>, ∀f : A->Boolean
    // find(f,l) = headOption(filter(f,l))
    @Property @Report(Reporting.GENERATED)
    <A> boolean headOptionFind(@ForAll("lists") List<A> l, @ForAll Function<A,Boolean> f){
      return l.find(f).equals(l.filter(f).headOption());
    }

}