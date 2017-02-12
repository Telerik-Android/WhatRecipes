package com.whatrecipes.whatrecipes;

import com.whatrecipes.whatrecipes.data.NetComponent;
import com.whatrecipes.whatrecipes.presenters.PresentationModule;
import com.whatrecipes.whatrecipes.utils.CustomScope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dnt on 12.2.2017 г..
 */
@Singleton
@Component(modules = {AppModule.class, PresentationModule.class})
public interface AppComponent {

}
