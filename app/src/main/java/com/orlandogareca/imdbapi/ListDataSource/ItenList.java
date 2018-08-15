package com.orlandogareca.imdbapi.ListDataSource;

public class ItenList {
    private String poster;
    private String title;
    private String year;
    private String type;
    private String idimdb;
    public ItenList (String poster, String title, String year, String type, String idimdb) {
        this.poster = poster;
        this.title = title;
        this.year = year;
        this.type = type;
        this.idimdb = idimdb;
    }
    public String getPoster(){
        return this.poster;
    }

    public String getTitle() {
        return this.title;
    }

    public String getYear() {
        return this.year;
    }

    public String getType() {
        return this.type;
    }

    public String getIdimdb() {
        return this.idimdb;
    }
}
