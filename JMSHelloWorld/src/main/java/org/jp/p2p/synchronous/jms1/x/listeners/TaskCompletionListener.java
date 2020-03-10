package org.jp.p2p.synchronous.jms1.x.listeners;

import javax.jms.CompletionListener;
import javax.jms.Message;

public class TaskCompletionListener implements CompletionListener {

	@Override
	public void onCompletion(Message message) {
		System.out.println("message sent successfully");
	}

	@Override
	public void onException(Message message, Exception exception) {
		System.out.println("message sent failure with exception "+exception);
	}

}
