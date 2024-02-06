package edu.uga.cs.statecapitalsquiz;

public class Result {

    private long id;
    private String date;
    private int result;

    public Result()
    {
        this.id = -1;
        this.date = null;
        this.result = -1;
    }

    public Result(String date,int result) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.date = date;
        this.result = result;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public String toString()
    {
        return id + ": " + date + " " + result;
    }
}
