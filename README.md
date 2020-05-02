# WordCounter

This program counts the occurrences of words in a text (.txt) file and prints out an ordered list of the top 100 most frequently occuring words within that file along with the number of times each of the words listed occurred.

## Using WordCounter

### Prerequisites

- Java (JDK 1.8 or above) [Download Java](https://www.java.com/en/download/)
- Apache Maven (3.6.3 recommended) [Download Maven](https://maven.apache.org/download.cgi)

### Running WordCounter

Clone or download this repository.

In a command prompt or terminal, navigate to the root directory of WordCounter.

```
>cd C:\path\to\WordCounter
```

Build and install the project with Maven by running the command ‘mvn install’. (This will also run all unit tests in the project)

```
>mvn install
```

Run WordCounter from the root directory with the command ‘java -jar target\wordcounter-0.1.0.jar’. (Additional command line arguments may be provided to use custom inputs. See [Using Custom Inputs](#using-custom-inputs) below)

```
>java -jar target\wordcounter-0.1.0.jar
```

## Default Behavior

By default this program counts the 100 most frequently occuring words in an EBook version of *Moby Dick* by Herman Melville, with the exclusion of words provided in an included ‘stopwords’ file.
Custom inputs can be used for both the stopwords file and the file to count words in. For details please see the ‘Use with Custom Inputs’ section below.

### Default Behavior Specifics:

- The counting begins at the first line of the file and counts every word through the last line of the file.
- The order in the resulting output is based on the number of occurrences of each word counted with the word occurring the most times appearing first. Words that occur the same number of times are sorted alphabetically, a-z.
- By default, plural words and their singular counterparts are treated as separate words with separate counts (i.e. ‘ship’ and ‘ships’ may both appear in the list each with their own unique count).
- By default, the count is not case sensitive (i.e. ‘Chase’ used as a proper noun and ‘chase’ used as a verb will be counted as the same word).
- By default, hyphenated words (words separated by a hyphen (-) and containing no other whitespace) are counted as one word with a unique count (i.e. ‘mast-heads’ is counted as its own word with a unique count and is unassociated with the count for ‘mast’ or ‘heads’).
- By default, words containing an apostrophe will be counted as unique words (i.e. “clients'” the plural possessive noun will be counted separately from “clients” the plural noun, which will also be counted separately from “client's” the possessive noun). ***HOWEVER***, left and right single quotes are treated as whitespace. This means that using a left or right single quote as an apostrophe within a word will result in the word being split around the single quote (i.e. “client’s” will be counted as two words, one occurrence of ‘client’ and one occurrence of ‘s’). In the default text for this application, left and right single quotes are used as opening and closing single quotes in addition to being used as apostrophes while no actual apostrophes are used; therefore, the default behavior with the default text results in no possessive nouns or contractions being counted and some undesired behavior such as “he’ll” causing “ll” to be counted as a word.
- With the exception of apostrophes and hyphens all other non-letter characters (e.g. dashes (—), double-quotes (“), etc.) will be treated as whitespace and ignored with any adjacent words being counted on their own (i.e. ‘word—text’ will be counted as one occurrence of ‘word’ and one occurrence of ‘text’).

### Possible Problems with Default Apostrophe and Hyphen handling:

- Any words with a leading or trailing hyphen or apostrophe preceded or followed by whitespace will be counted as a unique word (i.e. ‘-text’ will be counted as a unique word while ‘- text’ will be counted as ‘text’ with the ‘- ‘ being ignored).
- Currently, hyphens that are surrounded by whitespace will be counted as a word (i.e. ‘text - text’ will be counted as two occurrences of the word ‘text’ and one occurrence of the word ‘-’).
- Multiple hyphens in a row will result in a new unique word for that number of hyphens (i.e. an occurrence such as ‘text--text’ is separate from ‘text-text’).
 
## Using Custom Inputs

When running WordCounter from the command line, one or two optional arguments may be provided (providing more than two will result in an IllegalArgumentException). Each argument is a file path to a .txt file on the machine where the program is being run. WordCounter can be run with no command line arguments, with only one argument, or with two arguments provided.
- If the first argument is provided then the program will print the top 100 (or fewer if there are not 100 unique words) words and their occurrences within the file at the provided file path instead of using the default file, *Moby Dick* by Herman Melville. If the file path is incorrect a FileNotFoundException can be expected. If the file type is not .txt an IllegalArgumentException will be thrown.
- If the second argument is provided the application will use the file found at the provided path to populate the ‘stopwords’ (words that will not be included in the counting) rather than using the default stopwords. If the file path is incorrect a FileNotFoundException can be expected. If the file type is not .txt an IllegalArgumentException will be thrown. Formatting for the custom stopwords file should be as follows:
	- Each individual ‘stopword’ should appear on a new line. Only the first word on any given line will be recognized with all subsequent words being ignored.
	- Commented lines are permitted, denoted by a ‘#’ as the first character of the line. Any line beginning with ‘#’ will be ignored.

## Future Possibilities

Future improvements to this application could include:
- additional command line arguments to change the default behavior for handling things such as:
	- plural words (count ‘ships’ and ‘ship’ as the same word, contrary to current default behavior)
	- case sensitive (count ‘Ship’ and ‘ship’ as separate words, contrary to current default behavior)
- create a UI and include the ability to upload files to count the words in
