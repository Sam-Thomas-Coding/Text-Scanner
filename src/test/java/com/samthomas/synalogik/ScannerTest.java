package com.samthomas.synalogik;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ScannerTest
{
	//In the following tests, we access .txt files from src\test\resources to ensure that certain test cases are processed correctly.
	
	/*
	 * First, the example text that we were given should return the specified text.
	 */
	@Test
	public void theExampleTextShouldReturnTheGivenString()
	{
		
		String result = ""; //the result
		TextScanner scanner = new TextScanner();
		try
		{
			result = scanner.scan("Original Example.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("file not able to be located");
		}
		
		String s = "Word count = 9\n" + 
				"Average word length = 4.556\n" + 
				"Number of words of length 1 is 1\n" + 
				"Number of words of length 2 is 1\n" + 
				"Number of words of length 3 is 1\n" + 
				"Number of words of length 4 is 2\n" + 
				"Number of words of length 5 is 2\n" + 
				"Number of words of length 7 is 1\n" + 
				"Number of words of length 10 is 1\n" + 
				"The most frequently occuring word length is 2, for word lengths of 4 & 5";
		
		assertEquals(s, result); //ensure output is correct
	}
	
	
	/*
	 * We should be able to distinguish between decimal points and commas within (or before) numbers, and punctuation.
	 * For example: 1,000,000.00 should be a single word with . and , counted as letters.
	 */
	@Test
	public void numericalPunctuationShouldBeCounted()
	{
		
		String result = ""; //the result
		TextScanner scanner = new TextScanner();
		try
		{
			result = scanner.scan("Numbers.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("file not able to be located");
		}
		
		String s = "Word count = 4\n" + 
				"Average word length = 3.5\n" + 
				"Number of words of length 2 is 1\n" + 
				"Number of words of length 3 is 1\n" + 
				"Number of words of length 4 is 1\n" + 
				"Number of words of length 5 is 1\n" + 
				"The most frequently occuring word length is 1, for word lengths of 2 & 3 & 4 & 5";
		
		assertEquals(s, result); //ensure output is correct
	}

	
	/*
	 * We should ignore : , . at the end of 'words'.
	 * For example, "cat." is a three-letter word.
	 * & is counted as a word, as in the example given.
	 */
	@Test
	public void punctuationShouldBeIgnoredAsSpecified()
	{
		
		String result = ""; //the result
		TextScanner scanner = new TextScanner();
		try
		{
			result = scanner.scan("Punctuation.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("file not able to be located");
		}
		
		String s = "Word count = 11\n" + 
				"Average word length = 4.636\n" + 
				"Number of words of length 1 is 2\n" + 
				"Number of words of length 2 is 1\n" + 
				"Number of words of length 3 is 1\n" + 
				"Number of words of length 4 is 2\n" + 
				"Number of words of length 5 is 1\n" + 
				"Number of words of length 6 is 1\n" + 
				"Number of words of length 7 is 2\n" + 
				"Number of words of length 11 is 1\n" + 
				"The most frequently occuring word length is 2, for word lengths of 1 & 4 & 7";
		
		assertEquals(s, result); //ensure output is correct
	}
	
	
	/*
	 * Dates, as in the example, should count as a single word.
	 */
	@Test
	public void datesShouldCountAsASingleWord()
	{
		
		String result = ""; //the result
		TextScanner scanner = new TextScanner();
		try
		{
			result = scanner.scan("Dates.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("file not able to be located");
		}
		
		String s = "Word count = 2\n" + 
				"Average word length = 9.0\n" + 
				"Number of words of length 8 is 1\n" + 
				"Number of words of length 10 is 1\n" + 
				"The most frequently occuring word length is 1, for word lengths of 8 & 10";
		
		assertEquals(s, result); //ensure output is correct
	}
	
	
	/*
	 * Forward-slash separated terms such as black/white should be counted as separate terms
	 */
	@Test
	public void forwardSlashShouldSeparateWords()
	{
		
		String result = ""; //the result
		TextScanner scanner = new TextScanner();
		try
		{
			result = scanner.scan("Forward Slash.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("file not able to be located");
		}

		String s = "Word count = 2\n" + 
				"Average word length = 5.0\n" + 
				"Number of words of length 5 is 2\n" + 
				"The most frequently occuring word length is 2, for word lengths of 5";
		
		assertEquals(s, result); //ensure output is correct
	}
	
	
	/*
	 * There are a few way to interpret dashes "-".
	 * The four cases sho\
	 */
	@Test
	public void dashesShouldBeProccessedCorrectly()
	{
		
		String result = ""; //the result
		TextScanner scanner = new TextScanner();
		try
		{
			result = scanner.scan("Dashes.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("file not able to be located");
		}
		
		String s = "Word count = 3\n" + 
				"Average word length = 5.667\n" + 
				"Number of words of length 3 is 1\n" + 
				"Number of words of length 4 is 1\n" + 
				"Number of words of length 10 is 1\n" + 
				"The most frequently occuring word length is 1, for word lengths of 3 & 4 & 10";
		
		assertEquals(s, result); //ensure output is correct
	}
	
}
