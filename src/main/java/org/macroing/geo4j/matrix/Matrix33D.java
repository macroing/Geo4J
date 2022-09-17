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
package org.macroing.geo4j.matrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.Objects;

import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.common.Vector2D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Ints;
import org.macroing.java.lang.Strings;

/**
 * A {@code Matrix33D} represents a 3 x 3 matrix with 9 {@code double}-based elements.
 * <p>
 * The default order of this {@code Matrix33D} class is row-major. The first number denotes the row and the second denotes the column.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Matrix33D {
	/**
	 * The offset for the element at index 0 or row 1 and column 1 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_1_1 = 0;
	
	/**
	 * The offset for the element at index 1 or row 1 and column 2 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_1_2 = 1;
	
	/**
	 * The offset for the element at index 2 or row 1 and column 3 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_1_3 = 2;
	
	/**
	 * The offset for the element at index 3 or row 2 and column 1 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_2_1 = 3;
	
	/**
	 * The offset for the element at index 4 or row 2 and column 2 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_2_2 = 4;
	
	/**
	 * The offset for the element at index 5 or row 2 and column 3 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_2_3 = 5;
	
	/**
	 * The offset for the element at index 6 or row 3 and column 1 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_3_1 = 6;
	
	/**
	 * The offset for the element at index 7 or row 3 and column 2 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_3_2 = 7;
	
	/**
	 * The offset for the element at index 8 or row 3 and column 3 in the {@code double[]}.
	 */
	public static final int ARRAY_OFFSET_ELEMENT_3_3 = 8;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The value of the element at index 0 or row 1 and column 1.
	 */
	public final double element11;
	
	/**
	 * The value of the element at index 1 or row 1 and column 2.
	 */
	public final double element12;
	
	/**
	 * The value of the element at index 2 or row 1 and column 3.
	 */
	public final double element13;
	
	/**
	 * The value of the element at index 3 or row 2 and column 1.
	 */
	public final double element21;
	
	/**
	 * The value of the element at index 4 or row 2 and column 2.
	 */
	public final double element22;
	
	/**
	 * The value of the element at index 5 or row 2 and column 3.
	 */
	public final double element23;
	
	/**
	 * The value of the element at index 6 or row 3 and column 1.
	 */
	public final double element31;
	
	/**
	 * The value of the element at index 7 or row 3 and column 2.
	 */
	public final double element32;
	
	/**
	 * The value of the element at index 8 or row 3 and column 3.
	 */
	public final double element33;
	
	/**
	 * The size of the {@code double[]}.
	 */
	public static final int ARRAY_SIZE = 9;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Matrix33D} instance denoting the identity matrix.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Matrix33D(1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	 * }
	 * </pre>
	 */
