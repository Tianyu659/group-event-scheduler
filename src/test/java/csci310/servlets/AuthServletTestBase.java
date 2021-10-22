package csci310.servlets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
abstract class AuthServletTestBase<R extends Enum<R>,T extends AuthServletTestCase<R>> {
    final T[] testCases;
    
    @SafeVarargs AuthServletTestBase(T...testCases) {this.testCases = testCases;}
    
    abstract AuthServletBase<R> getServlet();
    
    @Before public void setUp() {MockitoAnnotations.initMocks(this);}
    @Test public void testDoPost() {for(final T t : testCases) t.exec(getServlet());}
}