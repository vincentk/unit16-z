package com.unit16.z.io;

import java.io.IOException;
import java.nio.channels.Channel;

public interface Sink<A> extends Channel {

	public void put(A val) throws IOException;
}
