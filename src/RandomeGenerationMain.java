import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class RandomGenerationMain
{
	int arity = 0;
	int n = 0;
	int d = 0;
	float p2 = 0;
	int m = 0;
	int model = 0;

	Random random = new Random();
	String[] variableArr;
	int[] domain;

	public static void main(String[] args) throws Exception
	{
		Scanner inputScanner = new Scanner(System.in);

		RandomGenerationMain randomGenerationInstance = new RandomGenerationMain();
		System.out.println("(Enter no. of variables) n:");
		randomGenerationInstance.n = inputScanner.nextInt();

		System.out.println("(Enter arity of each constraints) k:");
		randomGenerationInstance.arity = inputScanner.nextInt();

		if (randomGenerationInstance.arity > randomGenerationInstance.n)
		{
			throw new Exception("Invalid value for arity");
		}

		System.out.println("Please choose the model for random CSP generation...");
		System.out.println("1. Model B\n2. Model D\n3. Model RD\n4. Model RB\nPlease select Random CSP model:");

		randomGenerationInstance.model = inputScanner.nextInt();

//		System.out.println("[Enter alpha value: (d=n^alpha)] alpha: ");
//		float alpha = inputScanner.nextFloat();
//		int d = Integer.valueOf(String.valueOf(Math.round(Math.pow(variables, alpha))));

		switch(randomGenerationInstance.model)
		{
			case 1://B model
				System.out.println("(Enter Domain size) d: ");
				randomGenerationInstance.d = inputScanner.nextInt();

				randomGenerationInstance.handle_m_For_ModelB_And_ModelD(inputScanner);

				System.out.println("p2 value: [(no. of disallowed tuples of values t = p2 * (d ^ k)) : 0 < p2 < 1]:");
				randomGenerationInstance.p2 = getValueP2(inputScanner);

				randomGenerationInstance.handleModelBAndModelRB();
				break;
			case 2://D model
				System.out.println("(Enter Domain size) d: ");
				randomGenerationInstance.d = inputScanner.nextInt();

				randomGenerationInstance.handle_m_For_ModelB_And_ModelD(inputScanner);

				System.out.println("p2 value: [constraint tightness: 0 < p2 < 1]:");
				randomGenerationInstance.p2 = getValueP2(inputScanner);

				randomGenerationInstance.handleModelDAndModelRD();
				break;
			case 3://RD model
				randomGenerationInstance.handle_d_For_ModelRB_And_ModelRD(inputScanner);
				randomGenerationInstance.handle_m_For_ModelRB_And_ModelRD(inputScanner);

				System.out.println("(Enter p value for constraint tightness) p:");
				randomGenerationInstance.p2 = getValueP2(inputScanner);

				randomGenerationInstance.handleModelDAndModelRD();
				break;
			case 4://RB model
				randomGenerationInstance.handle_d_For_ModelRB_And_ModelRD(inputScanner);
				randomGenerationInstance.handle_m_For_ModelRB_And_ModelRD(inputScanner);

				System.out.println("(Enter p value for constraint tightness) p:");
				randomGenerationInstance.p2 = getValueP2(inputScanner);

				randomGenerationInstance.handleModelBAndModelRB();
				break;
			default:
				throw new Exception("Invalid value provided for model");
		}

	}

	private void handle_m_For_ModelB_And_ModelD(Scanner inputScanner) throws Exception
	{
		float p1 = getValueP1(inputScanner);
		double nChoose_k = factorial(n) / (factorial(arity) * factorial(n - arity));
		m = Integer.valueOf(String.valueOf(Math.round(p1 * (nChoose_k))));
	}

	private void handle_m_For_ModelRB_And_ModelRD(Scanner inputScanner) throws Exception
	{
		System.out.println("(Enter r such that m = r * n * ln(n)) r:");
		float r = inputScanner.nextFloat();
		if (r <= 0)
		{
			throw new Exception("Invalid value passed for r");
		}
		m = Integer.valueOf(String.valueOf(Math.round(r * n * Math.log(n))));
	}

	private void handle_d_For_ModelRB_And_ModelRD(Scanner inputScanner) throws Exception
	{
		System.out.println("(Enter alpha such that d = n ^ alpha) alpha: ");
		float alpha = inputScanner.nextFloat();
		if (alpha <= 0)
		{
			throw new Exception("Invalid value passed for alpha");
		}
		d = Integer.valueOf(String.valueOf(Math.round(Math.pow(n, alpha))));
	}


	private static float getValueP1(Scanner inputScanner) throws Exception
	{
		System.out.println("p1 value: [(m = p1 * (n choose k)) : 0 < p1 <= 1]:");
		float p1 = inputScanner.nextFloat();

		if (p1 < 0 || p1 > 1)
		{
			throw new Exception("Invalid value for p1");
		}
		return p1;
	}

	private static float getValueP2(Scanner inputScanner) throws Exception
	{
		float p2 = inputScanner.nextFloat();
		if (p2 < 0 || p2 >= 1)
		{
			throw new Exception("Invalid value for p2");
		}
		return p2;
	}

	private void initialiseArr()
	{
		variableArr = new String[n];
		for ( int i = 0; i < n ; i++)
		{
			variableArr[i] = "var" + (i+1);
		}
		domain = new int[d];
		for (int i = 0; i < d; i++)
		{
			domain[i] = i+1;
		}
	}

	private void handleCommonValidations() throws Exception
	{
		 if (arity < 2)
		 {
		 	throw new Exception("Invalid value for Arity");
		 }
		 else if (n < 2)
		 {
			 throw new Exception("Invalid value for no. of variables");
		 }
		 else if (d < 2)
		 {
			 throw new Exception("Invalid value for domain size");
		 }
	}

	private void handleModelBAndModelRB() throws Exception
	{
		handleCommonValidations();
		initialiseArr();
		int t = Integer.valueOf(String.valueOf(Math.round(p2 * Math.pow(d, arity))));

		int constraintCnt = 0;
		RandomCsp randomCsp = new RandomCsp();

		while(constraintCnt < m)
		{
			List<String> constraintVarList = getConstraintVariables();
			List<List<Integer>> allPossibleTupleList = getAllPossibleTupleValues(constraintVarList);
			int allowedTupleSize = allPossibleTupleList.size();

			int disAllowedTupleCnt = 0;
			List<List<Integer>> disallowedTupleValList = new ArrayList<>();

			while(disAllowedTupleCnt < t)
			{
				List<Integer> disallowedTuple = allPossibleTupleList.get(random.nextInt(allowedTupleSize));

				if (!disallowedTupleValList.contains(disallowedTuple))
				{
					disallowedTupleValList.add(disallowedTuple);
					disAllowedTupleCnt++;
				}
			}

			randomCsp.constraintList.add(new Constraint(constraintVarList, disallowedTupleValList));
			constraintCnt++;
		}

		randomCsp.print();
	}

	private void handleModelDAndModelRD() throws Exception
	{
		handleCommonValidations();
		initialiseArr();

		int constraintCnt = 0;
		RandomCsp randomCsp = new RandomCsp();

		while(constraintCnt < m)
		{
			List<String> constraintVarList = getConstraintVariables();
			List<List<Integer>> allPossibleTupleList = getAllPossibleTupleValues(constraintVarList);

			List<List<Integer>> disallowedTupleValList = new ArrayList<>();

			for (int i = 0; i < allPossibleTupleList.size(); i++)
			{
				List<Integer> tuple = allPossibleTupleList.get(i);
				double randomVal = Math.random();
//				System.out.println("Random:" + randomVal);
				if (randomVal < p2 && !disallowedTupleValList.contains(tuple))
				{
					disallowedTupleValList.add(tuple);
				}
			}

			randomCsp.constraintList.add(new Constraint(constraintVarList, disallowedTupleValList));
			constraintCnt++;
		}

		randomCsp.print();
	}

	private List<List<Integer>> getAllPossibleTupleValues(List<String> constraintVarList)
	{
		List<List<Integer>> tupleValueList = new ArrayList<>();
		int[] tupleHandlingArr = new int[constraintVarList.size()];

		while(true)
		{
			List<Integer> tuple = new ArrayList<>();
			for(int j = 0; j < tupleHandlingArr.length; j++)
			{
				tuple.add(domain[tupleHandlingArr[j]]);
			}
			tupleValueList.add(tuple);
			if (incrementTupleHandlingArr(tupleHandlingArr))
			{
				break;
			}
		}
		return tupleValueList;
	}

	private boolean incrementTupleHandlingArr(int[] disallowedTupleHandlingArr)
	{
		int length = disallowedTupleHandlingArr.length;
		for(int i= length - 1; i >= 0; i--)
		{
			disallowedTupleHandlingArr[i]++;
			if (disallowedTupleHandlingArr[i] == domain.length)
			{
				if (i == 0)
					return true;
				disallowedTupleHandlingArr[i] = 0;
			}
			else
				break;
		}
		return false;
	}

	private List<String> getConstraintVariables()
	{
		int constraintArityCnt = 0;
		List<String> constraintVarList = new ArrayList<>();

		while(constraintArityCnt < arity)
		{
			int constraintIndex = random.nextInt(n);
			String constraintVar = variableArr[constraintIndex];
			if (constraintVarList.contains(constraintVar) && (model == 1 || model == 2))//only for model B and model D, constrain var scope is selected without repetition
			{
				continue;
			}
			constraintVarList.add(constraintVar);
			constraintArityCnt++;
		}
		return constraintVarList;
	}

	static int factorial(int n)
	{
		if (n < 0)
		{
			System.out.println("Invalid n passed for Factorial computation.");
			return 1;
		}
		// single line to find factorial
		return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);

	}

	private class RandomCsp
	{
		public List<Constraint> constraintList;

		RandomCsp()
		{
			this.constraintList = new ArrayList<>();
		}

		public void print()
		{
			for(Constraint constraint : constraintList)
			{
				System.out.print("\nConstraint Var:");
				for (int i = 0; i < constraint.constraintVar.size(); i++)
				{
					String constraintVar = constraint.constraintVar.get(i);
					System.out.print(constraintVar);
					if (i != constraint.constraintVar.size()-1)
						System.out.print(",");
				}
				System.out.print("\nDisallowed Tuple:");
				System.out.print('[');
				List<List<Integer>> disAllowedTuplesList = constraint.disallowedTuples;
				for (int i = 0; i < disAllowedTuplesList.size(); i++)
				{
					List<Integer> disAllowedTuple = disAllowedTuplesList.get(i);
					System.out.print('(');
					for(int j = 0; j < disAllowedTuple.size(); j++)
					{
						int disallowedVal = disAllowedTuple.get(j);
						System.out.print(disallowedVal);
						if (j != disAllowedTuple.size()-1)
							System.out.print(',');
					}
					System.out.print(")");
				}
				System.out.print("]");
				System.out.println(("\n"));
			}
		}

		public void setConstraintList(List<Constraint> constraintList)
		{
			this.constraintList = constraintList;
		}
	}

	private class Constraint
	{
		public List<String> constraintVar;
		public List<List<Integer>> disallowedTuples;

		Constraint()
		{
			this.constraintVar = new ArrayList<>();
			this.disallowedTuples = new ArrayList<>();
		}

		Constraint(List<String> constraintVar, List<List<Integer>> disallowedTuples)
		{
			this.constraintVar = constraintVar;
			this.disallowedTuples = disallowedTuples;
		}
	}
}