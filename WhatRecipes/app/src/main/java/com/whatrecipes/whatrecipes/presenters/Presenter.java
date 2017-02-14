package com.whatrecipes.whatrecipes.presenters;

/**
 * Created by dnt on 14.2.2017 г..
 */

public interface Presenter<T> {
    void setView(T view);
}