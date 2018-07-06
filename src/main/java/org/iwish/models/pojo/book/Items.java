package org.iwish.models.pojo.book;

public class Items {

    private String id;

    private SearchInfo searchInfo;

    private VolumeInfo volumeInfo;

    private String selfLink;

    private String kind;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public SearchInfo getSearchInfo ()
    {
        return searchInfo;
    }

    public void setSearchInfo (SearchInfo searchInfo)
    {
        this.searchInfo = searchInfo;
    }

    public VolumeInfo getVolumeInfo ()
    {
        return volumeInfo;
    }

    public void setVolumeInfo (VolumeInfo volumeInfo)
    {
        this.volumeInfo = volumeInfo;
    }

    public String getSelfLink ()
    {
        return selfLink;
    }

    public void setSelfLink (String selfLink)
    {
        this.selfLink = selfLink;
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
        return "ClassPojo [ id = "+id+", searchInfo = "+searchInfo+",  volumeInfo = "+volumeInfo+", selfLink = "+selfLink+", kind = "+kind+"]";
    }
}
