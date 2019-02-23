import java.util.Objects;

public class Alphabet
{
  private static final char[] LETTERS;
  private static final char[] UCASE_LETTERS;
  private static final char[] LCASE_LETTERS;
  private final CharSet charSet;
  private final char[] chars;
  private final char firstChar;

  static
  {
    LETTERS = new char[52];
    UCASE_LETTERS = new char[26];
    LCASE_LETTERS = new char[26];

    for (char index = 'a'; index <= 'z'; ++index)
    {
      LCASE_LETTERS[index - 'a'] = index;
    }

    for (char index = 'A'; index <= 'Z'; ++index)
    {
      UCASE_LETTERS[index - 'A'] = index;
    }

    System.arraycopy(UCASE_LETTERS, 0, LETTERS, 0, UCASE_LETTERS.length);
    System.arraycopy(LCASE_LETTERS, 0, LETTERS, UCASE_LETTERS.length, LCASE_LETTERS.length);
  }

  public enum CharSet
  {LowerCase, UpperCase, LowerCaseAndUpperCase}

  public Alphabet(CharSet charSet)
  {
    this.charSet = Objects.requireNonNull(charSet);
    switch (charSet)
    {
      case LowerCase:
      {
        chars = LCASE_LETTERS;
        break;
      }
      case UpperCase:
      {
        chars = UCASE_LETTERS;
        break;
      }
      case LowerCaseAndUpperCase:
      {
        chars = LETTERS;
        break;
      }
      default:
      {
        throw new IllegalArgumentException("Unknown CharSet");
      }
    }

    firstChar = chars[0];
  }

  private void validateChar(char c)
  {
    if (charSet == CharSet.LowerCaseAndUpperCase)
    {
      if ((c < LCASE_LETTERS[0] || c > LCASE_LETTERS[LCASE_LETTERS.length - 1]) ||
          (c < UCASE_LETTERS[0] || c > UCASE_LETTERS[UCASE_LETTERS.length - 1]))
      {
        throw new IllegalArgumentException(c + " is not in the character set");
      }
    }
  }

  private void validateLowerCaseChar(char c)
  {
    if (c < LCASE_LETTERS[0] || c > LCASE_LETTERS[LCASE_LETTERS.length - 1])
    {
      throw new IllegalArgumentException("Invalid char: " + c);
    }
  }


  public static void main(String[] args)
  {
    System.out.println("Hello");
  }
}
