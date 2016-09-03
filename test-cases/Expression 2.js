function solve(parameterPassed)
{
var firstVariable, secondVariable,thirdVariable, fourthVariable;
firstVariable = false && parameterPassed;
secondVariable = parameterPassed || true && firstVariable;
thirdVariable = true || secondVariable && firstVariable;
fourthVariable = thirdVariable ;
return fourthVariable;
}

solve(true); 