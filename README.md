# Random CSPs

Following for 4 Random CSP models are supported:
1. Model B (k, n, d, p1, p2)
2. Model D (k, n, d, p1, p2)
3. Model RD (k, n, alpha, r, p)
4. Model RB (k, n, alpha, r, p)


# Model B:
 k- arity of each constraints
 
 n - number of variables
 
 d - domain size of each constraints
 
 p1 - value between 0 < p1 <= 1 
      such that m = p1 * (n choose k)
 
 p2 - value between 0 < p2 < 1
      such that t = p2 * (d ^ k)
      
 Random CSP in model B is generated in two steps:
 1. Each one of the 'm' constraints (without repetition) is formed by selecting 'k' distinct variable.
 2. For each constraint, 't' distinct disallowed tuples are selected.
 
 
 # Model D:
 k- arity of each constraints
 
 n - number of variables
 
 d - domain size of each constraints
 
 p1 - value between 0 < p1 <= 1 
      such that m = p1 * (n choose k)
 
 p2 - value between 0 < p2 < 1
      such that every possible (d ^ k) tuples of values is disallowed with probability 'p2'
      
 Random CSP in model D is generated in two steps:
 1. Each one of the 'm' constraints (without repetition) is formed by selecting 'k' distinct variable.
 2. For each constraint, each (d ^ k) tuples values is marked as disallowed tuple value with probability 'p2'
 
 
  # Model RD:
 k- arity of each constraints
 
 n - number of variables
 
 alpha - value greater than Zero
         such that d = (n ^ alpha)
 
 r - value greater than Zero
     such that m = r * n * ln(n)
 
 p - value between 0 < p < 1
     such that every possible (d ^ k) tuples of values is disallowed with probability 'p'
      
 Random CSP in model RD is generated in two steps:
 1. Each one of the 'm' constraints (with repetition) is formed by selecting 'k' distinct variable.
 2. For each constraint, each (d ^ k) tuples values is marked as disallowed tuple value with probability 'p'
 
 
 
  # Model RB:
 k- arity of each constraints
 
 n - number of variables
 
 alpha - value greater than Zero
         such that d = (n ^ alpha)
 
 r - value greater than Zero
     such that m = r * n * ln(n)
 
 p - value between 0 < p < 1
     such that t = p2 * (d ^ k)
      
 Random CSP in model RD is generated in two steps:
 1. Each one of the 'm' constraints (with repetition) is formed by selecting 'k' distinct variable.
 2. For each constraint, 't' distinct disallowed tuples are selected.
 
 
 # Input Format:
 Based on the model chosen initially, corresponding Input values will be asked and based on the provided input, Output will be generated.
 
**sample Input:**
 
  (Enter no. of variables) n:
  
  5
  
  (Enter arity of each constraints) k:
  
  3
  
  Please choose the model for random CSP generation...
  1. Model B
  2. Model D
  3. Model RD
  4. Model RB
  
  Please select Random CSP model:
  
  4
  
  (Enter alpha such that d = n ^ alpha) alpha: 
  
  0.34
  
  (Enter r such that m = r * n * ln(n)) r:
  
  0.5
  
  (Enter p value for constraint tightness) p:
  
  0.65
 
 # Output Format:
 1. Constarin variables:
 2. Disallowed tuples:
 
 **sample output:**
 
 Constraint Var:var4,var3,var5

Disallowed Tuple:[(2,2,1)(1,2,1)(1,2,2)(2,1,2)(2,1,1)]


Constraint Var:var1,var4,var2

Disallowed Tuple:[(1,1,1)(2,2,1)(1,1,2)(2,1,1)(1,2,2)]


Constraint Var:var5,var2,var1

Disallowed Tuple:[(1,2,2)(1,1,1)(2,2,2)(1,2,1)(1,1,2)]


Constraint Var:var1,var4,var3

Disallowed Tuple:[(2,2,1)(2,1,2)(2,1,1)(2,2,2)(1,2,2)]

 