//	TODO: Add unit tests!
	public Matrix33D() {
		this(1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	/**
	 * Constructs a new {@code Matrix33D} instance given its element values.
	 * 
	 * @param element11 the value of the element at index 0 or row 1 and column 1
	 * @param element12 the value of the element at index 1 or row 1 and column 2
	 * @param element13 the value of the element at index 2 or row 1 and column 3
	 * @param element21 the value of the element at index 3 or row 2 and column 1
	 * @param element22 the value of the element at index 4 or row 2 and column 2
	 * @param element23 the value of the element at index 5 or row 2 and column 3
	 * @param element31 the value of the element at index 6 or row 3 and column 1
	 * @param element32 the value of the element at index 7 or row 3 and column 2
	 * @param element33 the value of the element at index 8 or row 3 and column 3
	 */
//	TODO: Add unit tests!
	public Matrix33D(final double element11, final double element12, final double element13, final double element21, final double element22, final double element23, final double element31, final double element32, final double element33) {
		this.element11 = element11;
		this.element12 = element12;
		this.element13 = element13;
		this.element21 = element21;
		this.element22 = element22;
		this.element23 = element23;
		this.element31 = element31;
		this.element32 = element32;
		this.element33 = element33;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Transforms {@code p} with this {@code Matrix33D} instance.
	 * <p>
	 * Returns a {@link Point2D} instance with the result of the transformation.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2D} instance
	 * @return a {@code Point2D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public Point2D transform(final Point2D p) {
		final double x = this.element11 * p.x + this.element12 * p.y + this.element13;
		final double y = this.element21 * p.x + this.element22 * p.y + this.element23;
		
		return new Point2D(x, y);
	}
	
	/**
	 * Transforms {@code p} with this {@code Matrix33D} instance and divides the result.
	 * <p>
	 * Returns a {@link Point2D} instance with the result of the transformation.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2D} instance
	 * @return a {@code Point2D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public Point2D transformAndDivide(final Point2D p) {
		final double x = this.element11 * p.x + this.element12 * p.y + this.element13;
		final double y = this.element21 * p.x + this.element22 * p.y + this.element23;
		final double z = this.element31 * p.x + this.element32 * p.y + this.element33;
		
		return Doubles.equals(z, 1.0D) || Doubles.isZero(z) ? new Point2D(x, y) : new Point2D(x / z, y / z);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Matrix33D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Matrix33D} instance
	 */
//	TODO: Add unit tests!
	@Override
	public String toString() {
		final String row1 = String.format("%s, %s, %s", Strings.toNonScientificNotationJava(this.element11), Strings.toNonScientificNotationJava(this.element12), Strings.toNonScientificNotationJava(this.element13));
		final String row2 = String.format("%s, %s, %s", Strings.toNonScientificNotationJava(this.element21), Strings.toNonScientificNotationJava(this.element22), Strings.toNonScientificNotationJava(this.element23));
		final String row3 = String.format("%s, %s, %s", Strings.toNonScientificNotationJava(this.element31), Strings.toNonScientificNotationJava(this.element32), Strings.toNonScientificNotationJava(this.element33));
		
		return String.format("new Matrix33D(%s, %s, %s)", row1, row2, row3);
	}
	
	/**
	 * Transforms {@code v} with this {@code Matrix33D} instance.
	 * <p>
	 * Returns a {@link Vector2D} instance with the result of the transformation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector2D} instance
	 * @return a {@code Vector2D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public Vector2D transform(final Vector2D v) {
		final double x = this.element11 * v.x + this.element12 * v.y;
		final double y = this.element21 * v.x + this.element22 * v.y;
		
		return new Vector2D(x, y);
	}
	
	/**
	 * Transforms {@code v} with this {@code Matrix33D} instance in transpose order.
	 * <p>
	 * Returns a {@link Vector2D} instance with the result of the transformation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector2D} instance
	 * @return a {@code Vector2D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public Vector2D transformTranspose(final Vector2D v) {
		final double x = this.element11 * v.x + this.element21 * v.y;
		final double y = this.element12 * v.x + this.element22 * v.y;
		
		return new Vector2D(x, y);
	}
	
	/**
	 * Compares {@code m} to this {@code Matrix33D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code m} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param m the {@code Matrix33D} instance to compare to this {@code Matrix33D} instance for equality
	 * @return {@code true} if, and only if, {@code m} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
//	TODO: Add unit tests!
	public boolean equals(final Matrix33D m) {
		if(m == this) {
			return true;
		} else if(m == null) {
			return false;
		} else if(!Doubles.equals(this.element11, m.element11)) {
			return false;
		} else if(!Doubles.equals(this.element12, m.element12)) {
			return false;
		} else if(!Doubles.equals(this.element13, m.element13)) {
			return false;
		} else if(!Doubles.equals(this.element21, m.element21)) {
			return false;
		} else if(!Doubles.equals(this.element22, m.element22)) {
			return false;
		} else if(!Doubles.equals(this.element23, m.element23)) {
			return false;
		} else if(!Doubles.equals(this.element31, m.element31)) {
			return false;
		} else if(!Doubles.equals(this.element32, m.element32)) {
			return false;
		} else if(!Doubles.equals(this.element33, m.element33)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Compares {@code object} to this {@code Matrix33D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Matrix33D}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Matrix33D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Matrix33D}, and their respective values are equal, {@code false} otherwise
	 */
//	TODO: Add unit tests!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Matrix33D)) {
			return false;
		} else {
			return equals(Matrix33D.class.cast(object));
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Matrix33D} instance is invertible, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Matrix33D} instance is invertible, {@code false} otherwise
	 */
//	TODO: Add unit tests!
	public boolean isInvertible() {
		return Doubles.abs(determinant()) >= 1.0e-12D;
	}
	
	/**
	 * Returns the determinant of this {@code Matrix33D} instance.
	 * 
	 * @return the determinant of this {@code Matrix33D} instance
	 */
//	TODO: Add unit tests!
	public double determinant() {
		final double a = this.element11;
		final double b = this.element12;
		final double c = this.element13;
		final double d = this.element21 * this.element32 - this.element22 * this.element31;
		final double e = this.element21 * this.element33 - this.element23 * this.element31;
		final double f = this.element22 * this.element33 - this.element32 * this.element23;
		
		return a * f - b * e + c * d;
	}
	
	/**
	 * Returns the value of the element at index {@code index}.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 8}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the index of the element whose value to return
	 * @return the value of the element at index {@code index}
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 8}
	 */
//	TODO: Add unit tests!
	public double getElement(final int index) {
		switch(index) {
			case 0:
				return this.element11;
			case 1:
				return this.element12;
			case 2:
				return this.element13;
			case 3:
				return this.element21;
			case 4:
				return this.element22;
			case 5:
				return this.element23;
			case 6:
				return this.element31;
			case 7:
				return this.element32;
			case 8:
				return this.element33;
			default:
				throw new IllegalArgumentException(String.format("Illegal index: index=%s", Integer.toString(index)));
		}
	}
	
	/**
	 * Returns the value of the element at row {@code row} and column {@code column}.
	 * <p>
	 * If either {@code row} or {@code column} are less than {@code 1} or greater than {@code 3}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * <strong>Note:</strong> Both {@code row} and {@code column} starts at {@code 1}, rather than {@code 0}, as is sometimes the case.
	 * 
	 * @param row the row of the element whose value to return
	 * @param column the column of the element whose value to return
	 * @return the value of the element at row {@code row} and column {@code column}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code row} or {@code column} are less than {@code 1} or greater than {@code 3}
	 */
//	TODO: Add unit tests!
	public double getElement(final int row, final int column) {
		Ints.requireRange(row, 1, 3, "row");
		Ints.requireRange(column, 1, 3, "column");
		
		return getElement((row - 1) * 3 + (column - 1));
	}
	
	/**
	 * Returns a {@code double[]} representation of this {@code Matrix33D} instance.
	 * 
	 * @return a {@code double[]} representation of this {@code Matrix33D} instance
	 */
//	TODO: Add unit tests!
	public double[] toArray() {
		final double[] array = new double[ARRAY_SIZE];
		
		array[ARRAY_OFFSET_ELEMENT_1_1] = this.element11;
		array[ARRAY_OFFSET_ELEMENT_1_2] = this.element12;
		array[ARRAY_OFFSET_ELEMENT_1_3] = this.element13;
		array[ARRAY_OFFSET_ELEMENT_2_1] = this.element21;
		array[ARRAY_OFFSET_ELEMENT_2_2] = this.element22;
		array[ARRAY_OFFSET_ELEMENT_2_3] = this.element23;
		array[ARRAY_OFFSET_ELEMENT_3_1] = this.element31;
		array[ARRAY_OFFSET_ELEMENT_3_2] = this.element32;
		array[ARRAY_OFFSET_ELEMENT_3_3] = this.element33;
		
		return array;
	}
	
	/**
	 * Returns a hash code for this {@code Matrix33D} instance.
	 * 
	 * @return a hash code for this {@code Matrix33D} instance
	 */
//	TODO: Add unit tests!
	@Override
	public int hashCode() {
		return Objects.hash(new Object[] {
			Double.valueOf(this.element11), Double.valueOf(this.element12), Double.valueOf(this.element13),
			Double.valueOf(this.element21), Double.valueOf(this.element22), Double.valueOf(this.element23),
			Double.valueOf(this.element31), Double.valueOf(this.element32), Double.valueOf(this.element33)
		});
	}
	
	/**
	 * Writes this {@code Matrix33D} instance to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} instance to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
//	TODO: Add unit tests!
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeDouble(this.element11);
			dataOutput.writeDouble(this.element12);
			dataOutput.writeDouble(this.element13);
			dataOutput.writeDouble(this.element21);
			dataOutput.writeDouble(this.element22);
			dataOutput.writeDouble(this.element23);
			dataOutput.writeDouble(this.element31);
			dataOutput.writeDouble(this.element32);
			dataOutput.writeDouble(this.element33);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Matrix33D} instance denoting the identity matrix.
	 * 
	 * @return a {@code Matrix33D} instance denoting the identity matrix
	 */
//	TODO: Add unit tests!
	public static Matrix33D identity() {
		return new Matrix33D();
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that is the inverse of {@code m}.
	 * <p>
	 * If {@code m} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code m} cannot be inverted, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * To make sure {@code m} is invertible, consider calling {@link #isInvertible()}.
	 * 
	 * @param m a {@code Matrix33D} instance
	 * @return a {@code Matrix33D} instance that is the inverse of {@code m}
	 * @throws IllegalArgumentException thrown if, and only if, {@code m} cannot be inverted
	 * @throws NullPointerException thrown if, and only if, {@code m} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Matrix33D inverse(final Matrix33D m) {
		final double a = m.element11;
		final double b = m.element12;
		final double c = m.element13;
		final double d = m.element21 * m.element32 - m.element22 * m.element31;
		final double e = m.element21 * m.element33 - m.element23 * m.element31;
		final double f = m.element22 * m.element33 - m.element32 * m.element23;
		
		final double determinant = a * f - b * e + c * d;
		final double determinantReciprocal = 1.0D / determinant;
		
		if(Doubles.abs(determinant) < 1.0e-12D) {
			throw new IllegalArgumentException("The Matrix33D 'm' cannot be inverted!");
		}
		
		final double element11 = (m.element22 * m.element33 - m.element32 * m.element23) * determinantReciprocal;
		final double element12 = (m.element13 * m.element32 - m.element12 * m.element33) * determinantReciprocal;
		final double element13 = (m.element12 * m.element23 - m.element13 * m.element22) * determinantReciprocal;
		final double element21 = (m.element23 * m.element31 - m.element21 * m.element33) * determinantReciprocal;
		final double element22 = (m.element11 * m.element33 - m.element13 * m.element31) * determinantReciprocal;
		final double element23 = (m.element21 * m.element13 - m.element11 * m.element23) * determinantReciprocal;
		final double element31 = (m.element21 * m.element32 - m.element31 * m.element22) * determinantReciprocal;
		final double element32 = (m.element31 * m.element12 - m.element11 * m.element32) * determinantReciprocal;
		final double element33 = (m.element11 * m.element22 - m.element21 * m.element12) * determinantReciprocal;
		
		return new Matrix33D(element11, element12, element13, element21, element22, element23, element31, element32, element33);
	}
	
	/**
	 * Multiplies the element values of {@code mLHS} with the element values of {@code mRHS}.
	 * <p>
	 * Returns a {@code Matrix33D} instance with the result of the multiplication.
	 * <p>
	 * If either {@code mLHS} or {@code mRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param mLHS the {@code Matrix33D} instance on the left-hand side
	 * @param mRHS the {@code Matrix33D} instance on the right-hand side
	 * @return a {@code Matrix33D} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, either {@code mLHS} or {@code mRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Matrix33D multiply(final Matrix33D mLHS, final Matrix33D mRHS) {
		final double element11 = mLHS.element11 * mRHS.element11 + mLHS.element12 * mRHS.element21 + mLHS.element13 * mRHS.element31;
		final double element12 = mLHS.element11 * mRHS.element12 + mLHS.element12 * mRHS.element22 + mLHS.element13 * mRHS.element32;
		final double element13 = mLHS.element11 * mRHS.element13 + mLHS.element12 * mRHS.element23 + mLHS.element13 * mRHS.element33;
		final double element21 = mLHS.element21 * mRHS.element11 + mLHS.element22 * mRHS.element21 + mLHS.element23 * mRHS.element31;
		final double element22 = mLHS.element21 * mRHS.element12 + mLHS.element22 * mRHS.element22 + mLHS.element23 * mRHS.element32;
		final double element23 = mLHS.element21 * mRHS.element13 + mLHS.element22 * mRHS.element23 + mLHS.element23 * mRHS.element33;
		final double element31 = mLHS.element31 * mRHS.element11 + mLHS.element32 * mRHS.element21 + mLHS.element33 * mRHS.element31;
		final double element32 = mLHS.element31 * mRHS.element12 + mLHS.element32 * mRHS.element22 + mLHS.element33 * mRHS.element32;
		final double element33 = mLHS.element31 * mRHS.element13 + mLHS.element32 * mRHS.element23 + mLHS.element33 * mRHS.element33;
		
		return new Matrix33D(element11, element12, element13, element21, element22, element23, element31, element32, element33);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Matrix33D} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
//	TODO: Add unit tests!
	public static Matrix33D read(final DataInput dataInput) {
		try {
			return new Matrix33D(dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that rotates around the origin.
	 * <p>
	 * The layout looks like this:
	 * <pre>
	 * {@code
	 * +cos, sin, 0
	 * -sin, cos, 0
	 *    0,   0, 1
	 * }
	 * </pre>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Matrix33D.rotate(angle, false);
	 * }
	 * </pre>
	 * 
	 * @param angle an angle in degrees
	 * @return a {@code Matrix33D} instance that rotates around the origin
	 */
//	TODO: Add unit tests!
	public static Matrix33D rotate(final double angle) {
		return rotate(angle, false);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that rotates around the origin.
	 * <p>
	 * The layout looks like this:
	 * <pre>
	 * {@code
	 * +cos, sin, 0
	 * -sin, cos, 0
	 *    0,   0, 1
	 * }
	 * </pre>
	 * 
	 * @param angle an angle in degrees or radians
	 * @param isRadians {@code true} if, and only if, {@code angle} is in radians, {@code false} otherwise
	 * @return a {@code Matrix33D} instance that rotates around the origin
	 */
//	TODO: Add unit tests!
	public static Matrix33D rotate(final double angle, final boolean isRadians) {
		final double angleRadians = isRadians ? angle : Doubles.toRadians(angle);
		
		final double cos = Doubles.cos(angleRadians);
		final double sin = Doubles.sin(angleRadians);
		
		return new Matrix33D(cos, sin, 0.0D, -sin, cos, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that scales along the X- and Y-axes.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Matrix33D.scale(v.x, v.y);
	 * }
	 * </pre>
	 * 
	 * @param v a {@link Vector2D} instance that contains the scale factors along the X- and Y-axes
	 * @return a {@code Matrix33D} instance that scales along the X- and Y-axes
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Matrix33D scale(final Vector2D v) {
		return scale(v.x, v.y);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that scales along the X- and Y-axes.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Matrix33D.scale(s, s);
	 * }
	 * </pre>
	 * 
	 * @param s the scale factor along the X- and Y-axes
	 * @return a {@code Matrix33D} instance that scales along the X- and Y-axes
	 */
//	TODO: Add unit tests!
	public static Matrix33D scale(final double s) {
		return scale(s, s);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that scales along the X- and Y-axes.
	 * <p>
	 * The layout looks like this:
	 * <pre>
	 * {@code
	 * x, 0, 0
	 * 0, y, 0
	 * 0, 0, 1
	 * }
	 * </pre>
	 * 
	 * @param x the scale factor along the X-axis
	 * @param y the scale factor along the Y-axis
	 * @return a {@code Matrix33D} instance that scales along the X- and Y-axes
	 */
//	TODO: Add unit tests!
	public static Matrix33D scale(final double x, final double y) {
		return new Matrix33D(x, 0.0D, 0.0D, 0.0D, y, 0.0D, 0.0D, 0.0D, 1.0D);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that translates along the X- and Y-axes.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Matrix33D.translate(p.x, p.y);
	 * }
	 * </pre>
	 * 
	 * @param p a {@link Point2D} instance that contains the translation factors along the X- and Y-axes
	 * @return a {@code Matrix33D} instance that translates along the X- and Y-axes
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Matrix33D translate(final Point2D p) {
		return translate(p.x, p.y);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that translates along the X- and Y-axes.
	 * <p>
	 * The layout looks like this:
	 * <pre>
	 * {@code
	 * 1, 0, x
	 * 0, 1, y
	 * 0, 0, 1
	 * }
	 * </pre>
	 * 
	 * @param x the translation factor along the X-axis
	 * @param y the translation factor along the Y-axis
	 * @return a {@code Matrix33D} instance that translates along the X- and Y-axes
	 */
//	TODO: Add unit tests!
	public static Matrix33D translate(final double x, final double y) {
		return new Matrix33D(1.0D, 0.0D, x, 0.0D, 1.0D, y, 0.0D, 0.0D, 1.0D);
	}
	
	/**
	 * Returns a {@code Matrix33D} instance that is the transpose of {@code m}.
	 * <p>
	 * If {@code m} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@code Matrix33D} instance
	 * @return a {@code Matrix33D} instance that is the transpose of {@code m}
	 * @throws NullPointerException thrown if, and only if, {@code m} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Matrix33D transpose(final Matrix33D m) {
		return new Matrix33D(m.element11, m.element21, m.element31, m.element12, m.element22, m.element32, m.element13, m.element23, m.element33);
	}
}