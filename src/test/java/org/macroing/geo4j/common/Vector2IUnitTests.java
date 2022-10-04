/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.geo4j.
 * 
 * org.macroing.geo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.geo4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.geo4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.geo4j.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class Vector2IUnitTests {
	public Vector2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final Vector2I vector = new Vector2I();
		
		assertEquals(0, vector.x);
		assertEquals(0, vector.y);
	}
	
	@Test
	public void testConstructorIntInt() {
		final Vector2I vector = new Vector2I(1, 2);
		
		assertEquals(1, vector.x);
		assertEquals(2, vector.y);
	}
	
	@Test
	public void testDirection() {
		final Vector2I vector = Vector2I.direction(new Point2I(1, 2), new Point2I(2, 3));
		
		assertEquals(1, vector.x);
		assertEquals(1, vector.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.direction(new Point2I(1, 2), null));
		assertThrows(NullPointerException.class, () -> Vector2I.direction(null, new Point2I(2, 3)));
	}
	
	@Test
	public void testEquals() {
		final Vector2I a = new Vector2I(0, 1);
		final Vector2I b = new Vector2I(0, 1);
		final Vector2I c = new Vector2I(0, 2);
		final Vector2I d = new Vector2I(2, 1);
		final Vector2I e = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
	}
	
	@Test
	public void testHashCode() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = new Vector2I(1, 2);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testLength() {
		final Vector2I a = new Vector2I(4, 0);
		final Vector2I b = new Vector2I(0, 4);
		
		assertEquals(4, a.length());
		assertEquals(4, b.length());
	}
	
	@Test
	public void testLengthSquared() {
		final Vector2I vector = new Vector2I(2, 4);
		
		assertEquals(20, vector.lengthSquared());
	}
	
	@Test
	public void testToString() {
		final Vector2I vector = new Vector2I(1, 2);
		
		assertEquals("new Vector2I(1, 2)", vector.toString());
	}
}