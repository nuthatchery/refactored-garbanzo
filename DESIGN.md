## Foundations and terminology

### Identity

An identity $i \in I$ is a member of a global set of identities.

* URI
* Path

### Model

A model $`m \in M = <N,B,L>`$ has

* A set of nodes or elements $`N \subseteq I`$

* A set of labeled branches $B \subseteq N \times I \times N$. Branches are denoted by bulleted arrows,
  `a *-l-> b`, for a branch from a to b labeled l. The label may be omitted. Branches form a tree or DAG.
  
* A set of labeled links $L \subseteq I \times I \times I$. Links are denoted by dotted arrows,
  `a ··l··> b`, for a link from a to b labeled l. The label may be omitted. Links need not
  form a tree or DAG, and may link outside the model.

The branches and links may not overlap, e.g., $B \intersection L = \empty$
  
We may also talk about the set of edges $E = B \union L$, using plain labeled arrows for edges
that may be either branches or links: `a -l-> b`.

A *path* is a series of edges, `a -l1-> … -lk-> z`.

* Identities are nodes in only one model. I.e., $\forall m1,m2 \in M . i \in I_m1 \and i \in I_m2 \iff m1 = m2$.

  
   