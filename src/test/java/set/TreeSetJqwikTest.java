package set;

import list.List;
import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import tree.bst.Tree;

public class TreeSetJqwikTest extends ADTSortedSetJqwikTest {
	
	@Override
	protected <A extends Comparable<A>> SortedSet<A> empty() {
		return TreeSet.empty();
	}	
	
	@Override
	protected <A extends Comparable<A>> SortedSet<A> fromList(List<A> list){
		return TreeSet.fromList(list);
	}

	@Override
	protected <A extends Comparable<A>> SortedSet<A> set(A... list){
		return TreeSet.set(list);
	}

    @Property
    <A extends Comparable<A>> boolean lookupMax(@ForAll("sets") TreeSet<A> s){
        Assume.that(!s.isEmpty());
        return s.any(x -> s.lookupMax().exists(y -> (x.compareTo(y) == 0)));
    }
    @Property
    <A extends Comparable<A>> boolean lookupMin(@ForAll("sets") TreeSet<A> s){
        Assume.that(!s.isEmpty());
        return s.any(x -> s.lookupMin().exists(y -> (x.compareTo(y) == 0)));
    }
}
