package com.test.api.freestyle;

import com.brentcroft.tools.materializer.Materializer;
import com.brentcroft.tools.materializer.core.FlatTag;
import com.brentcroft.tools.materializer.core.Tag;
import lombok.Getter;
import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class FreeStyleReader
{
    String rootDir = "src/test/resources/detections";

    String[] detectionsUris = {
            "07-18-37_940-picods_05.xml",
            "07-32-02_639-picods_05.xml",
            "09-28-17_798-picods_05.xml",
            "12-29-10_879-picods_05.xml",
            "13-32-51_520-picods_05.xml",
            "14-10-49_461-picods_05.xml",
            "14-35-32_916-picods_05.xml"
    };

    @Getter
    public enum RootTag implements FlatTag< Map< String, String > >
    {
        ROOT( FreeStyleTag.ANY_ONE );

        // must be an empty string: @see TagHandler.getPath().
        private final String tag = "";
        private final FlatTag< Map< String, String > > self = this;
        private final Tag< ? super Map< String, String >, ? >[] children;

        @SafeVarargs
        RootTag( Tag< ? super Map< String, String >, ? >... children )
        {
            this.children = children;
        }
    }


    @Test
    public void test_freestyle() throws IOException
    {
        Materializer< Map< String, String > > materializer = new Materializer<>(
                null,
                0, () -> RootTag.ROOT,
                HashMap::new
        );

        for ( String detectionUri : detectionsUris )
        {
            Map< String, String > map = materializer
                    .apply(
                            new InputSource(
                                    new FileInputStream( format( "%s/%s", rootDir, detectionUri ) ) ) );

            System.out.println( map );
        }
    }
}