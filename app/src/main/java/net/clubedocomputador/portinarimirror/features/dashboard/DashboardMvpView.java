package net.clubedocomputador.portinarimirror.features.dashboard;

import net.clubedocomputador.portinarimirror.data.model.LoggedIn;
import net.clubedocomputador.portinarimirror.features.base.MvpView;

/**
 * Created by pedromalta on 14/03/18.
 */

public interface DashboardMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void recognized(LoggedIn loggedIn);

    void showError(Throwable error);
}
