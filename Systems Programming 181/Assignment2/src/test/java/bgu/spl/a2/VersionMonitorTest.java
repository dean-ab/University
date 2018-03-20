package bgu.spl.a2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VersionMonitorTest {

    VersionMonitor vTest;

    @Before
    public void setUp() throws Exception {
        vTest = new VersionMonitor();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getVersion() {
        try {
            assertEquals(vTest.getVersion(), 0);
            try {
                vTest.inc();
                assertEquals(vTest.getVersion(), 1);
            } catch (Exception ex) {
                Assert.fail();
            }
        } catch (Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void inc() {
        try {
            vTest.inc();
            assertEquals(vTest.getVersion(), 1);
        }
        catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void await(){
        try {
            Thread t1 = new Thread(() -> {
                try {
                    int oldVersion = vTest.getVersion();
                    vTest.await(oldVersion);
                    assertTrue(vTest.getVersion() > oldVersion);
                }
                catch (Exception ex) {
                    Assert.fail();
                }
            });
            t1.start();
            t1.sleep(1000);
            assertTrue(t1.getState() == Thread.State.WAITING);
            vTest.inc();
            assertTrue(t1.getState() != Thread.State.WAITING);
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}