/*
 * Copyright 2013 Michael Lasmanis.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lasmanis.javapatterns.singletonhelpers;

import com.lasmanis.javapatterns.Singleton;
import java.lang.reflect.InvocationTargetException;

/**
 * Testing {@link Integer} derived class for testing {@link com.lasmanis.javapatterns.Singleton}
 *
 * @author Michael Lasmanis <a href="mailto:michael@lasmanis.com">michael@lasmanis.com</a>
 */
public class IntegerContainer
    extends Singleton
{
    /**
     * Local data
     */
    public int a;

    /**
     * Constructor
     */
    private IntegerContainer()
    {
         a = 1;
    }

    /**
     * Instance method wrappers
     */
    public static IntegerContainer instance()
            throws InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                IllegalArgumentException,
                InvocationTargetException,
                ExceptionInInitializerError,
                SecurityException
    {
        return IntegerContainer.instance(IntegerContainer.class);
    }
}
