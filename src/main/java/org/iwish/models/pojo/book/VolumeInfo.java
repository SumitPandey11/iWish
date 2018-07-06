package org.iwish.models.pojo.book;

public class VolumeInfo {
    private String pageCount;

    private String averageRating;

    private String printType;

    private String publisher;

    private String[] authors;

    private String title;

    private String description;

    private String ratingsCount;

    private ImageLinks imageLinks;

    private String contentVersion;

    private String[] categories;

    private String language;

    private String publishedDate;

    private String maturityRating;

    private IndustryIdentifiers[] industryIdentifiers;

    public String getPageCount ()
    {
        return pageCount;
    }

    public void setPageCount (String pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getAverageRating ()
    {
        return averageRating;
    }

    public void setAverageRating (String averageRating)
    {
        this.averageRating = averageRating;
    }



    public String getPrintType ()
    {
        return printType;
    }

    public void setPrintType (String printType)
    {
        this.printType = printType;
    }


    public String getPublisher ()
    {
        return publisher;
    }

    public void setPublisher (String publisher)
    {
        this.publisher = publisher;
    }

    public String[] getAuthors ()
    {
        return authors;
    }

    public void setAuthors (String[] authors)
    {
        this.authors = authors;
    }


    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }


    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getRatingsCount ()
    {
        return ratingsCount;
    }

    public void setRatingsCount (String ratingsCount)
    {
        this.ratingsCount = ratingsCount;
    }

    public ImageLinks getImageLinks ()
    {
        return imageLinks;
    }

    public void setImageLinks (ImageLinks imageLinks)
    {
        this.imageLinks = imageLinks;
    }

    public String getContentVersion ()
    {
        return contentVersion;
    }

    public void setContentVersion (String contentVersion)
    {
        this.contentVersion = contentVersion;
    }

    public String[] getCategories ()
    {
        return categories;
    }

    public void setCategories (String[] categories)
    {
        this.categories = categories;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getPublishedDate ()
    {
        return publishedDate;
    }

    public void setPublishedDate (String publishedDate)
    {
        this.publishedDate = publishedDate;
    }

    public String getMaturityRating ()
    {
        return maturityRating;
    }

    public void setMaturityRating (String maturityRating)
    {
        this.maturityRating = maturityRating;
    }

    public IndustryIdentifiers[] getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(IndustryIdentifiers[] industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pageCount = "+pageCount+", averageRating = "+averageRating+", printType = "+printType+", publisher = "+publisher+", authors = "+authors+", title = "+title+", description = "+description+", ratingsCount = "+ratingsCount+", imageLinks = "+imageLinks+", contentVersion = "+contentVersion+", categories = "+categories+", language = "+language+", publishedDate = "+publishedDate+", maturityRating = "+maturityRating+", industryIdentifiers = "+industryIdentifiers+"]";
    }
}
