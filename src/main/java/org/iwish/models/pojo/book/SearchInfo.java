package org.iwish.models.pojo.book;

public class SearchInfo {
    private String textSnippet;

    public String getTextSnippet ()
    {
        return textSnippet;
    }

    public void setTextSnippet (String textSnippet)
    {
        this.textSnippet = textSnippet;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [textSnippet = "+textSnippet+"]";
    }
}
