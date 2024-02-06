package edu.uga.cs.statecapitalsquiz;

/**
 * This class (a POJO) represents a quiz question.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Question {

    private long id;
    private String state;
    private String capital;
    private String city1;
    private String city2;
    private String answer;

    public Question()
    {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.city1 = null;
        this.city2 = null;
        this.answer = null;
    }

    public Question(String state, String capital, String city1, String city2, String answer) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.state = state;
        this.capital = capital;
        this.city1 = city1;
        this.city2 = city2;
        this.answer = answer;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    public String getCity1()
    {
        return city1;
    }

    public void setCity1(String city1)
    {
        this.city1 = city1;
    }

    public String getCity2()
    {
        return city2;
    }

    public void setCity2(String city2)
    {
        this.city2 = city2;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public String toString()
    {
        return id + ": " + state + " " + capital + " " + city1 + " " + city2;
    }
}
