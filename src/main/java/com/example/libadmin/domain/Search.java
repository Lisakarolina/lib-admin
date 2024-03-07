package com.example.libadmin.domain;

import java.util.List;
import java.util.Objects;

public class Search {
    private String term;

    // either title or author
    private String searchBarFilter;
    private List<String> accessTypes;

    private List<String> departments;

    private List<String> languages;

    private String sortCriterion;

    public Search() {};

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSearchBarFilter() {
        return searchBarFilter;
    }

    public void setSearchBarFilter(String searchBarFilter) {
        this.searchBarFilter = searchBarFilter;
    }

    public List<String> getAccessTypes() {
        return accessTypes;
    }

    public void setAccessTypes(List<String> accessTypes) {
        this.accessTypes = accessTypes;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return search.getTerm().equals(getTerm()) &&
                search.getSearchBarFilter().equals(getSearchBarFilter()) &&
                search.getDepartments().size() == getDepartments().size() &&
                search.getDepartments().equals(getDepartments()) &&
                search.getLanguages().size() == getLanguages().size();

    }

    @Override
    public int hashCode() {
        return Objects.hash(term);
    }

    @Override
    public String toString() {
        return "Search{" +
                "term=" + term + "searchBarFilter=" + searchBarFilter + "accessTypes=" + accessTypes + "departments=" + departments +
                '}';
    }

    public String getSortCriterion() {
        return sortCriterion;
    }

    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }
}
