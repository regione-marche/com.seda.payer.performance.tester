/**
 * 
 */
package com.seda.payer.performance.tester.config;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author polenta
 *
 */
public enum WsUrls {
	wsCommonsUrl;

    private static ResourceBundle rb;

    public String format( Object... args ) {
        synchronized(WsUrls.class) {
            if(rb==null)
                rb = ResourceBundle.getBundle(WsUrls.class.getName());
            return MessageFormat.format(rb.getString(name()),args);
        }
    }
}
