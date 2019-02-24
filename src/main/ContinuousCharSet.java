package main;

public class ContinuousCharSet extends CharSet
{
  private final char firstChar;
  private final char lastChar;
  private final int length;

  ContinuousCharSet(char firstChar, char lastChar)
  {
    if (firstChar >= lastChar) throw new IllegalArgumentException(firstChar + " should be less than " + lastChar);

    this.firstChar = firstChar;
    this.lastChar = lastChar;
    this.length = this.lastChar - this.firstChar + 1;
  }

  @Override
  public char getChar(int index)
  {
    validateIndex(index);
    return (char)(index + this.firstChar);
  }

  @Override
  public int getIndex(char c)
  {
    validateChar(c);
    return c - this.firstChar;
  }

  @Override
  protected void validateChar(char c)
  {
    if (c < this.firstChar || c > this.lastChar)
    {
      throw new IllegalArgumentException("Invalid char: " + c);
    }
  }

  @Override
  public int size()
  {
    return this.length;
  }

  @Override
  protected void validateIndex(int index)
  {
    if (index < 0 || index >= this.length) throw new IllegalArgumentException("Invalid index: " + index);
  }
}
