package net.clubedocomputador.portinarimirror.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import net.clubedocomputador.portinarimirror.R;
import dagger.Module;
import dagger.Provides;
import net.clubedocomputador.portinarimirror.injection.ApplicationContext;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static net.clubedocomputador.portinarimirror.Constants.PREF_FILE_NAME;

@Module(includes = {ApiModule.class})
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @ApplicationContext
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Slabo27px-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
