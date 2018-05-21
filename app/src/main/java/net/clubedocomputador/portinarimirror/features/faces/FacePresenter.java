package net.clubedocomputador.portinarimirror.features.faces;



import javax.inject.Inject;

import net.clubedocomputador.portinarimirror.data.FaceService;
import net.clubedocomputador.portinarimirror.data.LoginService;
import net.clubedocomputador.portinarimirror.data.model.Login;
import net.clubedocomputador.portinarimirror.data.model.User;
import net.clubedocomputador.portinarimirror.features.base.BasePresenter;
import net.clubedocomputador.portinarimirror.injection.ConfigPersistent;
import net.clubedocomputador.portinarimirror.util.rx.scheduler.SchedulerUtils;

@ConfigPersistent
public class FacePresenter extends BasePresenter<FaceMvpView> {

    private final FaceService service;

    @Inject
    public FacePresenter(FaceService service) {
        this.service = service;
    }

    @Override
    public void attachView(FaceMvpView mvpView) {
        super.attachView(mvpView);
    }


    public void recognize(byte[] image) {
        checkViewAttached();
        getView().showLoading();
        //TODO
        service
                .recognize(image)
                .compose(SchedulerUtils.ioToMain())
                .subscribe(
                        loggedIn -> {
                            getView().recognized(loggedIn);
                        },
                        throwable -> {
                            getView().showError(throwable);
                        });
    }

    public void create(User user) {
        checkViewAttached();
        getView().showLoading();
        //TODO
        service
                .create(user)
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
