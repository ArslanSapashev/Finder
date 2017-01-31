import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;

/**
 * Created by Sony on 31.01.2017.
 */
public class TestFinder {
    @Test
    public void whenMaskPassedShouldReplaceForRegEx(){
        String mask = "???.xml";
        String result = ".{1}.{1}.{1}.xml";
        Finder f = new Finder();
        Assert.assertThat(f.maskToRegEx(mask), is(result));
    }

    @Test
    public void whenMaskWithAsteriskPassedShouldReplaceForRegEx(){
        String mask = "*.xml";
        String result = ".+.xml";
        Finder f = new Finder();
        Assert.assertThat(f.maskToRegEx(mask), is(result));
    }

    @Test
    public void whenAsteriskAndQuestionmarkPassedShouldReplaceForRegex(){
        String mask = "*??.?m*";
        String result = ".+.{1}.{1}..{1}m.+";
        Finder f = new Finder();
        Assert.assertThat(f.maskToRegEx(mask), is(result));
    }
}
