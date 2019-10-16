# PasteFile (Client)

This code follows the *Minecraft Forge* installation methodology. It will apply
some small patches to the vanilla MCP source code, giving you and it access 
to some of the data and functions you need to build a successful mod.

Note also that the patches are built against "unrenamed" MCP source code (aka
srgnames) - this means that you will not be able to read them directly against
normal code.

## Installation (Standalone)

__Step 1:__ Open your command-line and browse to the folder where you extracted the zip file.

__Step 2:__ Once you have a command window up in the folder that the downloaded material was placed, type:  
*__for Windows:__* `gradlew setupDecompWorkspace`   
*__for Linux/Mac OS:__* `./gradlew setupDecompWorkspace`

__Step 3:__ After all that finished, you're left with a choice.
For eclipse, run "gradlew eclipse" (./gradlew eclipse if you are on Mac/Linux)

If you preffer to use *IntelliJ*, the steps are a little different:
1. Open *IDEA*, and import project.
2. Select your `build.gradle` file and have it import.
3. Once it's finished you must close IntelliJ and run the following command:  
`gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)

__Step 4:__ The final step is to open Eclipse and switch your workspace to `/eclipse/`  
(if you use IDEA, it should automatically start on your project).

If at any point you are missing libraries in your IDE, or you've run into problems you can run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything (this does not effect your code) and then start the processs again.

Should it still not work, 
Refer to #ForgeGradle on *EsperNe*t for more information about the gradle environment.

__Tips:__  
* If you do not care about seeing Minecraft's source code you can replace `setupDecompWorkspace` with one of the following:  
`setupDevWorkspace`: Will patch, deobfusicated, and gather required assets to run minecraft, but will not generated human readable source code.  
`setupCIWorkspace`: Same as Dev but will not download any assets. This is useful in build servers as it is the fastest because it does the least work.
  
* When using Decomp workspace, the Minecraft source code is NOT added to your workspace in a editable way. Minecraft is treated like a normal Library. Sources are there for documentation and research purposes and usually can be accessed under the 'referenced libraries' section of your IDE.

## Installation (Forge source)

*MinecraftForge* ships with this code and installs it as part of the forge
installation process, no further action is required on your part.

## References

* *LexManos*' Install Video:  
https://www.youtube.com/watch?v=8VEdtQLuLO0&feature=youtu.be

* For more details update more often refer to the Forge Forums:
http://www.minecraftforge.net/forum/index.php/topic,14048.0.html
