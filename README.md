# Text-Scanner

This is a text scanner. When ran, the final .jar file searches for a Text.txt file in the same folder as it. If such a file exists, it outputs information about the file to the console and creates a Result.txt file with this information.

I have provided the finished .jar file, a .bat file to run the .jar file easily, and a Text.txt file containing the bible as an example. Place these files in the same folder and run the batch file to test.

Finally, I have included the Maven project file as a .zip, containing the java code in full, and some .txt files that are used in testing.



## Rules Defining a Word:

Dates in the formats xx/xx/xxxx and xx/xx/xx are counted as a single word, as shown in the example.

Slash-separated words such as black/white are counted as seperate words, as are both numbers in a fraction.

Dash-seperated words are counted as a single word, and the dash is counted in the length.

Decimal points are counted towards the length of a number, as are commas, minus signs, percentages, dollars, and pounds (such as in -£1,000.00).

& is counted as a 1-letter word.

Punctuation is otherwise not counted towards the length of a word.



## Installation Instructions:

1) Open the provided "text-scanner" folder as Maven project in Eclipse.

2) Right click text-scanner in Project Explorer. Select "Run As >> 4: Maven build"

3) In "Goals", type "clean". Run. In console, check for success.

4) Repeat 2. In Goals, type "compile". Run. In console, check for success.

5) Repeat 2. In Goals, type "test-compile". Run. In console, check for success.

6) Repeat 2. In Goals, type "test". Run. In console, check for success.

7) Repeat 2. In Goals, type "install". Run. In console, check for success.

**At this stage, there should be a "text-scanner-1.0.0.jar" file contained in the "target" folder of the project.

8) Copy to any empty folder on your computer, along with the provided RUN.bat file, and a Text.txt file that you would like to process.

9) Run the RUN.bat file.

**The result should be displayed, and a Result.txt should be created with the same result.
