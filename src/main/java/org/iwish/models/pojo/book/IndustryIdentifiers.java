package org.iwish.models.pojo.book;

public class IndustryIdentifiers {
    private String type;

    private String identifier;

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getIdentifier ()
    {
        return identifier;
    }

    public void setIdentifier (String identifier)
    {
        this.identifier = identifier;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [type = "+type+", identifier = "+identifier+"]";
    }
}
