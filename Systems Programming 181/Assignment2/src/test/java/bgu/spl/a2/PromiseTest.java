package bgu.spl.a2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PromiseTest {

    Promise<Integer> promise;
    Integer[] array = {0};

    @Before
    public void setUp() throws Exception {
        promise = new Promise<Integer>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
        Integer res;
        try {
            promise.get();
            Assert.fail();
        }
        catch (IllegalStateException ex) {}
        catch (Exception ex1) {// Object wasn't resolved
            try {
                promise.resolve(7);
                res = promise.get();
                assertEquals(res, (Integer)7);
            } catch (Exception ex2) { // Returns NULL
                Assert.fail();
            }
        }

    }

    @Test
    public void isResolved() {
        try {
            assertFalse(promise.isResolved());
            try {
                promise.resolve(7);
                assertTrue(promise.isResolved());
            } catch (Exception ex) {
                Assert.fail();
            }
        } catch (Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void resolve() {
        try {
            promise.resolve(7);
            promise.resolve(8);
            Assert.fail();
        }
        catch (Exception ex1) {
            try {
                assertTrue(promise.isResolved());
                try {
                    assertNotNull(promise.get());
                } catch (Exception ex3) {
                    Assert.fail();
                }
            } catch (Exception ex2) {
                Assert.fail();
            }
        }

    }

    @Test
    public void subscribe() {
        try { // isResolved = false
            promise.subscribe(() -> {
                array[0]++;
            });
            assertTrue(array[0] == 0);
            promise.resolve(8);
            assertTrue(array[0] == 1);
            try { // isResolved = true
                promise.subscribe(() -> {
                    array[0]++;
                });
                assertTrue(array[0] == 2);
            } catch (Exception ex) {
                Assert.fail();
            }
        } catch (Exception ex) {
            Assert.fail();
        }

    }
}