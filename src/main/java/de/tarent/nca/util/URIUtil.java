package de.tarent.nca.util;

import java.net.URI;

public class URIUtil {

    public static URI appendPath(URI uri, String path) {
        return uri.resolve(uri.getPath() + '/' + path);
    }
}
