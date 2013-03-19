/*
 ===========================================================================
 Copyright (c) 2013 3PillarGlobal

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 ===========================================================================

 */
package com.threepillar.labs.meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.threepillar.labs.meeting.email.EmailInviteImpl;
import com.threepillar.labs.meeting.google.GoogleInviteImpl;

public class Main {
	private static final Log LOG = LogFactory.getLog(Main.class);

	public static void main(final String[] args) throws Exception {
		// googleCalendarInvite();
		sendEmailInvite();
	}

	public static void sendEmailInvite() throws Exception {

		// set the properties to send email
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		props.put("username", "");
		props.put("password", "");

		// set the organizer of the event
		Participant from = new Participant("From", "from@from.com",
				ParticipantType.REQUIRED);

		// set the attendees of the event
		Participant attendee1 = new Participant("Attendee1",
				"attendee1@attendee.com", ParticipantType.REQUIRED);
		List<Participant> list = new ArrayList<Participant>();
		list.add(attendee1);
		Invite invite = new EmailInviteImpl(props);
		Date startDate = new Date(System.currentTimeMillis() + 600000);
		Date endDate = new Date(startDate.getTime() + 1800000);
		invite.sendInvite("Testing Event", "Testing dummy event", from, list,
				startDate, endDate, "Delhi");
	}

	public static void googleCalendarInvite() throws Exception {

		// set the client/secret and OAUTH2 AccessToken, RefreshToken and
		// ExpiryTime
		Properties props = new Properties();
		props.setProperty(GoogleInviteImpl.CLIENT_ID, "XXXXXXX");
		props.setProperty(GoogleInviteImpl.CLIENT_SECRET, "XXXXXXX");
		props.setProperty(GoogleInviteImpl.ACCESS_TOKEN, "XXXXXXX");
		props.setProperty(GoogleInviteImpl.REFRESH_TOKEN, "XXXXXXX");
		props.setProperty(GoogleInviteImpl.EXPIRY_TIME_IN_MILLIS, "XXXXXXX");

		// set the organizer of the event.
		Participant from = new Participant("From", "From@gmail.com",
				ParticipantType.REQUIRED);

		// set the attendee of the event.
		Participant attendee1 = new Participant("Attendee",
				"attendee@gmail.com", ParticipantType.REQUIRED);

		List<Participant> list = new ArrayList<Participant>();
		list.add(attendee1);
		Date startDate = new Date(System.currentTimeMillis() + 30000);
		Date endDate = new Date(startDate.getTime() + 1800000);
		Invite invite = new GoogleInviteImpl(props);
		invite.sendInvite("Testing Event", "Testing dummy event", from, list,
				startDate, endDate, "Delhi");
	}
}
