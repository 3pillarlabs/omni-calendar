OMNI-CALENDAR
====================
Omni-calendar is a java library to easily send calendar invitations, whether on Gmail, Outllook or any other mail client. To achieve this, we have provided two implementations: <br>
1. ICal attachments using email, which supports most mail clients including Gmail and Outlook. The heavy lifting in this case is done by the excellent and powerful iCal4j library.<br>
2. Creating meeting invitations directly using Google API. This requires the authentication of the sender using OAuth.

More implementations are on our roadmap, for example sending meeting invitation using Exchange Web Services API.

How to Incorporate this library in your application
===========================

You can use the library in following ways:-

- You can download the source code and run a maven buld to create a jar file. Then add this jar and other maven dependency in application.
- you can incorporate the source directly in your application and add following dependencies:-
	
	- ical4j-1.0.5-SNAPSHOT.jar
	- backport-util-concurrent-3.1.jar
	- mail-1.4.5.jar
	- activation-1.1.jar
	- commons-lang-2.6.jar
	- commons-logging-1.1.1.jar
	- commons-codec-1.5.jar
	- google-api-services-calendar-v3-rev22-1.13.2-beta.jar
	- google-api-client-1.13.2-beta.jar
	- google-oauth-client-1.13.1-beta.jar
	- google-http-client-jackson2-1.13.1-beta.jar
	- google-http-client-1.13.1-beta.jar
	- jsr305-1.3.9.jar
	- guava-jdk5-13.0.jar
	- httpclient-4.0.1.jar
	- httpcore-4.0.1.jar
	- xpp3-1.1.4c.jar
	- jackson-core-2.0.5.jar
	- google-oauth-client-jetty-1.13.1-beta.jar
	- google-oauth-client-java6-1.13.1-beta.jar
	- jetty-6.1.26.jar
	- jetty-util-6.1.26.jar
	- servlet-api-2.5-20081211.jar

How to Use
=============================

For sending calendar request through Email
-------------------------------------------

<p>First You'll have to create a Instance on EmailInviteImpl class.</p>
    Invite invite = new EmailInviteImpl(props);
Constructs an instance of this class. It accepts mail configuration properties.<br>
**Parameters:**<br>
properties - the mail configuration properties. <br>Properties should contain <br>
_mail.smtp.host, mail.smtp.socketFactory.port, mail.smtp.socketFactory.class, mail.smtp.auth, mail.smtp.port, username, password._<br>

<p>You'll have to call the following function:-</P>
    invite.sendInvite(String subject,String description,Participant from,
    List<Participant> attendees,Date startDate,Date endDate,String location) throws Exception

For sending google calendar request 
-------------------------------------------
<p>First You'll have to create a Instance on GoogleInviteImpl class.</p>
    Invite invite = new GoogleInviteImpl(props);

Constructs an instance of this class. It accepts OAuth configuration properties.<br>
**Parameters:** <br>
properties - the OAUTH configuration properties.<br>
Properties should contain <br>
_CLIENT_ID, CLIENT_SECRET, ACCESS_TOKEN, REFRESH_TOKEN, EXPIRY_TIME_IN_MILLIS, APPLICATION_NAME_

<p>You'll have to call the following function:-</P>
    invite.sendInvite(String subject,String description,Participant from,
    List<Participant> attendees,Date startDate,Date endDate,String location) throws Exception
