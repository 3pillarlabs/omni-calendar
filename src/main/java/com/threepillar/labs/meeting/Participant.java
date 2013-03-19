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

/**
 * Contains participant information
 * 
 * @author tarun.nagpal
 * 
 */
public class Participant {
	private String name;
	private String email;
	private ParticipantType type;

	public Participant() {
	}

	/**
	 * Construct a participant
	 * 
	 * @param name
	 *            the name of participant
	 * @param email
	 *            the email of participant
	 * @param type
	 *            the type of participant
	 */
	public Participant(final String name, final String email,
			final ParticipantType type) {
		this.name = name;
		this.email = email;
		this.type = type;
	}

	/**
	 * Returns the name of participant
	 * 
	 * @return name of participant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Updates the name of participant
	 * 
	 * @param name
	 *            the name of participant
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the email of participant
	 * 
	 * @return email of participant
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Updates the email of participant
	 * 
	 * @param email
	 *            the email of participant
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Returns the type of participant
	 * 
	 * @return type of participant
	 */
	public ParticipantType getType() {
		return type;
	}

	/**
	 * Updates the type of participant
	 * 
	 * @param type
	 *            the type of participant
	 */
	public void setType(final ParticipantType type) {
		this.type = type;
	}
}
