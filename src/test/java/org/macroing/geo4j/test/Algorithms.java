/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
package org.macroing.geo4j.test;

public final class Algorithms {
	private Algorithms() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	Source: https://www.baeldung.com/java-convert-latitude-longitude
	public static void main(final String[] args) {
		final MercatorProjection mercatorProjection = new EllipticalMercatorProjection();
//		final MercatorProjection mercatorProjection = new SphericalMercatorProjection();
		
		final double x = mercatorProjection.projectX(22.0D);
		final double y = mercatorProjection.projectY(44.0D);
		
		System.out.println(x);
		System.out.println(y);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final class EllipticalMercatorProjection implements MercatorProjection {
		public EllipticalMercatorProjection() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public double projectX(final double degrees) {
			return Math.toRadians(degrees) * RADIUS_MAJOR;
		}
		
		@Override
		public double projectY(final double degrees) {
			final double degreesSaturated = Math.min(Math.max(degrees, -89.5D), 89.5D);
			
			final double earthDimensionalRateNormalized = 1.0D - Math.pow(RADIUS_MINOR / RADIUS_MAJOR, 2.0D);
			
			final double degreesOnEarthProjectedA = Math.sqrt(earthDimensionalRateNormalized) * Math.sin(Math.toRadians(degreesSaturated));
			final double degreesOnEarthProjectedB = Math.pow(((1.0D - degreesOnEarthProjectedA) / (1.0D + degreesOnEarthProjectedA)), 0.5D * Math.sqrt(earthDimensionalRateNormalized));
			final double degreesOnEarthProjectedNormalized = Math.tan(0.5D * ((Math.PI * 0.5D) - Math.toRadians(degreesSaturated))) / degreesOnEarthProjectedB;
			
			return -RADIUS_MAJOR * Math.log(degreesOnEarthProjectedNormalized);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static interface MercatorProjection {
		double RADIUS_MAJOR = 6378137.0000D;
		double RADIUS_MINOR = 6356752.3142D;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		double projectX(final double degrees);
		
		double projectY(final double degrees);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final class SphericalMercatorProjection implements MercatorProjection {
		public SphericalMercatorProjection() {
			
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public double projectX(final double degrees) {
			return Math.toRadians(degrees) * RADIUS_MAJOR;
		}
		
		@Override
		public double projectY(final double degrees) {
			return Math.log(Math.tan(Math.PI / 4.0D + Math.toRadians(degrees) / 2.0D)) * RADIUS_MAJOR;
		}
	}
}