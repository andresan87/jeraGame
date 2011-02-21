package br.com.jera.util;

public class CommonMath {

	public final static float PI = 3.141592654f;
	public final static float _2PI = PI * 2.0f;
	public final static float HALF_PI = PI / 2.0f;
	public final static float QUARTER_PI = HALF_PI / 2.0f;

	public static class Vector4 {
		public Vector4() {
			x = y = z = w = 0;
		}

		public Vector4(float x, float y, float z, float w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}

		public Vector4(Vector4 v) {
			x = v.x;
			y = v.y;
			z = v.z;
			w = v.w;
		}
		
		public Vector4 interpolate(Vector4 dest, float bias) {
			return new Vector4(
					x+(dest.x-x)*bias,
					y+(dest.y-y)*bias,
					z+(dest.z-z)*bias,
					w+(dest.w-w)*bias);
		}

		public float x, y, z, w;
	}

	public static class Vector3 {
		public Vector3() {
			x = y = z = 0;
		}

		public Vector3(Vector3 v) {
			x = v.x;
			y = v.y;
			z = v.z;
		}

		public Vector3(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Vector3(Vector2 v, float z) {
			this.x = v.x;
			this.y = v.y;
			this.z = z;
		}

		public float x, y, z;
	}

	public static class Vector2 {
		public Vector2() {
			x = y = 0;
		}

		public Vector2(Vector2 v) {
			x = v.x;
			y = v.y;
		}

		public Vector2(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public float length() {
			return (float) Math.sqrt(x * x + y * y);
		}

		public Vector2 normalize() {
			return (this.multiply(1.0f / length()));
		}

		public Vector2 add(Vector2 v) {
			return new Vector2(x + v.x, y + v.y);
		}

		public Vector2 sub(Vector2 v) {
			return new Vector2(x - v.x, y - v.y);
		}

		public Vector2 multiply(float v) {
			return new Vector2(x * v, y * v);
		}

		public Vector2 multiply(Vector2 v) {
			return new Vector2(x * v.x, y * v.y);
		}

		public float distance(Vector2 v) {
			return sub(v).length();
		}

		public float squaredDistance(Vector2 v) {
			final Vector2 r = sub(v);
			return r.x * r.x + r.y * r.y;
		}

		public float x, y;
	}

	public enum PRIMITIVE_TYPE {
		TRIANGLE_LIST, TRIANGLE_STRIP, TRIANGLE_FAN
		// TODO lines, points...
	}

	public static int computeNumTriangles(PRIMITIVE_TYPE type, int vertexCount) {
		switch (type) {
		case TRIANGLE_LIST:
			return vertexCount / 3;
		case TRIANGLE_STRIP:
		case TRIANGLE_FAN:
		default:
			return vertexCount - 2;
			// TODO lines, points...
		}
	}
	
	public static Vector2 resizeToMatchWidth(Vector2 originalSize, final float width) {
		return new Vector2(width, (originalSize.y/originalSize.x)*width);
	}

	public static Vector2 resizeToMatchHeight(Vector2 originalSize, final float height) {
		return new Vector2((originalSize.x/originalSize.y)*height, height);
	}

	public static boolean isPointInRect(final Vector2 pos, final Vector2 size, final Vector2 p) {
		return isPointInRect(pos, size, new Vector2(0, 0), p);
	}

	public static float radianToDegree(final float angle) {
		return (angle / PI) * 180.0f;
	}

	public static float degreeToRadian(final float angle) {
		return (angle / 180.0f) * PI;
	}

	public static boolean isPointInRect(final Vector2 pos, final Vector2 size, final Vector2 normalizedOrigin, final Vector2 p) {
		final Vector2 min = new Vector2(pos.sub(size.multiply(normalizedOrigin)));
		final Vector2 max = new Vector2(pos.add(size.multiply(new Vector2(1, 1).sub(normalizedOrigin))));
		return !(p.x < min.x || p.y < min.y || p.x > max.x || p.y > max.y);
	}

	public static float getAngle(Vector2 v) {
		final float r = (float) Math.atan2(v.x, v.y);
		return (r < 0.0f) ? r + (2.0f * PI) : r;
	}

	public static Vector2 computeSpriteOriginOffset(Vector2 bitmapSize, Vector2 normalizedOrigin) {
		return new Vector2(-bitmapSize.x * normalizedOrigin.x, -bitmapSize.y * normalizedOrigin.y);
	}

	public static class Vertex {
		public Vertex(Vector3 pos, Vector4 color) {
			this.pos = pos;
			this.color = color;
		}

		public Vertex(Vector3 pos, Vector2 texCoord, Vector4 color) {
			this.pos = pos;
			this.texCoord = texCoord;
			this.color = color;
		}

		public Vertex(Vector3 pos, Vector2 texCoord) {
			this.pos = pos;
			this.texCoord = texCoord;
		}

		public Vector3 pos;
		public Vector4 color;
		public Vector2 texCoord;
	}

	public static class GridCutter {

		public Rectangle2D[] rects;

		public GridCutter(int columns, int rows) {
			rects = new Rectangle2D[rows * columns];

			float fRows = (float) rows;
			float fColumns = (float) columns;

			int index = 0;
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < columns; x++) {
					rects[index] = new Rectangle2D();
					rects[index].pos.x = 1 / fColumns * (float) x;
					rects[index].pos.y = 1 / fRows * (float) y;
					rects[index].size.x = 1 / fColumns;
					rects[index].size.y = 1 / fRows;

					index++;
				}
			}
		}
	}

	public static class Rectangle2D {
		public Rectangle2D() {
			this.pos = new Vector2();
			this.size = new Vector2();
		}

		public Rectangle2D(Vector2 pos, Vector2 size) {
			this.pos = pos;
			this.size = size;
		}

		public Rectangle2D(Vector2 pos, Vector2 size, Vector2 normalizedOrigin) {
			this.pos = pos.sub(size.multiply(normalizedOrigin));
			this.size = size;
		}

		public Vector2 getMin() {
			return new Vector2(pos);
		}

		public Vector2 getMax() {
			return pos.add(size);
		}

		public Vector2 getCenter() {
			return pos.add(size.multiply(0.5f));
		}

		public boolean isPointInRectangle(Vector2 p) {
			return isPointInRect(pos, size, p);
		}

		public boolean isIntersecting(final Rectangle2D rect) {
			Vector2 thisMax = getMax();
			Vector2 thisMin = getMin();
			Vector2 otherMax = rect.getMax();
			Vector2 otherMin = rect.getMin();
			return !(thisMax.x < otherMin.x || thisMax.y < otherMin.y || thisMin.x > otherMax.x || thisMin.y > otherMax.y);
		}

		public Vector2 pos;
		public Vector2 size;
	}
}
