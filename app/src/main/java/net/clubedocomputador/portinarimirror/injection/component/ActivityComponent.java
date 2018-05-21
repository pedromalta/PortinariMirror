package net.clubedocomputador.portinarimirror.injection.component;

import net.clubedocomputador.portinarimirror.features.dashboard.DashboardActivity;
import net.clubedocomputador.portinarimirror.features.map.MapActivity;
import net.clubedocomputador.portinarimirror.features.faces.FaceActivity;
import net.clubedocomputador.portinarimirror.features.login.LoginActivity;
import net.clubedocomputador.portinarimirror.features.splash.SplashActivity;
import dagger.Subcomponent;

import net.clubedocomputador.portinarimirror.features.users.UsersActivity;
import net.clubedocomputador.portinarimirror.injection.PerActivity;
import net.clubedocomputador.portinarimirror.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(UsersActivity usersActivity);

    void inject(DashboardActivity dashboardActivity);

    void inject(MapActivity mapActivity);

    void inject(FaceActivity faceActivity);

}