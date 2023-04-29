package com.daniel.dkm.baseapp.di.helpers.features;

import com.daniel.dkm.baseapp.di.helpers.activities.AddressableActivity;
import com.daniel.dkm.common.Constants;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class Modules {


    private static final List<FeatureModule> modules;

    public static final Modules INSTANCE;

    public final FeatureModule getModuleFromName(String moduleName) {

        Iterator var4 = modules.iterator();

        FeatureModule it;
        do {
            if (!var4.hasNext()) {
                throw new IllegalArgumentException(moduleName + " is not found");
            }
            Object element$iv = var4.next();
            it = (FeatureModule) element$iv;
        } while (!it.getName().equalsIgnoreCase(moduleName));

        return it;
    }

    private Modules() {
    }

    static {
        Modules var0 = new Modules();
        INSTANCE = var0;
        modules = Arrays.asList(new FeatureModule[]{
                        (FeatureModule) FeatureHomeModule.INSTANCE,
                        (FeatureModule) FeatureHiltModule.INSTANCE
                }.clone()
        );
    }

    public static final class FeatureHomeModule implements FeatureModule, AddressableActivity {

        private static final String name;

        private static final String injectorName;

        private static final String className;

        public static final FeatureHomeModule INSTANCE;

        public String getName() {
            return name;
        }

        public String getInjectorName() {
            return injectorName;
        }

        private FeatureHomeModule() {
        }

        static {
            FeatureHomeModule var0 = new FeatureHomeModule();
            INSTANCE = var0;
            name = "home";
            injectorName = Constants.BASE_PACKAGE_NAME + "." + name + ".di.HomeInjector";
            className = Constants.BASE_PACKAGE_NAME + "." + name + ".MainActivity";
        }

        @Override
        public String getClassName() {
            return className;
        }
    }

    public static final class FeatureHiltModule implements FeatureModule, AddressableActivity {

        private static final String name;

        private static final String injectorName;

        private static final String className;

        public static final FeatureHiltModule INSTANCE;

        public String getName() {
            return name;
        }

        public String getInjectorName() {
            return injectorName;
        }

        private FeatureHiltModule() {
        }

        static {
            FeatureHiltModule var0 = new FeatureHiltModule();
            INSTANCE = var0;
            name = "hiltmodule";
            injectorName = Constants.BASE_PACKAGE_NAME + "." + name + ".di.HiltModuleInjector";
            className = Constants.BASE_PACKAGE_NAME + "." + name + ".MainActivity";
        }

        @Override
        public String getClassName() {
            return className;
        }
    }


}
