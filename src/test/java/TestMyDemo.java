import okhttp3.HttpUrl;
import org.junit.Test;

/**
 * Created by shff on 2017/6/14.
 */
public class TestMyDemo {
    @Test
    public void testHttpUrl(){
        HttpUrl url = new HttpUrl.Builder().scheme("http").host("localhost").port(8080).encodedPath("/echo")
                .addEncodedPathSegments("userId").addEncodedPathSegment("java").query("hello").build();
        System.out.println(url.toString());

    }
}
