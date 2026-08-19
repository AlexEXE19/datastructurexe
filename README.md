# Data Structures from Scratch

Core data structures implemented from scratch in Java, as a study exercise ahead of technical interviews - no `java.util` collections used internally 
(the implementations only rely on plain arrays/objects and on each other, e.g. `LinkedStack`/`LinkedQueue` are built on top of `SinglyLinkedList`, `Graph` is built on top of `HashMap`/`HashSet`/`DynamicArray`).

## What's implemented

- **Linear** (`core.linear`): `DynamicArray`, `SinglyLinkedList`, `DoublyLinkedList`, `ArrayStack`, `ArrayQueue`, `LinkedStack`, `LinkedQueue`
- **Trees** (`core.hierarchical`): `BST` (binary search tree), `AvlTree` (self-balancing BST), `BinaryHeap` (max-heap)
- **Hashing** (`core.hash`): `HashMap` (separate chaining), `HashSet` (built on top of `HashMap`)
- **Graph** (`core.graph`): `Graph` (adjacency list, BFS/DFS)

> Note: `HashMap` and `HashSet` intentionally share names with `java.util.HashMap`/`HashSet`. That's deliberate (the point of the exercise is a from-scratch implementation
> of the same API surface), not a naming mistake - just be aware of it if you're used to `import java.util.*` picking up the standard library versions instead.

## Running it

```bash
mvn compile                                    # build
mvn test                                       # run the test suite
java -cp target/classes com.alexexe19.datastructures.Main   # run the demo
```

`Main.java` is a small smoke-test that exercises a few structures directly (mainly useful for a quick manual sanity check) - the actual correctness checks live in `src/test/java`.

## Tests

Each structure has a corresponding `*Test.java` under `src/test/java`, mirroring the main package structure, written with JUnit 5.

## Known design notes

- `BST` keeps both an iterative (`insert`/`delete`) and a recursive (`insertV2`/`deleteV2`) implementation side by side on purpose, as a comparison exercise - not leftover duplicate code.
- `DynamicArray.toArray()` returns `Object[]`, not `E[]`. This is intentional: due to Java's generic type erasure, a "real" `E[]` can't actually be created at runtime, and pretending otherwise causes a `ClassCastException` the moment a caller assigns the result to a concretely-typed array variable. For typed iteration, `DynamicArray` implements `Iterable<E>` instead (`for (E e : myDynamicArray)`), which doesn't have this problem. `HashMap.keySet()`/`values()` avoid the same issue by returning `List<K>`/`List<V>`.

