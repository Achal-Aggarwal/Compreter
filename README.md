Compreter
=========

A javascript compiler to optimize javascript before interpretation.

How to contribute to the project?

Step 1 : Fork this repo

Step 2 : Clone forked repo
To clone this repository run "git clone https://github.com/[YOUR-USER-NAME]/Compreter.git"

Step 3 : Add base remote
To add base remote run "git remote add base https://github.com/Achal-Aggarwal/Compreter.git"

Step 4 : Checkout your branch
To checkout a branch run "git branch [BRANCH-NAME]"

Step 5 : Do your thing.

Step 6 : Move your work to staging area.
To make changes to staging area run "git add ."

Step 7 : Commit your changes in git.
To commit run "git commit -m '[MEANINGFULL-MESSAGE]'"

Step 8 : Push your changes to forked repo
Run "git push origin [BRANCH-NAME]"

Step 9 : Send a Pull request to main repo.
Go to the branch you just made changes into and click on Small Green Button just left to branch selector, then click on the big send Pull Request button.

Sample
======

Input:
```javascript
function solve(parameterPassed)
{
var firstVariable, secondVariable,thirdVariable, fourthVariable;
firstVariable = false && parameterPassed;
secondVariable = parameterPassed || true && firstVariable;
thirdVariable = false || secondVariable && firstVariable;
fourthVariable = thirdVariable ;
return fourthVariable;
}
```

Output:

Original
```javascript
function solve(parameterPassed)
{
var firstVariable, secondVariable,thirdVariable, fourthVariable;
firstVariable = false && parameterPassed;
secondVariable = parameterPassed || true && firstVariable;
thirdVariable = false || secondVariable && firstVariable;
fourthVariable = thirdVariable ;
return fourthVariable;
}

solve(true);
```

UNOPTIMIZED 3 ADDRESS CODE
```javascript
function := _solve_ (_parameterPassed_)
Temp0 := false && _parameterPassed_
_firstVariable_ := Temp0
Temp1 := true && _firstVariable_
Temp2 := _parameterPassed_ || Temp1
_secondVariable_ := Temp2
Temp3 := _secondVariable_ && _firstVariable_
Temp4 := false || Temp3
_thirdVariable_ := Temp4
_fourthVariable_ := _thirdVariable_
return := _fourthVariable_
return := function _solve_
call := _solve_ ( true )
```


ONLY SPACE OPTIMIZED -- curent scenario
```javascript
function solve(parameterPassed){var fourthVariable,firstVariable,secondVariable,thirdVariable;var firstVariable,secondVariable,thirdVariable,fourthVariable;firstVariable=false&&parameterPassed;secondVariable=parameterPassed||true&&firstVariable;thirdVariable=false||secondVariable&&firstVariable;fourthVariable=thirdVariable;return fourthVariable};solve(true)
```


ONLY SPACE OPTIMIZED
```javascript
function F(A){var E,D,C,B;var B,C,D,E;B=false&&A;C=A||true&&B;D=false||C&&B;E=D;return E};F(true)
```


OTIMIZED at level 1
```javascript
function := _solve_ (_parameterPassed_)
_firstVariable_ := 0
Temp1 := 0
_secondVariable_ := _parameterPassed_ || Temp1
return := _secondVariable_ && _firstVariable_
return := function _solve_
call := _solve_ ( true )
```


OTIMIZED at level 2
```javascript
function := _solve_ (_parameterPassed_)
_secondVariable_ := _parameterPassed_ || 0
return := _secondVariable_ && 0
return := function _solve_
call := _solve_ ( true )
```


OTIMIZED at level 3
```javascript
function := _solve_ (_parameterPassed_)
return := 0
return := function _solve_
call := _solve_ ( true )
```


OTIMIZED at level 4
```javascript
function := _solve_ (_parameterPassed_)
return := 0
return := function _solve_
call := _solve_ ( true )
```


Optimized JS 
```javascript
function _solve_ ( _parameterPassed_ ){
	return 0;
}
_solve_( true  );
```


MinifiedJs 
```javascript
function H(G){return 0};H(true)
```

