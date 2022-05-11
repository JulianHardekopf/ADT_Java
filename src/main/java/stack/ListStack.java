package stack;

import list.List;
import tuple.Tuple;

import static list.List.list;
import static tuple.Tuple.tuple;

public class ListStack<A> implements Stack<A> {
	final private List<A> list;

	private ListStack(){
		this.list = List.list();
	}

	private ListStack(List<A> list) {
		this.list = list;
	}

    // erzeugt einen leeren ListStack.
	public static <A> Stack<A> empty() {
		return new ListStack<>();
	}

    @Override
    public boolean isEmpty() {
        return this.toList().isEmpty();
    }

    // Laufzeit: O(1)
    @Override
    public Stack<A> push(A e) {
        return new ListStack<>(list.cons(e));
    }


    // Laufzeit: O(n^2)
    @Override
	public Stack<A> pushAll(List<A> xs) {
        return xs.isEmpty() ? this : new ListStack<>(List.append((xs).reverse(),list));
	}
    // Laufzeit: O(n)
    @Override
    public Stack<A> pushAll(A... es) {
        if(es.length == 0) {
            return this;
        } else {
            return new ListStack<A>(List.list(es));
        }
    }
    // Laufzeit: O(1)
    @Override
    public Stack<A> pop() {
        if(list.isEmpty()) {
            throw new Error("pop form an empty stack");
        } else {
            return new ListStack<>(list.tail());
        }
    }
    // Laufzeit: O(1)
    @Override
    public A top() {
        if(list.isEmpty()) {
            throw new Error("top form an empty stack");
        } else {
            return list.head();
        }
    }
    // Laufzeit: O(1)
    @Override
    public Tuple<A, Stack<A>> popTop() {
        if(list.isEmpty()) {
            throw new Error("top form an empty stack");
        } else {
            return tuple(top(),pop());
        }
    }
    // Laufzeit: O(n)
    @Override
    public Tuple<List<A>, Stack<A>> popTopAll() {
        if(list.isEmpty()) {
            throw new Error("top form an empty stack");
        } else {
            return tuple(new ListStack<>(list.drop(list.length())).toList(), new ListStack<A>(list));
        }
    }


    // Laufzeit: O(n)
	@Override
	public List<A> toList() {
		return list;
	}
    // Laufzeit: 0(n^2)

    //  return !xs.isEmpty() && xs.head().equals(this.head()) && xs.tail().isEqualTo(this.tail());
    @Override
    public boolean isEqualTo(Stack<A> s) {
        return this.toList().isEqualTo(s.toList());

    }
    @Override
    public boolean equals(Object o) {
       return o instanceof Stack && isEqualTo((Stack) o);

    }

    @Override
    public int size() {
        return 0;
    }



    public static void main(String[] args) {
        List list = list(1,2,34,5);
        List listtopushAll = list(2,3,45);
        Stack stacker = new ListStack(list).push(2).push(3).push(45);

        Stack stackPushallTest = new ListStack(list).pushAll(listtopushAll);

        Stack stackPushallTestdot = new ListStack(list).pushAll(1,23,4,2);



        System.out.println("Stack with mult push " + stacker.toList());
        System.out.println("Pushall " + stackPushallTest.toList());
        System.out.println("variablePushAll " +stackPushallTestdot.toList());
        System.out.println("test pop " + stackPushallTestdot.pop().toList());
        System.out.println("test Top " + stacker.top());

        System.out.println("popTop Test: " +stackPushallTestdot.popTop());

        System.out.println("popTopALl Test: " +stackPushallTestdot.popTopAll());
        System.out.println("popTop Test: " +stackPushallTestdot.toList());

        // tests zum testKlasse

        // // ∀s:Stack<A>, ∀xs:List<A> : pushAll(x:xs,s)= push(x,pushAll(xs,s)), falls s nicht leer
        // s.pushAll(xs.cons(x)).equals(s.pushAll(xs).push(x));
        List listtest = list(1,2,3);
        List listtest2 = list(1,2,3);

        int x = 4;
        Stack testStack1 = new ListStack(list()).pushAll(listtest).push(x);
        Stack testStack2 = new ListStack(list()).pushAll(listtest2).push(x);
        System.out.println("Test Equals: " + testStack1);
        System.out.println("TEst equals: " + testStack2);

        System.out.println(testStack1.equals(testStack2));

        Stack liststackempty = ListStack.empty().push(2);
        System.out.println(liststackempty.toList());

        List listtest3 = list(1,2,3);
        System.out.println(new ListStack<>(list()).pushAll(listtest3).toList());
        System.out.println(new ListStack<>(list()).pushAll(1,2,3).toList());

    }
}
