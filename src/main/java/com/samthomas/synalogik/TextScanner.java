package com.samthomas.synalogik;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;

public class TextScanner
{

	
public static void main(String[] args)
{
	String r = ""; //the result string
	try
	{
		TextScanner scanner = new TextScanner();
		r = scanner.scan(""); //Scan the Text.txt file. We also write the result to a Result.txt file in the same location as Text.txt.
	}
	catch (IOException e)
	{
		e.printStackTrace();
	}
	
	System.out.println(r); //output the result
}
	

/*
 * Here, we scan a text file given by the fileName, and return a string.
 * If we input "", we use the Text.txt file in the same folder as the jar. In this case, we also write the result to a Result.txt file in the same folder.
 */
public String scan(String fileName) throws IOException
{
	String r = "";	//the final result string
	String dir = System.getProperty("user.dir");				//find the location of the project/jar file
	String fileLoc;												//the full address, including the file name
	
	boolean isDefaultCase = fileName.equals("");
	if (isDefaultCase)
	{
		fileLoc = dir + "\\Text.txt";							//if we scan without an argument, we use the Text.txt file in the same folder as the jar.
	}
	else
	{
		fileLoc = dir + "\\src\\test\\resources\\" + fileName;	//else, we use the relevant file in 'Testing Files'
	}
	

	File file = new File(fileLoc);
	Hashtable<Integer, Integer> hash = createHashtable(file);	//create a hash table of word lengths
	int nKeys = hash.keySet().size();							//count the number of distinct keys in the hash table
	
	int[] countArray = getWordAndLetterCount(hash, nKeys);		//an array containing word and letter count
	int wordCount = countArray[0];								//the total number of words
	int letterCount = countArray[1];							//the total number of letters
	
	r = r.concat("Word count = " + wordCount + "\n");			//add the global word count
	
	double avg = (double) letterCount / wordCount;				//compute average word length
	avg = (double)Math.round(avg * 1000d) / 1000d;				//round to 3 decimal places
	r = r.concat("Average word length = " + avg + "\n");		//add the average word length
	
	r = addWordCounts(hash, nKeys, r);							//add the word counts
	r = addMostFrequentLength(hash, nKeys, r);					//add the most frequent length(s)
	
	if (isDefaultCase)	//if we're running from a jar file and not testing, we want to write to a result file
	{
		PrintWriter pWriter = new PrintWriter(dir + "\\Results.txt");
		pWriter.print(r);
		pWriter.close();
	}
	
	return r;
}


/*
 * Here, we create a hash table with the frequency of each word length.
 * This step is where we decide how to count the letters of each word.
 */
private static Hashtable<Integer, Integer> createHashtable(File file)
{
	Hashtable<Integer, Integer> hash = new Hashtable<Integer, Integer>();
	try (Scanner scanner = new Scanner(file))
	{
		while (scanner.hasNext())
		{
			String s = scanner.next();		//next string
			s = s.replaceAll("[^a-zA-Z0-9&,./$£%-]", ""); 	//remove most special characters
			
			
			boolean isDate = s.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]")
				|| s.matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9]");
			if (!isDate						//Excluding dates such as 10/12/1997, we'd like to count forward-slash
			  && s.contains("/"))			//separated terms, such as black/white, as separate words.
			{
				String[] subStrings = s.split("/");		//split the string using / as a delimiter
				int n = subStrings.length;				//there are n substrings in the array
				for (int i=0;i<n;i++)		//for each substring, we compute the length and input into the hash table
				{
					int length = lengthWithoutPunctuation(subStrings[i]);
					if (hash.putIfAbsent(length, 1) != null)	//if this is the first word of this length, putIfAbsent inputs "1" into the table and return null
					{
						hash.compute(length, (key, val) -> val + 1);	//else, we increase the associated frequency by 1
					}
				}
				
				continue;
			}
			
			int length = lengthWithoutPunctuation(s);
			if (length==0)		//this is so we don't include singular "-" dashes in the word count or table.
			{
				continue;
			}
			if (hash.putIfAbsent(length, 1) != null)	//if this is the first word of this length, putIfAbsent inputs "1" into the table and return null
			{
				hash.compute(length, (key, val) -> val + 1);	//else, we increase the associated frequency by 1
			}
		}
		
		scanner.close();
	}
	
	catch (IOException e)
	{
		e.printStackTrace();
		return null;
	}
	
	return hash;
}


/*
 * If the final character of our string is .,- we don't include it in the length.
 */
private static int lengthWithoutPunctuation(String s)
{
	if ((s.endsWith("."))
		|| (s.endsWith(","))
		|| (s.endsWith("-")))
	{
		return s.length() - 1;
	}
	return s.length();
}


/*
 * A method that returns the total word and letter count of the whole file.
 */
private static int[] getWordAndLetterCount(Hashtable<Integer, Integer> hash, int nKeys)
{
	int wordCount = 0;
	int letterCount = 0;
	int keysCounted = 0;	//cumulative number of keys from the table that we've counted
	int i = 1;
	while (keysCounted != nKeys)	//iterate until we've counted all of the keys
	{
		if (hash.containsKey(i))
		{
			int value = hash.get(i);					//the number of words length i
			wordCount = wordCount + value;				//count each of these words
			letterCount = letterCount + (value * i);	//count each of the letters of these words
			keysCounted++;
		}
		i++;
	}
	
	int[] countArray = new int[]{wordCount, letterCount};
	return countArray;
}


/*
 * Here, we add all of the word counts, given a hash table and the number of keys.
 */
private static String addWordCounts(Hashtable<Integer, Integer> hash, int nKeys, String r)
{
	int keysCounted = 0;
	int i = 1;
	while (keysCounted != nKeys)	//we loop until we've accounted for all of the keys
	{
		if (hash.containsKey(i))
		{
			int value = hash.get(i);
			r = r.concat("Number of words of length " + i + " is " + value + "\n");
			keysCounted++;
		}
		i++;
	}
	
	return r;
}


/*
 * Here, we calculate and add the most frequent length, given a hash table and the number of keys.
 */
private static String addMostFrequentLength(Hashtable<Integer, Integer> hash, int nKeys, String r)
{
	Collection<Integer> values = hash.values();		//the collection of frequencies from the table
	int max = Collections.max(values);				//get the highest frequency
	r = r.concat("The most frequently occuring word length is " + max + ", for word lengths of ");
	
	int keysCounted = 0;
	int i = 1;
	boolean isFirstLength = true;	//the first length doesn't need an "&" printed before it
	while (keysCounted != nKeys)	//we loop until we've accounted for all of the keys
	{
		if (hash.containsKey(i))	//if there is a word of length i
		{
			int value = hash.get(i);	//get the frequency
			if (value == max)
			{
				if (isFirstLength)
				{
					r = r + i;
					isFirstLength = false;
				}
				else
				{
					r = r + " & " + i;
				}
			}
			keysCounted++;
		}
		i++;
	}
	
	return r;
}


}