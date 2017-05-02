package com.oloba.core.utils;

import java.util.Calendar;
import java.util.Random;

public class StringUtil {
	private static final Random RANDOM = new Random();

	public static byte[] hexToByteArray(final String s) {
		final byte[] ret = new byte[s.length() / 2];
		for (int i = 0; i < ret.length; i++) {
			final int begin = i * 2;
			ret[i] = (byte) Integer.parseInt(s.substring(begin, begin + 2), 16);
		}
		return ret;
	}

	public static boolean isSameDay(final long lastTime) {
		final Calendar now = Calendar.getInstance();
		final Calendar last = Calendar.getInstance();
		last.setTimeInMillis(lastTime);

		now.clear(Calendar.MILLISECOND);
		now.clear(Calendar.SECOND);
		now.clear(Calendar.MINUTE);
		now.set(Calendar.HOUR_OF_DAY, 0);

		last.clear(Calendar.MILLISECOND);
		last.clear(Calendar.SECOND);
		last.clear(Calendar.MINUTE);
		last.set(Calendar.HOUR_OF_DAY, 0);

		return now.equals(last);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of all characters.
	 * </p>
	 * 
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String random(final int count) {
		return random(count, false, false);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters as
	 * indicated by the arguments.
	 * </p>
	 * 
	 * @param count
	 *            the length of random string to create
	 * @param letters
	 *            if <code>true</code>, generated string will include alphabetic
	 *            characters
	 * @param numbers
	 *            if <code>true</code>, generated string will include numeric
	 *            characters
	 * @return the random string
	 */
	public static String random(final int count, final boolean letters, final boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters as
	 * indicated by the arguments.
	 * </p>
	 * 
	 * @param count
	 *            the length of random string to create
	 * @param start
	 *            the position in set of chars to start at
	 * @param end
	 *            the position in set of chars to end before
	 * @param letters
	 *            if <code>true</code>, generated string will include alphabetic
	 *            characters
	 * @param numbers
	 *            if <code>true</code>, generated string will include numeric
	 *            characters
	 * @return the random string
	 */
	public static String random(final int count, final int start, final int end, final boolean letters,
			final boolean numbers) {
		return random(count, start, end, letters, numbers, null, RANDOM);
	}

	/**
	 * <p>
	 * Creates a random string based on a variety of options, using supplied
	 * source of randomness.
	 * </p>
	 * 
	 * <p>
	 * If start and end are both <code>0</code>, start and end are set to
	 * <code>' '</code> and <code>'z'</code>, the ASCII printable characters,
	 * will be used, unless letters and numbers are both <code>false</code>, in
	 * which case, start and end are set to <code>0</code> and
	 * <code>Integer.MAX_VALUE</code>.
	 * 
	 * <p>
	 * If set is not <code>null</code>, characters between start and end are
	 * chosen.
	 * </p>
	 * 
	 * <p>
	 * This method accepts a user-supplied {@link Random} instance to use as a
	 * source of randomness. By seeding a single {@link Random} instance with a
	 * fixed seed and using it for each call, the same random sequence of
	 * strings can be generated repeatedly and predictably.
	 * </p>
	 * 
	 * @param count
	 *            the length of random string to create
	 * @param start
	 *            the position in set of chars to start at
	 * @param end
	 *            the position in set of chars to end before
	 * @param letters
	 *            only allow letters?
	 * @param numbers
	 *            only allow numbers?
	 * @param chars
	 *            the set of chars to choose randoms from. If <code>null</code>,
	 *            then it will use the set of all chars.
	 * @param random
	 *            a source of randomness.
	 * @return the random string
	 * @throws ArrayIndexOutOfBoundsException
	 *             if there are not <code>(end - start) + 1</code> characters in
	 *             the set array.
	 * @throws IllegalArgumentException
	 *             if <code>count</code> &lt; 0.
	 * @since 2.0
	 */
	public static String random(int count, int start, int end, final boolean letters, final boolean numbers,
			final char[] chars, final Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		final char[] buffer = new char[count];
		final int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				if ((ch >= 56320) && (ch <= 57343)) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if ((ch >= 55296) && (ch <= 56191)) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if ((ch >= 56192) && (ch <= 56319)) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters.
	 * </p>
	 * 
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String randomAlphanumeric(final int count) {
		return random(count, true, true);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters
	 * specified.
	 * </p>
	 * 
	 * <p>
	 * Characters will be chosen from the set of characters whose ASCII value is
	 * between <code>32</code> and <code>126</code> (inclusive).
	 * </p>
	 * 
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String randomAscii(final int count) {
		return random(count, 32, 127, false, false);
	}

	public static String randomNumeric(final int count) {
		return random(count, false, true);
	}

	/**
	 * convert byte array to hexadecimal string
	 */
	public static String toHexString(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			buf.append(String.format("%02X", bytes[i]));
		}
		return buf.toString();
	}
	
	/**
	 * 获取非空字符串
	 */
	public static String getNotNullStr(String str) {
		if(str == null) {
			return "";
		}
		return str;
	}
	
	/**
	 * 获取非空字符串
	 * 提供默认值
	 */
	public static String getNotNullStr(String str, String defaultVal) {
		if(str == null) {
			return defaultVal;
		}
		return str;
	}

	private StringUtil() {

	}
}
