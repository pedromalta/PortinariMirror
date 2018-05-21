package net.clubedocomputador.portinarimirror.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import net.clubedocomputador.portinarimirror.data.FaceService;
import net.clubedocomputador.portinarimirror.data.LoginService;
import net.clubedocomputador.portinarimirror.data.local.InstanceHolder;
import net.clubedocomputador.portinarimirror.data.local.PreferencesHelper;
import net.clubedocomputador.portinarimirror.util.location.LocationTracker;
import dagger.Component;
import net.clubedocomputador.portinarimirror.injection.ApplicationContext;
import net.clubedocomputador.portinarimirror.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    LocationTracker locationTracker();

    LoginService loginService();

    FaceService faceService();

    PreferencesHelper preferencesHelper();

    InstanceHolder instanceHolder();
}
