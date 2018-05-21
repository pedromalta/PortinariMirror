package net.clubedocomputador.portinarimirror.features.dashboard;


import net.clubedocomputador.portinarimirror.data.LoginService;
import net.clubedocomputador.portinarimirror.data.model.Login;
import net.clubedocomputador.portinarimirror.features.base.BasePresenter;
import net.clubedocomputador.portinarimirror.injection.ConfigPersistent;
import net.clubedocomputador.portinarimirror.util.rx.scheduler.SchedulerUtils;

import javax.inject.Inject;

@ConfigPersistent
public class DashboardPresenter extends BasePresenter<DashboardMvpView> {

    private final LoginService service;

    @Inject
    public DashboardPresenter(LoginService service) {
        this.service = service;
    }

    @Override
    public void attachView(DashboardMvpView mvpView) {
        super.attachView(mvpView);
    }


    public void recognise(Login login) {
        checkViewAttached();
        getView().showLoading();
        //TODO
        service
                .login(login)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        loggedIn -> {
                            getView().recognized(loggedIn);
                        },
                        throwable -> {
                            getView().showError(throwable);
                        });
    }
}
