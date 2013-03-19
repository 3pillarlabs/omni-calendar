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

import java.util.Date;
import java.util.List;

import com.threepillar.labs.meeting.exception.ConfigurationException;

/**
 * 
 * @author tarun.nagpal
 * 
 */
public interface Invite {

	/**
	 * Validates the given properties for specific implementation. It throws
	 * ConfigurationException if any required property is missing.
	 * 
	 * @throws ConfigurationException
	 */
	public void validate() throws ConfigurationException;

	/**
	 * Sends meeting/event invite
	 * 
	 * @param subject
	 *            the invitation subject
	 * @param description
	 *            the invitation description
	 * @param from
	 *            the organizer
	 * @param attendees
	 *            list of attendees of this events
	 * @param startDate
	 *            the start date time of an event
	 * @param endDate
	 *            the end date time of an event
	 * @param location
	 *            the location of event
	 * @throws Exception
	 */
	public void sendInvite(String subject, String description,
			Participant from, List<Participant> attendees, Date startDate,
			Date endDate, String location) throws Exception;

}
