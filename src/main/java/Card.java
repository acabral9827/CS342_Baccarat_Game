public class Card
{
    String suite;
    Integer value;

    // index of image array of card pictures
    Integer cardTrueVal;

    // Constructor:
    Card(String theSuite, Integer theValue)
    {
        this.suite = theSuite;
        this.value = theValue;
    }
}