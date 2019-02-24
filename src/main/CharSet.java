package main;

public class CharSet
{
  public char getChar(int index)
  {
    throw new IllegalCallerException("This method is not implemented by " + this.getClass().getSimpleName());
  }

  public int getIndex(char c)
  {
    throw new IllegalCallerException("This method is not implemented by " + this.getClass().getSimpleName());
  }

  public int size()
  {
    throw new IllegalCallerException("This method is not implemented by " + this.getClass().getSimpleName());
  }

  protected void validateChar(char c)
  {
    throw new IllegalCallerException("This method is not implemented by " + this.getClass().getSimpleName());
  }

  protected void validateIndex(int index)
  {
    throw new IllegalCallerException("This method is not implemented by " + this.getClass().getSimpleName());
  }
}
