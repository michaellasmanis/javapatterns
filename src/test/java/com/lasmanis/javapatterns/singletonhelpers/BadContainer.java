/*
 * Copyright © 2013 Michael Lasmanis (michael@lasmanis.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lasmanis.javapatterns.singletonhelpers;

import com.lasmanis.javapatterns.Singleton;

/**
 * Testing Bad derived class for testing {@link com.lasmanis.javapatterns.Singleton}
 *
 * @author mpl
 */
public class BadContainer
    extends Singleton
{
    /**
     * Local data
     */
    public int a = 0;

    /**
     * Constructor
     *
     * @param a  a sample param
     */
    private BadContainer(
            int a)
    {
    }
}
