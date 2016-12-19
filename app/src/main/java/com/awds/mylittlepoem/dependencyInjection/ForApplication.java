package com.awds.mylittlepoem.dependencyinjection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by huangyuefeng on 2016/12/16.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForApplication {
}
