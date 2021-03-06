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
package com.lasmanis.javapatterns;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Basic singleton implementation.
 * <p>
 * This class provide the implementation of a thread-safe, generic singleton
 * pattern.
 * {@link com.lasmanis.javapatterns.Singleton#instance(java.lang.Class)} is a
 * thread-safe operation for both retreval and creation.
 * Derived classes are responsible
 * for initializing themselves in a thread-safe manner.  If initialization is
 * required, the code involved is contained in a <code>synchronized</code>
 * block tied to the {@link com.lasmanis.javapatterns.Singleton} class.
 * <p>
 * Deriving classes should declare a non-public default constructor.  This
 * class assumes the existing of a default constructor (ie, takes no
 * parameters) and uses reflection to locate the constructor if an instance
 * of a particular derived class has not yet been instantiated.  For safety
 * reasons, derived classes should not declare any public constructors.
 * <p>
 * This class provides no facilities for calling a non-default constructor
 * and initialization of the created instance is left to the derived class.
 * <p>
 * The expect form of a derived class looks is as follows:
 * <pre>
 * <code>
 *public class IntegerContainer
 *    extends Singleton
 *{
 *    // instance local data
 *    public int a;
 *
 *    // private constructor for initialization
 *    private IntegerContainer()
 *    {
 *        a = 1;
 *    }
 *
 *    // convenience method
 *    public static IntegerContainer instance()
 *          throws InstantiationException,
 *              IllegalAccessException,
 *              NoSuchMethodException,
 *              IllegalArgumentException,
 *              InvocationTargetException,
 *              ExceptionInInitializerError,
 *              SecurityException
 *    {
 *        return IntegerContainer.instance(IntegerContainer.class);
 *    }
 *}
 * </code>
 * </pre>
 * The addition of the simplified <code>instance()</code> is purely for the
 * convenience of the programmer.  It simplifes the invocation and reduces
 * accidental type mis-matches.  With this additional method, getting an
 * instance is as simple as :
 * <pre>
 * <code>
 * IntegerContainer i = IntegerContainer.instance();
 * </code>
 * </pre>
 *
 * @author mpl
 */
public class Singleton {
    /**
     * Map of the class to local instances data.
     */
    private static final Map<Class<? extends Singleton>, Singleton> INSTANCES =
            new ConcurrentHashMap<Class<? extends Singleton>, Singleton>();

    /**
     * Constructor.
     * <p>
     * Scope is protected for instantiation control.
     */
    protected Singleton() {
        super();
    }

    /**
     * Access the instance backing the singleton
     * <p>
     * This method returns an existing derived
     * {@link com.lasmanis.javapatterns.Singleton} instance or
     * allocates a new instance.  This method uses reflection, specifically
     * {@link java.lang.reflect.Constructor#newInstance(java.lang.Object[])} and
     * {@link java.lang.Class#getDeclaredConstructor(java.lang.Class[])} to
     * accomplish this.  The method also assumes the existence of the default
     * constructor taking zero parameters and will throw
     * {@link java.lang.NoSuchMethodException} if
     * the default constructor is not found.
     * <p>
     * This is a thread-safe operation.
     *
     * @param <U> the type parameter of the derived object object.
     * @param c the {@link java.lang.Class} object corresponding to U.
     * @return the derived {@link com.lasmanis.javapatterns.Singleton} object.
     * @throws java.lang.InstantiationException if the class that declares the
     *          underlying constructor represents an abstract class.
     * @throws java.lang.IllegalAccessException if this Constructor object
     *          enforces Java language access control and the underlying
     *          constructor is inaccessible.
     * @throws java.lang.NoSuchMethodException if the default constructor is not
     *          found.
     * @throws java.lang.IllegalArgumentException  if the number of actual and
     *          formal parameters differ; if an unwrapping conversion for
     *          primitive arguments fails; or if, after possible unwrapping, a
     *          parameter value cannot be converted to the corresponding formal
     *          parameter type by a method invocation conversion; if this
     *          constructor pertains to an enum type.
     * @throws java.lang.reflect.InvocationTargetException if the underlying
     *          constructor throws an exception.
     * @throws java.lang.ExceptionInInitializerError if the initialization
     *          provoked by this method fails.
     * @throws java.lang.SecurityException if the security manager is present
     *          and prevents access.  See {@link
     *          java.lang.Class#getDeclaredConstructor(java.lang.Class[])}
     * @throws java.lang.NullPointerException if c is null.
     */
    public static <U extends Singleton> U instance(
            final Class<U> c)
            throws InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                IllegalArgumentException,
                InvocationTargetException,
                ExceptionInInitializerError,
                SecurityException,
                NullPointerException {
        // check
        if (c == null) {
            throw new NullPointerException();
        }

        // check for an existing instance
        @SuppressWarnings("unchecked")
        U i = (U) INSTANCES.get(c);
        if (i != null) {
            // an instance already exists, so return it
            return i;
        } else {
            // need to make a new instance
            synchronized (Singleton.class) {
                // check again in case someone else created it while we were
                // blocked
                @SuppressWarnings("unchecked")
                U j = (U) INSTANCES.get(c);
                if (j != null) {
                    // an instance already exists, so return it
                    return j;
                }

                // get the default constructor and make it accessible to us
                Class<?>[] typeList = new Class<?>[0];
                Constructor<U> ctor = c.getDeclaredConstructor(typeList);
                ctor.setAccessible(true);

                // create the new instance and save it
                j = ctor.newInstance(new Object[0]);
                INSTANCES.put(c, j);

                return j;
            }
        }
    }

    /**
     * retrieve the instances backing store.
     * @return the map of instances
     */
    public static Map<Class<? extends Singleton>, Singleton> getInstances() {
        return INSTANCES;
    }
}
