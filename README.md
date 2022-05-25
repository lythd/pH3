# pH3

## License

Licensed under the GNU Lesser General Public License v3.0.
This means basically if you distribute it you have to credit this project, and also distribute the source code with the object code. This does not mean it needs to be public, just whenever the object code is distributed the source code should be distributed too. However please read the license to understand exactly what it entails, as my explanation is not a substitute for the license document. The license is stored in COPYING and COPYING.LESSER.

## Overview

The pH series of projects is obviously primarily a bunch of chess engines. These are made to be open source for everyone to use, and also the code is built very modularly. You can use just the chess game without the engine easily, you can add your own graphics system, or add fancy chess rules, whatever you want. This is due to the very robust framework, which allows lots of compatability. Downloads for the runnable program, aswell as the library, will be provided.

## Community Contributions

If you want to make community contributions that is great, please just format your code as I do. If not I will just have to take the time to do it myself, so it just wastes my time.

Any new files should be headed with:
> /* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and
>  \* credit this project. For more info read 'COPYING' and 'COPYING.LESSER'. This is legally binding, and includes use as a library.   
>  \* Author: @GithubUsername   
>  */

Any new functions should be headed with:
> /* Author: @GithubUsername \*/

Any new or modified lines of code should be done like this:
> /* Author: @GithubUsername \*/ return false;

Any removed code should be done like this:
> /* Remover: @GithubUsername   
>  \* int i = 0;   
>  \* while(i==0) function();   
>  \*/

Please also comment your code properly as I have done, I normally have two types of comments:
> //This type of comment has proper grammar, and is used to explain broadly what the code does, aswell as explain limitations and usage.
> //This is done on its own line, and normally has empty lines between any code it does not refer to.   
      
> i++; // this type of comment after code that just quickly clarifies things

I don't want to update the main branch after the final release, so before then your changes can be included in it, otherwise they will go in another branch. Just because they aren't in the main branch doesn't mean they won't be included in the next pH, or in the downloads.
