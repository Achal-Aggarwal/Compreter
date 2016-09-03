function solve(parameterPassed)
{
var firstVariable, secondVariable, thirdVariable, fourthVariable;
firstVariable = parameterPassed && true ;
thirdVariable = false || parameterPassed;
fourthVariable = parameterPassed || thirdVariable;
return fourthVariable;
}

solve(true);