package org.iwish.models.pojo.book;

public class BookDetails {

    private Items[] items;

    private String totalItems;

    private String kind;

    public Items[] getItems ()
    {
        return items;
    }

    public void setItems (Items[] items)
    {
        this.items = items;
    }

    public String getTotalItems ()
    {
        return totalItems;
    }

    public void setTotalItems (String totalItems)
    {
        this.totalItems = totalItems;
    }

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [items = "+items+", totalItems = "+totalItems+", kind = "+kind+"]";
    }

}
