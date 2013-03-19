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
package com.threepillar.labs.meeting.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.threepillar.labs.meeting.Invite;
import com.threepillar.labs.meeting.Participant;
import com.threepillar.labs.meeting.ParticipantType;
import com.threepillar.labs.meeting.exception.ConfigurationException;

/**
 * 
 * @author tarun.nagpal
 * 
 */
public class GoogleInviteImpl implements Invite {

	private final Log LOG = LogFactory.getLog(this.getClass());

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	public static final String CLIENT_ID = "cliendId";
	public static final String CLIENT_SECRET = "clientSecret";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String EXPIRY_TIME_IN_MILLIS = "expireyTimeInMillis";
	public static final String APPLICATION_NAME = "applicationName";

	/**
	 * Be sure to specify the name of your application. If the application name
	 * is {@code null} or blank, the application will log a warning.
	 */
	private String applicationName = "";

	private com.google.api.services.calendar.Calendar client;

	private Properties properties;

	/**
	 * Constructs an instance of this class. It accepts OAuth configuration
	 * properties.
	 * 
	 * @param properties
	 *            the OAUTH configuration properties. Properties should contain
	 *            <code>CLIENT_ID</code>, <code>CLIENT_SECRET</code>,
	 *            <code>ACCESS_TOKEN</code>, <code>REFRESH_TOKEN</code>,
	 *            <code>EXPIRY_TIME_IN_MILLIS</code>,
	 *            <code>APPLICATION_NAME</code>
	 */
	public GoogleInviteImpl(final Properties properties) {
		this.properties = properties;
		if (properties.get(APPLICATION_NAME) != null) {
			this.applicationName = (String) properties.get(APPLICATION_NAME);
		}
	}

	@Override
	public void sendInvite(final String subject, final String description,
			final Participant from, final List<Participant> attendees,
			final Date startDate, final Date endDate, final String location)
			throws Exception {
		validate();

		LOG.info("Sending meeting invite");

		if (client == null) {
			createClient();
		}
		LOG.debug("Creating claendar");
		Calendar calendar = addCalendar(subject);
		LOG.debug("Calendar created with id :: " + calendar.getId());

		LOG.debug("Creating Event");
		Event event = newEvent(subject, description, startDate, endDate, from,
				attendees, location);

		LOG.debug("Inserting event in calendar id :: " + calendar.getId());
		client.events().insert(calendar.getId(), event).execute();

		LOG.info("Invite has been sent.....");
	}

	private void createClient() {
		Credential.Builder builder = new Credential.Builder(
				BearerToken.authorizationHeaderAccessMethod());
		builder.setJsonFactory(JSON_FACTORY).setTransport(HTTP_TRANSPORT);
		ClientParametersAuthentication auth = new ClientParametersAuthentication(
				properties.getProperty(CLIENT_ID),
				properties.getProperty(CLIENT_SECRET));
		builder.setRequestInitializer(auth);
		builder.setClientAuthentication(auth);
		builder.setTokenServerUrl(new GenericUrl(
				GoogleOAuthConstants.TOKEN_SERVER_URL));
		builder.setTokenServerEncodedUrl(GoogleOAuthConstants.TOKEN_SERVER_URL);

		Credential cr = builder.build();

		cr.setAccessToken(properties.getProperty(ACCESS_TOKEN));
		cr.setRefreshToken(properties.getProperty(REFRESH_TOKEN));
		System.out.println(System.currentTimeMillis());
		cr.setExpirationTimeMilliseconds(new Long(properties
				.getProperty(EXPIRY_TIME_IN_MILLIS)));
		// set up global Calendar instance
		client = new com.google.api.services.calendar.Calendar.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, cr).setApplicationName(
				APPLICATION_NAME).build();
	}

	@Override
	public void validate() throws ConfigurationException {
		LOG.info("Validating configuration properties for email");
		if (properties == null) {
			throw new ConfigurationException("Properties can not be null");
		}
		if (properties.get(CLIENT_ID) == null) {
			throw new ConfigurationException(CLIENT_ID + "can not be null");
		}
		if (properties.get(CLIENT_SECRET) == null) {
			throw new ConfigurationException(CLIENT_SECRET + "can not be null");
		}
		if (properties.get(ACCESS_TOKEN) == null) {
			throw new ConfigurationException(ACCESS_TOKEN + "can not be null");
		}
		if (properties.get(REFRESH_TOKEN) == null) {
			throw new ConfigurationException(REFRESH_TOKEN + "can not be null");
		}
		if (properties.get(EXPIRY_TIME_IN_MILLIS) == null) {
			throw new ConfigurationException(EXPIRY_TIME_IN_MILLIS
					+ "can not be null");
		}

	}

	private Event newEvent(final String subject, final String description,
			final Date startDate, final Date endDate, final Participant from,
			final List<Participant> attendeesList, final String location) {
		Event event = new Event();
		if (subject != null) {
			event.setSummary(subject);
		}
		if (description != null) {
			event.setDescription(description);
		}
		if (location != null) {
			event.setLocation(location);
		}

		DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
		event.setStart(new EventDateTime().setDateTime(start));
		DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
		event.setEnd(new EventDateTime().setDateTime(end));

		List<EventAttendee> list = new ArrayList<EventAttendee>();
		for (Participant bean : attendeesList) {
			EventAttendee attendee = new EventAttendee();
			attendee.setDisplayName(bean.getName());
			attendee.setEmail(bean.getEmail());
			if (ParticipantType.REQUIRED.equals(bean.getType())) {
				attendee.setOptional(false);
			}
			list.add(attendee);
		}

		EventAttendee organizer = new EventAttendee();
		organizer.setDisplayName(from.getName());
		organizer.setEmail(from.getEmail());
		if (ParticipantType.REQUIRED.equals(from.getType())) {
			organizer.setOptional(false);
		}
		organizer.setOrganizer(true);
		list.add(organizer);

		event.setAttendees(list);
		return event;
	}

	private Calendar addCalendar(final String summary) throws IOException {
		Calendar entry = new Calendar();
		entry.setSummary(summary);
		Calendar result = client.calendars().insert(entry).execute();
		return result;
	}
}
