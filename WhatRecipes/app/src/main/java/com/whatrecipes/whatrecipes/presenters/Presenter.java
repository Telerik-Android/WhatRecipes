package com.whatrecipes.whatrecipes.presenters;

/**
 * Created by fatal on 2/20/2017.
 */

public interface Presenter<T> {
    void setView(T view);
}