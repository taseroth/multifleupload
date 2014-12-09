package org.faboo.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author Bert.radke <bert.radke@t-systems.com>
 */
public class FillMemory extends WebPage {


    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Link<Void>("garbage") {
            @Override
            public void onClick() {
                System.gc();
            }
        });
    }
}
