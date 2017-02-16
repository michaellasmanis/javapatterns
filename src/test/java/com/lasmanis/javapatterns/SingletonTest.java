/**
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

import com.lasmanis.javapatterns.singletonhelpers.BadContainer;
import com.lasmanis.javapatterns.singletonhelpers.IntegerContainer;
import com.lasmanis.javapatterns.singletonhelpers.StringContainer;
import java.lang.reflect.InvocationTargetException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for the {@link com.lasmanis.javapatterns.Singleton} class.
 *
 * @author Michael Lasmanis <a href="mailto:michael@lasmanis.com">michael@lasmanis.com</a>
 */
public class SingletonTest
{
    /**
     * Constructor
     */
    public SingletonTest()
    {
    }

    /**
     * BeforeClass hook
     */
    @BeforeClass
    public static void setUpClass()
    {
    }

    /**
     * AfterClass hook
     */
    @AfterClass
    public static void tearDownClass()
    {
    }

    /**
     * Before hook
     */
    @Before
    public void setUp()
    {
        // reset the map
        Singleton.instances.clear();
    }

    /**
     * After hook
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Basic functionality check.
     *
     * @throws java.lang.InstantiationException if any.
     * @throws java.lang.IllegalAccessException if any.
     * @throws java.lang.NoSuchMethodException if any.
     * @throws java.lang.IllegalArgumentException if any.
     * @throws java.lang.reflect.InvocationTargetException if any.
     */
    @Test
    public void basicFunctionalityTest()
            throws InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                IllegalArgumentException,
                InvocationTargetException
    {
        int testInt1 = 7;
        int testInt2 = 17;
        String testString1 = "this is a test";
        String testString2 = "this is another test";

        // safety
        assertFalse(testInt1 == testInt2);
        assertFalse(testString1.equals(testString2));

        // start as null
        assertTrue(Singleton.instances.isEmpty());

        // get a copy and check
        IntegerContainer i = IntegerContainer.instance(IntegerContainer.class);
        assertTrue(Singleton.instances.size() == 1);
        assertTrue(i != null);
        assertTrue(i == Singleton.instances.get(IntegerContainer.class));
        assertTrue(i.equals(Singleton.instances.get(IntegerContainer.class)));

        // access the data
        assertTrue(i.a == 1);
        assertFalse(i.a == testInt1);
        i.a = testInt1;
        assertTrue(i.a == testInt1);

        // get a copy and check (use the convenience method as a check
        IntegerContainer j = IntegerContainer.instance();
        assertTrue(Singleton.instances.size() == 1);
        assertTrue(j != null);
        assertTrue(j == Singleton.instances.get(IntegerContainer.class));
        assertTrue(j.equals(Singleton.instances.get(IntegerContainer.class)));

        // ensure i and j are the same
        assertTrue(i == j);
        assertTrue(i.equals(j));

        // access the data
        assertTrue(j.a == testInt1);
        j.a = testInt2;
        assertTrue(j.a == testInt2);
        assertTrue(i.a == testInt2);

        // get a copy and check
        StringContainer k = StringContainer.instance(StringContainer.class);
        assertTrue(Singleton.instances.size() == 2);
        assertTrue(k != null);
        assertTrue(k == Singleton.instances.get(StringContainer.class));
        assertTrue(k.equals(Singleton.instances.get(StringContainer.class)));

        // access the data
        assertTrue(k.a.equals("a"));
        assertFalse(k.a.equals(testString1));
        k.a = testString1;
        assertTrue(k.a.equals(testString1));

        // get a copy and check
        StringContainer l = StringContainer.instance(StringContainer.class);
        assertTrue(Singleton.instances.size() == 2);
        assertTrue(l != null);
        assertTrue(l == Singleton.instances.get(StringContainer.class));
        assertTrue(l.equals(Singleton.instances.get(StringContainer.class)));

        // ensure k and l are the same
        assertTrue(k == l);
        assertTrue(k.equals(l));

        // access the data
        assertTrue(l.a.equals(testString1));
        l.a = testString2;
        assertTrue(l.a.equals(testString2));
        assertTrue(k.a.equals(testString2));

        // ensure that i/j and k/l are differenet
        assertFalse((Object) i == (Object) k);

        // release then check
        i = null;
        j = null;
        k = null;
        l = null;
        assertTrue(Singleton.instances.size() == 2);
    }

    /**
     * No default constructor.
     *
     * @throws java.lang.InstantiationException if any.
     * @throws java.lang.IllegalAccessException if any.
     * @throws java.lang.NoSuchMethodException if any.
     * @throws java.lang.IllegalArgumentException if any.
     * @throws java.lang.reflect.InvocationTargetException if any.
     */
    @Test(expected = NoSuchMethodException.class)
    public void noDefaultConstructorTest()
            throws InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                IllegalArgumentException,
                InvocationTargetException
    {
        // get a copy and check
        BadContainer i = BadContainer.instance(BadContainer.class);
    }

    /**
     * Null pointer exception test.
     *
     * @throws java.lang.InstantiationException if any.
     * @throws java.lang.IllegalAccessException if any.
     * @throws java.lang.NoSuchMethodException if any.
     * @throws java.lang.IllegalArgumentException if any.
     * @throws java.lang.reflect.InvocationTargetException if any.
     */
    @Test(expected = NullPointerException.class)
    public void nullPointerExceptionTest()
            throws InstantiationException,
                IllegalAccessException,
                NoSuchMethodException,
                IllegalArgumentException,
                InvocationTargetException
    {
        // get a copy and check
        StringContainer i = StringContainer.instance(null);
    }
}
